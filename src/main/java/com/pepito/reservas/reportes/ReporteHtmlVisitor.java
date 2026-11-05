package com.pepito.reservas.reportes;

public class ReporteHtmlVisitor  implements ReporteVisitor{

    @Override
    public String visitorServiciosMasReservados(ServicioMasReservado servicio) {
        return "\"<h1>Servicios Más Reservados</h1>\" +\n" +
                "  \"<table><tr><th>Servicio</th><th>Ventas</th></tr>\" +\n" +
                "  \"<tr><td>Excursión Guiada</td><td>150</td></tr></table>\";";
    }

    @Override
    public String visitorOcupacionYCupoDisponible(OcupacionYCupo ocupacion) {
        return "\"<h1>Ocupación y Cupo</h1><p>Hotel: 80% ocupado</p>\"";
    }

    @Override
    public String visitorExposicionDeProvedor(ExposicionDeProvedor exposicion) {
        return " \"<h1>Exposición por Proveedor</h1><p>Operador A: 45%</p>\"";
    }
}
