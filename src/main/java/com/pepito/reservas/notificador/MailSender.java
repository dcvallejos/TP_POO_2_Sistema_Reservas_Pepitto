package com.pepito.reservas.notificador;

public interface MailSender {

    void enviarMail(String direccionDestino, String titulo, String mensaje, Object adjunto);

}
