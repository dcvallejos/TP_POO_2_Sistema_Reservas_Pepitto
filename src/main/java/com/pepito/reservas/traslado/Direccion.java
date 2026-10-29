package com.pepito.reservas.traslado;

public class Direccion {
    private String calle;
    private int nro ;
    private Destino destino ;

    public Direccion(String calle , int nro , Destino destino  ){
        this.calle = calle ;
        this. nro= nro;
        this.destino =destino ;
    }

    public int getNro() {
        return nro;
    }
    public String getCalle(){
        return calle;
    }
}

