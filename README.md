# UNQ-Travel — Sistema de Reservas

Trabajo Práctico Integrador de Programación Orientada a Objetos II (UNQ).

Diseño e implementación en **Java** de la **capa de dominio** de un sistema de reservas para una empresa de viajes y turismo online: catálogo de servicios y paquetes, reservas, traslados y tasas, pagos, notificaciones, búsqueda y reportes.

> **Estado del proyecto:** todos los módulos implementados (2.1 a 2.6 y 2.8). **374 tests, 0 fallos** y **98,9 % de cobertura de líneas** (mínimo exigido: 95 %).

## Alcance

El trabajo consiste en diseñar e implementar **únicamente la capa de dominio**: sin interfaz gráfica, sin persistencia y sin frameworks. Solo clases, interfaces y pruebas. Las APIs externas (medios de pago, transporte, guías, envío de mails) se definen como interfaces y en los tests se reemplazan con mocks de Mockito.

El objetivo no es construir una aplicación completa, sino demostrar la correcta aplicación de los principios de diseño orientado a objetos en un dominio realista. Cada decisión de diseño debe poder justificarse en términos de qué problema resuelve y qué alternativa más frágil evita.

## Integrantes

| Nombre y apellido | Email | Usuario de GitHub |
|-------------------|-------|-------------------|
| Dominguez, Tomás | tomaselias.dw@gmail.com | TomasDominguez10 |
| Rossi, Tobías | trossi2001@gmail.com | TobiasRossi |
| Vallejos, Daniel | davallejos_ps@outlook.com | dcvallejos |

## Documentación

- **Informe** (integrantes, decisiones de diseño, detalles de implementación, patrones y roles según Gamma et al.): [`docs/Informe_UNQ-Travel.pdf`](docs/Informe_UNQ-Travel.pdf)
- **Diagrama de clases UML**: [`docs/UML_UNQ-Travel.png`](docs/UML_UNQ-Travel.png)

## Módulos y patrones

| Módulo | Paquete | Patrón | Roles (Gamma et al.) |
|--------|---------|--------|----------------------|
| 2.1 Catálogo de servicios y paquetes | `catalogo`, `catalogo.servicio` | Composite | Component `ItemCatalogo` · Leaf `Servicio` · Composite `Paquete` |
| 2.1 Atributos dinámicos | `catalogo.servicio.atributos` | Strategy | Strategy `TipoDinamico` · ConcreteStrategy `TipoTexto`, `TipoNumerico`, `TipoBooleano`, `TipoFecha` · Context `AtributoDinamico` |
| 2.1 Destinos geográficos | `destino` | Composite | Component `LugarGeografico` · Composite `Destino` · Leaf `Ciudad` |
| 2.2 Ciclo de vida de la reserva | `reserva` | State | Context `Reserva` · State `EstadoReserva` · ConcreteState `SolicitudPendiente`, `Confirmada`, `EnCheckIn`, `EnCurso`, `Finalizada`, `Cancelada` |
| 2.3 Métodos de traslado y tasas | `traslado` | Strategy (y Adapter de las librerías externas) | Strategy `MetodoTraslado` · ConcreteStrategy `MetodoTrasladoEstandar`, `MetodoTrasladoVip`, `MetodoPuntoDeEncuentro` · Context `Reserva` |
| 2.4 Métodos de pago | `metodoDePago`, `metodoDePago.api` | Template Method | AbstractClass `MetodoDePago` · Template method `procesarPago` · ConcreteClass `TarjetaDeCreditoInternacional`, `TransferenciaBancaria`, `BilleteraVirtual` |
| 2.5 Notificaciones de la reserva | `notificador`, `reserva.suscriptor` | Observer | Subject `Reserva` · Observer `ReservaObserver` · ConcreteObserver `NotificadorMail`, `GeneradorVoucher`, `Fidelizacion` |
| 2.6 Búsqueda en el catálogo | `catalogo.busqueda` | Composite | Component `CriterioBusqueda` · Leaf `PorNombre`, `PorPrecioMaximo`, `PorCategoria`, `PorDisponibilidad` · Composite `CriterioAnd`, `CriterioOr`, `CriterioNot` · Client `Catalogo` |
| 2.8 Reportes | `reportes` | Visitor | Visitor `ReporteVisitor` · ConcreteVisitor `ReporteTextoPlanoVisitor`, `ReporteCSVVisitor`, `ReporteHtmlVisitor` · Element `ReporteElemento` · ConcreteElement `ServiciosMasReservados`, `OcupacionYCupo`, `ExposicionPorProveedor` |

