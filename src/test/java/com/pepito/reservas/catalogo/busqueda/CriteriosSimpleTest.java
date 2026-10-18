package com.pepito.reservas.catalogo.busqueda;
 
import com.pepito.reservas.catalogo.Categoria;
import com.pepito.reservas.catalogo.ItemCatalogo;
import org.junit.jupiter.api.Test;
 
import static com.pepito.reservas.catalogo.ItemFixture.item;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
 
class CriteriosSimplesTest {
 
    private final ItemCatalogo hotel = item("Hotel Central", 15000, Categoria.ALOJAMIENTO, true);
    private final ItemCatalogo vuelo = item("Vuelo Buenos Aires-Roma", 90000, Categoria.VUELO, false);
 
    // ---- PorNombre ----
 
    @Test
    void porNombreEncuentraElTextoSinDistinguirMayusculas() {
        assertTrue(new PorNombre("hotel").cumple(hotel));
        assertTrue(new PorNombre("CENTRAL").cumple(hotel));
        assertTrue(new PorNombre("tel cen").cumple(hotel));
    }
 
    @Test
    void porNombreRechazaSiElTextoNoEstaContenido() {
        assertFalse(new PorNombre("resort").cumple(hotel));
    }
 
    @Test
    void porNombreNoAceptaTextoNulo() {
        assertThrows(NullPointerException.class, () -> new PorNombre(null));
    }
 
    // ---- PorPrecioMaximo ----
 
    @Test
    void porPrecioMaximoAceptaPreciosMenoresOIguales() {
        assertTrue(new PorPrecioMaximo(20000).cumple(hotel));
        assertTrue(new PorPrecioMaximo(15000).cumple(hotel));
    }
 
    @Test
    void porPrecioMaximoRechazaPreciosMayores() {
        assertFalse(new PorPrecioMaximo(14999).cumple(hotel));
        assertFalse(new PorPrecioMaximo(20000).cumple(vuelo));
    }
 
    // ---- PorCategoria ----
 
    @Test
    void porCategoriaAceptaSoloLaCategoriaIndicada() {
        assertTrue(new PorCategoria(Categoria.ALOJAMIENTO).cumple(hotel));
        assertFalse(new PorCategoria(Categoria.VUELO).cumple(hotel));
    }
 
    @Test
    void porCategoriaRechazaUnItemSinCategoria() {
        ItemCatalogo sinCategoria = item("Pack Mixto", 1000, null, true);
 
        assertFalse(new PorCategoria(Categoria.ALOJAMIENTO).cumple(sinCategoria));
    }
 
    @Test
    void porCategoriaNoAceptaCategoriaNula() {
        assertThrows(NullPointerException.class, () -> new PorCategoria(null));
    }
 
    // ---- PorDisponibilidad ----
 
    @Test
    void porDisponibilidadAceptaSoloItemsConCupo() {
        assertTrue(new PorDisponibilidad().cumple(hotel));
        assertFalse(new PorDisponibilidad().cumple(vuelo));
    }
}
