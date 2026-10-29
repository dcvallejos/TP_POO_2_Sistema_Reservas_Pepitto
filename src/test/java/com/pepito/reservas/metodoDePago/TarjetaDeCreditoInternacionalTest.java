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

        apiMock = mock(ApiTarjetaDeCredito.class);

        LocalDate fechaVencimiento = LocalDate.of(2018, 12, 9);

        // 2. Instanciamos la clase a testear pasándole el mock y datos de prueba
        tarjetaPago = new TarjetaDeCreditoInternacional(123456, 123, fechaVencimiento, apiMock);
    }

    @Test
    public void testValidarDatosDelegaEnApi() {
        tarjetaPago.validarDatos();


        verify(apiMock, times(1)).validar(
                eq(123456),
                eq(123),
                eq(LocalDate.of(2018, 12, 9))
        );
    }

    @Test
    public void testReservarFondosDelegaEnApi() {

        tarjetaPago.reservarFondos();


        verify(apiMock, times(1)).reservar();
    }

    @Test
    public void testEjecutarTransaccionDelegaEnApi() {

        tarjetaPago.ejecutarTransaccion();


        verify(apiMock, times(1)).ejecutar();
    }

    @Test
    public void testNotificarResultadoGeneraVoucherYLoRegistraEnReserva() {

        Reserva reservaMock = mock(Reserva.class);


        ComprobantePago resultado = tarjetaPago.notificarResultado(reservaMock);


        assertNotNull(resultado);
        assertTrue(resultado instanceof Voucher);


    }
}