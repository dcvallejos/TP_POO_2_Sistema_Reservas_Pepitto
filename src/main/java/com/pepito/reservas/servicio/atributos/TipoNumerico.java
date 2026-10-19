package com.pepito.reservas.servicio.atributos;

public class TipoNumerico implements TipoDinamico {

    public TipoNumerico(){}

    @Override
    public Boolean acepta(Object valor){
        return valor instanceof Number;
    }
}
