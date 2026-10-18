package com.pepito.reservas.catalogo;
 
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
 
/** Helper de tests: arma ítems simulados con los datos que usa la búsqueda. */
public final class ItemFixture {
 
    private ItemFixture() {
    }
 
    public static ItemCatalogo item(String nombre, double precioBase, Categoria categoria, boolean disponible) {
        ItemCatalogo item = mock(ItemCatalogo.class);
        when(item.nombre()).thenReturn(nombre);
        when(item.precioBase()).thenReturn(precioBase);
        when(item.categoria()).thenReturn(categoria);
        when(item.tieneDisponibilidad()).thenReturn(disponible);
        return item;
    }
}
