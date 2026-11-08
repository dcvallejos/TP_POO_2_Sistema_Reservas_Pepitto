package com.pepito.reservas.reportes;

public class ServicioMasReservado implements ReporteElemento  {
    @Override
    public String aceptar(ReporteVisitor visitor) {
        return visitor.visitorServiciosMasReservados(this);
    }
}
