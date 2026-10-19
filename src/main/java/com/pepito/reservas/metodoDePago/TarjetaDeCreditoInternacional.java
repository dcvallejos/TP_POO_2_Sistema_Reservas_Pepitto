package com.pepito.reservas.metodoDePago;

import java.time.LocalDate;

public class TarjetaDeCreditoInternacional  extends MetodoDePago{
    private ApiTarjetaDeCredito  apiTarjetaDeCredito;
    private int nroTarjeta ;
    private int cvv;
    private LocalDate fechaDeVencimiento;

    public TarjetaDeCreditoInternacional(int nroTarjeta ,int cvv , LocalDate fechaDeVencimiento ,ApiTarjetaDeCredito apiTarjetaDeCredito) {
        this.apiTarjetaDeCredito= apiTarjetaDeCredito;
        this.cvv = cvv;
        this.fechaDeVencimiento = fechaDeVencimiento;
    }

    @Override
    public void validarDatos() {
        apiTarjetaDeCredito.validar(  nroTarjeta ,  cvv , fechaDeVencimiento) ;
    }

    @Override
    public void reservarFondos() {
        apiTarjetaDeCredito.reservar();

    }


    @Override
    public void ejecutarTransaccion() {
        apiTarjetaDeCredito.ejecutar();
    }

    @Override
    public void notificarResultado() {
        apiTarjetaDeCredito.notificar();
    }
}
