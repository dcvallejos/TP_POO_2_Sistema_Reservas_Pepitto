package com.pepito.reservas.documento;

public class CuponDescuento extends Documento {

    Double descuento;
    static final Double DESCUENTO_FIJO = 0.05;

    public CuponDescuento() {
        super();
        this.descuento = DESCUENTO_FIJO;
    }

    public Double getDescuento() {
        return descuento;
    }

}
