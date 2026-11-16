package com.pepito.reservas.traslado;

/** TrasladoNoDisponibleException - El metodo de traslado elegido no está disponible al confirmar. */

public class TrasladoNoDisponibleException extends RuntimeException {
    public TrasladoNoDisponibleException(String mensaje) {
        super(mensaje);
    }
}