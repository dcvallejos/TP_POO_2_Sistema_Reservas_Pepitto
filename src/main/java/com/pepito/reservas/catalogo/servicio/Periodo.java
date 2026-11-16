package com.pepito.reservas.catalogo.servicio;

import java.time.LocalDate;
import java.util.stream.Stream;

/**
 * Periodo
 *
 * Descripcion:
 *  Objeto inmutable Record, que expresa el transcurso de un periodo entre dos fechas. Tambien puede expresar un
 *  día entero para poder representarlo de forma uniforme
 *
 * Atributos
 *  - fechaDesde:      (LocalDate) Fecha de inicio del Periodo
 *  - fechaHasta:      (LocalDate) Fecha Fin del Periodo.
 *
 * Decisión:
 *  Se definieron metodos de acceso dia y dias para expresar el transcurso de un Periodo
 *  -> dia: Define un único dia a partir de una fecha, casteandolo a Periodo
 *  -> dias: Define un Stream de LocalDate para poder ser iterado a partir de una fecha inicio y fin, de un Periodo
 *  inicializado.
 *
 * */

    public record Periodo(LocalDate fechaDesde, LocalDate fechaHasta){
        public Periodo{
            if (fechaHasta.isBefore(fechaDesde)) throw new PeriodoException.PeriodoInvalido();
        }

        public static Periodo dia(LocalDate fecha){ return new Periodo(fecha,fecha);}

        public Stream<LocalDate> dias() {return fechaDesde.datesUntil(fechaHasta.plusDays(1));}
    }


