package main.servicio;

import main.catalogo.ItemCatalogo;

public interface GestorServicios {

    public void agregarItem(ItemCatalogo item);

    public void removerItem(ItemCatalogo item);

}
