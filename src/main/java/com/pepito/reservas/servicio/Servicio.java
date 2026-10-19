package com.pepito.reservas.servicio;

import com.pepito.reservas.catalogo.ItemCatalogo;
import com.pepito.reservas.excepciones.ServiceExceptions;
import com.pepito.reservas.servicio.atributos.AtributoDinamico;

import java.util.ArrayList;
import java.util.List;

public class Servicio implements ItemCatalogo {

    String codigoReserva;
    String nombre;
    String proveedor;
    Categoria categoria;
    Double precioBase;
    Double descuento;
    List<AtributoDinamico> atributosAdicionales;

    public Servicio(
            String codigoReserva,
            String nombre,
            String proveedor,
            Categoria categoria,
            Double precioBase,
            Double descuento) throws ServiceExceptions {
        // Validación de reglas de creación
        if (precioBase < 0.0){throw new ServiceExceptions.ErrorCreacionServicio(nombre, "PrecioBase no puede ser negativo");}
        if (descuento < 0.0 || descuento > 1.0 ){throw new ServiceExceptions.ErrorCreacionServicio(nombre, "Descuento puede tener un valor entre 0.0 y 1.0");}

        this.codigoReserva = codigoReserva;
        this.nombre = nombre;
        this.proveedor = proveedor;
        this.categoria = categoria;
        this.precioBase = precioBase;
        this.descuento = descuento;
        this.atributosAdicionales = new ArrayList<>(List.of());
    }

    @Override
    public Double precioBase() {
        return precioBase;
    }

    @Override
    public Double descuento() {
        return 1 - descuento;
    }

    @Override
    public Double precioFinal() {
        return precioBase * descuento();
    }

    public void agregarAtributo(AtributoDinamico atributo){
        this.atributosAdicionales.add(atributo);
    }

    public Integer cantidadAtributos(){
        return atributosAdicionales.size();
    }

    public void eliminarAtributo(AtributoDinamico atributo) throws ServiceExceptions{

        if (!atributosAdicionales.contains(atributo)){throw new ServiceExceptions.MetodoInvalido("EliminarAtributo", "El atributo a remover no se encuentra dentro del Servicio");}

        this.atributosAdicionales.remove(atributo);
    }

    @Override
    public Boolean validate() throws ServiceExceptions{
        if (
                codigoReserva.isBlank()
                || nombre.isBlank()
                || proveedor.isBlank()
                || precioBase == 0.0
        )
        {
            return false;
        }

        return atributosAdicionales.stream().allMatch(AtributoDinamico::validate);
    }
}
