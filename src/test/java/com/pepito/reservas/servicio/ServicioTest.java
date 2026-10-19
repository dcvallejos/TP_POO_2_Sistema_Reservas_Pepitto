package com.pepito.reservas.servicio;

import com.pepito.reservas.excepciones.ServiceExceptions;
import com.pepito.reservas.servicio.atributos.AtributoDinamico;
import com.pepito.reservas.servicio.atributos.TipoBooleano;
import com.pepito.reservas.servicio.atributos.TipoFecha;
import com.pepito.reservas.servicio.atributos.TipoNumerico;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ServicioTest {

    Servicio servicio_vuelo;
    Servicio servicio_vuelo_2;
    Servicio servicio_alojamiento;
    Servicio servicio_excursion;
    Servicio servicio_auto;

    Double DELTA = 0.0001;
    @BeforeEach
    void setUp() {

        servicio_vuelo = new Servicio(
                "123VUELO",
                "Vuelo a Aruba",
                "QATAR AIRWAYS",
                Categoria.VUELO,
                123.0,
                0.1
        );

        servicio_alojamiento = new Servicio(
                "123HOTEL",
                "Hotel en Aruba",
                "AIRBNB",
                Categoria.ALOJAMIENTO,
                32.5,
                0.15
        );

        servicio_vuelo_2 = new Servicio(
                "234VUELO",
                "Traslado a Yahib",
                "QATAR MINI-AIRWAYS",
                Categoria.VUELO,
                50.34,
                0.05
        );

        servicio_auto = new Servicio(
                "123AUTO",
                "Alquiler de auto en Yahib",
                "CAMEL TIRE",
                Categoria.ALQUILER_AUTO,
                12.2,
                0.00
        );

        servicio_excursion = new Servicio(
                "123TEMPLO",
                "Excursion a tumbas de Sazagrev",
                "WISE GUY TRAVEL AGENCY",
                Categoria.EXCURSION,
                5.6,
                0.2
        );

    }

    @Test
    void servicio_devuelve_precio_base(){
        assertEquals(5.6,this.servicio_excursion.precioBase(),DELTA);
    }

    @Test
    void servicio_devuelve_precio_final_con_descuento_correcto(){
        assertEquals(110.7,this.servicio_vuelo.precioFinal(),DELTA);
    }

    @Test
    void servicio_posee_descuento_directo(){
        assertEquals(0.85,this.servicio_alojamiento.descuento(),DELTA);
    }

    @Test
    void servicio_posee_atributos_validos() {
        assertTrue(this.servicio_vuelo_2.validate());
    }

    @Test
    void servicio_posee_atributo_fijo_precioBase_invalido() {

        Servicio servicio_excursion_invalid = new Servicio(
                "321TEMPLO",
                "Excursion a tumbas de Sazagrev Falso",
                "UNWISE GUY TRAVEL AGENCY",
                Categoria.EXCURSION,
                0.0,
                0.2
        );

        assertFalse(servicio_excursion_invalid.validate());
    }

    @Test
    void servicio_posee_atributo_fijo_codigoReserva_invalido() {

        Servicio servicio_excursion_invalid = new Servicio(
                "",
                "Excursion a tumbas de Sazagrev Falso",
                "UNWISE GUY TRAVEL AGENCY",
                Categoria.EXCURSION,
                1.0,
                0.2
        );

        assertFalse(servicio_excursion_invalid.validate());
    }

    @Test
    void servicio_posee_atributo_fijo_nombre_invalido() {

        Servicio servicio_excursion_invalid = new Servicio(
                "TEST",
                "",
                "UNWISE GUY TRAVEL AGENCY",
                Categoria.EXCURSION,
                1.0,
                0.2
        );

        assertFalse(servicio_excursion_invalid.validate());
    }

    @Test
    void servicio_posee_atributo_fijo_proveedor_invalido() {

        Servicio servicio_excursion_invalid = new Servicio(
                "TEST",
                "Excursion a tumbas de Sazagrev Falso",
                "",
                Categoria.EXCURSION,
                1.0,
                0.2
        );

        assertFalse(servicio_excursion_invalid.validate());
    }

    @Test
    void servicio_con_error_creacion_precio_negativo() {

        assertThrowsExactly(ServiceExceptions.ErrorCreacionServicio.class, () -> {
            Servicio servicio_excursion_invalid = new Servicio(
                    "321TEMPLO",
                    "Excursion a tumbas de Sazagrev Falso",
                    "UNWISE GUY TRAVEL AGENCY",
                    Categoria.EXCURSION,
                    -100.0,
                    0.2
            );
        });
    }
    @Test
    void servicio_con_error_creacion_descuento_mayor_a_1() {

        assertThrowsExactly(ServiceExceptions.ErrorCreacionServicio.class, () -> {
            Servicio servicio_excursion_invalid = new Servicio(
                    "321TEMPLO",
                    "Excursion a tumbas de Sazagrev Falso",
                    "UNWISE GUY TRAVEL AGENCY",
                    Categoria.EXCURSION,
                    100.0,
                    1.2
            );
        });
    }

    @Test
    void servicio_agrega_atributo_dinamico_valido() {
        AtributoDinamico puntuacionEstrella = new AtributoDinamico("Puntuación",true,new TipoNumerico(),4.5);

        assertEquals(0,this.servicio_vuelo_2.cantidadAtributos());

        this.servicio_vuelo_2.agregarAtributo(puntuacionEstrella);

        assertTrue(this.servicio_vuelo_2.validate());

        assertEquals(1,this.servicio_vuelo_2.cantidadAtributos());
    }

    @Test
    void servicio_agrega_atributo_dinamico_invalido_tipo_erroneo() {
        AtributoDinamico mascotasHabilitadas = new AtributoDinamico("Apto Mascotas",true,new TipoBooleano(),4.5);

        assertEquals(0,this.servicio_vuelo_2.cantidadAtributos());

        this.servicio_vuelo_2.agregarAtributo(mascotasHabilitadas);

        assertFalse(this.servicio_vuelo_2.validate());
        assertEquals(1,this.servicio_vuelo_2.cantidadAtributos());
    }
    @Test
    void servicio_agrega_atributo_dinamico_invalido_valor_nulo() {
        AtributoDinamico mascotasHabilitadas = new AtributoDinamico("Apto Mascotas",true,new TipoFecha(),null);

        assertEquals(0,this.servicio_vuelo_2.cantidadAtributos());

        this.servicio_vuelo_2.agregarAtributo(mascotasHabilitadas);

        assertFalse(this.servicio_vuelo_2.validate());
        assertEquals(1,this.servicio_vuelo_2.cantidadAtributos());
    }

    @Test
    void servicio_elimina_atributo_dinamico_existente_decrementa_cantidades() {
        AtributoDinamico inicioActividad = new AtributoDinamico("Fecha Inicio Actividad",true,new TipoFecha(), LocalDate.of(2026, 2, 1));
        AtributoDinamico puntuacionEstrella = new AtributoDinamico("Puntuación",true,new TipoNumerico(),4.5);

        this.servicio_vuelo_2.agregarAtributo(inicioActividad);
        this.servicio_vuelo_2.agregarAtributo(puntuacionEstrella);
        assertEquals(2,this.servicio_vuelo_2.cantidadAtributos());

        this.servicio_vuelo_2.eliminarAtributo(inicioActividad);
        assertEquals(1,this.servicio_vuelo_2.cantidadAtributos());
    }

    @Test
    void servicio_elimina_atributo_dinamico_inexistente_tira_excepcion() {
        AtributoDinamico inicioActividad = new AtributoDinamico("Fecha Inicio Actividad",true,new TipoFecha(), LocalDate.of(2026, 2, 1));
        AtributoDinamico puntuacionEstrella = new AtributoDinamico("Puntuación",true,new TipoNumerico(),4.5);

        this.servicio_vuelo_2.agregarAtributo(puntuacionEstrella);
        assertEquals(1,this.servicio_vuelo_2.cantidadAtributos());

        assertThrowsExactly(ServiceExceptions.MetodoInvalido.class, () -> {
            this.servicio_vuelo_2.eliminarAtributo(inicioActividad);
        });


    }
}