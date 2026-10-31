package com.pepito.reservas.reserva;


/*
 * Reserva confirmada/pagada: el Cupo ya está bloqueado.
 */

class Confirmada extends EstadoReserva{
	
	Confirmada() {
        super(NombreEstado.CONFIRMADA);
    }

	@Override
    void hacerCheckIn(Reserva reserva) {
        reserva.cambiarEstado(new EnCheckIn());
    }
	
	/* Reserva confirmada/pagada: el Cupo ya está bloqueado. Si cancela: se libera el Cupo y se reembolsan Servicios y Tasas. */
 
    @Override
    void cancelar(Reserva reserva) {
        reserva.liberarCupos();
        reserva.generarVoucherReembolsoTotal();
        reserva.cambiarEstado(new Cancelada());
    }
}