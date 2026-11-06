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
        // Configuramos el mock del visitor para que devuelva un valor esperado
        when(visitorMock.visitorServiciosMasReservados(servicioMasReservado))
                .thenReturn("Resultado Visita Mock");

        // Ejecutamos el método aceptar
        String resultado = servicioMasReservado.aceptar(visitorMock);

        // Verificamos el resultado y que se haya llamado al método correcto del visitor
        assertEquals("Resultado Visita Mock", resultado);
        verify(visitorMock, times(1)).visitorServiciosMasReservados(servicioMasReservado);
    }
}