package com.pepito.reservas.traslado;



import com.pepito.reservas.reserva.Reserva;
import com.pepito.reservas.traslado.Direccion;
import com.pepito.reservas.traslado.MetodoTrasladoEstandar;
import com.pepito.reservas.traslado.PlataformaTransporte;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

    public class MetodoTrasladoEstandarTest {

        private MetodoTrasladoEstandar metodoTrasladoEstandar;
        private PlataformaTransporte plataformaTransporteMock;
        private Direccion direccionDestinoMock;
        private Reserva reservaMock;

        @BeforeEach
        public void setUp() {

            plataformaTransporteMock = mock(PlataformaTransporte.class);
            direccionDestinoMock = mock(Direccion.class);
            reservaMock = mock(Reserva.class);

            metodoTrasladoEstandar = new MetodoTrasladoEstandar(plataformaTransporteMock,direccionDestinoMock);


        }

        @Test
        public void testCostoDelegaEnPlataformaTransporte() {
            when(reservaMock.getCantidadPasajeros()).thenReturn(3);

            when(plataformaTransporteMock.estimarTraslado(3, direccionDestinoMock)).thenReturn(1500f);

            // Ejecutamos el método costo
            Double costoObtenido = metodoTrasladoEstandar.costo(reservaMock);

            assertEquals(1500.0, costoObtenido);
            verify(reservaMock, times(1)).getCantidadPasajeros();
            verify(plataformaTransporteMock, times(1)).estimarTraslado(eq(3), eq(direccionDestinoMock));
        }
        @Test
        public void testCostoConGranCantidadDePasajeros() {
            when(reservaMock.getCantidadPasajeros()).thenReturn(50);
            when(plataformaTransporteMock.estimarTraslado(50, direccionDestinoMock)).thenReturn(25000f);

            Double costoObtenido = metodoTrasladoEstandar.costo(reservaMock);

            assertEquals(25000.0, costoObtenido);
            verify(reservaMock, times(1)).getCantidadPasajeros();
            verify(plataformaTransporteMock, times(1)).estimarTraslado(50, direccionDestinoMock);
        }

        @Test
        public void testVerificaQueSeEnviaLaDireccionCorrecta() {

            when(reservaMock.getCantidadPasajeros()).thenReturn(2);
            when(plataformaTransporteMock.estimarTraslado(eq(2), eq(direccionDestinoMock))).thenReturn(1200f);

            metodoTrasladoEstandar.costo(reservaMock);


            verify(plataformaTransporteMock, times(1)).estimarTraslado(2, direccionDestinoMock);
        }
    }

