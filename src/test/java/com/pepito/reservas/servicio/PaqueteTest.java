package com.pepito.reservas.servicio;

import com.pepito.reservas.catalogo.CatalogoException;
import com.pepito.reservas.catalogo.servicio.*;
import com.pepito.reservas.excepciones.DisponibilidadException;
import com.pepito.reservas.excepciones.ServiceExceptions;
import com.pepito.reservas.catalogo.servicio.atributos.AtributoDinamico;
import com.pepito.reservas.catalogo.servicio.atributos.TipoBooleano;
import com.pepito.reservas.destino.Ciudad;
import com.pepito.reservas.destino.Destino;
import com.pepito.reservas.destino.TipoGeografico;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/*
 * Paquete = Composite. No tiene Disponibilidad propia: todo lo que tiene que ver con cupos
 * (disponible, reservar, liberar, cubre) lo resuelven las hojas (Servicio) y el Paquete solo delega.
 */
class PaqueteTest {

    static final Double DELTA = 0.0001;

    Paquete paquete_aruba;
    Servicio servicio_vuelo;
    Servicio servicio_alojamiento;
    Servicio servicio_excursion;
    Servicio servicio_auto;

    LocalDate dia1 = LocalDate.of(2026, 1, 1);
    LocalDate dia2 = LocalDate.of(2026, 1, 2);
    LocalDate dia3 = LocalDate.of(2026, 1, 3);
    Periodo tresDias = new Periodo(dia1, dia3);

    Destino europa;
    Destino italia;
    Destino francia;
    Ciudad roma;
    Ciudad lyon;
    Ciudad paris;

    // Unico lugar donde se crean los objetos: si cambia un constructor, se toca aca.
    private Destino continente(String nombre) {return new Destino(TipoGeografico.CONTINENTE, nombre);}
    private Destino pais(String nombre) {return new Destino(TipoGeografico.PAIS, nombre);}
    private Ciudad ciudad(String nombre) {return new Ciudad(nombre);}

    private void cargarCupo(Servicio servicio, Periodo periodo, int cupos) {
        servicio.disponibilidad().agregarCupo(periodo, cupos);
    }

    @BeforeEach
    void setUp() {

        roma = ciudad("Roma");
        lyon = ciudad("Lyon");
        paris = ciudad("Paris");

        italia = pais("Italia");
        francia = pais("Francia");
        europa = continente("Europa");

        europa.agregarDestino(italia);
        europa.agregarDestino(francia);

        italia.agregarDestino(roma);
        francia.agregarDestino(lyon);
        francia.agregarDestino(paris);

        servicio_vuelo = new Servicio("Vuelo a Aruba","Travesia paradisíaca", 0.1, new Disponibilidad(),
                "123VUELO", "QATAR AIRWAYS", Categoria.VUELO, 123.0, roma);

        servicio_alojamiento = new Servicio("Hotel en Aruba","El mejor descanso", 0.15, new Disponibilidad(),
                "123HOTEL", "AIRBNB", Categoria.ALOJAMIENTO, 32.5, lyon);

        servicio_auto = new Servicio("Alquiler de auto en Yahib","Viaje confortablemente", 0.00, new Disponibilidad(),
                "123AUTO", "CAMEL TIRE", Categoria.ALQUILER_AUTO, 12.2, italia);

        servicio_excursion = new Servicio("Excursion a tumbas de Sazagrev","Un paseo historico e imperdible", 0.2,
                new Disponibilidad(),
                "123TEMPLO", "WISE GUY TRAVEL AGENCY", Categoria.EXCURSION, 5.6, europa);

        paquete_aruba = new Paquete("Pack Escapada Aruba", "Paquete de prueba",0.1 , paris);
    }

    // Servicio invalido: tiene un atributo dinamico cuyo valor no corresponde al tipo declarado
    private Servicio servicioInvalido() {
        Servicio s = new Servicio("Servicio invalido", "Descripcion de servicio inválido",0.0, new Disponibilidad(),
                "999INV", "PROVEEDOR", Categoria.EXCURSION, 10.0, paris);
        s.agregarAtributo(new AtributoDinamico("Apto Mascotas", true, new TipoBooleano(), 4.5));
        return s;
    }

