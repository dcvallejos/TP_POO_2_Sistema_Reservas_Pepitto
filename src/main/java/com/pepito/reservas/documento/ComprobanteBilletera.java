package com.pepito.reservas.documento;

/** ComprobanteBilletera - Comprobante de la acreditación por billetera virtual. */

public class ComprobanteBilletera extends ComprobantePago{

    public ComprobanteBilletera(String codigoTransaccion, Double monto){
        super(codigoTransaccion,monto);
    }

}
