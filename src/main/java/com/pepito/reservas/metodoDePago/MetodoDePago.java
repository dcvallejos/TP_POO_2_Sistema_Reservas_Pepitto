package com.pepito.reservas.metodoDePago;

import com.pepito.reservas.documento.ComprobantePago;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public  abstract class MetodoDePago {

    private final List<ComprobantePago> comprobantesRegistrados;
    public MetodoDePago(){
        this.comprobantesRegistrados = new ArrayList<>(List.of());
    }
// template method
    public final ComprobantePago procesarPago(Double monto  ) { // métodoTemplate
        this.validarDatos();
        this.reservarFondos();
        String numeroTransaccion = this.ejecutarTransaccion();
        ComprobantePago comprobantePago = this.generarComprobante(monto, numeroTransaccion);
        this.notificarResultado(comprobantePago);
        return comprobantePago;
    }

    protected   abstract void validarDatos() ;
    protected   abstract void reservarFondos() ;
    protected   abstract String ejecutarTransaccion();
    protected   abstract ComprobantePago generarComprobante(Double monto, String numeroTransaccion) ;
    protected   void notificarResultado(ComprobantePago comprobantePago) {
        comprobantesRegistrados.add(comprobantePago);
    }
    public List<ComprobantePago> getComprobantesRegistrados() {
        return Collections.unmodifiableList(this.comprobantesRegistrados);
    }
}
