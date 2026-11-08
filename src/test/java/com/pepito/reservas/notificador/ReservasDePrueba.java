package com.pepito.reservas.notificador;

import com.pepito.reservas.catalogo.ItemCatalogo;
import com.pepito.reservas.catalogo.servicio.Periodo;
import com.pepito.reservas.cliente.Cliente;
import com.pepito.reservas.documento.VoucherTarjeta;
import com.pepito.reservas.metodoDePago.MetodoDePago;
import com.pepito.reservas.reserva.RegistroReservas;
import com.pepito.reservas.reserva.Reserva;
import com.pepito.reservas.reserva.suscriptor.ReservaObserver;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;

import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/*
 * Fixture y metodos helper para tests Notificador
 * Las reservas se crean como en el sistema real:
 * -> desde un RegistroReservas
 * -> con el subsistema suscripto, y
 * -> se mueven solo con las operaciones disponibles de la Reserva.
 */
final class ReservasDePrueba {

    static final Clock RELOJ = Clock.fixed(Instant.parse("2026-11-12T10:00:00Z"), ZoneOffset.UTC);
    static final Periodo PERIODO = new Periodo(LocalDate.of(2026, 12, 21), LocalDate.of(2026, 12, 23));
    static final String CORREO = "ana@mail.com";

    private ReservasDePrueba() {
    }

    static Cliente cliente() {
        return new Cliente("Ana Pérez", "27-12345678-9", "Calle Falsa 123", CORREO);
    }

    static Reserva reservaObservadaPor(ReservaObserver subsistema, int pasajeros) {
        RegistroReservas registro = new RegistroReservas(RELOJ);
        registro.agregarSuscriptor(subsistema);
        Reserva reserva = registro.crearReserva(cliente(), pasajeros);
        reserva.agregarItem(item(100.0), PERIODO);
        return reserva;
    }

    static ItemCatalogo item(double precioFinal) {
        ItemCatalogo item = mock(ItemCatalogo.class);
        when(item.precioFinal()).thenReturn(precioFinal);
        when(item.validate()).thenReturn(true);
        return item;
    }

    /* Medio de pago que siempre aprueba y devuelve un comprobante por el monto cobrado. */
    static MetodoDePago pagoAprobado() {
        MetodoDePago pago = mock(MetodoDePago.class);
        when(pago.procesarPago(anyDouble())).thenAnswer(inv -> new VoucherTarjeta("TX-1", inv.getArgument(0)));
        return pago;
    }
}
