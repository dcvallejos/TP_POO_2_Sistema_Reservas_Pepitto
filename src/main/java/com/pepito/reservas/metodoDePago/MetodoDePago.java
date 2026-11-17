package com.pepito.reservas.metodoDePago;

import com.pepito.reservas.documento.ComprobantePago;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * MetodoDePago
 * Parte AbstractClass del patrón Template Method
 *
 * Descripción:
 *   Define el proceso de pago con pasos prefijados con una única cabecera de acceso (procesarPago):
 *   validar datos → reservar fondos → ejecutar la transacción → generar el comprobante → notificar el resultado.
 *   Cada medio de pago se encarga de elegir su implementación.
 *
 * Atributos
 *   - comprobantesRegistrados:  (List<ComprobantePago>) Registro de comprobantes realizados.
 *
 * Roles:
 *   - Template method:          procesarPago (final: ninguna subclase puede alterar el orden).
 *   - Operaciones primitivas:   validarDatos, reservarFondos, ejecutarTransaccion, generarComprobante (abstractas).
 *   - Consecuencia:             notificarResultado (por default registra el comprobante. Se puede redefinir para
 *   cuando no aplica).
 *
 * Decisión:
 *   Se separó generarComprobante de notificarResultado: el comprobante lo genera el dominio a partir del código de
 *   transacción que devuelve la API, la notificación solo lo comunica.
 *   El rechazo se decide en el template: si validarDatos devuelve false se lanza PagoRechazadoException antes de reservar fondos.
 *
 * Alternativa descartada:
 *  Strategy de pago: cada medio tenia la posibilidad de implmenetar pasos en cualquier orden o no hacer alguno.
 */

public  abstract class MetodoDePago {

    private final List<ComprobantePago> comprobantesRegistrados;
    public MetodoDePago(){
        this.comprobantesRegistrados = new ArrayList<>(List.of());
    }

    // Cuerpo de Template Method
    public final ComprobantePago procesarPago(Double monto  ) { // métodoTemplate
    	if (!this.validarDatos()) {
            throw new PagoRechazadoException("El medio de pago rechazó los datos: el pago no se procesó.");
        }
        this.reservarFondos();
        String numeroTransaccion = this.ejecutarTransaccion();
        ComprobantePago comprobantePago = this.generarComprobante(monto, numeroTransaccion);
        this.notificarResultado(comprobantePago);
        return comprobantePago;
    }

    protected   abstract boolean validarDatos() ;
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
