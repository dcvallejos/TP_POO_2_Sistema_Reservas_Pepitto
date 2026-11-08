package com.pepito.reservas.reportes;

public class OcupacionYCupo implements ReporteElemento{

    @Override
    public String aceptar(ReporteVisitor visitor) {
        return visitor.visitorOcupacionYCupoDisponible(this);
    }
}
