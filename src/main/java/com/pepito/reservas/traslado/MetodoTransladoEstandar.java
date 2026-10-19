package com.pepito.reservas.traslado;

import com.pepito.reservas.reserva.Reserva;

public class MetodoTransladoEstandar implements  MetodoTraslado{
    private  PlataformaTransporte plataformaTransporte ;
    private  int cantidadDePasajeros ;
    private  Direccion direccionDestino;

    public MetodoTransladoEstandar(int cantidadDePasajeros , Direccion direccionDestino){
        this.cantidadDePasajeros = cantidadDePasajeros;
        this.direccionDestino=direccionDestino;

    }


    @Override
    public Double costo(Reserva reserva) {
        return plataformaTransporte.estimarTranslado(cantidadDePasajeros,direccionDestino);
    }
}
