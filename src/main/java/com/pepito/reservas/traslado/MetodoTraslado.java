package com.pepito.reservas.traslado;

import com.pepito.reservas.reserva.Reserva;

public abstract class MetodoTraslado {

    public abstract Double costo(Reserva reserva);

    public void verificarDisponibilidad(){}
}
//