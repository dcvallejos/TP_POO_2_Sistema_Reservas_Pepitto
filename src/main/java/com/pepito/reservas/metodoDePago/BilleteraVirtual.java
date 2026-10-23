package com.pepito.reservas.metodoDePago;

import com.pepito.reservas.reserva.Reserva;

public class BilleteraVirtual  extends MetodoDePago{
    private ApiBilletera apiBilletera;

    public BilleteraVirtual(ApiBilletera apiBilletera ){
        this.apiBilletera=apiBilletera;
    }

    @Override
    public void validarDatos() {
            apiBilletera.validar();
    }

    @Override
    public void reservarFondos() {
        apiBilletera.reservar();

    }

    @Override
    public void ejecutarTransaccion() {
        apiBilletera.ejecutar();
    }

    @Override
    public void notificarResultado(Reserva reserva) {
        apiBilletera.notificar();
    }
}
