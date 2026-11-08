package com.pepito.reservas.destino;

import com.pepito.reservas.destino.Ciudad;
import com.pepito.reservas.destino.Destino;
import com.pepito.reservas.destino.DestinoException;
import com.pepito.reservas.destino.TipoGeografico;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class DestinoTest {

    Destino europa;
    Destino italia;
    Destino francia;
    Ciudad roma;

    private Destino destino(TipoGeografico tipo, String nombre) {
        return new Destino(tipo, nombre) {};
    }

    private Ciudad roma(String nombre) {
        return new Ciudad(nombre);
    }

    @BeforeEach
    void setUp() {
        europa = destino(TipoGeografico.CONTINENTE, "Europa");
        italia = destino(TipoGeografico.PAIS, "Italia");
        francia = destino(TipoGeografico.PAIS, "Francia");
        roma = roma("Roma");
    }

    @Test
    void un_destino_puede_contener_solo_niveles_mas_bajos() {
        assertTrue(europa.puedeContener(italia));
        assertTrue(europa.puedeContener(roma));
        assertTrue(italia.puedeContener(roma));

        assertFalse(italia.puedeContener(europa));
        assertFalse(italia.puedeContener(francia));
    }

    @Test
    void agrega_un_destino_compatible() {
        italia.agregarDestino(roma);

        assertTrue(italia.contiene(roma));
    }

    @Test
    void agregar_un_destino_incompatible_lanza_excepcion_y_no_lo_agrega() {
        DestinoException e = assertThrowsExactly(DestinoException.IncompatibilidadDestino.class,
                () -> italia.agregarDestino(europa));

        assertEquals("CONTINENTE no puede agregarse a un PAIS", e.getMessage());
        assertFalse(italia.contiene(europa));
    }

    @Test
    void un_destino_se_contiene_a_si_mismo() {
        assertTrue(italia.contiene(italia));
    }

    @Test
    void contiene_a_cualquier_profundidad_y_no_contiene_ajenos() {
        italia.agregarDestino(roma);
        europa.agregarDestino(italia);

        assertTrue(europa.contiene(roma));
        assertFalse(europa.contiene(francia));
        assertFalse(italia.contiene(europa));
    }

    @Test
    void remueve_un_hijo_directo() {
        italia.agregarDestino(roma);

        italia.removerDestino(roma,true);

        assertFalse(italia.contiene(roma));
    }

    @Test
    void remover_un_destino_ajeno_lanza_excepcion() {
        DestinoException e = assertThrowsExactly(DestinoException.InexistenciaDestino.class,
                () -> italia.removerDestino(roma,true));

        assertTrue(e.getMessage().contains("Roma"));
    }

    @Test
    void remover_un_destino_que_no_es_hijo_directo_lanza_excepcion() {
        italia.agregarDestino(roma);
        europa.agregarDestino(italia);

        assertThrowsExactly(DestinoException.ViolacionRango.class,
                () -> europa.removerDestino(roma,false));
        assertTrue(europa.contiene(roma)); // sigue donde estaba
    }

    @Test
    void remueve_un_destino_que_no_es_hijo_directo_con_override() {
        italia.agregarDestino(roma);
        europa.agregarDestino(italia);

        europa.removerDestino(roma,true);

        assertFalse(europa.contiene(roma));
        assertFalse(italia.contiene(roma)); // se quitó de su padre directo
        assertTrue(europa.contiene(italia)); // el resto del árbol queda igual
    }

    @Test
    void remover_un_destino_inexistente_sin_override_lanza_inexistencia_y_no_violacion_de_rango() {
        italia.agregarDestino(roma);
        europa.agregarDestino(italia);
        Ciudad tokio = roma("Tokio");

        DestinoException e = assertThrowsExactly(DestinoException.InexistenciaDestino.class,
                () -> europa.removerDestino(tokio, false));

        assertTrue(e.getMessage().contains("Tokio"));
    }

    @Test
    void remover_un_destino_inexistente_con_override_lanza_inexistencia() {
        italia.agregarDestino(roma);
        europa.agregarDestino(italia);
        Ciudad tokio = roma("Tokio");

        assertThrowsExactly(DestinoException.InexistenciaDestino.class,
                () -> europa.removerDestino(tokio, true));
        assertTrue(europa.contiene(roma)); // no tocó nada
    }

    @Test
    void un_destino_no_puede_removerse_a_si_mismo() {
        assertThrowsExactly(DestinoException.InexistenciaDestino.class,
                () -> europa.removerDestino(europa, false));
        assertThrowsExactly(DestinoException.InexistenciaDestino.class,
                () -> europa.removerDestino(europa, true));
    }

    @Test
    void remover_un_hijo_directo_compuesto_se_lleva_su_subarbol() {
        italia.agregarDestino(roma);
        europa.agregarDestino(italia);

        europa.removerDestino(italia, false);

        assertFalse(europa.contiene(italia));
        assertFalse(europa.contiene(roma));
    }

    @Test
    void una_ciudad_no_contiene_destinos_para_remover() {
        assertThrowsExactly(DestinoException.InexistenciaDestino.class,
                () -> roma.removerDestino(roma("Milan"), true));
    }

    @Test
    void agregar_un_destino_que_ya_esta_en_el_arbol_lanza_excepcion() {
        italia.agregarDestino(roma);
        europa.agregarDestino(italia);

        assertThrowsExactly(DestinoException.DuplicidadDestino.class, () -> europa.agregarDestino(roma));
        assertThrowsExactly(DestinoException.DuplicidadDestino.class, () -> italia.agregarDestino(roma));
    }

    @Test
    void se_puede_crear_un_destino_con_sus_lugares() {
        java.util.ArrayList<LugarGeografico> ciudades = new java.util.ArrayList<>(java.util.List.of(roma));

        Destino conCiudades = new Destino(TipoGeografico.PAIS, "Italia", ciudades);

        assertTrue(conCiudades.contiene(roma));
    }

    @Test
    void la_lista_usada_para_crear_un_destino_no_queda_compartida() {
        java.util.ArrayList<LugarGeografico> ciudades = new java.util.ArrayList<>(java.util.List.of(roma));
        Destino conCiudades = new Destino(TipoGeografico.PAIS, "Italia", ciudades);

        Ciudad milan = new Ciudad("Milán");
        ciudades.add(milan);

        assertFalse(conCiudades.contiene(milan));
    }

    @Test
    void ciudad_siempre_es_de_tipo_ciudad() {
        assertEquals(TipoGeografico.CIUDAD, new Ciudad("X").tipo());
    }
}