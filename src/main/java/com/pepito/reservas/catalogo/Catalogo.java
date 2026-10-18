package com.pepito.reservas.catalogo;
 
import com.pepito.reservas.catalogo.busqueda.CriterioBusqueda;
import com.pepito.reservas.servicio.GestorServicios;
 
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
 
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
 
    /*
     * El catálogo no decide cómo evaluar los ítems: esa responsabilidad es del criterio recibido.
     * Devuelve una lista nueva, así quien la reciba no puede modificar el catálogo.
     */
    
    public List<ItemCatalogo> buscar(CriterioBusqueda criterio){
        return items.stream()
                .filter(criterio::cumple)
                .collect(Collectors.toCollection(ArrayList::new));
    }
 
}