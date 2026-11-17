package com.pepito.reservas.reportes;

import com.pepito.reservas.catalogo.Catalogo;
import com.pepito.reservas.catalogo.servicio.Servicio;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * ExposicionPorProveedor
 * Rol ConcreteElement del patrón Visitor.
 *
 * Descripción:
 *  Qué porcentaje del catálogo depende de cada proveedor, para medir el riesgo de concentración.
 *
 * Decisión:
 *  Se cuenta sobre los servicios hoja del catálogo: un paquete depende de los proveedores de sus servicios.
 */

public class ExposicionPorProveedor implements ReporteElemento {

    private final List<FilaExposicion> filas;

    public ExposicionPorProveedor(Catalogo catalogo) {
        this.filas = calcular(catalogo);
    }

    @Override
    public String aceptar(ReporteVisitor visitor) {
        return visitor.visitarExposicionPorProveedor(this);
    }

    public List<FilaExposicion> getFilas() {
        return this.filas;
    }

    private static List<FilaExposicion> calcular(Catalogo catalogo) {
        List<Servicio> servicios = catalogo.getCatalogo().stream()
                .flatMap(item -> item.serviciosHoja().stream())
                .distinct()
                .toList();

        Map<String, Long> porProveedor = servicios.stream()
                .collect(Collectors.groupingBy(Servicio::getProveedor, LinkedHashMap::new, Collectors.counting()));

        return porProveedor.entrySet().stream()
                .map(entrada -> new FilaExposicion(entrada.getKey(), entrada.getValue().intValue(),
                        100.0 * entrada.getValue() / servicios.size()))
                .sorted(Comparator.comparingInt(FilaExposicion::cantidadServicios).reversed())
                .toList();
    }
}