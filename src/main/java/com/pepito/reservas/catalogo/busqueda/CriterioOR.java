package com.pepito.reservas.catalogo.busqueda;
 
import com.pepito.reservas.catalogo.ItemCatalogo;
 
import java.util.List;
import java.util.stream.Stream;
 
/*
 * Composite: el ítem debe cumplir AL MENOS UNO de los criterios.
 * El constructor pide al menos dos (2..*), y eso se garantiza al compilar.
 */
public class CriterioOR implements CriterioBusqueda {
 
    private final List<CriterioBusqueda> criterios;
 
    public CriterioOR(CriterioBusqueda primero, CriterioBusqueda segundo, CriterioBusqueda... otros) {
        this.criterios = List.copyOf(Stream.concat(Stream.of(primero, segundo), Stream.of(otros)).toList());
    }
 
    @Override
    public boolean cumple(ItemCatalogo item) {
        return criterios.stream().anyMatch(criterio -> criterio.cumple(item));
    }
}
