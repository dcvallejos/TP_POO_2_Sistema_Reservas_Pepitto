package com.pepito.reservas.catalogo;

import com.pepito.reservas.servicio.Disponibilidad;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;

public abstract class ItemCatalogo implements Validador {

    protected String nombre;
    protected Double descuento;
    protected Disponibilidad disponibilidad;

    public ItemCatalogo(String nombre, Double descuento, Disponibilidad disponibilidad) {
        this.nombre = nombre;
        this.descuento = descuento;
        this.disponibilidad = disponibilidad;
    }

    public abstract Double precioBase();
    public abstract Double descuento();
    public abstract Double precioFinal();
}
