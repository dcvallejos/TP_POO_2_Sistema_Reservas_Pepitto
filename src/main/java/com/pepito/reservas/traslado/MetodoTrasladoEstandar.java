package com.pepito.reservas.traslado;

import com.pepito.reservas.reserva.Reserva;

public class MetodoTrasladoEstandar extends   MetodoTraslado{
    private  PlataformaTransporte plataformaTransporte ;
    private final Direccion direccionDestino;

    public MetodoTrasladoEstandar(PlataformaTransporte plataformaTransporte,Direccion direccionDestino){
                this.direccionDestino=direccionDestino;
                this.plataformaTransporte=plataformaTransporte;

    }


    @Override
    public Double costo(Reserva reserva) {
        return (double) plataformaTransporte.estimarTraslado(reserva.getCantidadPasajeros(),direccionDestino);
    }
}
