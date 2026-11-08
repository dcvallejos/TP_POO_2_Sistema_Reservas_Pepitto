package com.pepito.reservas.notificador;

import com.pepito.reservas.documento.CuponDescuento;
import com.pepito.reservas.reserva.Reserva;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static com.pepito.reservas.notificador.ReservasDePrueba.CORREO;
import static com.pepito.reservas.notificador.ReservasDePrueba.pagoAprobado;
import static com.pepito.reservas.notificador.ReservasDePrueba.reservaObservadaPor;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.clearInvocations;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

/*
 * Testing dedicado a Club de Beneficios (fidelización)
 * se cancela una reserva -> se le envía un mensaje con un cupón de descuento del 5%."
 */
class FidelizacionTest {

    private MailSender mailSender;
    private Reserva reserva;

    @BeforeEach
    void setUp() {
        mailSender = mock(MailSender.class);
        reserva = reservaObservadaPor(new Fidelizacion(mailSender), 1);
    }

    private CuponDescuento cuponEnviado() {
        ArgumentCaptor<Object> adjunto = ArgumentCaptor.forClass(Object.class);
        verify(mailSender).enviarMail(eq(CORREO), anyString(), anyString(), adjunto.capture());
        return assertInstanceOf(CuponDescuento.class, adjunto.getValue());
    }

    @Test
    void al_cancelar_una_solicitud_pendiente_envia_al_cliente_un_cupon_del_5_por_ciento() {
        reserva.cancelar();

        assertEquals(0.05, cuponEnviado().getDescuento());
    }

    @Test
    void al_cancelar_una_reserva_confirmada_tambien_envia_el_cupon() {
        reserva.confirmar(pagoAprobado());

        reserva.cancelar();

        assertEquals(0.05, cuponEnviado().getDescuento());
    }

    @Test
    void al_cancelar_un_viaje_en_curso_tambien_envia_el_cupon() {
        reserva.confirmar(pagoAprobado());
        reserva.hacerCheckIn();
        reserva.iniciarViaje();

        reserva.cancelar();

        assertEquals(0.05, cuponEnviado().getDescuento());
    }

    @Test
    void ninguna_transicion_que_no_sea_cancelar_envia_cupon() {
        reserva.confirmar(pagoAprobado());
        reserva.hacerCheckIn();
        reserva.iniciarViaje();
        reserva.finalizar();

        verifyNoInteractions(mailSender);
    }

    @Test
    void cada_cancelacion_genera_un_cupon_nuevo() {
        Reserva otra = reservaObservadaPor(new Fidelizacion(mailSender), 1);
        reserva.cancelar();
        CuponDescuento primero = cuponEnviado();
        clearInvocations(mailSender);

        otra.cancelar();

        CuponDescuento segundo = cuponEnviado();
        assertNotEquals(primero.getNumero(), segundo.getNumero());
    }
}
