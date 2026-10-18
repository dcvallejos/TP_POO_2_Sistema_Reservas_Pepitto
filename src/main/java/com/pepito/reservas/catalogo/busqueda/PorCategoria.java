package com.pepito.reservas.catalogo.busqueda;
 
import com.pepito.reservas.catalogo.Categoria;
import com.pepito.reservas.catalogo.ItemCatalogo;
 
import java.util.Objects;
 
/* Leaf: el ítem pertenece a la categoría dada. */
/* Actualizar segun manejo de Categoria. */

public class PorCategoria implements CriterioBusqueda {
 
    private final Categoria categoria;
 
    public PorCategoria(Categoria categoria) {
        this.categoria = Objects.requireNonNull(categoria, "La categoría no puede ser nula");
    }
 
    @Override
    public boolean cumple(ItemCatalogo item) {
        return this.categoria == item.categoria();
    }
}