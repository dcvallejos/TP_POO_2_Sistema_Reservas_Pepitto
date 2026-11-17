package com.pepito.reservas.reportes;

import com.pepito.reservas.catalogo.ItemCatalogo;
import com.pepito.reservas.catalogo.servicio.Periodo;
import com.pepito.reservas.reserva.NombreEstado;
import com.pepito.reservas.reserva.RegistroReservas;
import com.pepito.reservas.reserva.Reserva;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * ServiciosMasReservados
 * Rol ConcreteElement del patrón Visitor.
 *
 * Descripción:
 *  Ordena los ítems del catálogo (servicios y paquetes) por unidades vendidas en un período, con el precio promedio cobrado.
 *
 * Decisiones:
 *  - Cuenta las reservas cobradas que no se cancelaron.
 *  - Una línea entra si su período se superpone con el del reporte. Cada pasajero es una unidad vendida.
 *  - El precio es el congelado en la línea de la reserva, que puede diferir del precio base por descuentos.
 */

public class ServiciosMasReservados implements ReporteElemento {

    private final List<FilaVentas> filas;

    public ServiciosMasReservados(RegistroReservas registro, Periodo periodo) {
        this.filas = calcular(registro, periodo);
    }

    @Override
    public String aceptar(ReporteVisitor visitor) {
        return visitor.visitarServiciosMasReservados(this);
    }

    public List<FilaVentas> getFilas() {
        return this.filas;
    }

    private record Venta(ItemCatalogo item, int unidades, double precio) {
    }

    private static List<FilaVentas> calcular(RegistroReservas registro, Periodo periodo) {
        Map<ItemCatalogo, List<Venta>> ventasPorItem = registro.getReservas().stream()
                .filter(ServiciosMasReservados::fueVendida)
                .flatMap(reserva -> reserva.getItems().stream()
                        .filter(linea -> seSuperponen(linea.getPeriodo(), periodo))
                        .map(linea -> new Venta(linea.getItem(), reserva.getCantidadPasajeros(), linea.precioFinal())))
                .collect(Collectors.groupingBy(Venta::item, LinkedHashMap::new, Collectors.toList()));

        return ventasPorItem.entrySet().stream()
                .map(entrada -> fila(entrada.getKey(), entrada.getValue()))
                .sorted(Comparator.comparingInt(FilaVentas::unidadesVendidas).reversed())
                .toList();
    }

    private static FilaVentas fila(ItemCatalogo item, List<Venta> ventas) {
        int unidades = ventas.stream().mapToInt(Venta::unidades).sum();
        double cobrado = ventas.stream().mapToDouble(venta -> venta.precio() * venta.unidades()).sum();
        return new FilaVentas(item.nombre(), unidades, cobrado / unidades);
    }

    private static boolean fueVendida(Reserva reserva) {
        return reserva.getCobro().isPresent() && reserva.getEstado().getNombre() != NombreEstado.CANCELADA;
    }

    private static boolean seSuperponen(Periodo uno, Periodo otro) {
        return !uno.fechaHasta().isBefore(otro.fechaDesde()) && !otro.fechaHasta().isBefore(uno.fechaDesde());
    }
}