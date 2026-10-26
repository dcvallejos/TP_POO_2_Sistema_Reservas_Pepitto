package com.pepito.reservas.translado;




import com.pepito.reservas.reserva.Reserva;
import com.pepito.reservas.traslado.MetodoPuntoDeEncuentro;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

public class MetodoPuntoDeEncuentroTest {

    private MetodoPuntoDeEncuentro metodoPuntoDeEncuentro;
    private Reserva reservaMock;

    @BeforeEach
    public void setUp() {

        reservaMock = mock(Reserva.class);


        metodoPuntoDeEncuentro = new MetodoPuntoDeEncuentro();
    }

    @Test
    public void testCostoRetornaCero() {

        Double costo = metodoPuntoDeEncuentro.costo(reservaMock);


        assertEquals(0.0, costo);
    }
    @Test
    public void testCostoConReservaNula() {

        Double costo = metodoPuntoDeEncuentro.costo(null);
        assertEquals(0.0, costo);
    }

    @Test
    public void testCostoMultiplesInvocacionesEsConsistente() {

        assertEquals(0.0, metodoPuntoDeEncuentro.costo(reservaMock));
        assertEquals(0.0, metodoPuntoDeEncuentro.costo(reservaMock));
        assertEquals(0.0, metodoPuntoDeEncuentro.costo(reservaMock));
    }

    @Test
    public void testCostoConDiferentesReservasMockeadas() {

        Reserva otraReservaMock = mock(Reserva.class);

        Double costo = metodoPuntoDeEncuentro.costo(otraReservaMock);
        assertEquals(0.0, costo);
    }
}
