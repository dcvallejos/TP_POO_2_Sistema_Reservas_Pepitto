package com.pepito.reservas.catalogo.servicio;

import java.time.LocalDate;
import java.util.stream.Stream;

    public record Periodo(LocalDate fechaDesde, LocalDate fechaHasta){
        public Periodo{
            if (fechaHasta.isBefore(fechaDesde)) throw new PeriodoException.PeriodoInvalido();
        }

        public static Periodo dia(LocalDate fecha){ return new Periodo(fecha,fecha);}

        public Stream<LocalDate> dias() {return fechaDesde.datesUntil(fechaHasta.plusDays(1));}
    }


