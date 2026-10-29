package com.pepito.reservas.traslado;

public class TrasladoNoDisponibleException extends RuntimeException {
    public TrasladoNoDisponibleException(String mensaje) {
        super(mensaje);
    }
}