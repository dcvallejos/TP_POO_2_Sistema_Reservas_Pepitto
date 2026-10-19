package com.pepito.reservas.metodoDePago;

public  abstract class MetodoDePago {

    public MetodoDePago(){

    }
// template method
    public void procesarPago() { // métodoTemplate
        this.validarDatos();
        this.reservarFondos();
        this.ejecutarTransaccion();
        this.notificarResultado();
    }

    public abstract void  validarDatos() ;
    public abstract void reservarFondos() ;
    public  abstract  void ejecutarTransaccion();
    public  abstract void notificarResultado();
}
