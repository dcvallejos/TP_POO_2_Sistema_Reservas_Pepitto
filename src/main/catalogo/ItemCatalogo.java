package main.catalogo;

import main.servicio.Validador;

public interface ItemCatalogo extends Validador {

    public Double precioBase();
    public Double descuento();
    public Double preciFinal();

    @Override
    public Boolean validate();

}
