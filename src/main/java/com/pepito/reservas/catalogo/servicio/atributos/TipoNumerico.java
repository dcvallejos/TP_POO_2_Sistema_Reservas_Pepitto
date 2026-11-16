package com.pepito.reservas.catalogo.servicio.atributos;

/** TipoNumerico - ConcreteStrategy. Acepta cualquier Number (Integer, Double, etc.). */

public class TipoNumerico implements TipoDinamico {

    public TipoNumerico(){}

    @Override
    public Boolean acepta(Object valor){
        return valor instanceof Number;
    }
}
