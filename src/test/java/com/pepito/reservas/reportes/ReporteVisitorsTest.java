package com.pepito.reservas.reportes;

import com.pepito.reservas.catalogo.servicio.Categoria;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/* Los visitors solo formatean: se les pasan reportes con filas fijas y se compara la salida exacta. */
class ReporteVisitorsTest {

    private ServiciosMasReservados ventas;
    private OcupacionYCupo ocupacion;
    private ExposicionPorProveedor exposicion;

    @BeforeEach
    void setUp() {
        ventas = mock(ServiciosMasReservados.class);
        when(ventas.getFilas()).thenReturn(List.of(new FilaVentas("Hotel, centro", 5, 1000.0)));
        ocupacion = mock(OcupacionYCupo.class);
        when(ocupacion.getFilas()).thenReturn(List.of(new FilaOcupacion(Categoria.ALOJAMIENTO, 45, 6, 39)));
        exposicion = mock(ExposicionPorProveedor.class);
        when(exposicion.getFilas()).thenReturn(List.of(new FilaExposicion("Viajes <Sur> & Cía", 3, 75.0)));
    }

    @Test
    void textoPlanoEscribeUnaLineaPorFila() {
        ReporteVisitor texto = new ReporteTextoPlanoVisitor();

        assertEquals("=== Servicios más reservados ===\nHotel, centro - 5 unidades - precio promedio $1000.00\n", texto.visitarServiciosMasReservados(ventas));
        assertEquals("=== Ocupación y cupo disponible ===\nALOJAMIENTO - totales 45 - reservados 6 - libres 39\n", texto.visitarOcupacionYCupo(ocupacion));
        assertEquals("=== Exposición por proveedor ===\nViajes <Sur> & Cía - 3 servicios - 75.00% del catálogo\n", texto.visitarExposicionPorProveedor(exposicion));
    }

    @Test
    void csvTieneEncabezadoYPoneLosTextosEntreComillas() {
        ReporteVisitor csv = new ReporteCSVVisitor();

        assertEquals("servicio,unidades_vendidas,precio_promedio\n\"Hotel, centro\",5,1000.00\n", csv.visitarServiciosMasReservados(ventas));
        assertEquals("categoria,cupos_totales,reservados,libres\nALOJAMIENTO,45,6,39\n", csv.visitarOcupacionYCupo(ocupacion));
        assertEquals("proveedor,cantidad_servicios,porcentaje\n\"Viajes <Sur> & Cía\",3,75.00\n", csv.visitarExposicionPorProveedor(exposicion));
    }

    @Test
    void csvDuplicaLasComillasDentroDeUnTexto() {
        when(ventas.getFilas()).thenReturn(List.of(new FilaVentas("Hotel \"5 estrellas\"", 1, 10.0)));

        assertEquals("servicio,unidades_vendidas,precio_promedio\n\"Hotel \"\"5 estrellas\"\"\",1,10.00\n", new ReporteCSVVisitor().visitarServiciosMasReservados(ventas));
    }

    @Test
    void htmlArmaUnaTablaYEscapaLosTextos() {
        ReporteVisitor html = new ReporteHtmlVisitor();

        assertEquals("<h1>Servicios más reservados</h1>\n<table>\n"
                + "<tr><th>Servicio</th><th>Unidades vendidas</th><th>Precio promedio</th></tr>\n"
                + "<tr><td>Hotel, centro</td><td>5</td><td>$1000.00</td></tr>\n</table>\n",
                html.visitarServiciosMasReservados(ventas));
        assertEquals("<h1>Ocupación y cupo disponible</h1>\n<table>\n"
                + "<tr><th>Categoría</th><th>Cupos totales</th><th>Reservados</th><th>Libres</th></tr>\n"
                + "<tr><td>ALOJAMIENTO</td><td>45</td><td>6</td><td>39</td></tr>\n</table>\n",
                html.visitarOcupacionYCupo(ocupacion));
        assertEquals("<h1>Exposición por proveedor</h1>\n<table>\n"
                + "<tr><th>Proveedor</th><th>Servicios</th><th>Porcentaje</th></tr>\n"
                + "<tr><td>Viajes &lt;Sur&gt; &amp; Cía</td><td>3</td><td>75.00%</td></tr>\n</table>\n",
                html.visitarExposicionPorProveedor(exposicion));
    }

    @Test
    void unReporteSinFilasSoloTieneElEncabezado() {
        when(ventas.getFilas()).thenReturn(List.of());

        assertEquals("=== Servicios más reservados ===\n", new ReporteTextoPlanoVisitor().visitarServiciosMasReservados(ventas));
        assertEquals("servicio,unidades_vendidas,precio_promedio\n", new ReporteCSVVisitor().visitarServiciosMasReservados(ventas));
    }
}