package com.pepito.reservas.traslado;

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
    public void testConGuiasDisponiblesNoLanzaExcepcion() {
        MetodoPuntoDeEncuentro metodo = new MetodoPuntoDeEncuentro(true);

        assertDoesNotThrow(() -> metodo.verificarDisponibilidad());
    }

    @Test
    public void testSinGuiasDisponiblesLanzaTrasladoNoDisponible() {
        MetodoPuntoDeEncuentro metodo = new MetodoPuntoDeEncuentro(false);

        assertThrows(TrasladoNoDisponibleException.class, () -> metodo.verificarDisponibilidad());
    }
}