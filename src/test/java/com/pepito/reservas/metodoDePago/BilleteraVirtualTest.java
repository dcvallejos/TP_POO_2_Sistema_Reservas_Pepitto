package com.pepito.reservas.metodoDePago;



import com.pepito.reservas.documento.ComprobanteBilletera;
import com.pepito.reservas.documento.ComprobantePago;
import com.pepito.reservas.documento.NotificacionPush;
import com.pepito.reservas.metodoDePago.api.ApiBilletera;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BilleteraVirtualTest {

    private ApiBilletera apiBilleteraMock;
    private BilleteraVirtual billeteraVirtual;
    private ComprobantePago comprobanteEsperado;

    @BeforeEach
    public void setUp() {
        // 1. Creamos los mocks necesarios
        apiBilleteraMock = mock(ApiBilletera.class);
        comprobanteEsperado = mock(ComprobantePago.class);

        billeteraVirtual = new BilleteraVirtual(apiBilleteraMock);
    }

    @Test
    public void testValidarDatosDelegaEnApi() {

        billeteraVirtual.validarDatos();


        verify(apiBilleteraMock, times(1)).validar();
    }

    @Test
    public void testReservarFondosDelegaEnApi() {

        billeteraVirtual.reservarFondos();


        verify(apiBilleteraMock, times(1)).reservar();
    }

    @Test
    public void testEjecutarTransaccionDelegaEnApi() {

        billeteraVirtual.ejecutarTransaccion();


        verify(apiBilleteraMock, times(1)).ejecutar();
    }

    @Test
    public void testNotificarResultadoEnviaPush() {
        when(apiBilleteraMock.ejecutar()).thenReturn("TX-1");

        ComprobantePago comprobante = billeteraVirtual.procesarPago(100.0);

        ArgumentCaptor<NotificacionPush> push = ArgumentCaptor.forClass(NotificacionPush.class);
        verify(apiBilleteraMock, times(1)).notificar(push.capture());
        assertTrue(comprobante.getCodigoTransaccion().contains("TX-1"));
    }

    @Test
    public void testProcesarPagoGeneraComprobanteYRegistraCodigo() {
        when(apiBilleteraMock.ejecutar()).thenReturn("TX-1");

        ComprobantePago comprobante = billeteraVirtual.procesarPago(100.0);

        assertInstanceOf(ComprobanteBilletera.class, comprobante);
        assertEquals("TX-1", comprobante.getCodigoTransaccion());
        assertEquals(100.0, comprobante.getMonto());
        assertEquals(List.of(comprobante), billeteraVirtual.getComprobantesRegistrados());
    }
}

