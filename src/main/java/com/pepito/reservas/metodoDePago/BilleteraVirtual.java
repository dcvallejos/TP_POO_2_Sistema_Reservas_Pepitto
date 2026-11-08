package com.pepito.reservas.metodoDePago;

import com.pepito.reservas.documento.ComprobanteBilletera;
import com.pepito.reservas.documento.ComprobantePago;
import com.pepito.reservas.documento.NotificacionPush;
import com.pepito.reservas.metodoDePago.api.ApiBilletera;

public class BilleteraVirtual  extends MetodoDePago{
    private final ApiBilletera apiBilletera;

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
    protected String ejecutarTransaccion() {
        return apiBilletera.ejecutar();
    }

    @Override
    protected ComprobantePago generarComprobante(Double monto, String numeroTransaccion) {
         return new ComprobanteBilletera(numeroTransaccion, monto);
    }

    @Override
    protected void notificarResultado(ComprobantePago comprobantePago) {
        super.notificarResultado(comprobantePago);
        apiBilletera.notificar(new NotificacionPush(comprobantePago));
    }
}
