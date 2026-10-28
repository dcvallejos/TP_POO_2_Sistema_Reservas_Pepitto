package com.pepito.reservas.traslado;

import com.pepito.reservas.reserva.Reserva;

public class MetodoTransladoEstandar extends   MetodoTraslado{
    private  PlataformaTransporte plataformaTransporte ;
    private final Direccion direccionDestino;

    public MetodoTransladoEstandar(PlataformaTransporte plataformaTransporte,Direccion direccionDestino){
                this.direccionDestino=direccionDestino;

    }


    @Override
    public Double costo(Reserva reserva) {
        return plataformaTransporte.estimarTranslado(reserva.getCantidadDePasajeros(),direccionDestino);
    }
}
