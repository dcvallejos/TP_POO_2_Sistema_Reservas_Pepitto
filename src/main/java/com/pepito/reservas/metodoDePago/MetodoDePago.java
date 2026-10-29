package com.pepito.reservas.metodoDePago;

import com.pepito.reservas.reserva.Reserva;

public  abstract class MetodoDePago {

    public MetodoDePago(){

    }
// template method
    public final ComprobantePago procesarPago(Double monto  ) { // métodoTemplate
        this.validarDatos();
        this.reservarFondos();
        this.ejecutarTransaccion();
        return this.notificarResultado(monto );
    }

    protected abstract void  validarDatos() ;
    protected abstract void reservarFondos() ;
    protected   abstract  void ejecutarTransaccion();
    protected   ComprobantePago notificarResultado( Double monto ) {
        return new ComprobantePago();
    }
}
