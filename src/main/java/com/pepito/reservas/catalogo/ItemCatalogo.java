package com.pepito.reservas.catalogo;

import com.pepito.reservas.servicio.Disponibilidad;
import com.pepito.reservas.servicio.Periodo;

import java.time.LocalDate;

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
    public abstract Boolean disponible(Periodo fechas, Integer cantidad);
    public abstract Boolean disponible(LocalDate fecha, Integer cantidad);
    public abstract void reservar(Periodo fechas, Integer cantidad);
    public abstract void reservar(LocalDate fecha, Integer cantidad);
    public abstract Disponibilidad disponibilidad();
    public Boolean cubre(Periodo periodo) {
        return disponibilidad.cubre(periodo);
    }
}
