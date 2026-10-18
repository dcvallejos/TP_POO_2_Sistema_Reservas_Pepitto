package com.pepito.reservas.catalogo.busqueda;
 
import com.pepito.reservas.catalogo.ItemCatalogo;
 
/* Component del patrón Composite: tanto los criterios simples como los compuestos (And, Or, Not) responden a este mismo contrato. */

public interface CriterioBusqueda {
 
    boolean cumple(ItemCatalogo item);
 
}