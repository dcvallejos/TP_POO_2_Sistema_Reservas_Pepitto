package com.pepito.reservas.documento;

import java.time.Clock;
import java.time.LocalDateTime;

public abstract class Documento {

    private static Integer contadorDocumentos = 0;

    private final String numero;
    private final LocalDateTime fechaEmision;

    public Documento(){
        this.numero = String.valueOf(++contadorDocumentos);
        this.fechaEmision = LocalDateTime.now(Clock.systemUTC());
    }

    public String getNumero() {
        return numero;
    }

    public LocalDateTime getFechaEmision() {
        return fechaEmision;
    }

    public static Integer getContadorDocumentos() {
        return contadorDocumentos;
    }
}
