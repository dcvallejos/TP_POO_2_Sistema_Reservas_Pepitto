package com.pepito.reservas.metodoDePago;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;

import com.pepito.reservas.documento.ComprobantePago;
import com.pepito.reservas.documento.VoucherTarjeta;
import com.pepito.reservas.metodoDePago.api.ApiTarjetaDeCredito;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TarjetaDeCreditoInternacionalTest {

    private ApiTarjetaDeCredito apiMock;
    private TarjetaDeCreditoInternacional tarjetaPago;
    private LocalDate fechaDeVencimiento;

    @BeforeEach
    public void setUp() {

        apiMock = mock(ApiTarjetaDeCredito.class);

        LocalDate fechaVencimiento = LocalDate.of(2018, 12, 9);

        // 2. Instanciamos la clase a testear pasándole el mock y datos de prueba
        tarjetaPago = new TarjetaDeCreditoInternacional("123456", 123, fechaVencimiento, apiMock);
    }

    @Test
    public void testValidarDatosDelegaEnApi() {
        tarjetaPago.validarDatos();


        verify(apiMock, times(1)).validar(
                eq("123456"),
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

        String codigoTransaccion = "";
        when(apiMock.ejecutar()).thenReturn(codigoTransaccion);

        ComprobantePago resultado = tarjetaPago.generarComprobante(100.0,codigoTransaccion);

        assertNotNull(resultado);
        assertInstanceOf(VoucherTarjeta.class, resultado);


    }
}