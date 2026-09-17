# Laboratorio VI — APIs REST con Java + Spring Boot

Laboratorio compuesto por 6 ejercicios: 3 de diseño OpenAPI/Swagger y 3 de
implementación en Spring Boot. Cada ejercicio vive en su propia carpeta de
primer nivel para evitar mezclar responsabilidades.

## Tecnologías
- Java 25
- Spring Boot 4.1.1 (Spring Web MVC)
- Maven (con Maven Wrapper `mvnw`/`mvnw.cmd`)
- Datos en memoria (`ArrayList`) — sin base de datos ni JPA
- OpenAPI 3.0.3 para el diseño de contratos

## Ejercicios de diseño (OpenAPI/Swagger)
| Ejercicio | Carpeta | Archivo |
|---|---|---|
| 1 — Libros | [ejercicio1-diseno-libros/](ejercicio1-diseno-libros) | `libros-api.yaml` |
| 2 — Cursos | [ejercicio2-diseno-cursos/](ejercicio2-diseno-cursos) | `cursos-api.yaml` |
| 3 — Reservas | [ejercicio3-diseno-reservas/](ejercicio3-diseno-reservas) | `reservas-api.yaml` |

## Ejercicios de implementación (Spring Boot)
Cada uno es un proyecto Maven/Spring Boot **independiente y autocontenido**
(propio `pom.xml`, `mvnw`, `src`), ejecutable por separado.

| Ejercicio | Carpeta | Puerto | Detalle |
|---|---|---|---|
| 4 — API de Libros | [ejercicio4-api-libros/](ejercicio4-api-libros) | 8081 | [README](ejercicio4-api-libros/README.md) |
| 5 — API de Cursos | [ejercicio5-api-cursos/](ejercicio5-api-cursos) | 8082 | [README](ejercicio5-api-cursos/README.md) |
| 6 — API de Reservas | [ejercicio6-api-reservas/](ejercicio6-api-reservas) | 8083 | [README](ejercicio6-api-reservas/README.md) |

Cada API usa una lista en memoria (sin base de datos), responde en JSON y sigue
la misma arquitectura por capas: `controller` → `service` → `repository`, con
el modelo en `model`.

## Estructura del repositorio
```
repasoparcial2/
├── README.md
├── ejercicio1-diseno-libros/
│   └── libros-api.yaml
├── ejercicio2-diseno-cursos/
│   └── cursos-api.yaml
├── ejercicio3-diseno-reservas/
│   └── reservas-api.yaml
├── ejercicio4-api-libros/       (proyecto Spring Boot independiente, puerto 8081)
│   └── src/main/java/com/repaso/libros/{controller,service,model,repository}
├── ejercicio5-api-cursos/       (proyecto Spring Boot independiente, puerto 8082)
│   └── src/main/java/com/repaso/cursos/{controller,service,model,repository}
└── ejercicio6-api-reservas/     (proyecto Spring Boot independiente, puerto 8083)
    └── src/main/java/com/repaso/reservas/{controller,service,model,repository}
```

## Cómo ejecutar cada API
Desde la carpeta del ejercicio correspondiente:
```
./mvnw spring-boot:run
```
(en Windows: `mvnw.cmd spring-boot:run`). Las tres pueden correr al mismo
tiempo porque cada una usa un puerto distinto (8081, 8082, 8083).

## Endpoints principales

**Libros** — `http://localhost:8081/api/libros`
| Método | Ruta | Descripción |
|---|---|---|
| POST | /api/libros | Registrar libro |
| GET | /api/libros | Consultar libros |
| GET | /api/libros/titulo/{titulo} | Consultar libro por título |
| PUT | /api/libros/{id} | Actualizar libro |
| DELETE | /api/libros/{id} | Eliminar libro |

**Cursos** — `http://localhost:8082/api/cursos`
| Método | Ruta | Descripción |
|---|---|---|
| POST | /api/cursos | Crear curso |
| GET | /api/cursos | Consultar cursos |
| GET | /api/cursos/codigo/{codigo} | Consultar curso por código |
| PUT | /api/cursos/{id} | Actualizar curso |
| DELETE | /api/cursos/{id} | Eliminar curso |

**Reservas** — `http://localhost:8083/api/reservas`
| Método | Ruta | Descripción |
|---|---|---|
| POST | /api/reservas | Crear reserva |
| GET | /api/reservas | Consultar reservas |
| GET | /api/reservas/{id} | Consultar reserva por id |
| PUT | /api/reservas/{id} | Actualizar reserva |
| DELETE | /api/reservas/{id} | Cancelar reserva (cambia `estado` a `CANCELADA`, no borra el registro) |

Detalles, modelos y ejemplos de JSON en el README de cada ejercicio (4, 5 y 6).
