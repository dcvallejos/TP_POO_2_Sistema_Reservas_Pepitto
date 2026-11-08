package com.pepito.reservas.reportes;

public interface ReporteVisitor {

    public String visitorServiciosMasReservados (ServicioMasReservado servicio ) ;
    public String visitorOcupacionYCupoDisponible (OcupacionYCupo ocupacion );

    public String visitorExposicionDeProvedor(ExposicionDeProvedor exposicion );


}

