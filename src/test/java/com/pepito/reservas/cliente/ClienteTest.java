package com.pepito.reservas.cliente;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ClienteTest {

    @Test
    void el_cliente_expone_los_datos_que_necesita_la_factura() {
        Cliente cliente = new Cliente("Ana Pérez", "27-12345678-9", "Calle Falsa 123", "ana@mail.com");

        assertEquals("Ana Pérez", cliente.getNombre());
        assertEquals("27-12345678-9", cliente.getCuit());
        assertEquals("Calle Falsa 123", cliente.getDireccion());
        assertEquals("ana@mail.com", cliente.getCorreoElectronico());
    }
}
