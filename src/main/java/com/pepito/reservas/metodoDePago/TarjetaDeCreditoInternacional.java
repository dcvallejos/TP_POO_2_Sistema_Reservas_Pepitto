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
        this.nroTarjeta=nroTarjeta ;
    }

    @Override
    protected void validarDatos() {
        apiTarjetaDeCredito.validar(  nroTarjeta ,  cvv , fechaDeVencimiento) ;
    }

    @Override
    protected void reservarFondos() {
        apiTarjetaDeCredito.reservar();

    }


    @Override
    protected void ejecutarTransaccion() {
        apiTarjetaDeCredito.ejecutar();
    }

    @Override
    protected   ComprobantePago notificarResultado(Double monto ) {
        return  new Voucher  (cvv );
    }

}
