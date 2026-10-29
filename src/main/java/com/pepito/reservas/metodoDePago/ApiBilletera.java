package com.pepito.reservas.metodoDePago;

public interface ApiBilletera {

    public void validar();
    public void reservar();
    public void ejecutar();
    public ComprobantePago notificar();
}
