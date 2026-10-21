package com.pepito.reservas.reserva;

import com.pepito.reservas.catalogo.ItemCatalogo;
import com.pepito.reservas.metodoDePago.MetodoDePago;
import com.pepito.reservas.traslado.MetodoTraslado;

/*
 * El cliente está armando la reserva. Único estado donde se agregan/quitan ítems y se elige el traslado.
 */

class SolicitudPendiente extends EstadoReserva {

	SolicitudPendiente() {
        super(NombreEstado.SOLICITUD_PENDIENTE);
    }

	/*
	 * TODO: Yo creo que a efectivizarAgregadoDeItem hay que pasarle:
	 * 		- por parametro: una fecha (para ver los cupos de ese item en esa fecha)
	 * 		- por llamamiento: reserva.cantidadPasajeros (para ver si dichos cupos alcanzan)
	 */
    @Override
    void agregarItem(Reserva reserva, ItemCatalogo item /*,LocalDate fecha*/) {
        reserva.efectivizarAgregadoDeItem(item /*, fecha, reserva.cantidadPasajeros*/);
    }

    @Override
    void quitarItem(Reserva reserva, ItemCatalogo item) {
        reserva.efectivizarEliminacionDeItem(item);
    }

    @Override
    void elegirTraslado(Reserva reserva, MetodoTraslado metodoTraslado) {
        reserva.efectivizarEleccionDeTraslado(metodoTraslado);
    }

    /*
     * Confirmar = pagar. Primero las verificaciones sin efectos (que tenga ítems, que el traslado esté disponible), después los Cupos
     * (bloquearlos es reversible sin costo para el cliente) y por último el cobro (si falla, la Reserva libera los Cupos).
     * La transición va al final: si algo falla, la Reserva sigue en SOLICITUD_PENDIENTE y no se notifica nada.
     */
    @Override
    void confirmar(Reserva reserva, MetodoDePago metodoPago) {
        reserva.verificarQueTengaItems();
        reserva.verificarDisponibilidadTraslado();
        reserva.bloquearCupos();
        reserva.cobrar(metodoPago);
        reserva.cambiarEstado(new Confirmada());
    }

    @Override
    void cancelar(Reserva reserva) {
        reserva.cambiarEstado(new Cancelada());
    }

}
