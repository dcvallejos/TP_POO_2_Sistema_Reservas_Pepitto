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
        // Caso 1: Comportamiento estándar del visitor ante la exposición
        when(visitorMock.visitorExposicionDeProvedor(exposicion)).thenReturn("Reporte Exposición Proveedor: OK");

        String resultado = exposicion.aceptar(visitorMock);

        assertNotNull(resultado);
        assertEquals("Reporte Exposición Proveedor: OK", resultado);
        verify(visitorMock, times(1)).visitorExposicionDeProvedor(exposicion);
    }

    @Test
    public void testAceptarVisitorConFormatoDetallado() {
        // Caso 2: El visitor devuelve un formato detallado o con métricas
        when(visitorMock.visitorExposicionDeProvedor(exposicion)).thenReturn("DETALLE_PROVEEDOR_V2");

        String resultado = exposicion.aceptar(visitorMock);

        assertTrue(resultado.contains("DETALLE"));
        verify(visitorMock).visitorExposicionDeProvedor(exposicion);
    }

    @Test
    public void testAceptarVisitorConResultadoVacio() {
        // Caso 3: El visitor retorna una cadena vacía (caso borde)
        when(visitorMock.visitorExposicionDeProvedor(exposicion)).thenReturn("");

        String resultado = exposicion.aceptar(visitorMock);

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }

    @Test
    public void testAceptarVisitorLanzaExcepcionInterna() {
        // Caso 4: Manejo de errores si el visitor falla al procesar la exposición
        when(visitorMock.visitorExposicionDeProvedor(exposicion)).thenThrow(new RuntimeException("Error en visitor"));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            exposicion.aceptar(visitorMock);
        });

        assertEquals("Error en visitor", exception.getMessage());
    }

    @Test
    public void testAceptarVerificaInvocacionUnicaExacta() {
        // Caso 5: Asegurar que se invoca exactamente una vez y no múltiples veces por error
        when(visitorMock.visitorExposicionDeProvedor(exposicion)).thenReturn("Unico");

        exposicion.aceptar(visitorMock);

        // Verificamos que el método del visitor fue llamado exactamente una vez con esta instancia
        verify(visitorMock, times(1)).visitorExposicionDeProvedor(eq(exposicion));
    }
}