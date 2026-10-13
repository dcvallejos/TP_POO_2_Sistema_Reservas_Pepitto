package main.catalogo;

import main.catalogo.busqueda.CriterioBusqueda;
import main.servicio.GestorServicios;

import java.util.ArrayList;
import java.util.List;

public class Catalogo implements GestorServicios {

    List<ItemCatalogo> items;

    public Catalogo(){
        this.items = new ArrayList<ItemCatalogo>();
    }

    @Override
    public void agregarItem(ItemCatalogo item){

    }

    @Override
    public void removerItem(ItemCatalogo item){

    }

    public List<ItemCatalogo> buscar(CriterioBusqueda criterio){
        return new ArrayList<>(List.of()); // Placeholder para que no chille
    }



}