    // Paquete valido (con un item) para usar como hijo de otro paquete
    private Paquete paqueteInternoCon(Servicio servicio) {
        Paquete interno = new Paquete("Pack Interno", "Paquete interno",0.1,  europa);
        interno.agregarItem(servicio);
        return interno;
    }

    // -> Creacion

    @Test
    void paquete_con_error_creacion_descuento_mayor_a_1() {
        assertThrowsExactly(ServiceExceptions.ErrorCreacionPaquete.class,
                () -> new Paquete("Pack Falso", "Paquete invalido", 1.2,  europa));
    }

    @Test
    void paquete_con_error_creacion_descuento_negativo() {
        assertThrowsExactly(ServiceExceptions.ErrorCreacionPaquete.class,
                () -> new Paquete("Pack Falso", "Paquete invalido",-0.1,  europa));
    }

    @Test
    void paquete_acepta_descuentos_limite_0_y_1() {
        Paquete sinDescuento = new Paquete("Test", "Sin descuento",0.0,  lyon);
        Paquete descuentoTotal = new Paquete("Test", "Descuento total",1.0,  italia);

        sinDescuento.agregarItem(servicio_auto);
        descuentoTotal.agregarItem(servicio_auto);

        assertEquals(12.2, sinDescuento.precioFinal(), DELTA);
        assertEquals(0.0, descuentoTotal.precioFinal(), DELTA);
    }

    // -> Precios

    @Test
    void paquete_vacio_tiene_precio_base_cero() {
        assertEquals(0.0, paquete_aruba.precioBase(), DELTA);
    }

    @Test
    void paquete_precio_base_es_la_suma_de_los_precios_finales_de_sus_servicios() {
        paquete_aruba.agregarItem(servicio_vuelo);       // 123.0 * 0.90 = 110.7
        paquete_aruba.agregarItem(servicio_alojamiento); //  32.5 * 0.85 = 27.625

        assertEquals(138.325, paquete_aruba.precioBase(), DELTA);
    }

    @Test
    void paquete_posee_descuento_directo() {
        assertEquals(0.9, paquete_aruba.descuento(), DELTA);
    }

    @Test
    void paquete_devuelve_precio_final_con_descuento_correcto() {
        paquete_aruba.agregarItem(servicio_vuelo);
        paquete_aruba.agregarItem(servicio_alojamiento);

        assertEquals(124.4925, paquete_aruba.precioFinal(), DELTA);
    }

    @Test
    void paquete_vacio_tiene_precio_final_cero() {
        assertEquals(0.0, paquete_aruba.precioFinal(), DELTA);
    }

    @Test
    void paquete_calcula_precio_segun_ejemplo_del_enunciado() {
        Servicio hotel = new Servicio("Noche de Hotel Central","Entre algodones", 0.0, new Disponibilidad(), "HC1",
                "HOTELES " +
                "SA",
                Categoria.ALOJAMIENTO, 15000.0, europa);
        Servicio museo = new Servicio("Excursion Guiada Museo","Sugerencia: Contrate guía", 0.0, new Disponibilidad(),
                "EM1", "MUSEOS SA",
                Categoria.EXCURSION, 4000.0, europa);
        Servicio traslado = new Servicio("Traslado Aeropuerto-Hotel","El viaje mas rápidos del país", 0.0,
                new Disponibilidad(),
                "TR1",
                "TRASLADOS " +
                "SA", Categoria.ALQUILER_AUTO, 2000.0, europa);
        Paquete escapadaUrbana = new Paquete("Pack Escapada Urbana", "Vea desde primer plano como es la jungla de " +
                "cemento",0.1,  europa);

        escapadaUrbana.agregarItem(hotel);
        escapadaUrbana.agregarItem(museo);
        escapadaUrbana.agregarItem(traslado);

        assertEquals(21000.0, escapadaUrbana.precioBase(), DELTA);
        assertEquals(18900.0, escapadaUrbana.precioFinal(), DELTA);
    }

    @Test
    void paquete_anidado_calcula_precio_final_de_forma_recursiva() {
        Paquete paqueteInterno = new Paquete("Pack Interno","Paquete interno", 0.1,  europa);
        paqueteInterno.agregarItem(servicio_alojamiento); // 27.625
        paqueteInterno.agregarItem(servicio_excursion);   //  4.48
        // interno: (27.625 + 4.48) * 0.9 = 28.8945

        Paquete paqueteExterno = new Paquete("Pack Externo","Paquete externo", 0.2,  europa);
        paqueteExterno.agregarItem(paqueteInterno);
        paqueteExterno.agregarItem(servicio_vuelo);       // 110.7
        // externo: (28.8945 + 110.7) * 0.8 = 111.6756

        assertEquals(28.8945, paqueteInterno.precioFinal(), DELTA);
        assertEquals(139.5945, paqueteExterno.precioBase(), DELTA);
        assertEquals(111.6756, paqueteExterno.precioFinal(), DELTA);
    }

