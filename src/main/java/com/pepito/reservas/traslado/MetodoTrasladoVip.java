package com.pepito.reservas.traslado;

import com.pepito.reservas.reserva.Reserva;

public class MetodoTrasladoVip  extends   MetodoTraslado{
    private final TrasladoVip trasladoVip;


    public MetodoTrasladoVip(TrasladoVip trasladoVip){
        this.trasladoVip=trasladoVip;

    }

    @Override
    public Double costo(Reserva reserva) {
        return (double) trasladoVip.calcularCosto((float) reserva.getTotalServicios() );
    }

}
