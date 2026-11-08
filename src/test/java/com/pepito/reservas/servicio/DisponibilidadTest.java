package com.pepito.reservas.servicio;

import com.pepito.reservas.catalogo.servicio.Disponibilidad;
import com.pepito.reservas.catalogo.servicio.Periodo;
import com.pepito.reservas.excepciones.DisponibilidadException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertTrue;

/*
 * Contrato de Disponibilidad:
 *  -> Se separa Disponibilidad de Reservados
 *  -> Disponibilidad: Refiere a la cantidad de cupos libres que quedan
 *  -> Reservados: Los cupos que ya fueron bloqueados
 * libres = capacidad - reservados. Bloquear y liberar precisan de que haya disponibilidad y reserva disponible.
 *
 */
class DisponibilidadTest {

    private final LocalDate dia1 = LocalDate.of(2026, 3, 1);
    private final LocalDate dia2 = LocalDate.of(2026, 3, 2);
    private final LocalDate dia3 = LocalDate.of(2026, 3, 3);
    private final Periodo tresDias = new Periodo(dia1, dia3);

    private Disponibilidad disponibilidad;

    @BeforeEach
    void setUp() {
        disponibilidad = new Disponibilidad();
    }

    @Test
    void una_disponibilidad_nueva_no_ofrece_nada() {
        assertEquals(0, disponibilidad.capacidad(dia1));
        assertEquals(0, disponibilidad.libres(dia1));
        assertFalse(disponibilidad.disponible(Periodo.dia(dia1), 1));
        assertFalse(disponibilidad.cubre(Periodo.dia(dia1)));
    }

    @Test
    void se_puede_crear_con_cupos_para_un_periodo() {
        Disponibilidad conCupos = new Disponibilidad(tresDias, 4);

        assertEquals(4, conCupos.capacidad(dia1));
        assertEquals(4, conCupos.capacidad(dia2));
        assertEquals(4, conCupos.capacidad(dia3));
        assertEquals(0, conCupos.capacidad(dia3.plusDays(1)));
    }

    @Test
    void agregar_cupo_acumula_capacidad() {
        disponibilidad.agregarCupo(tresDias, 4);
        disponibilidad.agregarCupo(Periodo.dia(dia2), 3);

        assertEquals(4, disponibilidad.capacidad(dia1));
        assertEquals(7, disponibilidad.capacidad(dia2));
    }

    @Test
    void bloquear_toma_cupos_en_cada_dia_sin_cambiar_la_capacidad() {
        disponibilidad.agregarCupo(tresDias, 10);

        disponibilidad.bloquear(tresDias, 3);

        assertEquals(10, disponibilidad.capacidad(dia2));
        assertEquals(3, disponibilidad.reservados(dia2));
        assertEquals(7, disponibilidad.libres(dia2));
    }

    @Test
    void bloquear_todo_el_cupo_deja_el_periodo_sin_disponibilidad() {
        disponibilidad.agregarCupo(tresDias, 2);

        disponibilidad.bloquear(tresDias, 2);

        assertFalse(disponibilidad.disponible(tresDias, 1));
        assertTrue(disponibilidad.cubre(tresDias)); // sigue operando, solo que lleno
    }

    @Test
    void bloquear_mas_de_lo_libre_en_algun_dia_falla_y_no_bloquea_ningun_dia() {
        disponibilidad.agregarCupo(tresDias, 5);
        disponibilidad.bloquear(Periodo.dia(dia3), 4); // el día 3 queda con 1 libre

        assertThrowsExactly(DisponibilidadException.PeriodoSinDisponibilidad.class,
                () -> disponibilidad.bloquear(tresDias, 2));

        assertEquals(0, disponibilidad.reservados(dia1));
        assertEquals(0, disponibilidad.reservados(dia2));
        assertEquals(4, disponibilidad.reservados(dia3));
    }

    @Test
    void bloquear_un_dia_sin_oferta_falla() {
        disponibilidad.agregarCupo(Periodo.dia(dia1), 5);

        assertThrowsExactly(DisponibilidadException.PeriodoSinDisponibilidad.class,
                () -> disponibilidad.bloquear(new Periodo(dia1, dia2), 1));
    }

    @Test
    void liberar_devuelve_los_cupos_bloqueados() {
        disponibilidad.agregarCupo(tresDias, 10);
        disponibilidad.bloquear(tresDias, 6);

        disponibilidad.liberar(tresDias, 4);

        assertEquals(2, disponibilidad.reservados(dia1));
        assertEquals(8, disponibilidad.libres(dia1));
    }

    @Test
    void no_se_puede_liberar_mas_de_lo_reservado() {
        disponibilidad.agregarCupo(tresDias, 10);
        disponibilidad.bloquear(tresDias, 2);

        assertThrowsExactly(DisponibilidadException.LiberacionInvalida.class,
                () -> disponibilidad.liberar(tresDias, 3));
    }

    @Test
    void liberar_sin_reservas_en_algun_dia_falla_y_no_libera_ningun_dia() {
        disponibilidad.agregarCupo(tresDias, 10);
        disponibilidad.bloquear(new Periodo(dia1, dia2), 3); // el día 3 no tiene reservas

        assertThrowsExactly(DisponibilidadException.LiberacionInvalida.class,
                () -> disponibilidad.liberar(tresDias, 3));

        assertEquals(3, disponibilidad.reservados(dia1));
        assertEquals(3, disponibilidad.reservados(dia2));
    }

    @Test
    void liberar_nunca_deja_mas_libres_que_la_capacidad() {
        disponibilidad.agregarCupo(tresDias, 10);
        disponibilidad.bloquear(tresDias, 2);
        disponibilidad.liberar(tresDias, 2);

        assertThrowsExactly(DisponibilidadException.LiberacionInvalida.class,
                () -> disponibilidad.liberar(tresDias, 1));
        assertEquals(10, disponibilidad.libres(dia1));
    }

    @Test
    void agregar_cupo_con_cantidad_no_positiva_falla() {
        assertThrowsExactly(DisponibilidadException.CantidadInvalida.class,
                () -> disponibilidad.agregarCupo(tresDias, 0));
        assertThrowsExactly(DisponibilidadException.CantidadInvalida.class,
                () -> disponibilidad.agregarCupo(tresDias, -1));
    }

    @Test
    void bloquear_con_cantidad_no_positiva_falla() {
        disponibilidad.agregarCupo(tresDias, 10);

        assertThrowsExactly(DisponibilidadException.CantidadInvalida.class,
                () -> disponibilidad.bloquear(tresDias, 0));
        assertEquals(0, disponibilidad.reservados(dia1));
    }

    @Test
    void liberar_con_cantidad_no_positiva_falla() {
        disponibilidad.agregarCupo(tresDias, 10);
        disponibilidad.bloquear(tresDias, 2);

        assertThrowsExactly(DisponibilidadException.CantidadInvalida.class,
                () -> disponibilidad.liberar(tresDias, -2));
        assertEquals(2, disponibilidad.reservados(dia1));
    }

    @Test
    void cubre_detecta_huecos_en_el_medio_del_periodo() {
        disponibilidad.agregarCupo(Periodo.dia(dia1), 5);
        disponibilidad.agregarCupo(Periodo.dia(dia3), 5);

        assertFalse(disponibilidad.cubre(tresDias));
        assertFalse(disponibilidad.disponible(tresDias, 1));
    }
}
