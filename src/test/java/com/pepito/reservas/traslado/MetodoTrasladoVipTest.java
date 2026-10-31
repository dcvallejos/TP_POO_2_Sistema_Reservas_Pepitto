package com.pepito.reservas.traslado;

import com.pepito.reservas.reserva.Reserva;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MetodoTrasladoVipTest {

    private MetodoTrasladoVip metodoTrasladoVip;
    private TrasladoVip trasladoVipMock;
    private Reserva reservaMock;

    @BeforeEach
    public void setUp() {
        trasladoVipMock = mock(TrasladoVip.class);
        reservaMock = mock(Reserva.class);
        metodoTrasladoVip = new MetodoTrasladoVip(trasladoVipMock);
    }

    @Test
    public void testCostoDelegaEnTrasladoVip() {
        when(reservaMock.getTotalServicios()).thenReturn(20000.0);
        when(trasladoVipMock.calcularCosto(20000f)).thenReturn(5000f);

        Double costoObtenido = metodoTrasladoVip.costo(reservaMock);

        assertEquals(5000.0, costoObtenido);
        verify(trasladoVipMock, times(1)).calcularCosto(anyFloat());
    }

    @Test
    public void testCostoVipCuandoLanzaExcepcion() {
        when(trasladoVipMock.calcularCosto(anyFloat()))
                .thenThrow(new RuntimeException("Error en servicio VIP"));

        assertThrows(RuntimeException.class, () -> metodoTrasladoVip.costo(reservaMock));

        verify(trasladoVipMock, times(1)).calcularCosto(anyFloat());
    }

    @Test
    public void testElTrasladoVipSiempreEstaDisponible() {
        assertDoesNotThrow(() -> metodoTrasladoVip.verificarDisponibilidad());
    }
}