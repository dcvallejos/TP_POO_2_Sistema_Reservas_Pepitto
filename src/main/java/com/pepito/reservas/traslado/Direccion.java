package com.pepito.reservas.traslado;

import com.pepito.reservas.destino.Destino;

public class Direccion {
    private final String calle;
    private final int nro ;
    private final Destino destino ;

    public Direccion(String calle , int nro , Destino destino  ){
        this.calle = calle ;
        this. nro= nro;
        this.destino = destino ;
    }

    public int getNro() {
        return nro;
    }
    
    public String getCalle(){
        return calle;
    }
    
    public Destino getDestino() {
    	return destino;
    }
}

