package main.reserva;

import main.reserva.estado.EstadoReserva;
import main.reserva.estado.MetodoInvalidoException;
import main.reserva.suscriptor.ReservaObserver;
import main.servicio.GestorServicios;
import main.catalogo.ItemCatalogo;
import main.cliente.Cliente;
import main.traslado.MetodoTraslado;

import java.util.List;

public class Reserva implements GestorServicios, GestorReservas {

    Cliente cliente;
    List<ItemCatalogo> items;
    EstadoReserva estado;
    MetodoTraslado metodoTraslado;
    List<VoucherCredito> vouchers;
    List<ReservaObserver> observadores;

    public Reserva(){}

    @Override
    public void agregarItem(ItemCatalogo item) {

    }

    @Override
    public void removerItem(ItemCatalogo item) {

    }

    @Override
    public void agregarServicio(ItemCatalogo item) {

    }

    @Override
    public void quitarServicio(ItemCatalogo item) {

    }

    @Override
    public void confirmar() {

    }

    @Override
    public void checkIn() {

    }

    @Override
    public void ejecutar() {

    }

    @Override
    public void checkOut() {

    }

    @Override
    public void cancelar() {

    }

    @Override
    public void rechazar(String message) throws MetodoInvalidoException {
        this.estado.rechazar(message);
    }

    public void agregarSuscriptor(ReservaObserver observador){

    }
    public void quitarSuscriptor(ReservaObserver observador){

    }
    public void notificar(EstadoReserva anterior, EstadoReserva nuevo){

    }

}
