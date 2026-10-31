package com.pepito.reservas.metodoDePago;

public class ComprobantePago {
    private String codigoTransaccion;
    private double monto;

    public ComprobantePago(){

    }

    public ComprobantePago(String codigoTransaccion, double monto){
        this.codigoTransaccion = codigoTransaccion;
        this.monto = monto;
    }

    public String getCodigoTransaccion(){
        return codigoTransaccion;
    }

    public double getMonto(){
        return monto;
    }
}