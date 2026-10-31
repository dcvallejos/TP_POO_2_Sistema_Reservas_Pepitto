package com.pepito.reservas.metodoDePago;

import java.time.LocalDate;

public interface ApiTarjetaDeCredito {

    public void validar(int numeroDeTarjeta,int cvv , LocalDate fechaDeVencimiento);
    public void reservar();
    public void ejecutar();
}
