package com.pepito.reservas.documento;

import com.pepito.reservas.catalogo.ItemCatalogo;
import com.pepito.reservas.catalogo.servicio.Periodo;
import com.pepito.reservas.cliente.Cliente;
import com.pepito.reservas.reserva.ItemReservado;
import com.pepito.reservas.reserva.Reserva;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class DocumentosTest {
    /*
    *   Testing dedicado a probar TODOS los tipos de documentos
    *   - Comprobantes de pago -> Reflejan numero de transaccion, monto y atributos particulares de subclases
    *   - Documentos generales -> Reflejan fecha y contador de documentos, asimismo atributos particulares de subclases
    * */
    private static final Periodo PERIODO = new Periodo(LocalDate.of(2026, 12, 21), LocalDate.of(2026, 12, 23));

    private final Cliente cliente = new Cliente("Ana Pérez", "27-12345678-9", "Calle Falsa 123", "ana@mail.com");

    private static ItemCatalogo item(double precio) {
        ItemCatalogo item = mock(ItemCatalogo.class);
        when(item.precioFinal()).thenReturn(precio);
        when(item.validate()).thenReturn(true);
        return item;
    }

    // -> Documento (numeración y fecha )

    @Test
    void cada_documento_nuevo_recibe_un_numero_distinto_y_creciente() {
        Documento primero = new CuponDescuento(0.05);
        Documento segundo = new CuponDescuento(0.05);

        assertNotEquals(primero.getNumero(), segundo.getNumero());
        assertTrue(Integer.parseInt(segundo.getNumero()) > Integer.parseInt(primero.getNumero()));
    }

    @Test
    void el_contador_refleja_el_ultimo_numero_emitido() {
        Documento documento = new CuponDescuento(0.05);

        assertEquals(Integer.parseInt(documento.getNumero()), Documento.getContadorDocumentos());
    }

    @Test
    void la_fecha_de_emision_es_el_momento_de_creacion() {
        LocalDateTime antes = LocalDateTime.now(ZoneOffset.UTC);
        Documento documento = new CuponDescuento(0.05);
        LocalDateTime despues = LocalDateTime.now(ZoneOffset.UTC);

        assertFalse(documento.getFechaEmision().isBefore(antes));
        assertFalse(documento.getFechaEmision().isAfter(despues));
    }

    // -> Factura

    @Test
    void la_factura_registra_los_datos_del_cliente_y_el_monto() {
        Factura factura = new Factura(cliente, 250.0);

        assertEquals("Ana Pérez", factura.getNombreCliente());
        assertEquals("27-12345678-9", factura.getCuitCiente());
        assertEquals("Calle Falsa 123", factura.getDireccionCliente());
        assertEquals("ana@mail.com", factura.getCorreoElectronicoCliente());
        assertEquals(250.0, factura.getMonto());
    }

    // -> VoucherReserva

    @Test
    void el_voucher_de_reserva_lista_los_items_reservados() {
        Reserva reserva = new Reserva(cliente, 1);
        reserva.agregarItem(item(100.0), PERIODO);
        reserva.agregarItem(item(50.0), PERIODO);

        VoucherReserva voucher = new VoucherReserva(reserva);

        assertEquals(reserva.getItems(), voucher.getListaItems());
    }

    @Test
    void el_voucher_es_una_foto_de_la_reserva_al_emitirse() {
        Reserva reserva = new Reserva(cliente, 1);
        reserva.agregarItem(item(100.0), PERIODO);
        VoucherReserva voucher = new VoucherReserva(reserva);

        reserva.agregarItem(item(50.0), PERIODO);

        assertEquals(1, voucher.getListaItems().size());
    }

    @Test
    void los_items_del_voucher_no_se_pueden_modificar_desde_afuera() {
        Reserva reserva = new Reserva(cliente, 1);
        reserva.agregarItem(item(100.0), PERIODO);
        VoucherReserva voucher = new VoucherReserva(reserva);
        List<ItemReservado> items = voucher.getListaItems();

        try {
            items.clear();
        } catch (UnsupportedOperationException esperado) {
            // una vista inmodificable también cumple
        }

        assertEquals(1, voucher.getListaItems().size());
    }

    // -> Comprobantes de pago

    @Test
    void el_comprobante_de_transferencia_tiene_numero_de_operacion_y_monto() {
        ComprobantePago comprobante = new ComprobanteTransferencia("OP-77", 300.0, "0000003100010000000001", "mi.alias");

        assertEquals("OP-77", comprobante.getCodigoTransaccion());
        assertEquals(300.0, comprobante.getMonto());
    }

    @Test
    void la_notificacion_push_informa_el_codigo_y_el_monto_del_pago() {
        ComprobantePago comprobante = new ComprobanteBilletera("TX-9", 120.5);

        String mensaje = new NotificacionPush(comprobante).getNotificacion();

        assertTrue(mensaje.contains("TX-9"));
        assertTrue(mensaje.contains("120.5"));
    }

    @Test
    void la_notificacion_push_es_un_documento_y_no_un_comprobante_de_pago() {
        Documento push = new NotificacionPush(new ComprobanteBilletera("TX-9", 120.5));

        assertFalse(push instanceof ComprobantePago);
    }
}
