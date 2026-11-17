package com.pepito.reservas.catalogo;

import com.pepito.reservas.catalogo.servicio.Periodo;
import com.pepito.reservas.catalogo.servicio.Validador;
import com.pepito.reservas.destino.LugarGeografico;
import com.pepito.reservas.catalogo.servicio.Categoria;
import java.util.List;
import com.pepito.reservas.catalogo.servicio.Servicio;

/**
 *ItemCatalogo
 * Sección Component del patrón Composite
 *
 * Descripción:
 *  Es el contrato comun referido a un Paquete (clase Compuesta) y el Servicio (clase Hoja). El cliente del Catalogo
 *  y Reserva solamente se relacionan con este contrato, independientemente de que el ítem sea simple o compuesto.
 *  Las operaciones referidas a precio, disponibilidad y operaciones de edicion y consulta de reservas y cupos son
 *  abstractas, dejando la implementación a las hijas:
 *      - Hoja resuelve con sus propios datos
 *      - Compuesto la delega en sus propias hijas de forma recursiva
 *
 * Atributos:
 *  - nombre:       (String) Nombre comercial del ítem
 *  - descripcion:  (String) Descripcion breve del item.
 *  - descuento:    (Double) Porcentaje de descuento expresado entre 0.0 y 1.0 (por ej. 0.35 = 35% Off)
 *  - destino:      (LugarGeografico) Lugar donde se presta el item. Es un criterio de Búsqueda.
 *
 * Decisiones:
 *  - La disponibilidad se desplaza como ATRIBUTO a la Hoja (Servicio). Un paquete no tiene cupo ni disponibilidad,
 *  solo está disponible si lo están todos sus componentes (recursividad)
 *
 * Descartado:
 *  - Al principio cada nivel tenia su propia disponibilidad/cupo. Pero por el exceso de invarianzas, duplicación de
 *  estado y violación del  patrón polimórfico, se optó por dotar el atributo al servicio en sí
 *
 * */


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

    /** Representa el factor a aplicar sobre el precio base. No devuelve el off, sino ya el porcentaje por el cual se
    debe multiplicar el precio*/
    public abstract Double descuento();
    public abstract Double precioFinal();
    public abstract void reservar(Periodo fechas, Integer cantidad);
    public abstract void liberar(Periodo fechas, Integer cantidad);

    /** Retorna true si hay al menos de cupos reservados para liberar (si se quiere liberar 5 cupos, pero el paquete
     * tiene 4 cupos originales, falla (no se puede liberar de mas)*/
    public abstract boolean liberable(Periodo fechas, Integer cantidad);
    public abstract Boolean perteneceA(Categoria categoria);
    public abstract Boolean cubre(Periodo periodo);
    
    /** Los servicios individuales que componen este ítem: un Servicio se devuelve a sí mismo, un Paquete junta los de
     * sus hijos. Lo usan los reportes para contar cupos y proveedores sin contar dos veces ni preguntar el tipo. */
    public abstract List<Servicio> serviciosHoja();


    /** Retorna si el item es el mismo que se consulta o si está en alguno de sus descendientes. Se utiliza para
     * evitar el instance of, utilizando polimorfismo*/
    public abstract boolean contiene(ItemCatalogo otro);

    /** Retorna true si el item está en algún otro servicio que tenga dentro. Esto se hace para evitar la duplicidad
     * de servicios o paquetes identicos*/
    public abstract boolean comparteServicios(ItemCatalogo otro);

    public abstract Boolean disponible(Periodo fechas, Integer cantidad);

    // Pre-implementados (comunes a todos)
    public String nombre(){return nombre;}
    public String descripcion(){return descripcion;}
    public LugarGeografico getDestino() {return destino;}
}
