package com.pepito.reservas.metodoDePago;

import com.pepito.reservas.reserva.Reserva;

public class TransferenciaBancaria extends MetodoDePago{
    private ApiTransferencia apiTrasnferencia;
    private int cbu;
    private String alias;

    public TransferenciaBancaria(ApiTransferencia apiTransferencia , int cbu ,String alias  ){
        this.apiTrasnferencia=apiTransferencia;
        this.cbu=cbu;
        this.alias=alias;
    }


    @Override
    public void validarDatos() {
        apiTrasnferencia.validar(cbu ,alias  );
    }

    @Override
    public void reservarFondos() {
        // no aplica
    }

    @Override
    public void ejecutarTransaccion() {
            apiTrasnferencia.ejecutar();
    }

    @Override
    public void notificarResultado(Reserva reserva) {
            Comprobante comprobanteDeOperacion = new Comprobante (alias,cbu  );
    }
}
