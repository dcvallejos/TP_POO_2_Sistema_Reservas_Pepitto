package com.pepito.reservas.catalogo.servicio;

import com.pepito.reservas.catalogo.ItemCatalogo;

/**
 * GestorServicios
 *
 * Descripción:
 *  Contrato de los objetos que administran una colección de ítems del catálogo: el Catálogo y el Paquete.
 *
 * Decisión:
 *  Las operaciones de agregar y remover items no tienen sentido en las Leafs del patrón Composite. Siguiendo el
 *  principio de "variante segura" del Composite, segun Gamma et al., solo se implementa este contrato en el Paquete
 *  (composite): Una hoja no puede recibir agregarItem por error.
 *  
 * */

public interface GestorServicios {

    void agregarItem(ItemCatalogo item);

    void removerItem(ItemCatalogo item);

    Integer cantidadItems();
}
