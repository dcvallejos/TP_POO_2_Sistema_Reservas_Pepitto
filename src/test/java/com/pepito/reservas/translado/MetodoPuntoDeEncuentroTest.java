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
        // 1. Creamos el mock de Reserva para mantener la estructura de tests con mocks
        reservaMock = mock(Reserva.class);

        // 2. Instanciamos la clase a testear
        metodoPuntoDeEncuentro = new MetodoPuntoDeEncuentro();
    }

    @Test
    public void testCostoRetornaCero() {
        // Ejecutamos el método costo pasándole la reserva mockeada
        Double costo = metodoPuntoDeEncuentro.costo(reservaMock);

        // Verificamos que el resultado sea siempre 0.0
        assertEquals(0.0, costo);
    }
    @Test
    public void testCostoConReservaNula() {
        // Verificamos que maneje correctamente un valor nulo sin lanzar excepciones
        Double costo = metodoPuntoDeEncuentro.costo(null);
        assertEquals(0.0, costo);
    }

    @Test
    public void testCostoMultiplesInvocacionesEsConsistente() {
        // Verificamos que múltiples llamadas consecutivas retornen siempre 0.0
        assertEquals(0.0, metodoPuntoDeEncuentro.costo(reservaMock));
        assertEquals(0.0, metodoPuntoDeEncuentro.costo(reservaMock));
        assertEquals(0.0, metodoPuntoDeEncuentro.costo(reservaMock));
    }

    @Test
    public void testCostoConDiferentesReservasMockeadas() {
        // Creamos otro mock de reserva independiente
        Reserva otraReservaMock = mock(Reserva.class);

        Double costo = metodoPuntoDeEncuentro.costo(otraReservaMock);
        assertEquals(0.0, costo);
    }
}
