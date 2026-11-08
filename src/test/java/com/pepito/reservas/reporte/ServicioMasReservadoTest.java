package com.pepito.reservas.reporte;

import com.pepito.reservas.reportes.ReporteVisitor;
import com.pepito.reservas.reportes.ServicioMasReservado;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ServicioMasReservadoTest {

    private ServicioMasReservado servicioMasReservado;
    private ReporteVisitor visitorMock;

    @BeforeEach
    public void setUp() {
        servicioMasReservado = new ServicioMasReservado();
        visitorMock = mock(ReporteVisitor.class);
    }

    @Test
    public void testAceptarVisitorDelegaCorrectamente() {
        when(visitorMock.visitorServiciosMasReservados(servicioMasReservado))
                .thenReturn("Resultado Visita Mock");

        String resultado = servicioMasReservado.aceptar(visitorMock);

        assertEquals("Resultado Visita Mock", resultado);
        verify(visitorMock, times(1)).visitorServiciosMasReservados(servicioMasReservado);
    }
    @Test
    public void testAceptarVisitorRetornaNoNulo() {
        when(visitorMock.visitorServiciosMasReservados(servicioMasReservado))
                .thenReturn("Reporte Válido");

        String resultado = servicioMasReservado.aceptar(visitorMock);

        assertNotNull(resultado);
    }

    @Test
    public void testAceptarVisitorConResultadoVacio() {
        when(visitorMock.visitorServiciosMasReservados(servicioMasReservado))
                .thenReturn("");

        String resultado = servicioMasReservado.aceptar(visitorMock);

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }

    @Test
    public void testAceptarVisitorLanzaExcepcion() {
        when(visitorMock.visitorServiciosMasReservados(servicioMasReservado))
                .thenThrow(new RuntimeException("Error en procesamiento del visitor"));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            servicioMasReservado.aceptar(visitorMock);
        });

        assertEquals("Error en procesamiento del visitor", exception.getMessage());
    }

    @Test
    public void testAceptarVerificaInvocacionUnicaExacta() {
        when(visitorMock.visitorServiciosMasReservados(servicioMasReservado))
                .thenReturn("OK");

        servicioMasReservado.aceptar(visitorMock);

        verify(visitorMock, times(1)).visitorServiciosMasReservados(eq(servicioMasReservado));
    }


}