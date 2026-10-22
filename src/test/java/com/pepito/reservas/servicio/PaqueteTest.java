package com.pepito.reservas.servicio;

import com.pepito.reservas.excepciones.ServiceExceptions;
import com.pepito.reservas.servicio.atributos.AtributoDinamico;
import com.pepito.reservas.servicio.atributos.TipoBooleano;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class PaqueteTest {

    Paquete paquete_aruba;
    Servicio servicio_vuelo;
    Servicio servicio_vuelo_2;
    Servicio servicio_alojamiento;
    Servicio servicio_excursion;
    Servicio servicio_auto;

    Double DELTA = 0.0001;

    @BeforeEach
    void setUp() {

        servicio_vuelo = new Servicio(
                "Vuelo a Aruba",
                0.1,
                new Disponibilidad(),
                "123VUELO",
                "QATAR AIRWAYS",
                Categoria.VUELO,
                123.0
        );

        servicio_alojamiento = new Servicio(
                "Hotel en Aruba",
                0.15,
                new Disponibilidad(),
                "123HOTEL",
                "AIRBNB",
                Categoria.ALOJAMIENTO,
                32.5
        );

        servicio_vuelo_2 = new Servicio(
                "Traslado a Yahib",
                0.05,
                new Disponibilidad(),
                "234VUELO",
                "QATAR MINI-AIRWAYS",
                Categoria.VUELO,
                50.34
        );

        servicio_auto = new Servicio(
                "Alquiler de auto en Yahib",
                0.00,
                new Disponibilidad(),
                "123AUTO",
                "CAMEL TIRE",
                Categoria.ALQUILER_AUTO,
                12.2
        );

        servicio_excursion = new Servicio(
                "Excursion a tumbas de Sazagrev",
                0.2,
                new Disponibilidad(),
                "123TEMPLO",
                "WISE GUY TRAVEL AGENCY",
                Categoria.EXCURSION,
                5.6
        );

        paquete_aruba = new Paquete(
                "Pack Escapada Aruba",
                0.1,
                "Paquete de prueba",
                100.0
        );
    }


    @Test
    void paquete_con_error_creacion_precio_negativo() {

        assertThrowsExactly(ServiceExceptions.ErrorCreacionPaquete.class, () -> {
            new Paquete(
                    "Pack Falso",
                    0.1,
                    "Paquete invalido",
                    -100.0
            );
        });
    }

    @Test
    void paquete_con_error_creacion_descuento_mayor_a_1() {

        assertThrowsExactly(ServiceExceptions.ErrorCreacionPaquete.class, () -> {
            new Paquete(
                    "Pack Falso",
                    1.2,
                    "Paquete invalido",
                    100.0
            );
        });
    }

    @Test
    void paquete_con_error_creacion_descuento_negativo() {

        assertThrowsExactly(ServiceExceptions.ErrorCreacionPaquete.class, () -> {
            new Paquete(
                    "Pack Falso",
                    -0.1,
                    "Paquete invalido",
                    100.0
            );
        });
    }

    @Test
    void paquete_acepta_descuentos_limite_0_y_1() {
        Paquete sinDescuento = new Paquete("Test", 0.0, "Sin descuento", 0.0);
        Paquete descuentoTotal = new Paquete("Test", 1.0, "Descuento total", 0.0);

        sinDescuento.agregarItem(servicio_auto);
        descuentoTotal.agregarItem(servicio_auto);

        assertEquals(12.2, sinDescuento.precioFinal(), DELTA);
        assertEquals(0.0, descuentoTotal.precioFinal(), DELTA);
    }

    @Test
    void paquete_vacio_tiene_precio_base_cero() {
        assertEquals(0.0, this.paquete_aruba.precioBase(), DELTA);
    }

    @Test
    void paquete_precio_base_es_la_suma_de_los_precios_finales_de_sus_servicios() {
        this.paquete_aruba.agregarItem(servicio_vuelo);       // 123.0 * 0.90 = 110.7
        this.paquete_aruba.agregarItem(servicio_alojamiento); //  32.5 * 0.85 = 27.625

        assertEquals(138.325, this.paquete_aruba.precioBase(), DELTA);
    }

    @Test
    void paquete_posee_descuento_directo() {
        assertEquals(0.9, this.paquete_aruba.descuento(), DELTA);
    }

    @Test
    void paquete_devuelve_precio_final_con_descuento_correcto() {
        this.paquete_aruba.agregarItem(servicio_vuelo);
        this.paquete_aruba.agregarItem(servicio_alojamiento);

        assertEquals(124.4925, this.paquete_aruba.precioFinal(), DELTA);
    }

    @Test
    void paquete_vacio_tiene_precio_final_cero() {
        assertEquals(0.0, this.paquete_aruba.precioFinal(), DELTA);
    }

    @Test
    void paquete_calcula_precio_segun_ejemplo_del_enunciado() {
        Servicio hotel = new Servicio("Noche de Hotel Central", 0.0, new Disponibilidad(), "HC1", "HOTELES SA", Categoria.ALOJAMIENTO, 15000.0);
        Servicio museo = new Servicio("Excursion Guiada Museo", 0.0, new Disponibilidad(), "EM1", "MUSEOS SA", Categoria.EXCURSION, 4000.0);
        Servicio traslado = new Servicio("Traslado Aeropuerto-Hotel", 0.0, new Disponibilidad(), "TR1", "TRASLADOS SA", Categoria.ALQUILER_AUTO, 2000.0);
        Paquete escapadaUrbana = new Paquete("Pack Escapada Urbana", 0.1, "Escapada urbana", 0.0);

        escapadaUrbana.agregarItem(hotel);
        escapadaUrbana.agregarItem(museo);
        escapadaUrbana.agregarItem(traslado);

        assertEquals(21000.0, escapadaUrbana.precioBase(), DELTA);
        assertEquals(18900.0, escapadaUrbana.precioFinal(), DELTA);
    }

    @Test
    void paquete_anidado_calcula_precio_final_de_forma_recursiva() {
        Paquete paqueteInterno = new Paquete("Pack Interno", 0.1, "Paquete interno", 0.0);
        paqueteInterno.agregarItem(servicio_alojamiento); // 27.625
        paqueteInterno.agregarItem(servicio_excursion);   //  4.48
        // interno: (27.625 + 4.48) * 0.9 = 28.8945

        Paquete paqueteExterno = new Paquete("Pack Externo", 0.2, "Paquete externo", 0.0);
        paqueteExterno.agregarItem(paqueteInterno);
        paqueteExterno.agregarItem(servicio_vuelo);       // 110.7
        // externo: (28.8945 + 110.7) * 0.8 = 111.6756

        assertEquals(28.8945, paqueteInterno.precioFinal(), DELTA);
        assertEquals(139.5945, paqueteExterno.precioBase(), DELTA);
        assertEquals(111.6756, paqueteExterno.precioFinal(), DELTA);
    }

    @Test
    void paquete_con_servicios_validos_es_valido() {
        this.paquete_aruba.agregarItem(servicio_vuelo);
        this.paquete_aruba.agregarItem(servicio_alojamiento);

        assertTrue(this.paquete_aruba.validate());
    }

    @Test
    void paquete_vacio_es_valido() {
        assertTrue(this.paquete_aruba.validate());
    }

    @Test
    void paquete_posee_atributo_fijo_nombre_invalido() {
        Paquete paqueteInvalido = new Paquete(null, 0.1, "Paquete sin nombre", 100.0);

        assertFalse(paqueteInvalido.validate());
    }

    @Test
    void paquete_posee_atributo_fijo_descripcion_invalida() {
        Paquete paqueteInvalido = new Paquete("Pack sin descripcion", 0.1, null, 100.0);

        assertFalse(paqueteInvalido.validate());
    }

    @Test
    void paquete_es_invalido_si_un_servicio_deja_de_ser_valido() {
        this.paquete_aruba.agregarItem(servicio_vuelo);
        assertTrue(this.paquete_aruba.validate());

        AtributoDinamico mascotasHabilitadas = new AtributoDinamico("Apto Mascotas", true, new TipoBooleano(), 4.5);
        this.servicio_vuelo.agregarAtributo(mascotasHabilitadas);

        assertFalse(this.paquete_aruba.validate());
    }

    @Test
    void paquete_es_invalido_si_un_paquete_anidado_deja_de_ser_valido() {
        Paquete paqueteInterno = new Paquete("Pack Interno", 0.1, "Paquete interno", 0.0);
        paqueteInterno.agregarItem(servicio_auto);
        this.paquete_aruba.agregarItem(paqueteInterno);
        assertTrue(this.paquete_aruba.validate());

        paqueteInterno.agregarItem(servicio_excursion);
        AtributoDinamico mascotasHabilitadas = new AtributoDinamico("Apto Mascotas", true, new TipoBooleano(), 4.5);
        this.servicio_excursion.agregarAtributo(mascotasHabilitadas);

        assertFalse(this.paquete_aruba.validate());
    }

    @Test
    void paquete_nuevo_no_tiene_items() {
        assertEquals(0, this.paquete_aruba.cantidadItems());
    }

    @Test
    void paquete_agrega_servicio_valido_incrementa_cantidad() {
        this.paquete_aruba.agregarItem(servicio_vuelo);
        this.paquete_aruba.agregarItem(servicio_alojamiento);

        assertEquals(2, this.paquete_aruba.cantidadItems());
    }

    @Test
    void paquete_agrega_paquete_valido_incrementa_cantidad() {
        Paquete paqueteInterno = new Paquete("Pack Interno", 0.1, "Paquete interno", 0.0);
        paqueteInterno.agregarItem(servicio_auto);

        this.paquete_aruba.agregarItem(paqueteInterno);

        assertEquals(1, this.paquete_aruba.cantidadItems());
    }

    @Test
    void paquete_agrega_servicio_invalido_tira_excepcion() {
        Servicio servicioInvalido = new Servicio(
                "Excursion a tumbas de Sazagrev Falso",
                0.2,
                new Disponibilidad(),
                "321TEMPLO",
                "UNWISE GUY TRAVEL AGENCY",
                Categoria.EXCURSION,
                0.0
        );

        assertThrowsExactly(ServiceExceptions.MetodoInvalido.class, () -> {
            this.paquete_aruba.agregarItem(servicioInvalido);
        });
        assertEquals(0, this.paquete_aruba.cantidadItems());
    }

    @Test
    void paquete_agrega_paquete_invalido_tira_excepcion() {
        Paquete paqueteInvalido = new Paquete("Pack sin descripcion", 0.1, null, 100.0);

        assertThrowsExactly(ServiceExceptions.MetodoInvalido.class, () -> {
            this.paquete_aruba.agregarItem(paqueteInvalido);
        });
        assertEquals(0, this.paquete_aruba.cantidadItems());
    }

    @Test
    void paquete_remueve_item_existente_decrementa_cantidad() {
        this.paquete_aruba.agregarItem(servicio_vuelo);
        this.paquete_aruba.agregarItem(servicio_alojamiento);
        assertEquals(2, this.paquete_aruba.cantidadItems());

        this.paquete_aruba.removerItem(servicio_vuelo);

        assertEquals(1, this.paquete_aruba.cantidadItems());
        assertEquals(27.625, this.paquete_aruba.precioBase(), DELTA);
    }

    @Test
    void paquete_remueve_item_inexistente_no_modifica_cantidad() {
        this.paquete_aruba.agregarItem(servicio_vuelo);
        assertEquals(1, this.paquete_aruba.cantidadItems());

        this.paquete_aruba.removerItem(servicio_alojamiento);

        assertEquals(1, this.paquete_aruba.cantidadItems());
    }


}