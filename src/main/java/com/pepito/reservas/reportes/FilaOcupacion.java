package com.pepito.reservas.reportes;

import com.pepito.reservas.catalogo.servicio.Categoria;

/** Una fila del reporte de ocupación: cupos de una categoría sumados en todos los días del período.*/

public record FilaOcupacion(Categoria categoria, int cuposTotales, int reservados, int libres) {
}