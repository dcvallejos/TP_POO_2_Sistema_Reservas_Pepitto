package com.pepito.reservas.catalogo;
 
import com.pepito.reservas.servicio.Validador;
 
public interface ItemCatalogo extends Validador {
 
    public Double precioBase();
    public Double descuento();
    public Double getPrecioFinal();
 
    /* Agregados para el 2.6 (búsqueda). Refactorizar al hacer el 2.1. */
    
    public String nombre();
    public Categoria categoria();
    public boolean tieneDisponibilidad();
 
    @Override
    public Boolean validate();
 
}
