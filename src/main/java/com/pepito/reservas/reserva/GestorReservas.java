package com.pepito.reservas.reserva;

import com.pepito.reservas.catalogo.ItemCatalogo;
import com.pepito.reservas.reserva.estado.MetodoInvalidoException;

public interface GestorReservas {

    void agregarServicio(ItemCatalogo item);
    void quitarServicio(ItemCatalogo item);

    void confirmar();
    void checkIn();
    void ejecutar();   // pasa a EN_CURSO
    void checkOut(); // pasa a FINALIZADO
    void cancelar();

    void rechazar(String message) throws MetodoInvalidoException;

}