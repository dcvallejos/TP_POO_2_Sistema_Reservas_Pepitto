package com.pepito.reservas.metodoDePago;

import com.pepito.reservas.reserva.Reserva;

public  abstract class MetodoDePago {

    public MetodoDePago(){

    }
// template method
    public final ComprobantesDePago procesarPago(Reserva reserva ) { // métodoTemplate
        this.validarDatos();
        this.reservarFondos();
        this.ejecutarTransaccion();
        return this.notificarResultado(reserva);
    }

    public abstract void  validarDatos() ;
    public abstract void reservarFondos() ;
    public  abstract  void ejecutarTransaccion();
    public  abstract ComprobantesDePago notificarResultado( Reserva reserva);
}
