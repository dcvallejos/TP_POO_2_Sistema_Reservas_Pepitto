package com.pepito.reservas.documento;

import com.pepito.reservas.reserva.ItemReservado;
import com.pepito.reservas.reserva.Reserva;

import java.util.List;

/**
 * VoucherReserva
 *  Detalle de los servicios reservados. Guarda una copia de los ítems (no la lista viva de la reserva) y no
 *  tiene monto: la parte fiscal es la Factura.
 */

public class VoucherReserva extends Documento {

    List<ItemReservado> listaItems;

    public VoucherReserva(Reserva reserva) {
        super();
        this.listaItems = List.copyOf(reserva.getItems());
    }

    public List<ItemReservado> getListaItems() {
        return listaItems;
    }
}
