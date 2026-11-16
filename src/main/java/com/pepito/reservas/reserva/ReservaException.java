package com.pepito.reservas.reserva;

/**
 * ReservaException
 * Base de las excepciones de dominio de la reserva
 * OperacionInvalidaException informa el estado y la operación rechazada.
 */

public class ReservaException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ReservaException(String message) {
        super(message);
    }
}
