package com.pepito.reservas.documento;

public class CuponDescuento extends Documento {

    Double descuento;

    public CuponDescuento(Double descuento) {
        super();
        this.descuento = descuento;
    }

    public Double getDescuento() {
        return descuento;
    }

}
