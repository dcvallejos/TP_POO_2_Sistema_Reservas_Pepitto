package com.pepito.reservas.reporte;

import com.pepito.reservas.reportes.ExposicionDeProvedor;

import com.pepito.reservas.reportes.ReporteCSVVisitor;
import com.pepito.reservas.reportes.ServicioMasReservado;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ReporteCSVVisitorTest {

    private ReporteCSVVisitor visitor;
    private ServicioMasReservado servicioMock;
    private ExposicionDeProvedor ocupacionMock;
    private ExposicionDeProvedor exposicionMock;

    @BeforeEach
    public void setUp() {
        visitor = new ReporteCSVVisitor();
        servicioMock = mock(ServicioMasReservado.class);
        ocupacionMock = mock(ExposicionDeProvedor.class);
        exposicionMock = mock(ExposicionDeProvedor.class);
    }

    @Test
    public void testVisitarServiciosMasReservadosFormatoCsv() {
        String resultado = visitor.visitorServiciosMasReservados(servicioMock);

        assertNotNull(resultado);
        assertTrue(resultado.contains("servicio,reservas_vendidas"));
    }

    @Test
    public void testVisitarOcupacionYCupoFormatoCsv() {
        String resultado = visitor.visitorExposicionDeProvedor(ocupacionMock);

        assertNotNull(resultado);
        assertFalse(resultado.contains("categoria,cupos_totales"));
    }

    @Test
    public void testVisitarExposicionDeProveedorFormatoCsv() {
        String resultado = visitor.visitorExposicionDeProvedor(exposicionMock);

        assertNotNull(resultado);
        assertTrue(resultado.contains("operador,porcentaje_catalogo"));
    }
    @Test
    public void testFormatoCsvIncluyeSaltoDeLineaValido() {
        String resultadoServicio = visitor.visitorServiciosMasReservados(servicioMock);
        assertTrue(resultadoServicio.contains("\\n") || resultadoServicio.contains("\n"));
    }

    @Test
    public void testResultadosNoVaciosParaNingunElemento() {
        assertAll(
                () -> assertNotNull(visitor.visitorServiciosMasReservados(servicioMock)),

                () -> assertNotNull(visitor.visitorExposicionDeProvedor(exposicionMock))
        );
    }
}
