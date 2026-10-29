package com.pepito.reservas.traslado;

import com.pepito.reservas.reserva.Reserva;

public class MetodoPuntoDeEncuentro extends   MetodoTraslado{
    private Boolean verificarDisponibilidad;

    public MetodoPuntoDeEncuentro(Boolean verificarDisponibilidad){
        this.verificarDisponibilidad = verificarDisponibilidad;
    }

    @Override
    public Double costo(Reserva reserva) {
            return 0.0;
    }

    @Override
    public void verificarDisponibilidad(){
        if (! this. verificarDisponibilidad ){
         throw new RuntimeException("No hay disponibilidad de guías en el punto de encuentro.");
        }
    }






}

