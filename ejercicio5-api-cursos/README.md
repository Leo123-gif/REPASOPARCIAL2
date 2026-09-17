# Ejercicio 5 — API de Cursos (Spring Boot)

## Objetivo
Implementar en Spring Boot la API diseñada en
[ejercicio2-diseno-cursos/cursos-api.yaml](../ejercicio2-diseno-cursos/cursos-api.yaml):
un CRUD (sin base de datos) para administrar cursos académicos, con
arquitectura por capas.

Proyecto Spring Boot independiente y autocontenido (propio `pom.xml`), puerto
por defecto `8082`.

```
ejercicio5-api-cursos/
├── pom.xml
├── mvnw / mvnw.cmd
└── src/main/java/com/repaso/cursos/
    ├── CursosApiApplication.java
    ├── controller/CursoController.java
    ├── service/CursoService.java
    ├── model/Curso.java
    └── repository/CursoRepository.java
```

Los datos se almacenan en una lista en memoria (`ArrayList`), precargada con
5 cursos al iniciar la aplicación.

## Modelo: Curso
| Campo | Tipo |
|---|---|
| id | Long (autogenerado) |
| nombre | String |
| codigo | String (único) |
| creditos | Integer (> 0) |
| estado | String (`ACTIVO`, `INACTIVO`; por defecto `ACTIVO`) |

## Endpoints
| Método | Ruta | Descripción | HTTP OK | HTTP error |
|--------|------|-------------|---------|------------|
| POST   | /api/cursos | Crear curso | 201 | 400 si faltan datos o el código ya existe |
| GET    | /api/cursos | Consultar todos los cursos | 200 | — |
| GET    | /api/cursos/codigo/{codigo} | Consultar curso por código | 200 | 404 si no existe |
| PUT    | /api/cursos/{id} | Actualizar curso | 200 | 400 / 404 |
| DELETE | /api/cursos/{id} | Eliminar curso | 204 | 404 si no existe |

## Ejemplos de JSON

Crear curso (`POST /api/cursos`):
```json
{
  "nombre": "Inteligencia Artificial",
  "codigo": "IA601",
  "creditos": 4
}
```

Respuesta (`201 Created`):
```json
{
  "id": 6,
  "nombre": "Inteligencia Artificial",
  "codigo": "IA601",
  "creditos": 4,
  "estado": "ACTIVO"
}
```

## Cómo probarlo
```
./mvnw spring-boot:run
```
Luego, con `curl`:
```
curl http://localhost:8082/api/cursos
curl http://localhost:8082/api/cursos/codigo/BDD301
curl -X POST http://localhost:8082/api/cursos -H "Content-Type: application/json" \
  -d '{"nombre":"Inteligencia Artificial","codigo":"IA601","creditos":4}'
curl -X PUT http://localhost:8082/api/cursos/1 -H "Content-Type: application/json" \
  -d '{"nombre":"Programación Avanzada","codigo":"PROG101","creditos":5,"estado":"ACTIVO"}'
curl -X DELETE http://localhost:8082/api/cursos/2
```

> Validado: compila con `mvnw compile`, arranca con `mvnw spring-boot:run` y
> se probaron manualmente los 5 endpoints, incluyendo 400 por código
> duplicado, 400 por créditos inválidos y 404 por curso inexistente.
