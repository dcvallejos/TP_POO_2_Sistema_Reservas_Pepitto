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

class ExposicionPorProveedorTest {

    private Catalogo catalogo;

    @BeforeEach
    void setUp() {
        catalogo = new Catalogo();
    }

    @Test
    void calculaElPorcentajeDelCatalogoDeCadaProveedorDeMayorAMenor() {
        catalogo.agregarItem(servicio("Hotel", "AIRBNB", Categoria.ALOJAMIENTO, 1000.0, 10));
        catalogo.agregarItem(servicio("Excursión", "GUIAS SA", Categoria.EXCURSION, 300.0, 10));
        catalogo.agregarItem(servicio("Hostel", "AIRBNB", Categoria.ALOJAMIENTO, 400.0, 10));
        catalogo.agregarItem(servicio("Depto", "AIRBNB", Categoria.ALOJAMIENTO, 700.0, 10));

        List<FilaExposicion> filas = new ExposicionPorProveedor(catalogo).getFilas();

        assertEquals(List.of(
                new FilaExposicion("AIRBNB", 3, 75.0),
                new FilaExposicion("GUIAS SA", 1, 25.0)), filas);
    }

    @Test
    void unPaqueteDependeDeLosProveedoresDeSusServicios() {
        Servicio vuelo = servicio("Vuelo", "QATAR", Categoria.VUELO, 900.0, 10);
        Servicio hotel = servicio("Hotel", "AIRBNB", Categoria.ALOJAMIENTO, 1000.0, 10);
        Paquete pack = new Paquete("Pack", "vuelo y hotel", 0.1, MADRID);
        pack.agregarItem(vuelo);
        pack.agregarItem(hotel);
        catalogo.agregarItem(pack);
        catalogo.agregarItem(hotel);

        List<FilaExposicion> filas = new ExposicionPorProveedor(catalogo).getFilas();

        assertEquals(List.of(
                new FilaExposicion("QATAR", 1, 50.0),
                new FilaExposicion("AIRBNB", 1, 50.0)), filas);
    }

    @Test
    void unCatalogoVacioNoTieneFilas() {
        assertTrue(new ExposicionPorProveedor(catalogo).getFilas().isEmpty());
    }

    @Test
    void aceptarLePideAlVisitorElFormatoDeEsteReporte() {
        ExposicionPorProveedor reporte = new ExposicionPorProveedor(catalogo);
        ReporteVisitor visitor = mock(ReporteVisitor.class);

        reporte.aceptar(visitor);

        verify(visitor).visitarExposicionPorProveedor(reporte);
    }
}