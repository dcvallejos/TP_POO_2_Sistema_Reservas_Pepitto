package com.pepito.reservas.catalogo;

import com.pepito.reservas.catalogo.servicio.Periodo;
import com.pepito.reservas.catalogo.servicio.Validador;
import com.pepito.reservas.destino.LugarGeografico;
import com.pepito.reservas.catalogo.servicio.Categoria;

public abstract class ItemCatalogo implements Validador {

    protected String nombre;
    protected String descripcion;
    protected Double descuento;
    protected LugarGeografico destino;


    public ItemCatalogo(String nombre, String descripcion, Double descuento, LugarGeografico destino) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.descuento = descuento;
        this.destino = destino;
    }

    public abstract Double precioBase();
    public abstract Double descuento();
    public abstract Double precioFinal();
    public abstract void reservar(Periodo fechas, Integer cantidad);
    public abstract void liberar(Periodo fechas, Integer cantidad);
    public abstract boolean liberable(Periodo fechas, Integer cantidad);
    public abstract Boolean perteneceA(Categoria categoria);
    public abstract Boolean cubre(Periodo periodo);
    public abstract boolean contiene(ItemCatalogo otro);
    public abstract boolean comparteServicios(ItemCatalogo otro);
    public abstract Boolean disponible(Periodo fechas, Integer cantidad);

    // Pre-implementados (comunes a todos)
    public String nombre(){return nombre;}
    public String descripcion(){return descripcion;}
    public LugarGeografico getDestino() {return destino;}
}
