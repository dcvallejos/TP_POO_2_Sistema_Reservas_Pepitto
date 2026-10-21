package com.pepito.reservas.catalogo;

import com.pepito.reservas.servicio.Validador;

public interface ItemCatalogo extends Validador {

    public Double precioBase();
    public Double descuento();
    public Double precioFinal();
    void bloquear(int cantidad);
    void liberar(int cantidad);

    @Override
    public Boolean validate();

}
