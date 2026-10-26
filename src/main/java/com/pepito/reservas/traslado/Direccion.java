package com.pepito.reservas.traslado;

public class Direccion {
    private String calle;
    private int nro ;

    public Direccion(String calle , int nro ){
        this.calle = calle ;
        this. nro= nro;
    }

    public int getNro() {
        return nro;
    }
    public String getCalle(){
        return calle;
    }
}

