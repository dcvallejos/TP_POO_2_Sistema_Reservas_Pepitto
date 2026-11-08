package com.pepito.reservas.cliente;

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