    //  -> Validacion

    @Test
    void paquete_con_servicios_validos_es_valido() {
        paquete_aruba.agregarItem(servicio_vuelo);
        paquete_aruba.agregarItem(servicio_alojamiento);

        assertTrue(paquete_aruba.validate());
    }

    @Test
    void paquete_vacio_es_invalido() {
        assertFalse(paquete_aruba.validate());
    }

    @Test
    void paquete_sin_nombre_es_invalido() {
        Paquete paqueteInvalido = new Paquete(null,"Paquete sin nombre", 0.1,  europa);
        paqueteInvalido.agregarItem(servicio_vuelo);

        assertFalse(paqueteInvalido.validate());
    }

    @Test
    void paquete_sin_descripcion_es_invalido() {
        Paquete paqueteInvalido = new Paquete("Pack sin descripcion", null, 0.1, europa);
        paqueteInvalido.agregarItem(servicio_vuelo);

        assertFalse(paqueteInvalido.validate());
    }

    @Test
    void paquete_es_invalido_si_un_servicio_deja_de_ser_valido() {
        paquete_aruba.agregarItem(servicio_vuelo);
        assertTrue(paquete_aruba.validate());

        servicio_vuelo.agregarAtributo(new AtributoDinamico("Apto Mascotas", true, new TipoBooleano(), 4.5));

        assertFalse(paquete_aruba.validate());
    }

    @Test
    void paquete_es_invalido_si_un_paquete_anidado_deja_de_ser_valido() {
        Paquete paqueteInterno = paqueteInternoCon(servicio_auto);
        paquete_aruba.agregarItem(paqueteInterno);
        assertTrue(paquete_aruba.validate());

        paqueteInterno.agregarItem(servicio_excursion);
        servicio_excursion.agregarAtributo(new AtributoDinamico("Apto Mascotas", true, new TipoBooleano(), 4.5));

        assertFalse(paquete_aruba.validate());
    }

    //  -> Items

    @Test
    void paquete_nuevo_no_tiene_items() {
        assertEquals(0, paquete_aruba.cantidadItems());
    }

    @Test
    void paquete_agrega_servicio_valido_incrementa_cantidad() {
        paquete_aruba.agregarItem(servicio_vuelo);
        paquete_aruba.agregarItem(servicio_alojamiento);

        assertEquals(2, paquete_aruba.cantidadItems());
    }

    @Test
    void paquete_agrega_paquete_valido_incrementa_cantidad() {
        paquete_aruba.agregarItem(paqueteInternoCon(servicio_auto));

        assertEquals(1, paquete_aruba.cantidadItems());
    }

    @Test
    void paquete_agrega_servicio_invalido_tira_excepcion() {
        assertThrowsExactly(ServiceExceptions.MetodoInvalido.class,
                () -> paquete_aruba.agregarItem(servicioInvalido()));
        assertEquals(0, paquete_aruba.cantidadItems());
    }

    @Test
    void paquete_agrega_paquete_invalido_tira_excepcion() {
        Paquete paqueteInvalido = new Paquete("Pack inválido", null, 0.1, europa);
        paqueteInvalido.agregarItem(servicio_auto);

        assertThrowsExactly(ServiceExceptions.MetodoInvalido.class,
                () -> paquete_aruba.agregarItem(paqueteInvalido));
        assertEquals(0, paquete_aruba.cantidadItems());
    }

    @Test
    void paquete_agrega_paquete_vacio_tira_excepcion() {
        Paquete vacio = new Paquete("Pack Vacio","Pack Vacio", 0.1,  europa);

        assertThrowsExactly(ServiceExceptions.MetodoInvalido.class,
                () -> paquete_aruba.agregarItem(vacio));
    }

