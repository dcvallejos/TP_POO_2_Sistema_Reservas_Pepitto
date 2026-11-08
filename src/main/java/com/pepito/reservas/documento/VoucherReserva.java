package com.pepito.reservas.documento;

import com.pepito.reservas.reserva.ItemReservado;
import com.pepito.reservas.reserva.Reserva;

import java.util.List;

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
