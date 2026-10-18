package com.pepito.reservas.catalogo;

import com.pepito.reservas.catalogo.busqueda.CriterioAND;
import com.pepito.reservas.catalogo.busqueda.CriterioBusqueda;
import com.pepito.reservas.catalogo.busqueda.PorCategoria;
import com.pepito.reservas.catalogo.busqueda.PorDisponibilidad;
import com.pepito.reservas.catalogo.busqueda.PorNombre;
import com.pepito.reservas.catalogo.busqueda.PorPrecioMaximo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.pepito.reservas.catalogo.ItemFixture.item;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CatalogoBusquedaTest {

    private Catalogo catalogo;
    private ItemCatalogo hotel;
    private ItemCatalogo hostel;
    private ItemCatalogo museo;
    private ItemCatalogo vuelo;

    @BeforeEach
    void setUp() {
        hotel = item("Hotel Central", 15000, Categoria.ALOJAMIENTO, true);
        hostel = item("Hostel del Centro", 8000, Categoria.ALOJAMIENTO, false);
        museo = item("Excursión Guiada Museo", 4000, Categoria.EXCURSION, true);
        vuelo = item("Vuelo Buenos Aires-Roma", 90000, Categoria.VUELO, true);

        catalogo = new Catalogo();
        // Se carga directo en la lista porque agregarItem() es del 2.1 y todavía no está implementado. Cuando lo esté, descomentar por catalogo.agregarItem(...).
        catalogo.items.addAll(List.of(hotel, hostel, museo, vuelo));
        //catalogo.agregarItem(hotel);
        //catalogo.agregarItem(hostel);
        //catalogo.agregarItem(museo);
        //catalogo.agregarItem(vuelo);

    }

    @Test
    void buscarDevuelveLosItemsQueCumplenElCriterioEnElOrdenDelCatalogo() {
        List<ItemCatalogo> resultado = catalogo.buscar(new PorCategoria(Categoria.ALOJAMIENTO));

        assertEquals(List.of(hotel, hostel), resultado);
    }

    @Test
    void buscarConUnCriterioCompuesto() {
        CriterioBusqueda alojamientoBaratoYDisponible = new CriterioAND(
                new PorCategoria(Categoria.ALOJAMIENTO), new PorPrecioMaximo(20000), new PorDisponibilidad());

        assertEquals(List.of(hotel), catalogo.buscar(alojamientoBaratoYDisponible));
    }

    @Test
    void buscarSinCoincidenciasDevuelveListaVacia() {
        assertTrue(catalogo.buscar(new PorNombre("resort")).isEmpty());
    }

    @Test
    void buscarEnUnCatalogoVacioDevuelveListaVacia() {
        assertTrue(new Catalogo().buscar(new PorDisponibilidad()).isEmpty());
    }

    @Test
    void laListaDevueltaEsUnaCopiaYNoModificaElCatalogo() {
        List<ItemCatalogo> resultado = catalogo.buscar(new PorDisponibilidad());

        resultado.clear();

        assertEquals(3, catalogo.buscar(new PorDisponibilidad()).size());
    }
}