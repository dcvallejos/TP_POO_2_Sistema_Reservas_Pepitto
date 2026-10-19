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
    public void agregarItem(Validador item){

    }

    @Override
    public void removerItem(Validador item){

    }

    public List<Validador> buscar(CriterioBusqueda criterio){
        return new ArrayList<>(List.of()); // Placeholder para que no chille
    }



}
