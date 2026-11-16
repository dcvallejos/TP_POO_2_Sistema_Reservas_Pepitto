package com.pepito.reservas.catalogo.servicio;

import com.pepito.reservas.catalogo.ItemCatalogo;
import com.pepito.reservas.excepciones.DisponibilidadException;
import com.pepito.reservas.excepciones.ServiceExceptions;
import com.pepito.reservas.catalogo.servicio.atributos.AtributoDinamico;
import com.pepito.reservas.destino.LugarGeografico;

import java.util.ArrayList;
import java.util.List;

/*
 * Servicio
 * Seccion Leaf del patrón
 *
 * Descripcion:
 *   Servicio representa el nivel concreto de ItemCatalogo. Representría a los servicios individuales.
 *   Asimismo, respetando el patron, todos los metodos tiene una funcionalidad final, alterando u operando según sus
 *      propios estados.
 *
 * Atributos
 *   - Nombre:                  (String) Nombre del servicio en cuestion. Ej: "Aereo" "Hotel"
 *   - Descripción:             (String) Breve descripcion de que trata el servicio
 *   - PrecioBase:              (Double) Precio sin descuento del servicio.
 *   - Descuento:               (Double) El descuento se inicializa como el porcentaje individual que se carga al valor
 *                                  Se inicializa con el descuento en cuestion (ej. 35% off, representaria 0.35).
 *   - Destino:                 (LugarGeografico) Representa el destino del servicio. Sirve como criterio de búsqueda.
 *   - Categoria:               (Categoria) Representa la categoria del servicio. Sirve como criterio de busqueda.
 *   - Disponibildiad           (Disponibilidad) Representa un objeto al que se le delega el cupo y la ocupación de un servicio
 *                                  en una colección de fechas.
 *   - AtributosAdicionales     (AtributoDinamico): Lista de atributos adicionales que enriquecen o dan información
 *                                  adicional del servicio. Declaran tipo, valor y obligatoriedad.
 *
 * */

public class Servicio extends ItemCatalogo {
    String codigoReserva;
    String proveedor;
    Categoria categoria;
    Double precioBase;
    Disponibilidad disponibilidad;

    List<AtributoDinamico> atributosAdicionales;

    public Servicio(
            String nombre,
            String descripcion,
            Double descuento,
            Disponibilidad disponibilidad,
            String codigoReserva,
            String proveedor,
            Categoria categoria,
            Double precioBase,
            LugarGeografico destino
        )
    {
        super(nombre, descripcion, descuento, destino);
        if (precioBase < 0.0){throw new ServiceExceptions.ErrorCreacionServicio(nombre, "PrecioBase no puede ser negativo");}
        if (descuento < 0.0 || descuento > 1.0 ){throw new ServiceExceptions.ErrorCreacionServicio(nombre, "Descuento puede tener un valor entre 0.0 y 1.0");}

        this.codigoReserva = codigoReserva;
        this.proveedor = proveedor;
        this.categoria = categoria;
        this.precioBase = precioBase;
        this.atributosAdicionales = new ArrayList<>(List.of());
        this.disponibilidad = disponibilidad;
    }

    public Disponibilidad disponibilidad() {
        return disponibilidad;
    }

    @Override
    public Boolean perteneceA(Categoria categoria) {
        return this.categoria == categoria;
    }

    @Override
    public Boolean cubre(Periodo periodo) {
        return disponibilidad.cubre(periodo);
    }

    @Override
    public Boolean disponible(Periodo fechas, Integer cantidad) {
        return disponibilidad.disponible(fechas, cantidad);
    }

    @Override
    public boolean contiene(ItemCatalogo otro) { return this == otro; }

    @Override
    public boolean comparteServicios(ItemCatalogo otro) {
        return otro.contiene(this);
    }

    @Override
    public void reservar(Periodo fechas, Integer cantidad) {
        disponibilidad.bloquear(fechas, cantidad);
    }


    @Override
    public boolean liberable(Periodo fechas, Integer cantidad){
        return disponibilidad.liberable(fechas,cantidad);
    }
    @Override
    public void liberar(Periodo fechas, Integer cantidad) {
        if (liberable(fechas, cantidad))
            disponibilidad.liberar(fechas, cantidad);
        else
            throw new DisponibilidadException.LiberacionInvalida(fechas,cantidad);
    }

    @Override
    public Double precioBase() {
        return precioBase;
    }

    @Override
    public Double descuento() {
        return 1 - this.descuento;
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
                    codigoReserva == null
                ||  codigoReserva.isBlank()
                ||  nombre == null
                ||  nombre.isBlank()
                ||  proveedor.isBlank()
                ||  precioBase < 0.0
        )
        {
            return false;
        }

        return atributosAdicionales.stream().allMatch(AtributoDinamico::validate);
    }

}
