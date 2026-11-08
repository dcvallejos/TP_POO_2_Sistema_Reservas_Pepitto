package com.pepito.reservas.catalogo.servicio;

import com.pepito.reservas.catalogo.CatalogoException;
import com.pepito.reservas.catalogo.ItemCatalogo;
import com.pepito.reservas.excepciones.DisponibilidadException;
import com.pepito.reservas.excepciones.ServiceExceptions;
import com.pepito.reservas.destino.LugarGeografico;

import java.util.ArrayList;
import java.util.List;

public class Paquete extends ItemCatalogo implements GestorServicios {

    String descripcion;
    List<ItemCatalogo> servicios;

    public Paquete(
            String nombre,
            String descripcion,
            Double descuento,
            LugarGeografico destino
    ) {
        if (descuento < 0.0 || descuento > 1.0) {
            throw new ServiceExceptions.ErrorCreacionPaquete(nombre, "Descuento puede tener un valor entre 0.0 y 1.0");
        }

        super(nombre, descripcion, descuento, destino);
        this.descripcion = descripcion;
        this.servicios = new ArrayList<>();
    }

    @Override
    public Boolean perteneceA(Categoria categoria) {
        return servicios.stream().anyMatch(item -> item.perteneceA(categoria));
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
    public Boolean disponible(Periodo fechas, Integer cantidad) {
        return !servicios.isEmpty()
                && servicios.stream().allMatch(i -> i.disponible(fechas, cantidad));
    }

    @Override
    public void reservar(Periodo fechas, Integer cantidad) {
        if (!disponible(fechas, cantidad)) {
            throw new DisponibilidadException.PeriodoSinDisponibilidad(fechas);
    }
        servicios.forEach(i -> i.reservar(fechas, cantidad));
    }

    @Override
    public boolean liberable(Periodo fechas, Integer cantidad) {
        return servicios.stream().allMatch(i->i.liberable(fechas,cantidad));
    }
    @Override
    public void liberar(Periodo fechas, Integer cantidad) {
        if(cantidad <= 0 ) throw new ServiceExceptions.CupoInvalido(cantidad);

        if (servicios.stream().allMatch(i -> i.liberable(fechas,cantidad)))
            servicios.forEach(i -> i.liberar(fechas, cantidad));
        else
            throw new DisponibilidadException.LiberacionInvalida(fechas,cantidad);
    }


    @Override
    public Boolean validate() {
        return
                nombre != null
                && !nombre.isBlank()
                && descripcion != null
                && !descripcion.isBlank()
                && !servicios.isEmpty()
                && servicios.stream().allMatch(Validador::validate);
    }

    @Override
    public Boolean cubre(Periodo periodo) {
        return servicios.stream().allMatch(i -> i.cubre(periodo));
    }

    @Override
    public boolean contiene(ItemCatalogo otro) {
        return this == otro || servicios.stream().anyMatch(i -> i.contiene(otro));
    }

    @Override
    public boolean comparteServicios(ItemCatalogo otro) {
        return servicios.stream().anyMatch(i -> i.comparteServicios(otro));
    }

    @Override
    public void agregarItem(ItemCatalogo item) {

        if (item.contiene(this)) throw new CatalogoException.ErrorCiclico(item);

        if (this.contiene(item)) throw new CatalogoException.ItemExistente(item);

        if (this.comparteServicios(item)) throw new CatalogoException.ItemExistente(item);

        if (!item.validate()) throw new ServiceExceptions.MetodoInvalido("Agregar Servicio", "No se pudo agregar el " + item.getClass().getName());

        this.servicios.add(item);

    }


    @Override
    public void removerItem(ItemCatalogo item) {
        this.servicios.remove(item);
    }

    @Override
    public Integer cantidadItems() {
        return servicios.size();
    }
}