package com.pepito.reservas.traslado;

import com.pepito.reservas.reserva.Reserva;

/*
* Se implementó una interfaz DisponibilidadGuias que retorna un metodo booleano de disponibilidad de agentes de guia
* Esto se realizó dado que la unica forma de hacerlo era inyectando sino un booleano en el constructor
* lo que hacia que siempre estuviese fijado en un valor en particular
* */
public class MetodoPuntoDeEncuentro extends   MetodoTraslado{
    private final DisponibilidadGuias disponibilidadGuias;

    public MetodoPuntoDeEncuentro(DisponibilidadGuias disponibilidadGuias){
        this.disponibilidadGuias = disponibilidadGuias;
    }

    @Override
    public Double costo(Reserva reserva) {
            return 0.0;
    }

    @Override
    public void verificarDisponibilidad(){
        if (! this.disponibilidadGuias.guiasDisponibles() ){
         throw new TrasladoNoDisponibleException("No hay disponibilidad de guías en el punto de encuentro.");
        }
    }






}

