package com.pepito.reservas.metodoDePago;

import com.pepito.reservas.documento.ComprobantePago;
import com.pepito.reservas.documento.VoucherTarjeta;
import com.pepito.reservas.metodoDePago.api.ApiTarjetaDeCredito;

import java.time.LocalDate;

/**
 * TarjetaDeCreditoInternacional - ConcreteClass.
 * Valida número, CVV y vencimiento mediante una API y genera un VoucherTarjeta.
 *
 * Decisión:
 *  Se cambio el número de tarjeta a String dado que en las pruebas, un numero real de 16 digitos no entraba por la
 *  limitación de Integer
 */

public class TarjetaDeCreditoInternacional extends MetodoDePago{
    private final ApiTarjetaDeCredito apiTarjetaDeCredito;
    private final String nroTarjeta ;
    private final int cvv;
    private final LocalDate fechaDeVencimiento;

    public TarjetaDeCreditoInternacional(String nroTarjeta ,int cvv , LocalDate fechaDeVencimiento ,
                                         ApiTarjetaDeCredito apiTarjetaDeCredito) {
        this.apiTarjetaDeCredito= apiTarjetaDeCredito;
        this.cvv = cvv;
        this.fechaDeVencimiento = fechaDeVencimiento;
        this.nroTarjeta=nroTarjeta ;
    }

    @Override
    protected boolean validarDatos() {
        return apiTarjetaDeCredito.validar(  nroTarjeta ,  cvv , fechaDeVencimiento) ;
    }

    @Override
    protected void reservarFondos() {
        apiTarjetaDeCredito.reservar();
    }

    @Override
    protected String ejecutarTransaccion() {
        return apiTarjetaDeCredito.ejecutar();
    }

    @Override
    protected ComprobantePago generarComprobante(Double monto, String codigoTransaccion) {
        return  new VoucherTarjeta(codigoTransaccion, monto);
    }

}
