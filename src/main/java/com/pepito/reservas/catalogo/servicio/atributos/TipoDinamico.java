package com.pepito.reservas.catalogo.servicio.atributos;

/**
 * TipoDinamico
 * Sección Strategy del patrón
 *
 * Descripción:
 *  Es el tipo de valor de un atributo dinámico. Se delega al atributo si sabe si un valor le corresponde
 *
 * Decisión:
 *  Como en el enunciado se explicita que se van a crear mas tipos de atributos dinámicos, se descartó la opción de
 *  un ENUM y se crean tipos nuevos generando nuevas clases, sin tocar AtributoDinamico ni Servicio, respetando el
 *  principio Open/Closed.
 *
 * */

public interface TipoDinamico {

    Boolean acepta(Object valor);

}
