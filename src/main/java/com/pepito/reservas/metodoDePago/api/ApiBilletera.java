package com.pepito.reservas.metodoDePago.api;


import com.pepito.reservas.documento.NotificacionPush;

public interface ApiBilletera {

    void validar();
    void reservar();
    String ejecutar();
    void notificar(NotificacionPush notificacionPush);
}
