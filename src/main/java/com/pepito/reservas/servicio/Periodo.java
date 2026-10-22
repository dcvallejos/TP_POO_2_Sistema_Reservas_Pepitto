package com.pepito.reservas.servicio;

import java.time.LocalDate;

public class Periodo {

    LocalDate fechaDesde;
    LocalDate fechaHasta;


    public Periodo(LocalDate fechaDesde, LocalDate fechaHasta){
        this.fechaDesde = fechaDesde;
        this.fechaHasta = fechaHasta;
    }

    public LocalDate fechaDesde() {
        return fechaDesde;
    }

    public LocalDate fechaHasta() {
        return fechaHasta;
    }


}
