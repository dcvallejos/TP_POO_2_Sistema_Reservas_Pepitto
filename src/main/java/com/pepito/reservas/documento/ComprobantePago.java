package com.pepito.reservas.documento;

/**
 * ComprobantePago
 * Comprobante emitido al completar un pago: código de transacción y monto.
 *
 * Decisión:
 *  Es una clase abstracta dado que cada medio emite el suyo.
 */

public abstract class ComprobantePago extends Documento {
    private final String codigoTransaccion;
    private final Double monto;

    public ComprobantePago(String codigoTransaccion, Double monto){
        super();
        this.codigoTransaccion = codigoTransaccion;
        this.monto = monto;
    }

    public String getCodigoTransaccion(){
        return codigoTransaccion;
    }

    public Double getMonto(){
        return monto;
    }
}