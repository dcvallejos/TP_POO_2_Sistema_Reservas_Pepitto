package com.pepito.reservas.servicio;

import com.pepito.reservas.catalogo.servicio.Periodo;
import com.pepito.reservas.catalogo.servicio.PeriodoException;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

class PeriodoTest {

    private final LocalDate dia1 = LocalDate.of(2026, 3, 1);
    private final LocalDate dia3 = LocalDate.of(2026, 3, 3);

    @Test
    void un_periodo_no_puede_terminar_antes_de_empezar() {
        assertThrowsExactly(PeriodoException.PeriodoInvalido.class, () -> new Periodo(dia3, dia1));
    }

    @Test
    void un_periodo_de_un_solo_dia_es_valido() {
        Periodo unDia = Periodo.dia(dia1);

        assertEquals(dia1, unDia.fechaDesde());
        assertEquals(dia1, unDia.fechaHasta());
        assertEquals(List.of(dia1), unDia.dias().toList());
    }

    @Test
    void los_dias_del_periodo_incluyen_ambos_extremos() {
        assertEquals(List.of(dia1, dia1.plusDays(1), dia3), new Periodo(dia1, dia3).dias().toList());
    }

    @Test
    void dos_periodos_con_las_mismas_fechas_son_iguales() {
        assertEquals(new Periodo(dia1, dia3), new Periodo(dia1, dia3));
    }
}
