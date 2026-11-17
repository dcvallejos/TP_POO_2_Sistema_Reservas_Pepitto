package com.pepito.reservas.reportes;

/**
 * ReporteVisitor
 * Rol Visitor del patrón Visitor.
 *
 * Descripción:
 *  Un formato de salida. Tiene un método por cada reporte; cada formato concreto (texto plano, CSV, HTML) solo
 *  presenta las filas que el reporte ya calculó.
 *
 * Decisión:
 *  Agregar un formato es agregar un visitor, sin tocar los reportes.
 *
 * Alternativa descartada:
 *  Que cada reporte reciba el formato y decida con un if/switch cómo escribirse.
 */
public interface ReporteVisitor {

    String visitarServiciosMasReservados(ServiciosMasReservados reporte);

    String visitarOcupacionYCupo(OcupacionYCupo reporte);

    String visitarExposicionPorProveedor(ExposicionPorProveedor reporte);
}