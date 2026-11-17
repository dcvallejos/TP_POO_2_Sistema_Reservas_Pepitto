package com.pepito.reservas.reportes;

/** Una fila del reporte de exposición: cuántos servicios del catálogo aporta un proveedor y qué porcentaje representan.*/

public record FilaExposicion(String proveedor, int cantidadServicios, double porcentaje) {
}