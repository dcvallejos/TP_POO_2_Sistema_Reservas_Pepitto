package com.pepito.reservas.catalogo.servicio.atributos;

/** TipoBooleano - ConcreteStrategy. Acepta Boolean. */
public class TipoBooleano implements  TipoDinamico {
    public TipoBooleano(){}

    @Override
    public Boolean acepta(Object valor){
        return valor instanceof Boolean;
    }

}
