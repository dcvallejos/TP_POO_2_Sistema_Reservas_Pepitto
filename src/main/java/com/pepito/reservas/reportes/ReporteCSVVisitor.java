package com.pepito.reservas.reportes;

import java.util.Locale;

/**
 * ReporteCSVVisitor
 * Rol ConcreteVisitor del patrón Visitor: presenta cada reporte como CSV, con una línea de encabezado.
 *
 * Decisión:
 *  Los textos van entre comillas (y las comillas internas se duplican) para que una coma en un nombre no rompa
 *  las columnas.
 */

public class ReporteCSVVisitor implements ReporteVisitor {

    @Override
    public String visitarServiciosMasReservados(ServiciosMasReservados reporte) {
        StringBuilder csv = new StringBuilder("servicio,unidades_vendidas,precio_promedio\n");
        reporte.getFilas().forEach(fila -> csv.append(String.format(Locale.ROOT,
                "%s,%d,%.2f\n", texto(fila.nombre()), fila.unidadesVendidas(), fila.precioPromedio())));
        return csv.toString();
    }

    @Override
    public String visitarOcupacionYCupo(OcupacionYCupo reporte) {
        StringBuilder csv = new StringBuilder("categoria,cupos_totales,reservados,libres\n");
        reporte.getFilas().forEach(fila -> csv.append(String.format(Locale.ROOT,
                "%s,%d,%d,%d\n", fila.categoria(), fila.cuposTotales(), fila.reservados(), fila.libres())));
        return csv.toString();
    }

    @Override
    public String visitarExposicionPorProveedor(ExposicionPorProveedor reporte) {
        StringBuilder csv = new StringBuilder("proveedor,cantidad_servicios,porcentaje\n");
        reporte.getFilas().forEach(fila -> csv.append(String.format(Locale.ROOT,
                "%s,%d,%.2f\n", texto(fila.proveedor()), fila.cantidadServicios(), fila.porcentaje())));
        return csv.toString();
    }

    private static String texto(String valor) {
        return "\"" + valor.replace("\"", "\"\"") + "\"";
    }
}