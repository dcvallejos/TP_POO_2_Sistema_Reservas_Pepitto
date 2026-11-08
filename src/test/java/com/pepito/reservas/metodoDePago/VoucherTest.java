package com.pepito.reservas.metodoDePago;


import com.pepito.reservas.documento.ComprobantePago;
import com.pepito.reservas.documento.VoucherTarjeta;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class VoucherTest {

    @Test
    public void testCreacionYObtencionDeNroId() {

        String idEsperado = "12345";
        VoucherTarjeta voucher = new VoucherTarjeta(idEsperado,200.0);

        assertEquals(idEsperado, voucher.getCodigoTransaccion());
    }

    @Test
    public void testCreacionConIdCeroONegativo() {

        VoucherTarjeta voucherCero = new VoucherTarjeta("0",0.0);
        assertEquals("0", voucherCero.getCodigoTransaccion());

        VoucherTarjeta voucherNegativo = new VoucherTarjeta("-99",0.0);
        assertEquals("-99", voucherNegativo.getCodigoTransaccion());
    }

    @Test
    public void testHerenciaDeComprobante() {

        VoucherTarjeta voucher = new VoucherTarjeta("1",0.0);

        assertInstanceOf(ComprobantePago.class,  voucher);
    }
}