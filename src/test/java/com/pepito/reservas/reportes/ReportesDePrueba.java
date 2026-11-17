package com.pepito.reservas.reportes;

import com.pepito.reservas.catalogo.servicio.Categoria;
import com.pepito.reservas.catalogo.servicio.Disponibilidad;
import com.pepito.reservas.catalogo.servicio.Periodo;
import com.pepito.reservas.catalogo.servicio.Servicio;
import com.pepito.reservas.cliente.Cliente;
import com.pepito.reservas.destino.Ciudad;
import com.pepito.reservas.documento.VoucherTarjeta;
import com.pepito.reservas.metodoDePago.MetodoDePago;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/* Objetos reales del catálogo y medios de pago simulados, compartidos por los tests de reportes.*/

final class ReportesDePrueba {

    static final Ciudad MADRID = new Ciudad("Madrid");
    static final Periodo DICIEMBRE = new Periodo(LocalDate.of(2026, 12, 1), LocalDate.of(2026, 12, 31));
    static final Periodo DEL_10_AL_12 = new Periodo(LocalDate.of(2026, 12, 10), LocalDate.of(2026, 12, 12));

    private ReportesDePrueba() {
    }

    static Servicio servicio(String nombre, String proveedor, Categoria categoria, double precio, int cupos) {
        return new Servicio(nombre, "descripción", 0.0, new Disponibilidad(DICIEMBRE, cupos), "COD-" + nombre,
                proveedor, categoria, precio, MADRID);
    }

    static Cliente cliente() {
        return new Cliente("Ana", "20-12345678-9", "Calle 123", "ana@mail.com");
    }

    static MetodoDePago pagoAprobado() {
        MetodoDePago pago = mock(MetodoDePago.class);
        when(pago.procesarPago(anyDouble())).thenAnswer(inv -> new VoucherTarjeta("TX-1", inv.getArgument(0)));
        return pago;
    }
}