package com.pepito.reservas.reportes;

public class ReporteTextoPlanoVisitor  implements  ReporteVisitor {
    @Override
    public String visitorServiciosMasReservados(ServicioMasReservado servicio) {
        return "\"=== REPORTE DE SERVICIOS MÁS RESERVADOS (TEXTO) ===\\n\" +\n" +
                "  \"1. Excursión Guiada - Ventas: 150 - Precio Promedio: $1200.00\\n\"";
    }

    @Override
    public String visitorOcupacionYCupoDisponible(OcupacionYCupo ocupacion) {
        return "  \"=== REPORTE DE OCUPACIÓN Y CUPO (TEXTO) ===\\n\" +\n" +
                " \"Categoría Hotel: Ocupados 80 / Libres 20\\n\";";
    }

    @Override
    public String visitorExposicionDeProvedor(ExposicionDeProvedor exposicion) {
        return "\"=== REPORTE DE EXPOSICIÓN DE PROVEEDOR (TEXTO) ===\\n\" +\n" +
                " \"Operador A: 45% del catálogo\\n\";";
    }
}
