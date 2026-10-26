package com.pepito.reservas.metodoDePago;



import com.pepito.reservas.reserva.Reserva;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class BilleteraVirtualTest {

    private ApiBilletera apiBilleteraMock;
    private BilleteraVirtual billeteraVirtual;
    private Reserva reservaMock;
    private ComprobantesDePago comprobanteEsperado;

    @BeforeEach
    public void setUp() {
        // 1. Creamos los mocks necesarios
        apiBilleteraMock = mock(ApiBilletera.class);
        reservaMock = mock(Reserva.class);
        comprobanteEsperado = mock(ComprobantesDePago.class);

        // 2. Instanciamos la clase a testear inyectándole el mock de la API
        billeteraVirtual = new BilleteraVirtual(apiBilleteraMock);
    }

    @Test
    public void testValidarDatosDelegaEnApi() {
        // Ejecutamos
        billeteraVirtual.validarDatos();

        // Verificamos que se delegue en la API
        verify(apiBilleteraMock, times(1)).validar();
    }

    @Test
    public void testReservarFondosDelegaEnApi() {
        // Ejecutamos
        billeteraVirtual.reservarFondos();

        // Verificamos que se delegue en la API
        verify(apiBilleteraMock, times(1)).reservar();
    }

    @Test
    public void testEjecutarTransaccionDelegaEnApi() {
        // Ejecutamos
        billeteraVirtual.ejecutarTransaccion();

        // Verificamos que se delegue en la API
        verify(apiBilleteraMock, times(1)).ejecutar();
    }

    @Test
    public void testNotificarResultadoDelegaEnApi() {
        // Configuramos el comportamiento del mock para la notificación
        when(apiBilleteraMock.notificar()).thenReturn(comprobanteEsperado);

        // Ejecutamos pasándole la reserva de prueba
        ComprobantesDePago resultado = billeteraVirtual.notificarResultado(reservaMock);

        // Verificamos que retorne el comprobante correcto y que se haya llamado a la API
        assertEquals(comprobanteEsperado, resultado);
        verify(apiBilleteraMock, times(1)).notificar();
    }
}

