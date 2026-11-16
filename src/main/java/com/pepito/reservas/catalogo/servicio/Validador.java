package com.pepito.reservas.catalogo.servicio;

/**
 * Validador
 *
 * Descripción:
 *  Define algo que sabe si su estado es válido. Es implementado por ItemCatalogo y AtributoDinamico. Esto permite
 *  consular a un objeto si tanto su estado como sus atributos son válidos. Asimismo permite que los hijos del
 *  Composite puedan responderle al padre, pudiendo este último responder.
 *
 * Decisión:
 *  Antes tenia un metodo adicional de "disponible" pero no tenia sentido tenerlo como contrato, dado que solo tiene
 *  sentido dentro del Leaf. Por lo que por Principio de Segregación de Interfaces se lo removió y se definió esta
 *  interfaz única.
 * */

public interface Validador {

    Boolean validate();

}
