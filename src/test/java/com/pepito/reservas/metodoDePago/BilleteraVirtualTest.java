package com.pepito.reservas.metodoDePago;



import com.pepito.reservas.reserva.Reserva;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
    public void testNotificarResultadoDelegaEnApi() {

        when(apiBilleteraMock.notificar()).thenReturn(comprobanteEsperado);



        ComprobantePago resultado = billeteraVirtual.notificarResultado(100.0);

        assertEquals(comprobanteEsperado, resultado);
        verify(apiBilleteraMock, times(1)).notificar();
    }
}

