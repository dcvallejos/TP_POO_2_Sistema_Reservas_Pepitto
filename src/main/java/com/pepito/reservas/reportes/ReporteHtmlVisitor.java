package com.pepito.reservas.reportes;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * ReporteHtmlVisitor
 * Rol ConcreteVisitor del patrón Visitor: presenta cada reporte como un título y una tabla HTML.
 *
 * Decisión:
 *  Los textos se escapan (&, <, >) para que un nombre no se interprete como HTML.
 */

public class ReporteHtmlVisitor implements ReporteVisitor {

    @Override
    public String visitarServiciosMasReservados(ServiciosMasReservados reporte) {
        return tabla("Servicios más reservados", List.of("Servicio", "Unidades vendidas", "Precio promedio"),
                reporte.getFilas().stream()
                        .map(fila -> List.of(escapar(fila.nombre()), String.valueOf(fila.unidadesVendidas()),
                                String.format(Locale.ROOT, "$%.2f", fila.precioPromedio())))
                        .toList());
    }

    @Override
    public String visitarOcupacionYCupo(OcupacionYCupo reporte) {
        return tabla("Ocupación y cupo disponible", List.of("Categoría", "Cupos totales", "Reservados", "Libres"),
                reporte.getFilas().stream()
                        .map(fila -> List.of(fila.categoria().name(), String.valueOf(fila.cuposTotales()),
                                String.valueOf(fila.reservados()), String.valueOf(fila.libres())))
                        .toList());
    }

    @Override
    public String visitarExposicionPorProveedor(ExposicionPorProveedor reporte) {
        return tabla("Exposición por proveedor", List.of("Proveedor", "Servicios", "Porcentaje"),
                reporte.getFilas().stream()
                        .map(fila -> List.of(escapar(fila.proveedor()), String.valueOf(fila.cantidadServicios()),
                                String.format(Locale.ROOT, "%.2f%%", fila.porcentaje())))
                        .toList());
    }

    private static String tabla(String titulo, List<String> encabezados, List<List<String>> filas) {
        return "<h1>" + titulo + "</h1>\n<table>\n"
                + fila("th", encabezados)
                + filas.stream().map(celdas -> fila("td", celdas)).collect(Collectors.joining())
                + "</table>\n";
    }

    private static String fila(String etiqueta, List<String> celdas) {
        return "<tr>" + celdas.stream()
                .map(celda -> "<" + etiqueta + ">" + celda + "</" + etiqueta + ">")
                .collect(Collectors.joining()) + "</tr>\n";
    }

    private static String escapar(String texto) {
        return texto.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
    }
}