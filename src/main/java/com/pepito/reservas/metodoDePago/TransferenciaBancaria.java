package com.pepito.reservas.metodoDePago;

import com.pepito.reservas.documento.ComprobanteTransferencia;
import com.pepito.reservas.documento.ComprobantePago;
import com.pepito.reservas.metodoDePago.api.ApiTransferencia;

/**
 * TransferenciaBancaria - ConcreteClass.
 *  Valida CBU/CVU y alias; reservar fondos no aplica (transferencia directa)
 *  Genera un ComprobanteTransferencia con el número de operación.
 *
 * Decisión:
 *  El CBU se define como String (22 dígitos).
 */

public class TransferenciaBancaria extends MetodoDePago{
    private final ApiTransferencia apiTransferencia;
    private final String cbu;
    private final String alias;

    public TransferenciaBancaria(ApiTransferencia apiTransferencia , String cbu , String alias  ){
        this.apiTransferencia=apiTransferencia;
        this.cbu=cbu;
        this.alias=alias;
    }


    @Override
    protected void validarDatos() {
        apiTransferencia.validar(cbu ,alias  );
    }

    @Override
    protected void reservarFondos() {
        // no aplica
    }

    @Override
    protected String ejecutarTransaccion() {
            return apiTransferencia.ejecutar();
    }

    @Override
    protected ComprobantePago generarComprobante(Double monto , String numeroOperacion) {
            return new ComprobanteTransferencia(numeroOperacion, monto,cbu,alias);
    }

    public String getCbu() {
        return cbu;
    }

    public String getAlias() {
        return alias;
    }

}
