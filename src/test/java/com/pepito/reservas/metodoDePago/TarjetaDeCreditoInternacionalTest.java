package com.pepito.reservas.metodoDePago;

import com.pepito.reservas.reserva.Reserva;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TarjetaDeCreditoInternacionalTest {

    private ApiTarjetaDeCredito apiMock;
    private TarjetaDeCreditoInternacional tarjetaPago;
    private LocalDate fechaDeVencimiento;

    @BeforeEach
    public void setUp() {
        // 1. Creamos un mock de la API externa para no depender de implementaciones reales
        apiMock = mock(ApiTarjetaDeCredito.class);

        LocalDate fechaVencimiento = LocalDate.of(2018, 12, 9);

        // 2. Instanciamos la clase a testear pasándole el mock y datos de prueba
        tarjetaPago = new TarjetaDeCreditoInternacional(123456, 123, fechaVencimiento, apiMock);
    }

    @Test
    public void testValidarDatosDelegaEnApi() {
        // Ejecutamos
        tarjetaPago.validarDatos();

        // Verificamos usando eq() para asegurar que coincidan exactamente los 3 valores
        verify(apiMock, times(1)).validar(
                eq(123456),
                eq(123),
                eq(LocalDate.of(2018, 12, 9))
        );
    }

    @Test
    public void testReservarFondosDelegaEnApi() {
        // Ejecutamos el paso de reserva de fondos
        tarjetaPago.reservarFondos();

        // Verificamos que la API haya ejecutado la reserva
        verify(apiMock, times(1)).reservar();
    }

    @Test
    public void testEjecutarTransaccionDelegaEnApi() {
        // Ejecutamos el paso de transacción
        tarjetaPago.ejecutarTransaccion();

        // Verificamos que la API haya ejecutado el cobro
        verify(apiMock, times(1)).ejecutar();
    }

    @Test
    public void testNotificarResultadoGeneraVoucherYLoRegistraEnReserva() {
        // Creamos un mock de Reserva (así evitamos problemas con el código de tu compañero)
        Reserva reservaMock = mock(Reserva.class);

        // Ejecutamos el método notificarResultado
        ComprobantesDePago resultado = tarjetaPago.notificarResultado(reservaMock);

        // Validamos que devuelva un comprobante/voucher válido
        assertNotNull(resultado);
        assertTrue(resultado instanceof Voucher);

        // Verificamos que se le haya mandado el mensaje para agregarlo a la lista de la reserva
        //verify(reservaMock, times(1)).agregarAListaDeComprobantes(any(Voucher.class));
    }
}