package com.pepito.reservas.servicio;

import static org.junit.jupiter.api.Assertions.*;

class PaqueteTest {

    Paquete paquete;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {

        Servicio servicio_vuelo = new Servicio(
                "123VUELO",
                "Vuelo a Aruba",
                "QATAR AIRWAYS",
                Categoria.VUELO,
                123.0,
                0.1
        );

        Servicio servicio_alojamiento = new Servicio(
                "123HOTEL",
                "Hotel en Aruba",
                "AIRBNB",
                Categoria.ALOJAMIENTO,
                32.5,
                0.15
        );

        Servicio servicio_vuelo_2 = new Servicio(
                "234VUELO",
                "Traslado a Yahib",
                "QATAR MINI-AIRWAYS",
                Categoria.VUELO,
                50.34,
                0.05
        );

        Servicio servicio_auto = new Servicio(
                "123AUTO",
                "Alquiler de auto en Yahib",
                "CAMEL TIRE",
                Categoria.ALQUILER_AUTO,
                12.2,
                0.00
        );

        Servicio servicio_excursion = new Servicio(
                "123TEMPLO",
                "Excursion a tumbas de Sazagrev",
                "WISE GUY TRAVEL AGENCY",
                Categoria.EXCURSION,
                5.6,
                0.2
        );

    }

    @org.junit.jupiter.api.Test
    void precioBase() {
    }

    @org.junit.jupiter.api.Test
    void descuento() {
    }

    @org.junit.jupiter.api.Test
    void precioFinal() {
    }

    @org.junit.jupiter.api.Test
    void validate() {
    }

    @org.junit.jupiter.api.Test
    void agregarItem() {
    }

    @org.junit.jupiter.api.Test
    void cantidadItems() {
    }

    @org.junit.jupiter.api.Test
    void eliminarItem() {
    }
}