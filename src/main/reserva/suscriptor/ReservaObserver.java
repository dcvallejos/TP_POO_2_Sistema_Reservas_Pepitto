package main.reserva.suscriptor;

import main.reserva.Reserva;
import main.reserva.estado.EstadoReserva;

public interface ReservaObserver {

    public void CambioDeEstado(Reserva reserva, EstadoReserva anterior, EstadoReserva nuevo);

}
