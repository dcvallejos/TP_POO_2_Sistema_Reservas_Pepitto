package com.pepito.reservas.metodoDePago;

/** PagoRechazadoException - El medio de pago rechazó la operación. La reserva libera los cupos y sigue en
 * SOLICITUD_PENDIENTE. */

public class PagoRechazadoException extends RuntimeException {
    public PagoRechazadoException(String mensaje) {
        super(mensaje);
    }
}