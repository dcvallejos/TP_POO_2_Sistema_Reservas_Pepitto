package com.pepito.reservas.traslado;

import com.pepito.reservas.reserva.Reserva;

/**
 * MetodoTrasladoEstandar - ConcreteStrategy
 * El costo lo estima la api/servicio PlataformaTransporte según la cantidad de pasajeros y la dirección de destino.
 */

public class MetodoTrasladoEstandar extends   MetodoTraslado{
    private final PlataformaTransporte plataformaTransporte ;
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
