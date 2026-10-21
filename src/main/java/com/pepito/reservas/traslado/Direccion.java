package com.pepito.reservas.traslado;

public record Direccion(String pais, String ciudad, String calle, int numero) {

    public Direccion {
        if (pais == null || pais.isBlank()
                || ciudad == null || ciudad.isBlank()
                || calle == null || calle.isBlank()) {
            throw new IllegalArgumentException("País, ciudad y calle son obligatorios");
        }
        if (numero <= 0) {
            throw new IllegalArgumentException("El número de calle debe ser positivo");
        }
    }
}