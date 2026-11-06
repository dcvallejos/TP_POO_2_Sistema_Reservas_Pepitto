package com.pepito.reservas.reporte;

import com.pepito.reservas.reportes.OcupacionYCupo;
import com.pepito.reservas.reportes.ReporteVisitor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class OcupacionYCupoTest {

    private OcupacionYCupo ocupacionYCupo;
    private ReporteVisitor visitorMock;

    @BeforeEach
    public void setUp() {
        ocupacionYCupo = new OcupacionYCupo();
        visitorMock = mock(ReporteVisitor.class);
    }

    @Test
    public void testAceptarVisitorRetornaReporteValido() {
        when(visitorMock.visitorOcupacionYCupoDisponible(ocupacionYCupo)).thenReturn("Reporte Ocupación y Cupo: 80% Ocupado");

        String resultado = ocupacionYCupo.aceptar(visitorMock);

        assertNotNull(resultado);
        assertEquals("Reporte Ocupación y Cupo: 80% Ocupado", resultado);
        verify(visitorMock, times(1)).visitorOcupacionYCupoDisponible(ocupacionYCupo);
    }

    @Test
    public void testAceptarVisitorConFormatoTextoPlano() {
        when(visitorMock.visitorOcupacionYCupoDisponible(ocupacionYCupo)).thenReturn("OCUPACION_Y_CUPO_PLANO");

        String resultado = ocupacionYCupo.aceptar(visitorMock);

        assertTrue(resultado.contains("OCUPACION"));
        verify(visitorMock).visitorOcupacionYCupoDisponible(ocupacionYCupo);
    }

    @Test
    public void testAceptarVisitorConResultadoVacio() {
        when(visitorMock.visitorOcupacionYCupoDisponible(ocupacionYCupo)).thenReturn("");

        String resultado = ocupacionYCupo.aceptar(visitorMock);

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }

    @Test
    public void testAceptarVisitorLanzaExcepcion() {
        when(visitorMock.visitorOcupacionYCupoDisponible(ocupacionYCupo)).thenThrow(new IllegalStateException("Error de cupo"));

        Exception exception = assertThrows(IllegalStateException.class, () -> {
            ocupacionYCupo.aceptar(visitorMock);
        });

        assertEquals("Error de cupo", exception.getMessage());
    }

    @Test
    public void testAceptarVerificaInvocacionUnicaExacta() {
        when(visitorMock.visitorOcupacionYCupoDisponible(ocupacionYCupo)).thenReturn("OK");

        ocupacionYCupo.aceptar(visitorMock);

        verify(visitorMock, times(1)).visitorOcupacionYCupoDisponible(eq(ocupacionYCupo));
    }
}