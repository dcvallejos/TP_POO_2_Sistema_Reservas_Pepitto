package com.pepito.reservas.translado;



import com.pepito.reservas.reserva.Reserva;
import com.pepito.reservas.traslado.Direccion;
import com.pepito.reservas.traslado.MetodoTransladoEstandar;
import com.pepito.reservas.traslado.PlataformaTransporte;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

    public class MetodoTransladoEstandarTest {

        private MetodoTransladoEstandar metodoTransladoEstandar;
        private PlataformaTransporte plataformaTransporteMock;
        private Direccion direccionDestinoMock;
        private Reserva reservaMock;

        @BeforeEach
        public void setUp() {

            plataformaTransporteMock = mock(PlataformaTransporte.class);
            direccionDestinoMock = mock(Direccion.class);
            reservaMock = mock(Reserva.class);

            metodoTransladoEstandar = new MetodoTransladoEstandar(plataformaTransporteMock,direccionDestinoMock);


        }

        @Test
        public void testCostoDelegaEnPlataformaTransporte() {
            when(reservaMock.getCantidadDePasajeros()).thenReturn(3);

            when(plataformaTransporteMock.estimarTranslado(3, direccionDestinoMock)).thenReturn(1500.0);

            // Ejecutamos el método costo
            Double costoObtenido = metodoTransladoEstandar.costo(reservaMock);

            assertEquals(1500.0, costoObtenido);
            verify(reservaMock, times(1)).getCantidadDePasajeros();
            verify(plataformaTransporteMock, times(1)).estimarTranslado(eq(3), eq(direccionDestinoMock));
        }
        @Test
        public void testCostoConGranCantidadDePasajeros() {
            when(reservaMock.getCantidadDePasajeros()).thenReturn(50);
            when(plataformaTransporteMock.estimarTranslado(50, direccionDestinoMock)).thenReturn(25000.0);

            Double costoObtenido = metodoTransladoEstandar.costo(reservaMock);

            assertEquals(25000.0, costoObtenido);
            verify(reservaMock, times(1)).getCantidadDePasajeros();
            verify(plataformaTransporteMock, times(1)).estimarTranslado(50, direccionDestinoMock);
        }

        @Test
        public void testVerificaQueSeEnviaLaDireccionCorrecta() {

            when(reservaMock.getCantidadDePasajeros()).thenReturn(2);
            when(plataformaTransporteMock.estimarTranslado(eq(2), eq(direccionDestinoMock))).thenReturn(1200.0);

            metodoTransladoEstandar.costo(reservaMock);


            verify(plataformaTransporteMock, times(1)).estimarTranslado(2, direccionDestinoMock);
        }
    }

