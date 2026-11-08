package com.pepito.reservas.catalogo.atributos;

import com.pepito.reservas.catalogo.servicio.atributos.AtributoDinamico;
import com.pepito.reservas.catalogo.servicio.atributos.TipoBooleano;
import com.pepito.reservas.catalogo.servicio.atributos.TipoDinamico;
import com.pepito.reservas.catalogo.servicio.atributos.TipoFecha;
import com.pepito.reservas.catalogo.servicio.atributos.TipoNumerico;
import com.pepito.reservas.catalogo.servicio.atributos.TipoTexto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/*
 * Contrato de Atributo Dinamico
 * La validacion comprueba que:
 *   -> que un atributo obligatorio tenga valor, y
 *   -> que el valor asignado sea consistente con el tipo declarado.
 */
class AtributoDinamicoTest {

    private TipoDinamico texto;
    private TipoDinamico numerico;
    private TipoDinamico booleano;
    private TipoDinamico fecha;

    @BeforeEach
    void setUp() {
        texto = new TipoTexto();
        numerico = new TipoNumerico();
        booleano = new TipoBooleano();
        fecha = new TipoFecha();
    }

    // -> Tipo Texto

    @Test
    void el_tipo_texto_acepta_cadenas_y_caracteres() {
        assertTrue(texto.acepta("Se admiten mascotas pequeñas"));
        assertTrue(texto.acepta(""));
        assertTrue(texto.acepta('S'));
    }

    @Test
    void el_tipo_texto_rechaza_valores_que_no_son_texto() {
        assertFalse(texto.acepta(5));
        assertFalse(texto.acepta(true));
        assertFalse(texto.acepta(LocalDate.of(2026, 1, 1)));
    }

    // -> Tipo numérico

    @Test
    void el_tipo_numerico_acepta_cualquier_numero() {
        assertTrue(numerico.acepta(4));
        assertTrue(numerico.acepta(4.5));
        assertTrue(numerico.acepta(-3L));
        assertTrue(numerico.acepta(new BigDecimal("4.50")));
    }

    @Test
    void el_tipo_numerico_rechaza_numeros_escritos_como_texto() {
        assertFalse(numerico.acepta("4"));
        assertFalse(numerico.acepta(true));
    }

    // -> Tipo booleano

    @Test
    void el_tipo_booleano_acepta_verdadero_y_falso() {
        assertTrue(booleano.acepta(true));
        assertTrue(booleano.acepta(false));
    }

    @Test
    void el_tipo_booleano_rechaza_textos_y_numeros() {
        assertFalse(booleano.acepta("true"));
        assertFalse(booleano.acepta(1));
    }

    // -> Tipo fecha

    @Test
    void el_tipo_fecha_acepta_fechas_en_sus_distintas_representaciones() {
        assertTrue(fecha.acepta(LocalDate.of(2026, 1, 1)));
        assertTrue(fecha.acepta(LocalDateTime.of(2026, 1, 1, 10, 0)));
        assertTrue(fecha.acepta(new Date()));
        assertTrue(fecha.acepta(Calendar.getInstance()));
    }

    @Test
    void el_tipo_fecha_rechaza_valores_que_no_son_fechas() {
        assertFalse(fecha.acepta("2026-01-01"));
        assertFalse(fecha.acepta(20260101));
        assertFalse(fecha.acepta(true));
    }

    // -> Atributos obligatorios validan tipo y valor no nulo

    @Test
    void un_atributo_obligatorio_sin_valor_es_invalido_sea_cual_sea_su_tipo() {
        for (TipoDinamico tipo : List.of(texto, numerico, booleano, fecha)) {
            assertFalse(new AtributoDinamico("Obligatorio", true, tipo, null).validate(),
                    "Debería ser inválido para " + tipo.getClass().getSimpleName());
        }
    }

    @Test
    void un_atributo_opcional_sin_valor_es_valido_sea_cual_sea_su_tipo() {
        for (TipoDinamico tipo : List.of(texto, numerico, booleano, fecha)) {
            assertTrue(new AtributoDinamico("Opcional", false, tipo, null).validate(),
                    "Debería ser válido para " + tipo.getClass().getSimpleName());
        }
    }

    // -> Verifiación de tipo, sea obligatorio o no

    @Test
    void los_atributos_del_enunciado_con_valores_de_su_tipo_son_validos() {
        assertTrue(new AtributoDinamico("CapacidadMáxima", true, numerico, 4).validate());
        assertTrue(new AtributoDinamico("PolíticaMascotas", true, texto, "Solo perros pequeños").validate());
        assertTrue(new AtributoDinamico("RatingEstrellas", false, numerico, 4.5).validate());
        assertTrue(new AtributoDinamico("AptoMascotas", true, booleano, true).validate());
        assertTrue(new AtributoDinamico("FechaApertura", false, fecha, LocalDate.of(2026, 3, 1)).validate());
    }

    @Test
    void un_atributo_con_valor_de_otro_tipo_es_invalido_aunque_sea_obligatorio() {
        assertFalse(new AtributoDinamico("CapacidadMáxima", true, numerico, "cuatro").validate());
        assertFalse(new AtributoDinamico("AptoMascotas", true, booleano, 4.5).validate());
        assertFalse(new AtributoDinamico("FechaApertura", true, fecha, "2026-03-01").validate());
        assertFalse(new AtributoDinamico("PolíticaMascotas", true, texto, 3).validate());
    }

    @Test
    void un_atributo_opcional_con_valor_de_otro_tipo_tambien_es_invalido() {
        assertFalse(new AtributoDinamico("RatingEstrellas", false, numerico, "cinco").validate());
    }

    // -> verificación de escalamiento (segun TP pueden sumarse nuevos tipos de atributo), ejemplo, correo

    @Test
    void se_puede_incorporar_un_tipo_nuevo_sin_modificar_atributo_dinamico() {
        TipoDinamico email = valor -> valor instanceof String s && s.contains("@");

        assertTrue(new AtributoDinamico("Contacto", true, email, "hotel@mail.com").validate());
        assertFalse(new AtributoDinamico("Contacto", true, email, "sin arroba").validate());
    }
}
