package com.pepito.reservas.notificador;

import com.pepito.reservas.cliente.Cliente;
import com.pepito.reservas.reserva.EstadoReserva;
import com.pepito.reservas.reserva.NombreEstado;
import com.pepito.reservas.reserva.Reserva;
import com.pepito.reservas.reserva.suscriptor.ReservaObserver;

public class NotificadorMail implements ReservaObserver {

    MailSender mailSender;

    public NotificadorMail(MailSender mailSender){
        this.mailSender = mailSender;
    }

    @Override
    public void update(Reserva reserva, EstadoReserva anterior, EstadoReserva nuevo) {
        Cliente cliente = reserva.getCliente();

        if(
                    nuevo.getNombre() == NombreEstado.CONFIRMADA
                ||  nuevo.getNombre() == NombreEstado.EN_CURSO
                ||  nuevo.getNombre() == NombreEstado.FINALIZADA
        ) {

            mailSender.enviarMail(
                    cliente.getCorreoElectronico(),
                    "Notificación de cambio de Estado de su Reserva",
                    "Se le notifica el cambio de estado de la reserva de " + anterior.getNombre() + " a " + nuevo.getNombre(),
                    null
            );
        }
    }

}


