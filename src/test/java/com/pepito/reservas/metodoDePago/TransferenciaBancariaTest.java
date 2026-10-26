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
        // 1. Creamos el mock de la API externa de transferencia
        apiTransferenciaMock = mock(ApiTransferencia.class);

        // 2. Instanciamos la transferencia bancaria con datos de prueba
        transferenciaBancaria = new TransferenciaBancaria(apiTransferenciaMock, CBU_PRUEBA, ALIAS_PRUEBA);
    }

    @Test
    public void testValidarDatosDelegaEnApiConCbuYAlias() {
        // Ejecutamos
        transferenciaBancaria.validarDatos();

        // Verificamos que se delegue en la API pasando exactamente el CBU y el Alias
        verify(apiTransferenciaMock, times(1)).validar(eq(CBU_PRUEBA), eq(ALIAS_PRUEBA));
    }

    @Test
    public void testReservarFondosNoAplica() {
        // Ejecutamos (como no aplica y está vacío, solo comprobamos que no rompa y no llame a la API)
        transferenciaBancaria.reservarFondos();

        // Verificamos que NUNCA se llame a la API en este paso
        verify(apiTransferenciaMock, never()).ejecutar();
    }

    @Test
    public void testEjecutarTransaccionDelegaEnApi() {
        // Ejecutamos
        transferenciaBancaria.ejecutarTransaccion();

        // Verificamos que se delegue en la API
        verify(apiTransferenciaMock, times(1)).ejecutar();
    }
}

