package com.pepito.reservas.catalogo.servicio;

public abstract class PeriodoException extends RuntimeException {
    public PeriodoException(String message) {
        super(message);
    }

    public static class PeriodoInvalido extends PeriodoException{
        public PeriodoInvalido() {
            super("Periodo de formato inválido. La fecha desde no puede ser mayor que la fecha hasta");
        }
    }
}
