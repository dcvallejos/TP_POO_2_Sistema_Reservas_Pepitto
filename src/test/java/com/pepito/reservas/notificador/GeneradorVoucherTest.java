package com.pepito.reservas.notificador;

import com.pepito.reservas.documento.Factura;
import com.pepito.reservas.documento.VoucherReserva;
import com.pepito.reservas.reserva.Reserva;
import com.pepito.reservas.traslado.MetodoTraslado;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.pepito.reservas.notificador.ReservasDePrueba.CORREO;
import static com.pepito.reservas.notificador.ReservasDePrueba.pagoAprobado;
import static com.pepito.reservas.notificador.ReservasDePrueba.reservaObservadaPor;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

/*
 * Testing de Generador de voucher/factura:
 * Cuando reserva FINALIZADA -> crea el comprobante fiscal y voucher
 */
class GeneradorVoucherTest {

    private MailSender mailSender;
    private Reserva reserva;

    @BeforeEach
    void setUp() {
        mailSender = mock(MailSender.class);
        reserva = reservaObservadaPor(new GeneradorVoucher(mailSender), 2);
    }

    private void llevarAEnCurso() {
        reserva.confirmar(pagoAprobado());
        reserva.hacerCheckIn();
        reserva.iniciarViaje();
    }

    private List<?> adjuntosEnviados() {
        ArgumentCaptor<Object> adjunto = ArgumentCaptor.forClass(Object.class);
        verify(mailSender).enviarMail(eq(CORREO), anyString(), anyString(), adjunto.capture());
        return new ArrayList<>(assertInstanceOf(Collection.class, adjunto.getValue()));
    }

    private static <T> T unicoDeTipo(List<?> documentos, Class<T> tipo) {
        List<?> deEseTipo = documentos.stream().filter(tipo::isInstance).toList();
        assertEquals(1, deEseTipo.size(), "Se esperaba exactamente un " + tipo.getSimpleName());
        return tipo.cast(deEseTipo.get(0));
    }

    @Test
    void no_genera_nada_antes_de_finalizar() {
        llevarAEnCurso();

        verifyNoInteractions(mailSender);
    }

    @Test
    void no_genera_nada_si_la_reserva_se_cancela() {
        reserva.confirmar(pagoAprobado());
        reserva.cancelar();

        verifyNoInteractions(mailSender);
    }

    @Test
    void al_finalizar_envia_al_cliente_un_voucher_y_una_factura() {
        llevarAEnCurso();

        reserva.finalizar();

        List<?> adjuntos = adjuntosEnviados();
        unicoDeTipo(adjuntos, VoucherReserva.class);
        unicoDeTipo(adjuntos, Factura.class);
    }

    @Test
    void el_voucher_lista_los_items_de_la_reserva() {
        llevarAEnCurso();

        reserva.finalizar();

        VoucherReserva voucher = unicoDeTipo(adjuntosEnviados(), VoucherReserva.class);
        assertEquals(reserva.getItems(), voucher.getListaItems());
    }

    @Test
    void la_factura_lleva_los_datos_del_cliente() {
        llevarAEnCurso();

        reserva.finalizar();

        Factura factura = unicoDeTipo(adjuntosEnviados(), Factura.class);
        assertEquals("Ana Pérez", factura.getNombreCliente());
        assertEquals("27-12345678-9", factura.getCuitCiente());
        assertEquals("Calle Falsa 123", factura.getDireccionCliente());
        assertEquals(CORREO, factura.getCorreoElectronicoCliente());
    }

    @Test
    void la_factura_es_por_lo_que_el_cliente_pago_aunque_despues_cambie_el_costo_del_traslado() {
        MetodoTraslado traslado = mock(MetodoTraslado.class);
        when(traslado.costo(any())).thenReturn(50.0);
        reserva.elegirTraslado(traslado);
        llevarAEnCurso();                                   // cobra 2 pasajeros x 100 + 50 de tasas = 250
        when(traslado.costo(any())).thenReturn(80.0);     // el traslado sube después del pago

        reserva.finalizar();

        Factura factura = unicoDeTipo(adjuntosEnviados(), Factura.class);
        assertEquals(250.0, factura.getMonto());
    }
}
