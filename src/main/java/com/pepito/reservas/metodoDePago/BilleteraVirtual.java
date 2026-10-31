package com.pepito.reservas.metodoDePago;

public class BilleteraVirtual  extends MetodoDePago{
    private ApiBilletera apiBilletera;

    public BilleteraVirtual(ApiBilletera apiBilletera ){
        this.apiBilletera=apiBilletera;
    }

    @Override
    protected  void validarDatos() {
            apiBilletera.validar();
    }

    @Override
    protected void reservarFondos() {
        apiBilletera.reservar();

    }

    @Override
    protected void ejecutarTransaccion() {
        apiBilletera.ejecutar();
    }

    @Override
    protected ComprobantePago  notificarResultado(Double monto ) {
         return apiBilletera.notificar();
    }
}
