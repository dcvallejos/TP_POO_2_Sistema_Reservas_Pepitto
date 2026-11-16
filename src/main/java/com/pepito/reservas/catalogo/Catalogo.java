package com.pepito.reservas.catalogo;

import com.pepito.reservas.catalogo.busqueda.CriterioBusqueda;
import com.pepito.reservas.catalogo.servicio.GestorServicios;
import com.pepito.reservas.catalogo.servicio.Periodo;

import java.util.ArrayList;
import java.util.List;

/**
 * Catalogo
 * Cliente del patrón Composite y del Composite de criterios de búsqueda
 *
 * Descripción
 *  Ofrece una Lista de ItemsCatalogo. Permite operar en altas, bajas y consultas de items y permite búsquedas con
 *  criterio. Como cliente, se maneja con el contrato de la Clase Componente ItemCatalogo, dirigiendose tanto a
 *  Paquetes como Servicios.
 *
 * Atributos
 *  - items:        (List<ItemCatalogo>) Son los items publicados en el catáogo, independientemente de su
 *  disponibilidad. Solo se modifican a través de sus metodos agregarItem y removerItem.
 *
 * Decision:
 *  - Se definió que la búsqueda reciba un UNICO CriterioBusqueda y no decide nada sobre como ejecutar la evaluación.
 *  Esto se hizo para respetar el PRS y que la clase que realizó el criterio sepa como se armó. Se devuelve una copia
 *  del item o lista de items para que nadie agregue items sin validaciones.
* */

public class Catalogo implements GestorServicios {

    List<ItemCatalogo> items;


    public Catalogo(){
        this.items = new ArrayList<>();
    }

    public List<ItemCatalogo> getCatalogo() {
        return List.copyOf(items);
    }

    @Override
    public void agregarItem(ItemCatalogo item){
        /** Agrega un ítem si no existe y si pasa las validaciones propias.
         * Retorna:
         *  - ErrorItemInvalido si no pasa la validacion.
         *  - ItemExistente si ya existe en el catálogo
         *  */
        if(contieneItem(item)){
            throw new CatalogoException.ItemExistente(item);
        }

        if(!item.validate()){
            throw new CatalogoException.ErrorItemInvalido(item);
        }

        this.items.add(item);
    }

    public Boolean contieneItem(ItemCatalogo itemBuscado){
        return this.items.stream().anyMatch(itemCatalogo -> itemCatalogo.equals(itemBuscado));
    }

    @Override
    public void removerItem(ItemCatalogo item){
        if(!contieneItem(item)){
            throw new CatalogoException.ItemNoEncontrado(item);
        }

        this.items.remove(item);
    }

    @Override
    public Integer cantidadItems() {
        return this.items.size();
    }

    public List<ItemCatalogo> buscar(CriterioBusqueda criterio) {
        // Obtenes una lista de ItemsCatalogo que cada uno cumple con el criterio impuesto
        return items.stream()
                .filter(criterio::cumple)
                .toList();
    }

    public List<ItemCatalogo> buscarDisponibles(CriterioBusqueda criterio, Periodo fechas, Integer cantidad) {
        /**
         * Metodo polimórfico de búsqueda en donde verificas disponibilidad para un rango de fechas para cualquier
         * objeto ItemCatalogo.
         * Por otro lado verifica si tiene la cantidad mínima de disponibilidad
         *  */
        
        return buscar(criterio).stream()
                .filter(item -> item.disponible(fechas, cantidad))
                .toList();
    }


}
