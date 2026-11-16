package com.pepito.reservas.reserva;

import com.pepito.reservas.documento.ComprobantePago;

import java.time.LocalDateTime;

/**
 * Cobro
 *
 * Descripción:
 *  Registro inmutable de lo que se congela al confirmar/pagar: montos cobrados, comprobante y
 *  fecha.
 *
 * Atributos
 *  - item:     (ItemCatalogo) Servicio o paquete reservado.
 *  - periodo:  (Periodo) Fechas de uso.
 *  - precio:   (double) Precio final del ítem al momento de agregarlo.
 *
 * Decision:
 *  Los reembolsos, la factura y los reportes usan esto y no los precios actuales del catálogo. Esto es para que
 *  funcione como una captura y no pueda ser modificado por calculos futuros (el valor de las reservas son calculados).
 *
 */

public record Cobro(double montoServicios, double montoTasas, ComprobantePago comprobante, LocalDateTime fecha) {

    public double total() {
        return this.montoServicios + this.montoTasas;
    }
}