    @Test
    void paquete_remueve_item_existente_decrementa_cantidad() {
        paquete_aruba.agregarItem(servicio_vuelo);
        paquete_aruba.agregarItem(servicio_alojamiento);

        paquete_aruba.removerItem(servicio_vuelo);

        assertEquals(1, paquete_aruba.cantidadItems());
        assertEquals(27.625, paquete_aruba.precioBase(), DELTA);
    }

    @Test
    void paquete_remueve_item_inexistente_no_modifica_cantidad() {
        paquete_aruba.agregarItem(servicio_vuelo);

        paquete_aruba.removerItem(servicio_alojamiento);

        assertEquals(1, paquete_aruba.cantidadItems());
    }

    //  -> Estructura del Composite (contiene, ciclos, duplicados)

    @Test
    void paquete_contiene_a_si_mismo_y_a_sus_items_directos_e_indirectos() {
        Paquete paqueteInterno = paqueteInternoCon(servicio_auto);
        paquete_aruba.agregarItem(paqueteInterno);

        assertTrue(paquete_aruba.contiene(paquete_aruba));
        assertTrue(paquete_aruba.contiene(paqueteInterno));
        assertTrue(paquete_aruba.contiene(servicio_auto));
        assertFalse(paquete_aruba.contiene(servicio_vuelo));
    }

    @Test
    void paquete_no_puede_agregarse_a_si_mismo() {
        paquete_aruba.agregarItem(servicio_vuelo);

        assertThrowsExactly(CatalogoException.ErrorCiclico.class,
                () -> paquete_aruba.agregarItem(paquete_aruba));
    }

    @Test
    void paquete_no_puede_agregar_a_un_paquete_que_lo_contiene() {
        Paquete paqueteInterno = paqueteInternoCon(servicio_auto);
        paquete_aruba.agregarItem(paqueteInterno);

        assertThrowsExactly(CatalogoException.ErrorCiclico.class,
                () -> paqueteInterno.agregarItem(paquete_aruba));
    }

    @Test
    void paquete_no_acepta_un_item_que_ya_contiene() {
        paquete_aruba.agregarItem(servicio_vuelo);

        assertThrowsExactly(CatalogoException.ItemExistente.class,
                () -> paquete_aruba.agregarItem(servicio_vuelo));
    }

    @Test
    void paquete_no_acepta_un_item_que_ya_contiene_un_paquete_anidado() {
        paquete_aruba.agregarItem(paqueteInternoCon(servicio_auto));

        assertThrowsExactly(CatalogoException.ItemExistente.class,
                () -> paquete_aruba.agregarItem(servicio_auto));
    }

    //  -> Disponibilidad (solo las hojas tienen cupos; el paquete delega)

    @Test
    void paquete_vacio_no_esta_disponible() {
        assertFalse(paquete_aruba.disponible(tresDias, 1));
    }

    @Test
    void paquete_esta_disponible_si_todos_sus_items_lo_estan() {
        cargarCupo(servicio_vuelo, tresDias, 5);
        cargarCupo(servicio_alojamiento, tresDias, 3);
        paquete_aruba.agregarItem(servicio_vuelo);
        paquete_aruba.agregarItem(servicio_alojamiento);

        assertTrue(paquete_aruba.disponible(tresDias, 3));
        assertFalse(paquete_aruba.disponible(tresDias, 4)); // el alojamiento solo tiene 3
    }

    @Test
    void paquete_no_esta_disponible_si_un_item_no_tiene_cupo() {
        cargarCupo(servicio_vuelo, tresDias, 5);
        paquete_aruba.agregarItem(servicio_vuelo);
        paquete_aruba.agregarItem(servicio_alojamiento); // sin cupo cargado

        assertFalse(paquete_aruba.disponible(tresDias, 1));
    }

    @Test
    void paquete_no_esta_disponible_si_a_un_item_le_falta_cupo_un_dia() {
        cargarCupo(servicio_vuelo, tresDias, 5);
        cargarCupo(servicio_alojamiento, Periodo.dia(dia1), 5);
        cargarCupo(servicio_alojamiento, Periodo.dia(dia3), 5); // dia2 sin cupo
        paquete_aruba.agregarItem(servicio_vuelo);
        paquete_aruba.agregarItem(servicio_alojamiento);

        assertFalse(paquete_aruba.disponible(tresDias, 1));
    }

