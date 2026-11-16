package com.pepito.reservas.documento;


/** NotificacionPush - Mensaje que la billetera virtual envía por su API al registrarse un pago. */

public class NotificacionPush extends Documento{

    ComprobantePago comprobantePago;
    public NotificacionPush(ComprobantePago comprobantePago) {
        super();
        this.comprobantePago = comprobantePago;
    }

    public String getNotificacion() {
        return "Se ha registrado un pago bajo el código: " + comprobantePago.getCodigoTransaccion() + " con monto:" +
                " " + comprobantePago.getMonto().toString() ;
    }

}
