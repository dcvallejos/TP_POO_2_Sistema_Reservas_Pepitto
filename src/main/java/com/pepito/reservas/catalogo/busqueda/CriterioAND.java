package com.pepito.reservas.catalogo.busqueda;
 
import com.pepito.reservas.catalogo.ItemCatalogo;
 
import java.util.List;
import java.util.stream.Stream;
 
/*
 * Composite: el ítem debe cumplir TODOS los criterios.
 * El constructor pide al menos 2 argumentos. "CriterioBusqueda... otros" permite agregar mas de ser necesario.
 */

public class CriterioAND implements CriterioBusqueda {
 
    private final List<CriterioBusqueda> criterios;
 
    public CriterioAND(CriterioBusqueda primero, CriterioBusqueda segundo, CriterioBusqueda... otros) {
        this.criterios = List.copyOf(Stream.concat(Stream.of(primero, segundo), Stream.of(otros)).toList());
    }
 
    @Override
    public boolean cumple(ItemCatalogo item) {
        return criterios.stream().allMatch(criterio -> criterio.cumple(item));
    }
}
