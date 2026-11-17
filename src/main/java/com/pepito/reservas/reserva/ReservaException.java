package com.pepito.reservas.reserva;

/*
 * ReservaException
 *
 * Descripción:
 *  Base de las excepciones de dominio del módulo de reservas.
 *
 * Decisión:
 *  Permite atrapar cualquier error del dominio de la reserva con un solo catch, y distinguirlo de errores
 *  genéricos. Subclases: OperacionInvalidaException, ReservaVaciaException, ItemInvalidoException,
 *  ItemNoIncluidoException, CantidadPasajerosInvalidaException.
 */

public class ReservaException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ReservaException(String message) {
        super(message);
    }
}
