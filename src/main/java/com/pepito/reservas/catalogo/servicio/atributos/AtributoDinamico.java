package com.pepito.reservas.catalogo.servicio.atributos;

import com.pepito.reservas.catalogo.servicio.Validador;

/**
 * AtributoDinamico
 *  Es la sección Contexto del patrón Strategy (la estrategia es el TipoDinamico)
 *
 * Descipcion:
 *  Representa un atributo opcional, que se va agregando a medida que se va necesitando. No hay una cantidad definida
 *  ni limite alguno. Pueden ser, por ejemplo, "CapacidadMaxima", "PoliticaMascotas", etc. Tienen consistencia
 *  interna y una validación definida, según el tipo de atributo que sea
 *
 * Atributos:
 *  - nombre:           (String) Define el nombre del atributo
 *  - obligatorio:      (Boolean) Define la obligatoriedad que representa la existencia o no de valor
 *  - tipo:             (TipoDinamico) Es el tipo declarado del valor. Esto se hace porque puede haber valores nuevos
 *  hechos en el futuro.
 *  - valor:            (Object) Es el valor asignado. Nullable.
 *
 * Validación:
 *  - obligatorio:      Debe tener valor si el tipo lo acepta
 *  - no obligatorio:   El valor puede ser null
 * */


public class AtributoDinamico implements Validador {

    String nombre;
    Boolean obligatorio;
    TipoDinamico tipo;
    Object valor;

    public AtributoDinamico(
            String nombre,
            Boolean obligatorio,
            TipoDinamico tipo,
            Object valor)
    {
        this.nombre = nombre;
        this.obligatorio = obligatorio;
        this.tipo = tipo;
        this.valor = valor;
    }

    @Override
    public Boolean validate() {
        if (valor != null){
            return tipo.acepta(valor);
        }
        else{
            return !obligatorio;
        }
    }
}
