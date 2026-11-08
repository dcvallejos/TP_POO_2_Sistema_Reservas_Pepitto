package com.pepito.reservas.reportes;

public class ExposicionDeProvedor implements ReporteElemento {
    @Override
    public String aceptar(ReporteVisitor visitor) {
        return  visitor.visitorExposicionDeProvedor(this);
    }
}
