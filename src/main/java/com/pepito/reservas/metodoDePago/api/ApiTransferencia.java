package com.pepito.reservas.metodoDePago.api;

public interface ApiTransferencia {

    boolean validar(String cbu , String alias);
    String ejecutar();



}
