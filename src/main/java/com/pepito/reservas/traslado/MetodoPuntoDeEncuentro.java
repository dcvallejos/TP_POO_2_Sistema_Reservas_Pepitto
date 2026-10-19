package com.pepito.reservas.traslado;

import com.pepito.reservas.reserva.Reserva;

public class MetodoPuntoDeEncuentro implements  MetodoTraslado{

    public MetodoPuntoDeEncuentro(){} // constructor vacio porque no tiene atributos

    @Override
    public Double costo(Reserva reserva) {
        return 0.0 ;
    }
}
