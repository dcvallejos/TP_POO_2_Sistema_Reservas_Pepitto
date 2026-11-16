package com.pepito.reservas.cliente;

/**
 * Cliente
 * Titular de una reserva. Guarda los datos que necesitan los documentos y las notificaciones:
 *
 * Atributos:
 *  - nombreCliente:               (String) Nombre del cliente identificatiorio
 *  - cuitCliente:                 (String) Cuit del cliente.
 *  - direccionCliente:            (String) Utilizable por el Observer de Documentos en la parte de Factura
 *  - correoElectronicoCliente:    (String) Utilizable por el Observer de Documentos en la parte de MailSender
 *
 * Decision:
 *  Se decidió  extender esta clase para poder obtener datos del cliente en cuestíón al emitir documentos.
 */

public class Cliente {


    String nombreCliente;
    String cuitCliente;
    String direccionCliente;
    String correoElectronicoCliente;



    public Cliente(String nombreCliente, String cuitCliente, String direccionCliente, String correoElectronicoCliente) {
        this.nombreCliente = nombreCliente;
        this.cuitCliente = cuitCliente;
        this.direccionCliente = direccionCliente;
        this.correoElectronicoCliente = correoElectronicoCliente;
    }

    public String getNombre() {
        return nombreCliente;
    }

    public String getCuit() {
        return cuitCliente;
    }

    public String getDireccion() {
        return direccionCliente;
    }

    public String getCorreoElectronico() {
        return correoElectronicoCliente;
    }

}
