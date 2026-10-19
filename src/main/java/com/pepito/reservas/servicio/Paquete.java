package com.pepito.reservas.servicio;

import com.pepito.reservas.catalogo.ItemCatalogo;

import java.util.ArrayList;
import java.util.List;

public class Paquete implements ItemCatalogo {

    String nombre;
    String descripcion;
    Double precioBase;
    Double descuento;
    List<ItemCatalogo> servicios;

    public Paquete(
            String nombre,
            String descripcion,
            Double precioBase,
            Double descuento) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precioBase = precioBase;
        this.descuento = descuento;
        this.servicios = new ArrayList<>(List.of());
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

    public void agregarItem(ItemCatalogo item) throws Exception {

        if(item.validate()){
            this.servicios.add(item);
        }
        else{
            throw new Exception("No se encuentra correctamente inicializado el servicio. Verifique que los atributos esten completos, y sus tipos declarados sean los correspondientes");
        }

    }

    public Integer cantidadItems(){
        return servicios.size();
    }

    public void eliminarItem(ItemCatalogo item){

        this.servicios.remove(item);
    }
}
