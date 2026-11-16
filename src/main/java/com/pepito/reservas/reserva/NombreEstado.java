package com.pepito.reservas.reserva;

/** NombreEstado - Nombres de los estados del enunciado. Permite a los observers filtrar sin conocer las clases de
 * estado. */

public enum NombreEstado {
    SOLICITUD_PENDIENTE,
    CONFIRMADA,
    EN_CHECKIN,
    EN_CURSO,
    FINALIZADA,
    CANCELADA
}
