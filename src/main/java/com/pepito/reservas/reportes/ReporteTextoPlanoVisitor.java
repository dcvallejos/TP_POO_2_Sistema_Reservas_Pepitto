package com.pepito.reservas.reportes;

import java.util.Locale;

/**
 * ReporteTextoPlanoVisitor
 * Rol ConcreteVisitor del patrón Visitor: presenta cada reporte como texto plano, una línea por fila.
 */

public class ReporteTextoPlanoVisitor implements ReporteVisitor {

    @Override
    public String visitarServiciosMasReservados(ServiciosMasReservados reporte) {
        StringBuilder texto = new StringBuilder("=== Servicios más reservados ===\n");
        reporte.getFilas().forEach(fila -> texto.append(String.format(Locale.ROOT,
                "%s - %d unidades - precio promedio $%.2f\n", fila.nombre(), fila.unidadesVendidas(), fila.precioPromedio())));
        return texto.toString();
    }

    @Override
    public String visitarOcupacionYCupo(OcupacionYCupo reporte) {
        StringBuilder texto = new StringBuilder("=== Ocupación y cupo disponible ===\n");
        reporte.getFilas().forEach(fila -> texto.append(String.format(Locale.ROOT,
                "%s - totales %d - reservados %d - libres %d\n",
                fila.categoria(), fila.cuposTotales(), fila.reservados(), fila.libres())));
        return texto.toString();
    }

    @Override
    public String visitarExposicionPorProveedor(ExposicionPorProveedor reporte) {
        StringBuilder texto = new StringBuilder("=== Exposición por proveedor ===\n");
        reporte.getFilas().forEach(fila -> texto.append(String.format(Locale.ROOT,
                "%s - %d servicios - %.2f%% del catálogo\n", fila.proveedor(), fila.cantidadServicios(), fila.porcentaje())));
        return texto.toString();
    }
}