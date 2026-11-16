package com.pepito.reservas.documento;

/**
 * CuponDescuento
 *  Cupón que entrega el Club de Viajeros al cancelar. El porcentaje lo decide quien lo genera (Fidelizacion), no el
 *  cupón.
 */

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
