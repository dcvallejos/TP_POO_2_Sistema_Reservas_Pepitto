package com.pepito.reservas.notificador;

import com.pepito.reservas.reserva.Reserva;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.List;

import static com.pepito.reservas.notificador.ReservasDePrueba.CORREO;
import static com.pepito.reservas.notificador.ReservasDePrueba.pagoAprobado;
import static com.pepito.reservas.notificador.ReservasDePrueba.reservaObservadaPor;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.clearInvocations;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

/*
 * Testing de Notificador de email:
 * transiciones CONFIRMADA, EN_CURSO y FINALIZADA.
 *      -> envía un correo al cliente con el nuevo estado de su reserva.
 */
class NotificadorMailTest {

    private MailSender mailSender;
    private Reserva reserva;

    @BeforeEach
    void setUp() {
        mailSender = mock(MailSender.class);
        reserva = reservaObservadaPor(new NotificadorMail(mailSender), 1);
    }

    @Test
    void al_confirmar_envia_un_correo_al_cliente_con_el_nuevo_estado() {
        reserva.confirmar(pagoAprobado());

        verify(mailSender).enviarMail(eq(CORREO), anyString(), contains("CONFIRMADA"), any());
    }

    @Test
    void al_iniciar_el_viaje_envia_un_correo_con_el_estado_en_curso() {
        reserva.confirmar(pagoAprobado());
        reserva.hacerCheckIn();
        clearInvocations(mailSender);

        reserva.iniciarViaje();

        verify(mailSender).enviarMail(eq(CORREO), anyString(), contains("EN_CURSO"), any());
    }

    @Test
    void al_finalizar_envia_un_correo_con_el_estado_finalizada() {
        reserva.confirmar(pagoAprobado());
        reserva.hacerCheckIn();
        reserva.iniciarViaje();
        clearInvocations(mailSender);

        reserva.finalizar();

        verify(mailSender).enviarMail(eq(CORREO), anyString(), contains("FINALIZADA"), any());
    }

    @Test
    void el_check_in_no_envia_correo() {
        reserva.confirmar(pagoAprobado());
        clearInvocations(mailSender);

        reserva.hacerCheckIn();

        verifyNoInteractions(mailSender);
    }

    @Test
    void cancelar_una_solicitud_pendiente_no_envia_correo() {
        reserva.cancelar();

        verifyNoInteractions(mailSender);
    }

    @Test
    void cancelar_una_reserva_confirmada_no_envia_correo() {
        reserva.confirmar(pagoAprobado());
        clearInvocations(mailSender);

        reserva.cancelar();

        verifyNoInteractions(mailSender);
    }

    @Test
    void el_camino_completo_envia_exactamente_tres_correos_en_orden() {
        reserva.confirmar(pagoAprobado());
        reserva.hacerCheckIn();
        reserva.iniciarViaje();
        reserva.finalizar();

        ArgumentCaptor<String> mensajes = ArgumentCaptor.forClass(String.class);
        verify(mailSender, times(3)).enviarMail(eq(CORREO), anyString(), mensajes.capture(), any());
        List<String> enviados = mensajes.getAllValues();
        assertEquals(3, enviados.size());
        assertTrue(enviados.get(0).contains("CONFIRMADA"));
        assertTrue(enviados.get(1).contains("EN_CURSO"));
        assertTrue(enviados.get(2).contains("FINALIZADA"));
    }
}
