package com.pepito.reservas.reporte;

import com.pepito.reservas.reportes.ExposicionDeProvedor;
import com.pepito.reservas.reportes.ReporteVisitor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ExposicionDeProvedorTest {

    private ExposicionDeProvedor exposicion;
    private ReporteVisitor visitorMock;

    @BeforeEach
    public void setUp() {
        exposicion = new ExposicionDeProvedor();
        visitorMock = mock(ReporteVisitor.class);
    }

    @Test
    public void testAceptarVisitorRetornaTextoEsperado() {
        when(visitorMock.visitorExposicionDeProvedor(exposicion)).thenReturn("Reporte Exposición Proveedor: OK");

        String resultado = exposicion.aceptar(visitorMock);

        assertNotNull(resultado);
        assertEquals("Reporte Exposición Proveedor: OK", resultado);
        verify(visitorMock, times(1)).visitorExposicionDeProvedor(exposicion);
    }

    @Test
    public void testAceptarVisitorConFormatoDetallado() {
        when(visitorMock.visitorExposicionDeProvedor(exposicion)).thenReturn("DETALLE_PROVEEDOR_V2");

        String resultado = exposicion.aceptar(visitorMock);

        assertTrue(resultado.contains("DETALLE"));
        verify(visitorMock).visitorExposicionDeProvedor(exposicion);
    }

    @Test
    public void testAceptarVisitorConResultadoVacio() {
        when(visitorMock.visitorExposicionDeProvedor(exposicion)).thenReturn("");

        String resultado = exposicion.aceptar(visitorMock);

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }

    @Test
    public void testAceptarVisitorLanzaExcepcionInterna() {
        when(visitorMock.visitorExposicionDeProvedor(exposicion)).thenThrow(new RuntimeException("Error en visitor"));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            exposicion.aceptar(visitorMock);
        });

        assertEquals("Error en visitor", exception.getMessage());
    }

    @Test
    public void testAceptarVerificaInvocacionUnicaExacta() {
        when(visitorMock.visitorExposicionDeProvedor(exposicion)).thenReturn("Unico");

        exposicion.aceptar(visitorMock);

        verify(visitorMock, times(1)).visitorExposicionDeProvedor(eq(exposicion));
    }
}