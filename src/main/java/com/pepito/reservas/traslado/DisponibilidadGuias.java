package com.pepito.reservas.traslado;

/** DisponibilidadGuias - Fuente externa del cupo de guías en el punto de encuentro. Se inyecta para poder
 * consultarla en cada confirmación. */

public interface DisponibilidadGuias {

    Boolean guiasDisponibles();
}