    @Test
    void paquete_anidado_esta_disponible_si_todas_sus_hojas_lo_estan() {
        cargarCupo(servicio_vuelo, tresDias, 5);
        cargarCupo(servicio_auto, tresDias, 2);
        paquete_aruba.agregarItem(servicio_vuelo);
        paquete_aruba.agregarItem(paqueteInternoCon(servicio_auto));

        assertTrue(paquete_aruba.disponible(tresDias, 2));
        assertFalse(paquete_aruba.disponible(tresDias, 3)); // el auto (hoja anidada) solo tiene 2
    }

    @Test
    void paquete_reservar_descuenta_cupo_en_cada_item() {
        cargarCupo(servicio_vuelo, tresDias, 5);
        cargarCupo(servicio_alojamiento, tresDias, 5);
        paquete_aruba.agregarItem(servicio_vuelo);
        paquete_aruba.agregarItem(servicio_alojamiento);

        paquete_aruba.reservar(tresDias, 2);

        tresDias.dias().forEach(dia -> {
            assertEquals(3, servicio_vuelo.disponibilidad().libres(dia));
            assertEquals(3, servicio_alojamiento.disponibilidad().libres(dia));
        });
    }

    @Test
    void paquete_reservar_llega_a_las_hojas_de_los_paquetes_anidados() {
        cargarCupo(servicio_vuelo, tresDias, 5);
        cargarCupo(servicio_auto, tresDias, 5);
        paquete_aruba.agregarItem(servicio_vuelo);
        paquete_aruba.agregarItem(paqueteInternoCon(servicio_auto));

        paquete_aruba.reservar(tresDias, 2);

        assertEquals(2, servicio_auto.disponibilidad().reservados(dia2));
        assertEquals(2, servicio_vuelo.disponibilidad().reservados(dia2));
    }

    @Test
    void paquete_reservar_sin_cupo_en_un_item_tira_excepcion_y_no_modifica_ninguno() {
        cargarCupo(servicio_vuelo, tresDias, 5);
        cargarCupo(servicio_alojamiento, tresDias, 1); // no alcanza para 2
        paquete_aruba.agregarItem(servicio_vuelo);
        paquete_aruba.agregarItem(servicio_alojamiento);

        assertThrowsExactly(DisponibilidadException.PeriodoSinDisponibilidad.class,
                () -> paquete_aruba.reservar(tresDias, 2));

        tresDias.dias().forEach(dia -> {
            assertEquals(0, servicio_vuelo.disponibilidad().reservados(dia));
            assertEquals(0, servicio_alojamiento.disponibilidad().reservados(dia));
        });
    }

    @Test
    void paquete_liberar_devuelve_el_cupo_a_cada_item() {
        cargarCupo(servicio_vuelo, tresDias, 5);
        cargarCupo(servicio_alojamiento, tresDias, 5);
        paquete_aruba.agregarItem(servicio_vuelo);
        paquete_aruba.agregarItem(servicio_alojamiento);
        paquete_aruba.reservar(tresDias, 2);

        paquete_aruba.liberar(tresDias, 2);

        tresDias.dias().forEach(dia -> {
            assertEquals(5, servicio_vuelo.disponibilidad().libres(dia));
            assertEquals(5, servicio_alojamiento.disponibilidad().libres(dia));
        });
    }

    @Test
    void paquete_cubre_un_periodo_si_todos_sus_items_operan_esos_dias() {
        cargarCupo(servicio_vuelo, tresDias, 5);
        cargarCupo(servicio_alojamiento, new Periodo(dia1, dia2), 5); // no opera el dia3
        paquete_aruba.agregarItem(servicio_vuelo);
        paquete_aruba.agregarItem(servicio_alojamiento);

        assertTrue(paquete_aruba.cubre(new Periodo(dia1, dia2)));
        assertFalse(paquete_aruba.cubre(tresDias));
    }

    //  -> Categoria

    @Test
    void unPaquetePerteneceALaCategoriaDeAlgunoDeSusItems() {
        paquete_aruba.agregarItem(servicio_alojamiento);

        assertTrue(paquete_aruba.perteneceA(Categoria.ALOJAMIENTO));
        assertFalse(paquete_aruba.perteneceA(Categoria.EXCURSION));
    }

    @Test
    void unPaqueteVacioNoPerteneceANingunaCategoria() {
        assertFalse(paquete_aruba.perteneceA(Categoria.ALOJAMIENTO));
    }
}
