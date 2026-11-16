package com.pepito.reservas.documento;


/** VoucherTarjeta - Voucher impreso/digital del pago con tarjeta. */

public class VoucherTarjeta extends ComprobantePago {


    public VoucherTarjeta(String codigoTransaccion, Double monto ){
        super(codigoTransaccion,monto);
    }
}
