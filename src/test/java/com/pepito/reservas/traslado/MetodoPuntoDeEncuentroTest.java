package com.pepito.reservas.traslado;

import com.pepito.reservas.reserva.Reserva;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MetodoPuntoDeEncuentroTest {

    private Reserva reservaMock;
    private DisponibilidadGuias disponibilidadGuias;

    @BeforeEach
    public void setUp() {
        // Creamos el mock de la reserva
        reservaMock = mock(Reserva.class);
        disponibilidadGuias = mock(DisponibilidadGuias.class);
    }

    @Test
    public void testCostoConDisponibilidadDeGuiasRetornaCero() {
        // 1. Instanciamos pasando true (hay cupo)
        MetodoPuntoDeEncuentro metodo = new MetodoPuntoDeEncuentro(disponibilidadGuias);

        // 2. Ejecutamos el costo y verificamos que devuelva 0.0 sin romper
        Double costo = metodo.costo(reservaMock);
        assertEquals(0.0, costo);
    }

    @Test
    public void testConGuiasDisponiblesNoLanzaExcepcion() {
        when(disponibilidadGuias.guiasDisponibles()).thenReturn(true);
        MetodoPuntoDeEncuentro metodo = new MetodoPuntoDeEncuentro(disponibilidadGuias);

        assertDoesNotThrow(() -> metodo.verificarDisponibilidad());
    }

    @Test
    public void testSinGuiasDisponiblesLanzaTrasladoNoDisponible() {
        MetodoPuntoDeEncuentro metodo = new MetodoPuntoDeEncuentro(disponibilidadGuias);

        assertThrows(TrasladoNoDisponibleException.class, () -> metodo.verificarDisponibilidad());
    }
}