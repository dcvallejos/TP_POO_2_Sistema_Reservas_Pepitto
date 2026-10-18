package com.pepito.reservas.catalogo.busqueda;

import com.pepito.reservas.catalogo.Categoria;
import com.pepito.reservas.catalogo.ItemCatalogo;
import org.junit.jupiter.api.Test;

import static com.pepito.reservas.catalogo.ItemFixture.item;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CriteriosCompuestosTest {

    private final ItemCatalogo hotel = item("Hotel Central", 15000, Categoria.ALOJAMIENTO, true);
    private final ItemCatalogo museo = item("Excursión Guiada Museo", 4000, Categoria.EXCURSION, true);
    private final ItemCatalogo vuelo = item("Vuelo Buenos Aires-Roma", 90000, Categoria.VUELO, false);
    private final ItemCatalogo oferta = item("Oferta Relámpago", 50000, Categoria.VUELO, false);

    private final ItemCatalogo packUrbana = item("Pack Escapada Urbana", 18900, Categoria.ALOJAMIENTO, true);
    private final ItemCatalogo packMuseo = item("Pack Museo", 4000, Categoria.EXCURSION, true);
    private final ItemCatalogo packGranTour = item("Pack Gran Tour", 30000, Categoria.ALOJAMIENTO, true);

    private CriterioBusqueda siempreVerdadero() {
        CriterioBusqueda criterio = mock(CriterioBusqueda.class);
        when(criterio.cumple(any())).thenReturn(true);
        return criterio;
    }

    private CriterioBusqueda siempreFalso() {
        return mock(CriterioBusqueda.class); // por defecto devuelve false
    }

    // ---- And ----

    @Test
    void andCumpleSoloSiCumplenTodosLosCriterios() {
        CriterioBusqueda alojamientoBaratoDisponible = new CriterioAND(new PorCategoria(Categoria.ALOJAMIENTO), new PorPrecioMaximo(20000), new PorDisponibilidad());

        assertTrue(alojamientoBaratoDisponible.cumple(hotel));
        assertFalse(alojamientoBaratoDisponible.cumple(museo));
        assertFalse(alojamientoBaratoDisponible.cumple(vuelo));
    }

    @Test
    void andSeDetieneEnElPrimerCriterioQueFalla() {
        CriterioBusqueda posterior = siempreVerdadero();

        assertFalse(new CriterioAND(siempreFalso(), posterior).cumple(hotel));

        verify(posterior, never()).cumple(any());
    }

    // ---- Or ----

    @Test
    void orCumpleSiCumpleAlMenosUnCriterio() {
        CriterioBusqueda hotelOResort = new CriterioOR(new PorNombre("hotel"), new PorNombre("resort"));

        assertTrue(hotelOResort.cumple(hotel));
        assertFalse(hotelOResort.cumple(museo));
    }

    @Test
    void orSeDetieneEnElPrimerCriterioQueSeCumple() {
        CriterioBusqueda posterior = siempreFalso();

        assertTrue(new CriterioOR(siempreVerdadero(), posterior).cumple(hotel));

        verify(posterior, never()).cumple(any());
    }

    @Test
    void orFallaSiNingunCriterioSeCumple() {
        assertFalse(new CriterioOR(siempreFalso(), siempreFalso(), siempreFalso()).cumple(hotel));
    }

    // ---- Not ----

    @Test
    void notInvierteElResultadoDelCriterio() {
        CriterioBusqueda noExcursion = new CriterioNOT(new PorCategoria(Categoria.EXCURSION));

        assertTrue(noExcursion.cumple(hotel));
        assertFalse(noExcursion.cumple(museo));
    }

    // ---- Anidamiento (ejemplos del enunciado) ----

    @Test
    void alojamientoDisponibleONombreConOferta() {
        CriterioBusqueda criterio = new CriterioOR(
                new CriterioAND(new PorCategoria(Categoria.ALOJAMIENTO), new PorDisponibilidad()),
                new PorNombre("oferta"));

        assertTrue(criterio.cumple(hotel));
        assertTrue(criterio.cumple(oferta));
        assertFalse(criterio.cumple(vuelo));
    }

    @Test
    void packQueNoSeaExcursionYCuesteHasta25000() {
        CriterioBusqueda criterio = new CriterioAND(
                new PorNombre("pack"),
                new CriterioNOT(new PorCategoria(Categoria.EXCURSION)),
                new PorPrecioMaximo(25000));

        assertTrue(criterio.cumple(packUrbana));
        assertFalse(criterio.cumple(packMuseo));
        assertFalse(criterio.cumple(packGranTour));
    }

    @Test
    void losCompuestosSePuedenAnidarEnCualquierProfundidad() {
        CriterioBusqueda noHotel = new CriterioNOT(new PorNombre("hotel"));
        CriterioBusqueda criterio = new CriterioNOT(
                new CriterioNOT(new CriterioAND(noHotel, new PorDisponibilidad())));

        assertTrue(criterio.cumple(museo));
        assertFalse(criterio.cumple(hotel));
        assertFalse(criterio.cumple(vuelo));
    }

    // ---- Validaciones ----

    @Test
    void loCompuestosNoAceptanCriteriosNulos() {
        CriterioBusqueda cualquiera = siempreVerdadero();

        assertThrows(NullPointerException.class, () -> new CriterioAND(null, cualquiera));
        assertThrows(NullPointerException.class, () -> new CriterioOR(cualquiera, null));
        assertThrows(NullPointerException.class,
                () -> new CriterioAND(cualquiera, cualquiera, (CriterioBusqueda) null));
        assertThrows(NullPointerException.class, () -> new CriterioNOT(null));
    }
}