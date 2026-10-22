package com.pepito.reservas.catalogo;

import com.pepito.reservas.catalogo.busqueda.CriterioBusqueda;
import com.pepito.reservas.servicio.GestorServicios;

import java.util.ArrayList;
import java.util.List;

public class Catalogo implements GestorServicios {

    List<Validador> items;

    public Catalogo(){
        this.items = new ArrayList<Validador>();
    }

    @Override
    public void agregarItem(ItemCatalogo item){

    }

    @Override
    public void removerItem(ItemCatalogo item){

    }

    @Override
    public Integer cantidadItems() {
        return 0;
    }

    public List<Validador> buscar(CriterioBusqueda criterio){
        return new ArrayList<>(List.of()); // Placeholder para que no chille
    }



}
