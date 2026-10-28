package com.pepito.reservas.translado;



import com.pepito.reservas.reserva.Reserva;
import com.pepito.reservas.traslado.MetodoTransladoVip;
import com.pepito.reservas.traslado.TransladoVip;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MetodoTransladoVipTest {

    private MetodoTransladoVip metodoTransladoVip;
    private TransladoVip transladoVipMock;
    private Reserva reservaMock;

    @BeforeEach
    public void setUp() {

        transladoVipMock = mock(TransladoVip.class);
        reservaMock = mock(Reserva.class);


        metodoTransladoVip = new MetodoTransladoVip(transladoVipMock);
    }

    @Test
    public void testCostoDelegaEnTransladoVip() {
        // Configuramos el mock para que devuelva un costo VIP de ejemplo
        when(transladoVipMock.calcularCosto(reservaMock)).thenReturn(5000.0);

        // Ejecutamos
        Double costoObtenido = metodoTransladoVip.costo(reservaMock);

        // Verificamos
        assertEquals(5000.0, costoObtenido);
        verify(transladoVipMock, times(1)).calcularCosto(reservaMock);
    }

    @Test
    public void testCostoVipCuandoLanzaExcepcion() {
        // Verificamos que si el servicio VIP falla, propague la excepción correctamente
        when(transladoVipMock.calcularCosto(reservaMock))
                .thenThrow(new RuntimeException("Error en servicio VIP"));

        assertThrows(RuntimeException.class, () -> {
            metodoTransladoVip.costo(reservaMock);
        });

        verify(transladoVipMock, times(1)).calcularCosto(reservaMock);
    }

    @Test
    public void testHayDisponibilidadRetornaTruePorDefectoVip() {
        // Verificamos que responda true por defecto a la disponibilidad
        assertTrue(metodoTransladoVip.getHayCupoDeGuias());
    }
}