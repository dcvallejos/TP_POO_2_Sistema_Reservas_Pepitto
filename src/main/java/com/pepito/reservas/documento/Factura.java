package com.pepito.reservas.documento;

import com.pepito.reservas.cliente.Cliente;

public class Factura extends Documento{

    String nombreCliente;
    String cuitCiente;
    String direccionCliente;
    String correoElectronicoCliente;
    Double monto;


    public Factura(Cliente cliente, Double monto) {
        super();
        this.nombreCliente = cliente.getNombre();
        this.cuitCiente = cliente.getCuit();
        this.direccionCliente = cliente.getDireccion();
        this.correoElectronicoCliente = cliente.getCorreoElectronico();
        this.monto = monto;
    }
    public String getNombreCliente() {
        return nombreCliente;
    }

    public String getCuitCiente() {
        return cuitCiente;
    }

    public String getDireccionCliente() {
        return direccionCliente;
    }

    public String getCorreoElectronicoCliente() {
        return correoElectronicoCliente;
    }

    public Double getMonto() {
        return monto;
    }

}
