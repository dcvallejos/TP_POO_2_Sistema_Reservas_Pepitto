package com.pepito.reservas.catalogo.busqueda;
 
import com.pepito.reservas.catalogo.ItemCatalogo;
 
import java.util.Objects;
 
/* Composite: el ítem NO debe cumplir el criterio envuelto. */

public class CriterioNOT implements CriterioBusqueda {
 
    private final CriterioBusqueda criterio;
 
    public CriterioNOT(CriterioBusqueda criterio) {
        this.criterio = Objects.requireNonNull(criterio, "El criterio a negar no puede ser nulo");
    }
 
    @Override
    public boolean cumple(ItemCatalogo item) {
        return !criterio.cumple(item);
    }
}
