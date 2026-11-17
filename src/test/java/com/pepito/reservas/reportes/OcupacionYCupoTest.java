package com.pepito.reservas.reportes;

import com.pepito.reservas.catalogo.Catalogo;
import com.pepito.reservas.catalogo.servicio.Categoria;
import com.pepito.reservas.catalogo.servicio.Paquete;
import com.pepito.reservas.catalogo.servicio.Servicio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.pepito.reservas.reportes.ReportesDePrueba.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class OcupacionYCupoTest {

    private Catalogo catalogo;
    private Servicio hotel;
    private Servicio hostel;
    private Servicio excursion;

    @BeforeEach
    void setUp() {
        catalogo = new Catalogo();
        hotel = servicio("Hotel", "AIRBNB", Categoria.ALOJAMIENTO, 1000.0, 10);
        hostel = servicio("Hostel", "AIRBNB", Categoria.ALOJAMIENTO, 400.0, 5);
        excursion = servicio("Excursión", "GUIAS SA", Categoria.EXCURSION, 300.0, 4);
    }

    @Test
    void sumaLosCuposDeCadaCategoriaEnTodosLosDiasDelPeriodo() {
        catalogo.agregarItem(hotel);
        catalogo.agregarItem(hostel);
        catalogo.agregarItem(excursion);
        hotel.reservar(DEL_10_AL_12, 2);

        List<FilaOcupacion> filas = new OcupacionYCupo(catalogo, DEL_10_AL_12).getFilas();

        assertEquals(List.of(
                new FilaOcupacion(Categoria.ALOJAMIENTO, 45, 6, 39),
                new FilaOcupacion(Categoria.EXCURSION, 12, 0, 12)), filas);
    }

    @Test
    void unServicioSueltoYDentroDeUnPaqueteSeCuentaUnaSolaVez() {
        Paquete pack = new Paquete("Pack", "hotel y excursión", 0.1, MADRID);
        pack.agregarItem(hotel);
        pack.agregarItem(excursion);
        catalogo.agregarItem(hotel);
        catalogo.agregarItem(pack);

        List<FilaOcupacion> filas = new OcupacionYCupo(catalogo, DEL_10_AL_12).getFilas();

        assertEquals(List.of(
                new FilaOcupacion(Categoria.ALOJAMIENTO, 30, 0, 30),
                new FilaOcupacion(Categoria.EXCURSION, 12, 0, 12)), filas);
    }

    @Test
    void unCatalogoVacioNoTieneFilas() {
        assertTrue(new OcupacionYCupo(catalogo, DEL_10_AL_12).getFilas().isEmpty());
    }

    @Test
    void aceptarLePideAlVisitorElFormatoDeEsteReporte() {
        OcupacionYCupo reporte = new OcupacionYCupo(catalogo, DEL_10_AL_12);
        ReporteVisitor visitor = mock(ReporteVisitor.class);

        reporte.aceptar(visitor);

        verify(visitor).visitarOcupacionYCupo(reporte);
    }
}