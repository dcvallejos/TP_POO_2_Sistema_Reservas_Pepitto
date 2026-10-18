package com.pepito.reservas.catalogo.busqueda;
 
import com.pepito.reservas.catalogo.ItemCatalogo;
 
import java.util.Locale;
import java.util.Objects;
 
/* Leaf: el nombre del ítem contiene el texto, sin distinguir mayúsculas. */

public class PorNombre implements CriterioBusqueda {
 
    private final String texto;
 
    public PorNombre(String texto) {
        this.texto = Objects.requireNonNull(texto, "El texto a buscar no puede ser nulo").toLowerCase(Locale.ROOT);
    }
 
    @Override
    public boolean cumple(ItemCatalogo item) {
        return item.nombre().toLowerCase(Locale.ROOT).contains(texto);
    }
}