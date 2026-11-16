package com.pepito.reservas.destino;

/**
 * TipoGeografico
 *
 * Descripción:
 *   Nivel jerárquico de un lugar. Cuanto menor el nivel, más abarcativo (CONTINENTE=1 > PAIS=2 > CIUDAD=3).
 *   mayorQue define qué puede contener a qué, así la regla vive en un único lugar.
 */

public enum TipoGeografico {
    CONTINENTE(1),
    PAIS(2),
    CIUDAD(3);

    private final int nivel;

    TipoGeografico(int nivel) {
        this.nivel = nivel;
    }

    // El nivel de la entidad es mas bajo cuanto mas contenedor sea: CONTINENTE > PAIS > CIUDAD
    public boolean mayorQue(TipoGeografico otro) {
        return this.nivel < otro.nivel;
    }
}