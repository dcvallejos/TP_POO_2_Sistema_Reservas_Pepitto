package com.pepito.reservas.reportes;

public class ReporteCSVVisitor implements  ReporteVisitor{
    @Override
    public String visitorServiciosMasReservados(ServicioMasReservado servicio) {
        return "\"servicio,reservas_vendidas,precio_promedio\\n\" +\n" +
                " \"Excursion Guiada,150,1200.00\\n\"";
    }

    @Override
    public String visitorOcupacionYCupoDisponible(OcupacionYCupo ocupacion) {
        return "\"categoria,cupos_totales,reservados,libres\\n\" +\n" +
                "\"Hotel,100,80,20\\n\";";
    }

    @Override
    public String visitorExposicionDeProvedor(ExposicionDeProvedor exposicion) {
        return "\"operador,porcentaje_catalogo\\n\" +\n" +
                " \"Operador A,45%\\n\";";
    }
}
