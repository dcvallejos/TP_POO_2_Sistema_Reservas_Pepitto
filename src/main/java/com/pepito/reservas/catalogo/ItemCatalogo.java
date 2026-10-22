package com.pepito.reservas.catalogo;

import com.pepito.reservas.servicio.Servicio;

import java.time.LocalDate;
import java.util.Set;

public abstract class ItemCatalogo implements Validador {

    protected String nombre;
    protected Double descuento;

    public ItemCatalogo(String nombre, Double descuento) {
        this.nombre = nombre;
        this.descuento = descuento;
    }

    public abstract Double precioBase();
    public abstract Double descuento();
    public abstract Double precioFinal();
    public abstract Boolean disponible(LocalDate fecha, Integer cantidad);
    public abstract void reservar(LocalDate fecha, Integer cantidad);
}
