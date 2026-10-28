package com.pepito.reservas.metodoDePago;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class VoucherTest {

    @Test
    public void testCreacionYObtencionDeNroId() {

        int idEsperado = 12345;
        Voucher voucher = new Voucher(idEsperado);

        assertEquals(idEsperado, voucher.getNroId());
    }

    @Test
    public void testCreacionConIdCeroONegativo() {

        Voucher voucherCero = new Voucher(0);
        assertEquals(0, voucherCero.getNroId());

        Voucher voucherNegativo = new Voucher(-99);
        assertEquals(-99, voucherNegativo.getNroId());
    }

    @Test
    public void testHerenciaDeComprobante() {

        Voucher voucher = new Voucher(1);

        assertTrue(voucher instanceof Comprobante);
    }
}