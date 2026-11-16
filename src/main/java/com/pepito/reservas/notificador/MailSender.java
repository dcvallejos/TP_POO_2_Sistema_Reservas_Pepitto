package com.pepito.reservas.notificador;


/** MailSender - Interfaz dada por el enunciado. Se inyecta en cada observer; en tests se reemplaza por un mock. */

public interface MailSender {

    void enviarMail(String direccionDestino, String titulo, String mensaje, Object adjunto);

}
