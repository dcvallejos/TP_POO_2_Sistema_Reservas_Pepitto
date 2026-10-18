package com.pepito.reservas.catalogo.busqueda;
 
import com.pepito.reservas.catalogo.ItemCatalogo;
 
/* Leaf: el ítem tiene cupos disponibles en al menos una fecha/destino. */

public class PorDisponibilidad implements CriterioBusqueda {
 
    @Override
    public boolean cumple(ItemCatalogo item) {
        return item.tieneDisponibilidad(); //Actualizar segun manejo de Cupos.
    }
}
