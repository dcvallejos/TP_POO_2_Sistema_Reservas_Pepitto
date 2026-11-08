package com.pepito.reservas.documento;
 
/*
 * Comprobante de crédito/reembolso que se registra en la reserva al cancelar.
 */
public class VoucherCredito extends Documento {
 
    private final String motivo;
    private final Double monto;
 
    public VoucherCredito(Double monto, String motivo) {
        super();
        this.monto = monto;
        this.motivo = motivo;
    }

    public String getMotivo() {
        return motivo;
    }

    public Double getMonto() {
        return monto;
    }
}