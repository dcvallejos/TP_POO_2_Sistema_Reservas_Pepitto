package com.pepito.reservas.traslado;

import com.pepito.reservas.reserva.Reserva;

public class MetodoPuntoDeEncuentro extends   MetodoTraslado{
    private Boolean hayCupoDeGuias;

    public MetodoPuntoDeEncuentro(Boolean hayCupoDeGuias){
        this.hayCupoDeGuias=hayCupoDeGuias;
    }

    @Override
    public Double costo(Reserva reserva) {
        if (this.getHayCupoDeGuias()){
            return 0.0;
        } else {
            throw new RuntimeException("No hay disponibilidad de guías en el punto de encuentro.");
        }
    }

    public Boolean getHayCupoDeGuias(){
        return hayCupoDeGuias ;
    }


}

