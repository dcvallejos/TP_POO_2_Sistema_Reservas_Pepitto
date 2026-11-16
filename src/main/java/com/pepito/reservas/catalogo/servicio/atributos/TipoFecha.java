package com.pepito.reservas.catalogo.servicio.atributos;

import java.time.temporal.Temporal;
import java.util.Calendar;
import java.util.Date;

/** TipoFecha - ConcreteStrategy. Acepta java.util.Date, Calendar o cualquier Temporal (LocalDate, LocalDateTime…). */

public class TipoFecha implements TipoDinamico{

    public TipoFecha(){}

    @Override
    public Boolean acepta(Object valor){
        return valor instanceof Date
                || valor instanceof Temporal
                || valor instanceof Calendar;
    }
}
