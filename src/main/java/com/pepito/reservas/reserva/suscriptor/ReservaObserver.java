package com.pepito.reservas.reserva.suscriptor;

import com.pepito.reservas.reserva.EstadoReserva;
import com.pepito.reservas.reserva.Reserva;

/**
 * ReservaObserver
 * Sección Observer del patrón
 *
 * Descripción:
 *   Subsistema que reacciona a los cambios de estado de una reserva. La reserva solo conoce esta interfaz, no a los
 *   subsistemas concretos (enunciado 2.5).
 *
 * Decision:
 *  update recibe la reserva y los estados anterior y nuevo: cada observer decide si le interesa el evento.
 *  Agregar un subsistema nuevo es implementar esta interfaz y suscribirlo en RegistroReservas.
 */

public interface ReservaObserver {

    void update(Reserva reserva, EstadoReserva anterior, EstadoReserva nuevo);

}