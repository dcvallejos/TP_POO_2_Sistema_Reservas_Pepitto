package com.pepito.reservas.metodoDePago;

public class Comprobante extends ComprobantePago {
    private String alias;
    private int  cbu ;

    public Comprobante(String alias , int cbu ){
        super();
        this.alias=alias;
        this.cbu=cbu ;
    }

    public Comprobante() {

    }

    public String getAlias(){
        return alias ;
    }
    public int getCbu(){
        return cbu;
    }
}
