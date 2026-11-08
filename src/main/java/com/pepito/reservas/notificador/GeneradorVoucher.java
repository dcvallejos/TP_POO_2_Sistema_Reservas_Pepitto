package com.pepito.reservas.notificador;

import com.pepito.reservas.cliente.Cliente;
import com.pepito.reservas.documento.Documento;
import com.pepito.reservas.documento.Factura;
import com.pepito.reservas.documento.VoucherReserva;
import com.pepito.reservas.reserva.*;
import com.pepito.reservas.reserva.suscriptor.ReservaObserver;

import java.util.ArrayList;
import java.util.List;

public class GeneradorVoucher implements ReservaObserver {

    MailSender mailSender;

    public GeneradorVoucher(MailSender mailSender){
        this.mailSender = mailSender;
    }

    @Override
    public void update(Reserva reserva, EstadoReserva anterior, EstadoReserva nuevo) {
        Cliente cliente = reserva.getCliente();

        // Si no es finalizada, no hace nada
        if (nuevo.getNombre() != NombreEstado.FINALIZADA ){ return; }

        // Generación de documentos
        VoucherReserva voucher = generarVoucher(reserva);
        Factura factura = generarFactura(cliente,reserva);

        // Generación de documentos adjuntos
        List<Documento> adjuntos = new ArrayList<>(List.of(voucher, factura));

        mailSender.enviarMail(
                cliente.getCorreoElectronico(),
                "Envío de comprobantes por reserva " + voucher.getNumero(),
                "Muchas gracias por disfrutar de nuestros servicios. Dado que su reserva ha finalizado, le compartimos el voucher y facturas correspondientes. Lo esperamos nuevamente.",
                adjuntos
        );
    }

    private VoucherReserva generarVoucher(Reserva reserva){
        return new VoucherReserva(reserva);
    }

    private Factura generarFactura(Cliente cliente, Reserva reserva){
        double monto = reserva.getCobro()
                .map(Cobro::total)
                .orElseThrow(() -> new ReservaException("Una reserva finalizada siempre tiene cobro."));

        return new Factura(cliente, monto);
    }
}
