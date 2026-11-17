package com.pepito.reservas.reportes;

import com.pepito.reservas.catalogo.servicio.Categoria;
import com.pepito.reservas.catalogo.servicio.Paquete;
import com.pepito.reservas.catalogo.servicio.Periodo;
import com.pepito.reservas.catalogo.servicio.Servicio;
import com.pepito.reservas.reserva.RegistroReservas;
import com.pepito.reservas.reserva.Reserva;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static com.pepito.reservas.reportes.ReportesDePrueba.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class ServiciosMasReservadosTest {

    private RegistroReservas registro;
    private Servicio hotel;
    private Servicio excursion;

    @BeforeEach
    void setUp() {
        registro = new RegistroReservas();
        hotel = servicio("Hotel", "AIRBNB", Categoria.ALOJAMIENTO, 1000.0, 10);
        excursion = servicio("Excursión", "GUIAS SA", Categoria.EXCURSION, 300.0, 10);
    }

    private Reserva confirmada(int pasajeros, Servicio... servicios) {
        Reserva reserva = registro.crearReserva(cliente(), pasajeros);
        for (Servicio servicio : servicios) {
            reserva.agregarItem(servicio, DEL_10_AL_12);
        }
        reserva.confirmar(pagoAprobado());
        return reserva;
    }

    @Test
    void cadaPasajeroDeUnaReservaVendidaEsUnaUnidad() {
        confirmada(2, hotel);
        confirmada(3, hotel);

        List<FilaVentas> filas = new ServiciosMasReservados(registro, DICIEMBRE).getFilas();

        assertEquals(List.of(new FilaVentas("Hotel", 5, 1000.0)), filas);
    }

    @Test
    void ordenaDeMasAMenosUnidadesVendidas() {
        confirmada(1, hotel, excursion);
        confirmada(4, excursion);

        List<FilaVentas> filas = new ServiciosMasReservados(registro, DICIEMBRE).getFilas();

        assertEquals(List.of(new FilaVentas("Excursión", 5, 300.0), new FilaVentas("Hotel", 1, 1000.0)), filas);
    }

    @Test
    void noCuentaReservasPendientesNiCanceladas() {
        registro.crearReserva(cliente(), 2).agregarItem(hotel, DEL_10_AL_12);
        confirmada(3, hotel).cancelar();

        assertTrue(new ServiciosMasReservados(registro, DICIEMBRE).getFilas().isEmpty());
    }

    @Test
    void soloCuentaLineasQueSeSuperponenConElPeriodo() {
        confirmada(2, hotel);
        Periodo enero = new Periodo(LocalDate.of(2027, 1, 1), LocalDate.of(2027, 1, 31));
        Periodo hastaEl10 = new Periodo(LocalDate.of(2026, 11, 1), LocalDate.of(2026, 12, 10));

        assertTrue(new ServiciosMasReservados(registro, enero).getFilas().isEmpty());
        assertEquals(1, new ServiciosMasReservados(registro, hastaEl10).getFilas().size());
    }

    @Test
    void elPrecioPromedioEsElCobradoAunqueDespuesCambieElCatalogo() {
        Paquete pack = new Paquete("Pack Madrid", "hotel y excursión", 0.1, MADRID);
        pack.agregarItem(hotel);
        pack.agregarItem(excursion);
        Reserva reserva = registro.crearReserva(cliente(), 2);
        reserva.agregarItem(pack, DEL_10_AL_12);
        reserva.confirmar(pagoAprobado());

        List<FilaVentas> filas = new ServiciosMasReservados(registro, DICIEMBRE).getFilas();

        assertEquals(1, filas.size());
        assertEquals("Pack Madrid", filas.get(0).nombre());
        assertEquals(2, filas.get(0).unidadesVendidas());
        assertEquals(1170.0, filas.get(0).precioPromedio(), 0.001);
    }

    @Test
    void aceptarLePideAlVisitorElFormatoDeEsteReporte() {
        ServiciosMasReservados reporte = new ServiciosMasReservados(registro, DICIEMBRE);
        ReporteVisitor visitor = mock(ReporteVisitor.class);

        reporte.aceptar(visitor);

        verify(visitor).visitarServiciosMasReservados(reporte);
    }
}