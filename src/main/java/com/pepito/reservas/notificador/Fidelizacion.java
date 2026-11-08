package com.pepito.reservas.notificador;

import com.pepito.reservas.cliente.Cliente;
import com.pepito.reservas.documento.CuponDescuento;
import com.pepito.reservas.reserva.EstadoReserva;
import com.pepito.reservas.reserva.NombreEstado;
import com.pepito.reservas.reserva.Reserva;
import com.pepito.reservas.reserva.suscriptor.ReservaObserver;

public class Fidelizacion implements ReservaObserver{

    MailSender mailSender;

    public Fidelizacion(MailSender mailSender){
        this.mailSender = mailSender;
    }

    @Override
    public void update(Reserva reserva, EstadoReserva anterior, EstadoReserva nuevo) {
        Cliente cliente = reserva.getCliente();

        if(nuevo.getNombre() != NombreEstado.CANCELADA) {return;}

        CuponDescuento cupon = generarCupon(reserva);
        mailSender.enviarMail(
                cliente.getCorreoElectronico(),
                "Envío de Cupón",
                "Lamentamos que haya cancelado su reserva. Le ofrecemos un cupón de descuento para cuando decida nuevamente contar con nuestros servicios. Lo esperamos!",
                cupon
        );

    }

    private CuponDescuento generarCupon(Reserva reserva){
        return new CuponDescuento(0.05);
    }


}