Las decisiones de diseño de cada módulo, con el problema que resuelven y la alternativa que evitan, están en el informe.

## Estructura del repositorio

```
.
├── pom.xml
├── README.md
├── docs/                          Informe y diagrama UML
└── src/
    ├── main/java/com/pepito/reservas/
    │   ├── catalogo/              Catálogo e ItemCatalogo (2.1)
    │   │   ├── busqueda/          Criterios de búsqueda (2.6)
    │   │   └── servicio/          Servicio, Paquete, Disponibilidad, Periodo (2.1)
    │   │       └── atributos/     Atributos dinámicos (2.1)
    │   ├── cliente/               Cliente
    │   ├── destino/               Destinos geográficos (2.1)
    │   ├── documento/             Comprobantes, vouchers, factura y cupón
    │   ├── excepciones/           Excepciones de dominio del catálogo
    │   ├── metodoDePago/          Medios de pago (2.4)
    │   │   └── api/               Interfaces de las APIs de pago
    │   ├── notificador/           Subsistemas que observan la reserva (2.5)
    │   ├── reportes/              Reportes y formatos de exportación (2.8)
    │   ├── reserva/               Reserva, estados, cobro y registro (2.2)
    │   │   └── suscriptor/        ReservaObserver (2.5)
    │   └── traslado/              Métodos de traslado (2.3)
    └── test/java/com/pepito/reservas/   Tests de unidad, organizados por módulo
```

## Tecnologías

- Lenguaje: Java 26
- Build: Maven
- Tests: JUnit Jupiter 6.0.1 y Mockito 5.20.0
- Cobertura: JaCoCo 0.8.15, con un mínimo de 95 % de líneas que hace fallar el build si no se alcanza

## Cómo ejecutarlo

Desde la raíz del repositorio:

```bash
mvn test      # corre los tests
mvn verify    # corre los tests y controla la cobertura mínima del 95 %
```

El reporte de cobertura queda en `target/site/jacoco/index.html`.

Desde un IDE, se importa como proyecto Maven (Eclipse: *File → Import → Existing Maven Projects*; IntelliJ: abrir el `pom.xml`) y se corren los tests con *Run As → Maven test* o desde la carpeta `src/test/java`.

## Entregables

- [x] Diagrama de clases UML de la solución completa (`docs/`).
- [x] Documentación en PDF con integrantes y emails, decisiones de diseño, detalles de implementación, patrones utilizados y sus roles según Gamma et al. (`docs/`).
- [x] Implementación completa en Java con tests de unidad y **95 % de cobertura** del código de dominio (98,9 %).
- [x] Repositorio accesible para los docentes, con historial de trabajo de todos los integrantes.

## Fechas de entrega

| Instancia | Fecha |
|-----------|-------|
| 1.ª fecha de entrega | 17 de noviembre de 2026 |
| 1.ª fecha de defensa | 19 de noviembre de 2026 |
| 2.ª fecha de entrega | 1 de diciembre de 2026 |
| 2.ª fecha de defensa | 3 de diciembre de 2026 |

## Cómo trabajamos

- Un Issue por módulo del dominio; el trabajo se organiza a partir de ellos.
- Cada cambio se hace en una rama propia y se integra con Pull Request.
- Todos los integrantes commitean con su propio usuario: la participación se evalúa por el historial de git.
- Las decisiones de diseño se documentan en el informe (`docs/`), con el problema que resuelven y la alternativa que se descartó, porque hay que justificarlas en la entrevista.
