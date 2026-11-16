package com.pepito.reservas.excepciones;

import com.pepito.reservas.catalogo.servicio.Periodo;


/** DisponibilidadException - Errores de cupos: período sin disponibilidad, cantidad inválida, liberación mayor a lo
 * reservado. */

public abstract class DisponibilidadException extends ServiceExceptions {
    protected DisponibilidadException(String mensaje) { super(mensaje); }

     public static class PeriodoSinDisponibilidad extends DisponibilidadException {
        public PeriodoSinDisponibilidad(Periodo periodo) {
            super("No hay disponibilidad entre las fechas " + periodo.fechaDesde() + " - " + periodo.fechaHasta());
        }
    }
    public static class CantidadInvalida extends DisponibilidadException {
        public CantidadInvalida(int cantidad) {
            super("La cantidad debe ser mayor a cero: " + cantidad);
        }
    }

    public static class LiberacionInvalida extends DisponibilidadException {
        public LiberacionInvalida(Periodo periodo, int cantidad) {
            super("No se pueden liberar " + cantidad + " cupos entre " + periodo.fechaDesde()
                    + " y " + periodo.fechaHasta() + ": hay menos reservados en al menos una fecha");
        }
    }
}