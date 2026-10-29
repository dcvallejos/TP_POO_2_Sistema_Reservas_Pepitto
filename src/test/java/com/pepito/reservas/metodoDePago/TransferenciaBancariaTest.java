package com.pepito.reservas.metodoDePago;



import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class TransferenciaBancariaTest {

    private ApiTransferencia apiTransferenciaMock;
    private TransferenciaBancaria transferenciaBancaria;

    private final int CBU_PRUEBA = 123456789;
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
}

