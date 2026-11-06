package com.pepito.reservas.reporte;



import com.pepito.reservas.reportes.ExposicionDeProvedor;
import com.pepito.reservas.reportes.OcupacionYCupo;
import com.pepito.reservas.reportes.ReporteHtmlVisitor;
import com.pepito.reservas.reportes.ServicioMasReservado;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ReporteHtmlVisitorTest {

    private ReporteHtmlVisitor visitor;
    private ServicioMasReservado servicioMock;
    private OcupacionYCupo ocupacionMock;
    private ExposicionDeProvedor exposicionMock;

    @BeforeEach
    public void setUp() {
        visitor = new ReporteHtmlVisitor();
        servicioMock = mock(ServicioMasReservado.class);
        ocupacionMock = mock(OcupacionYCupo.class);
        exposicionMock = mock(ExposicionDeProvedor.class);
    }

    @Test
    public void testVisitarServiciosMasReservadosFormatoHtml() {
        String resultado = visitor.visitorServiciosMasReservados(servicioMock);

        assertNotNull(resultado);
        assertTrue(resultado.contains("<h1>"));
        assertTrue(resultado.contains("<table>"));
    }

    @Test
    public void testVisitarOcupacionYCupoFormatoHtml() {
        String resultado = visitor.visitorOcupacionYCupoDisponible(ocupacionMock);

        assertNotNull(resultado);
        assertTrue(resultado.contains("<h1>"));
    }

    @Test
    public void testVisitarExposicionDeProveedorFormatoHtml() {
        String resultado = visitor.visitorExposicionDeProvedor(exposicionMock);

        assertNotNull(resultado);
        assertTrue(resultado.contains("<h1>"));
    }
}