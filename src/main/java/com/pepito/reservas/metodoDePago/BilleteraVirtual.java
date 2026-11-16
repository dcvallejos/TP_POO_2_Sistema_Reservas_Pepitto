package com.pepito.reservas.metodoDePago;

import com.pepito.reservas.documento.ComprobanteBilletera;
import com.pepito.reservas.documento.ComprobantePago;
import com.pepito.reservas.documento.NotificacionPush;
import com.pepito.reservas.metodoDePago.api.ApiBilletera;

/**
 * BilleteraVirtual - ConcreteClass.
 * Verifica saldo, bloquea el saldo y acredita en tiempo real. Es el único medio que redefine la consecuencia del
 * proceso.
 *
 * Decisión:
 *  Se implementó el registro del comprobante (super) y el envío de una NotificacionPush por la API, que es la que se
 *  encargará de generar la notificación en sí.
 */

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
