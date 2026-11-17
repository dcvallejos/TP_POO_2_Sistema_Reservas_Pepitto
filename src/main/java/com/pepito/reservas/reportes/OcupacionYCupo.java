package com.pepito.reservas.reportes;

import com.pepito.reservas.catalogo.Catalogo;
import com.pepito.reservas.catalogo.servicio.Categoria;
import com.pepito.reservas.catalogo.servicio.Periodo;
import com.pepito.reservas.catalogo.servicio.Servicio;

import java.util.Arrays;
import java.util.List;

/**
 * OcupacionYCupo
 * Rol ConcreteElement del patrón Visitor.
 *
 * Descripción:
 *  Cupos totales, reservados y libres por categoría, sumados en todos los días del período.
 *
 * Decisión:
 *  Se cuenta sobre los servicios hoja del catálogo (los paquetes se abren): el cupo vive en los servicios, y un
 *  servicio que está suelto y también dentro de un paquete se cuenta una sola vez.
 */

public class OcupacionYCupo implements ReporteElemento {

    private final List<FilaOcupacion> filas;

    public OcupacionYCupo(Catalogo catalogo, Periodo periodo) {
        this.filas = calcular(catalogo, periodo);
    }

    @Override
    public String aceptar(ReporteVisitor visitor) {
        return visitor.visitarOcupacionYCupo(this);
    }

    public List<FilaOcupacion> getFilas() {
        return this.filas;
    }

    private static List<FilaOcupacion> calcular(Catalogo catalogo, Periodo periodo) {
        List<Servicio> servicios = catalogo.getCatalogo().stream()
                .flatMap(item -> item.serviciosHoja().stream())
                .distinct()
                .toList();

        return Arrays.stream(Categoria.values())
                .map(categoria -> servicios.stream().filter(servicio -> servicio.getCategoria() == categoria).toList())
                .filter(deLaCategoria -> !deLaCategoria.isEmpty())
                .map(deLaCategoria -> fila(deLaCategoria, periodo))
                .toList();
    }

    private static FilaOcupacion fila(List<Servicio> servicios, Periodo periodo) {
        int totales = servicios.stream()
                .mapToInt(servicio -> periodo.dias().mapToInt(dia -> servicio.disponibilidad().capacidad(dia)).sum())
                .sum();
        int reservados = servicios.stream()
                .mapToInt(servicio -> periodo.dias().mapToInt(dia -> servicio.disponibilidad().reservados(dia)).sum())
                .sum();
        return new FilaOcupacion(servicios.get(0).getCategoria(), totales, reservados, totales - reservados);
    }
}