package com.pepito.reservas.traslado;

import com.pepito.reservas.reserva.Reserva;


/**
 * MetodoTrasladoVip - ConcreteStrategy.
 * El costo lo calcula la api/servicio TrasladoVip sobre el valor de la reserva. Se le pasa el total de SERVICIOS:
 * el costo del traslado no puede depender de sí mismo.
 */

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
