package com.pepito.reservas.metodoDePago.api;

public interface ApiTransferencia {

    void validar(String cbu , String alias);
    String ejecutar();



}
