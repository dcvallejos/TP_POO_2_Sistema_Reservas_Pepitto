package com.pepito.reservas.servicio;

import com.pepito.reservas.catalogo.ItemCatalogo;
import com.pepito.reservas.excepciones.ServiceExceptions;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Paquete extends ItemCatalogo implements GestorServicios {

    String descripcion;
    Double precioBase;
    List<ItemCatalogo> servicios;

    public Paquete(
            String nombre,
            Double descuento,
            String descripcion,
            Double precioBase
        )
    {
        if (precioBase < 0.0){throw new ServiceExceptions.ErrorCreacionPaquete(nombre, "PrecioBase no puede ser negativo");}
        if (descuento < 0.0 || descuento > 1.0 ){throw new ServiceExceptions.ErrorCreacionPaquete(nombre, "Descuento puede tener un valor entre 0.0 y 1.0");}

        super(nombre, descuento);
        this.descripcion = descripcion;
        this.precioBase = precioBase;
        this.servicios = new ArrayList<>(List.of());;
    }

    @Override
    public Double precioBase() {
        return servicios.stream().mapToDouble(ItemCatalogo::precioFinal).sum();
    }

    @Override
    public Double descuento() {
        return 1 - descuento;
    }

    @Override
    public Double precioFinal() {
        return this.precioBase() * this.descuento();
    }

    @Override
    public Boolean disponible(LocalDate fecha, Integer cantidad) {
        return null;
    }

    @Override
    public void reservar(LocalDate fecha, Integer cantidad) {

    }

    @Override
    public Boolean validate() throws ExceptionInInitializerError {
        if (
                nombre == null
                        || descripcion == null
                        || precioBase == null
                        || descuento == null
        ){
            return false;
        }

        return servicios.stream().allMatch(ItemCatalogo::validate);
    }

    @Override
    public void agregarItem(ItemCatalogo item) throws ServiceExceptions {

        if(item.validate()){
            this.servicios.add(item);
        }
        else{
            throw new ServiceExceptions.MetodoInvalido("Agregar Servicio", "No se pudo agregar el " + item.getClass().getName()) ;
        }

    }

    @Override
    public void removerItem(ItemCatalogo item) {
        this.servicios.remove(item);
    }

    @Override
    public Integer cantidadItems(){
        return servicios.size();
    }


}
