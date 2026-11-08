package com.pepito.reservas.reporte;

import com.pepito.reservas.reportes.ExposicionDeProvedor;
import com.pepito.reservas.reportes.OcupacionYCupo;
import com.pepito.reservas.reportes.ReporteTextoPlanoVisitor;
import com.pepito.reservas.reportes.ServicioMasReservado;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ReporteTextoPlanoVisitorTest {

        private ReporteTextoPlanoVisitor visitor;
        private ServicioMasReservado servicioMock;
        private OcupacionYCupo ocupacionMock;
        private ExposicionDeProvedor exposicionMock;

        @BeforeEach
        public void setUp() {
            visitor = new ReporteTextoPlanoVisitor();
            servicioMock = mock(ServicioMasReservado.class);
            ocupacionMock = mock(OcupacionYCupo.class);
            exposicionMock = mock(ExposicionDeProvedor.class);
        }

        @Test
        public void testVisitarServiciosMasReservadosFormatoTexto() {
            String resultado = visitor.visitorServiciosMasReservados(servicioMock);

            assertNotNull(resultado);
            assertTrue(resultado.contains("TEXTO"));
            assertTrue(resultado.contains("SERVICIOS MÁS RESERVADOS"));
        }



    @Test
        public void testVisitarOcupacionYCupoFormatoTexto() {
            String resultado = visitor.visitorOcupacionYCupoDisponible(ocupacionMock);

            assertNotNull(resultado);
            assertTrue(resultado.contains("OCUPACIÓN Y CUPO"));
        }

        @Test
        public void testVisitarExposicionDeProveedorFormatoTexto() {
            String resultado = visitor.visitorExposicionDeProvedor(exposicionMock);

            assertNotNull(resultado);
            assertTrue(resultado.contains("EXPOSICIÓN DE PROVEEDOR"));
        }
    @Test
    public void testTextoPlanoExposicionProveedorDetalle() {
        String resultado = visitor.visitorExposicionDeProvedor(exposicionMock);

        assertNotNull(resultado);
        assertTrue(resultado.length() > 0);
    }

    @Test
    public void testNingunReporteTextoPlanoRetornaNulo() {
        assertAll(
                () -> assertNotNull(visitor.visitorServiciosMasReservados(servicioMock)),
                () -> assertNotNull(visitor.visitorOcupacionYCupoDisponible(ocupacionMock)),
                () -> assertNotNull(visitor.visitorExposicionDeProvedor(exposicionMock))
        );
    }
}

