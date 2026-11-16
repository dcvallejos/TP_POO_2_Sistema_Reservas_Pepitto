package com.pepito.reservas.traslado;

import com.pepito.reservas.reserva.Reserva;

/**
 * MetodoTraslado
 * Sección Strategy del patrón (el Context es la Reserva)
 *
 * Descripción:
 *  Forma de llegar al destino. Cada metodo calcula su costo (las tasas de la reserva) con un criterio distinto y
 *  puede verificar su propia disponibilidad.
 *
 * Decisión:
 *  Clase abstracta y no interfaz: verificarDisponibilidad tiene una implementación por defecto (no hace nada) y solo
 *  la redefine el metodo que la necesita (punto de encuentro).
 *
 * Alternativa descartada:
 *   Un switch sobre un tipo de traslado dentro de Reserva: agregar un metodo nuevo obligaría a modificarla.
 */

public abstract class MetodoTraslado {

    public abstract Double costo(Reserva reserva);

    public void verificarDisponibilidad(){}
}
//