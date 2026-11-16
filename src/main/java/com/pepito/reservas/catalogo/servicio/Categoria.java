package com.pepito.reservas.catalogo.servicio;

/**
 * Categoría
 *
 * Descripción:
 *  Categorías del servicio respetando el enunciado. Son utilizables por criterio de búsqueda de Categoría.
 *  La ventaja es que permite las comparaciones directas por enum y no por normalización libre de Strings.
 * */


public enum Categoria {
    ALOJAMIENTO,
    VUELO,
    EXCURSION,
    ALQUILER_AUTO
}