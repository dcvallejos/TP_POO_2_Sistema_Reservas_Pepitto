package com.pepito.reservas.catalogo.servicio;

import com.pepito.reservas.excepciones.DisponibilidadException;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/*
 * Cupos de un servicio por fecha.
 * Separa CAPACIDAD (lo que el negocio ofrece) de RESERVADOS (lo que tomaron las reservas):
 *  - agregarCupo amplía la oferta (sube la capacidad).
 *  - bloquear / liberar mueven reservados; liberar nunca puede devolver más de lo reservado.
 * Libres = capacidad - reservados. Tener ambos números es lo que permite el reporte de ocupación
 *  * Bloquear y liberar son atómicos: primero verifican todo el período y recién después modifican.
 */
public class Disponibilidad {

    private final Map<LocalDate, Integer> capacidad = new HashMap<>();
    private final Map<LocalDate, Integer> reservados = new HashMap<>();

    public Disponibilidad() {
    }

    public Disponibilidad(Periodo periodo, int cupos) {
        this.agregarCupo(periodo, cupos);
    }

    /* El negocio amplía su oferta: suma capacidad en cada día del período. */
    public void agregarCupo(Periodo periodo, int cupos) {
        this.validarCantidad(cupos);
        periodo.dias().forEach(fecha -> this.capacidad.merge(fecha, cupos, Integer::sum));
    }

    public void bloquear(Periodo periodo, int cantidad) {
        this.validarCantidad(cantidad);
        if (!this.disponible(periodo, cantidad)) {
            throw new DisponibilidadException.PeriodoSinDisponibilidad(periodo);
        }
        periodo.dias().forEach(fecha -> this.reservados.merge(fecha, cantidad, Integer::sum));
    }

    public void liberar(Periodo periodo, int cantidad) {
        this.validarCantidad(cantidad);
        if (!this.liberable(periodo, cantidad)) {
            throw new DisponibilidadException.LiberacionInvalida(periodo, cantidad);
        }
        periodo.dias().forEach(fecha -> this.reservados.merge(fecha, -cantidad, Integer::sum));
    }

    public boolean liberable(Periodo periodo, int cantidad) {
        return periodo.dias().allMatch(fecha -> this.reservados(fecha) >= cantidad);
    }

    /* Hay al menos 'cantidad' cupos libres en TODOS los días del período. */
    public boolean disponible(Periodo periodo, int cantidad) {
        return periodo.dias().allMatch(fecha -> this.libres(fecha) >= cantidad);
    }

    /* El servicio opera todos los días del período (aunque estén llenos). Detecta huecos, no solo extremos. */
    public boolean cubre(Periodo periodo) {
        return periodo.dias().allMatch(this.capacidad::containsKey);
    }

    public int capacidad(LocalDate fecha) {
        return this.capacidad.getOrDefault(fecha, 0);
    }

    public int reservados(LocalDate fecha) {
        return this.reservados.getOrDefault(fecha, 0);
    }

    public int libres(LocalDate fecha) {
        return this.capacidad(fecha) - this.reservados(fecha);
    }

    private void validarCantidad(int cantidad) {
        if (cantidad <= 0) {
            throw new DisponibilidadException.CantidadInvalida(cantidad);
        }
    }
}