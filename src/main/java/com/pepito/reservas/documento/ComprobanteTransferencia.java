package com.pepito.reservas.documento;

public class ComprobanteTransferencia extends ComprobantePago {

    private final String cbu;
    private final String alias;

    public ComprobanteTransferencia(String codigoTransaccion, Double monto, String cbu, String alias){
        super(codigoTransaccion,monto);
        this.alias = alias;
        this.cbu = cbu;
    }

    public String cbu() {
        return cbu;
    }

    public String alias() {
        return alias;
    }

}
