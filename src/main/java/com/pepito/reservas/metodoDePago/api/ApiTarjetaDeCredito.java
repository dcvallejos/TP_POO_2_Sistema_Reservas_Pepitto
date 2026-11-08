package com.pepito.reservas.metodoDePago.api;

import java.time.LocalDate;

public interface ApiTarjetaDeCredito {

    void validar(String numeroDeTarjeta,int cvv , LocalDate fechaDeVencimiento);
    void reservar();
    String ejecutar();
}
