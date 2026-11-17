package com.pepito.reservas.reportes;

/**
 * ReporteElemento
 * Rol Element del patrón Visitor.
 *
 * Descripción:
 *  Cada reporte calcula sus filas (qué datos y cómo se estructuran) y acepta un visitor que decide el formato.
 */

public interface ReporteElemento {

    String aceptar(ReporteVisitor visitor);
}