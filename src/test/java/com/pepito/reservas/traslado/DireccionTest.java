package com.pepito.reservas.traslado;

import com.pepito.reservas.destino.Destino;
import com.pepito.reservas.destino.TipoGeografico;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

/*
 * el traslado estándar necesita la dirección de destino para estimar el costo.
 */
class DireccionTest {

    @Test
    void la_direccion_expone_calle_numero_y_destino() {
        Destino argentina = new Destino(TipoGeografico.PAIS, "Argentina");

        Direccion direccion = new Direccion("Roque Sáenz Peña", 352, argentina);

        assertEquals("Roque Sáenz Peña", direccion.getCalle());
        assertEquals(352, direccion.getNro());
        assertSame(argentina, direccion.getDestino());
    }
}
