package com.pepito.reservas.documento;

import java.time.Clock;
import java.time.LocalDateTime;

/**
 * Documento
 *
 * Descripcion
 *  Es el padre de todos los tipos de documentos que emite el sistema de reservas (comprobantes, de pago, facturas,
 *  vouchers, etc)
 *  Se les asigna un numero correlativo y la fecha de emisión de forma automática al crearse.
 *
 * Atributos
 *   - numero:        (String) Número correlativo, único en la ejecución y autoincremental.
 *   - fechaEmision:  (LocalDateTime) Momento de creación.
 *
 * Decision
 *  Se creó como una unifiación de jerarquias para que los sistemas puedan adjuntar documentos sin conocer su tipo
 *  concreto.
 *  Se definió un contador static para poder llevar el registro autoincremental, haciendo la simulación de una base
 *  de datos, donde en la vida real las generaciones llevan un numero de documento incremental. Esto es para
 *  separarlo del numero de comprobante que las API entregan en algunos casos.
 *  Se definió Clock.systemUTC() para salvar los problemas de diferencia de husos horarios, se considero prudente
 *  establecer un tiempo neutral de emisión de documentos.
 *
 * */

public abstract class Documento {

    private static Integer contadorDocumentos = 0;

    private final String numero;
    private final LocalDateTime fechaEmision;

    public Documento(){
        this.numero = String.valueOf(++contadorDocumentos);
        this.fechaEmision = LocalDateTime.now(Clock.systemUTC());
    }

    public String getNumero() {
        return numero;
    }

    public LocalDateTime getFechaEmision() {
        return fechaEmision;
    }

    public static Integer getContadorDocumentos() {
        return contadorDocumentos;
    }
}
