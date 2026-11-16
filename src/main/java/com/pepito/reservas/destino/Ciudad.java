package com.pepito.reservas.destino;

/**
 * Ciudad
 * Sección Leaf del patrón Composite
 *
 * Descripción:
 *   Último nivel geográfico. No contiene a otros lugares: solo se contiene a sí misma.
 *   El tipo es siempre CIUDAD, por eso no se recibe en el constructor.
 */

public class Ciudad extends LugarGeografico{


    public Ciudad(String nombre) {
        super(TipoGeografico.CIUDAD, nombre);
    }

    @Override
    public Boolean contiene(LugarGeografico otro) {
        return this == otro;
    }

    @Override
    public Boolean puedeContener(LugarGeografico otro) {
        return false;
    }

    @Override
    public void removerDestino(LugarGeografico lugar, Boolean override) {
        throw new DestinoException.InexistenciaDestino(lugar);
    }
}
