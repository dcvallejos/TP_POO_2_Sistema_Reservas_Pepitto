package com.pepito.reservas.metodoDePago;

public interface ApiTransferencia {

    public void validar(int cbu , String alias);
    public void reservar();
    public void ejecutar();
    public void notificar();


}
