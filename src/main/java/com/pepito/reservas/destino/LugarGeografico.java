package com.pepito.reservas.destino;

/**
 * LugarGeografico
 * Sección Component del patrón Composite
 *
 * Descripción:
 *  Representa el lugar geográfico donde se ubica un ItemCatalogo: Tiene una jerarquía: Continente, Pais y Ciudad.
 *  Esto permite preguntar si un lugar esta contenido en otro en cualquier nivel y tambien manteniendo la invarianza
 *  de inventar lugares (un continente no puede estar dentro de un continente)
 *
 * Atributos:
 *  - tipo:         (TipoGeografico) Nivel jerárquico del lugar.
 *  - nombre:       (String) Nombre del lugar
 *
 * Decisión:
 *  removerDestino es abstracto y polimórfico, haciendo que cada nivel resuelva la baja a su manera.
 *
 * */


public abstract class LugarGeografico {

    TipoGeografico tipo;
    String nombre;

    public LugarGeografico(TipoGeografico tipo, String nombre) {
        this.tipo = tipo;
        this.nombre = nombre;
    }

    public String nombre(){return nombre;}
    public TipoGeografico tipo(){return tipo;}
    public abstract Boolean contiene(LugarGeografico otro);
    public abstract Boolean puedeContener(LugarGeografico otro);
    public abstract void removerDestino (LugarGeografico lugar, Boolean override);
}
