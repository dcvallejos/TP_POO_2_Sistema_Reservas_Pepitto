package com.pepito.reservas.translado;

import com.pepito.reservas.reserva.Reserva;
import com.pepito.reservas.traslado.MetodoPuntoDeEncuentro;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class MetodoPuntoDeEncuentroTest {

    private Reserva reservaMock;

    @BeforeEach
    public void setUp() {
        // Creamos el mock de la reserva
        reservaMock = mock(Reserva.class);
    }

    @Test
    public void testCostoConDisponibilidadDeGuiasRetornaCero() {
        // 1. Instanciamos pasando true (hay cupo)
        MetodoPuntoDeEncuentro metodo = new MetodoPuntoDeEncuentro(true);

        // 2. Ejecutamos el costo y verificamos que devuelva 0.0 sin romper
        Double costo = metodo.costo(reservaMock);
        assertEquals(0.0, costo);
    }

    @Test
    public void testCostoSinDisponibilidadDeGuiasLanzaExcepcion() {
        // 1. Instanciamos pasando false (no hay cupo)
        MetodoPuntoDeEncuentro metodo = new MetodoPuntoDeEncuentro(false);

        // 2. Verificamos que al invocar costo se lance una RuntimeException
        assertThrows(RuntimeException.class, () -> {
            metodo.costo(reservaMock);
        });
    }

    @Test
    public void testHayDisponibilidadRetornaElValorConfigurado() {
        // Verificamos que el método de disponibilidad responda correctamente según el estado
        MetodoPuntoDeEncuentro metodoConCupo = new MetodoPuntoDeEncuentro(true);
        assertTrue(metodoConCupo.getHayCupoDeGuias());

        MetodoPuntoDeEncuentro metodoSinCupo = new MetodoPuntoDeEncuentro(false);
        assertFalse(metodoSinCupo.getHayCupoDeGuias());
    }
}