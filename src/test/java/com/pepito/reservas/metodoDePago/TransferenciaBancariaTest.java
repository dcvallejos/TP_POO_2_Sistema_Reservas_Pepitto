package com.pepito.reservas.metodoDePago;



import com.pepito.reservas.documento.ComprobantePago;
import com.pepito.reservas.documento.ComprobanteTransferencia;
import com.pepito.reservas.metodoDePago.api.ApiTransferencia;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.mockito.Mockito.*;

public class TransferenciaBancariaTest {

    private ApiTransferencia apiTransferenciaMock;
    private TransferenciaBancaria transferenciaBancaria;

    private final String CBU_PRUEBA = "123456789";
    private final String ALIAS_PRUEBA = "mi.alias.cbu";

    @BeforeEach
    public void setUp() {

        apiTransferenciaMock = mock(ApiTransferencia.class);

        transferenciaBancaria = new TransferenciaBancaria(apiTransferenciaMock, CBU_PRUEBA, ALIAS_PRUEBA);
    }

    @Test
    public void testValidarDatosDelegaEnApiConCbuYAlias() {

        transferenciaBancaria.validarDatos();


        verify(apiTransferenciaMock, times(1)).validar(eq(CBU_PRUEBA), eq(ALIAS_PRUEBA));
    }

    @Test
    public void testReservarFondosNoAplica() {

        transferenciaBancaria.reservarFondos();


        verify(apiTransferenciaMock, never()).ejecutar();
    }

    @Test
    public void testEjecutarTransaccionDelegaEnApi() {

        transferenciaBancaria.ejecutarTransaccion();


        verify(apiTransferenciaMock, times(1)).ejecutar();
    }

    @Test
    public void procesarPagoGeneraUnComprobanteDeTransferenciaConElNumeroDeOperacion() {
        when(apiTransferenciaMock.ejecutar()).thenReturn("OP-77");

        ComprobantePago comprobante = transferenciaBancaria.procesarPago(300.0);

        assertInstanceOf(ComprobanteTransferencia.class, comprobante);
        assertEquals("OP-77", comprobante.getCodigoTransaccion());
        assertEquals(300.0, comprobante.getMonto());
    }

    @Test
    public void procesarPagoRegistraElComprobanteGenerado() {
        when(apiTransferenciaMock.ejecutar()).thenReturn("OP-77");

        ComprobantePago comprobante = transferenciaBancaria.procesarPago(300.0);

        assertEquals(List.of(comprobante), transferenciaBancaria.getComprobantesRegistrados());
    }

    @Test
    public void procesarPagoValidaElCbuYAliasAntesDeEjecutar() {
        when(apiTransferenciaMock.ejecutar()).thenReturn("OP-77");

        transferenciaBancaria.procesarPago(300.0);

        var orden = inOrder(apiTransferenciaMock);
        orden.verify(apiTransferenciaMock).validar(CBU_PRUEBA, ALIAS_PRUEBA);
        orden.verify(apiTransferenciaMock).ejecutar();
    }

    @Test
    public void laTransferenciaConoceSuCbuYAlias() {
        assertEquals(CBU_PRUEBA, transferenciaBancaria.getCbu());
        assertEquals(ALIAS_PRUEBA, transferenciaBancaria.getAlias());
    }
}

