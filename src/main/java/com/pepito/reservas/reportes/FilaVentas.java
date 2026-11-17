package com.pepito.reservas.reportes;

/** Una fila del reporte de servicios más reservados: qué ítem, cuántas unidades se vendieron y a qué precio promedio.*/

public record FilaVentas(String nombre, int unidadesVendidas, double precioPromedio) {
}