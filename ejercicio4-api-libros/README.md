# Ejercicio 4 — API de Libros (Spring Boot)

## Objetivo
Implementar en Spring Boot la API diseñada en
[ejercicio1-diseno-libros/libros-api.yaml](../ejercicio1-diseno-libros/libros-api.yaml):
un CRUD (sin base de datos) para administrar un catálogo de libros, con
arquitectura por capas.

Proyecto Spring Boot independiente y autocontenido (propio `pom.xml`), puerto
por defecto `8081`.

```
ejercicio4-api-libros/
├── pom.xml
├── mvnw / mvnw.cmd
└── src/main/java/com/repaso/libros/
    ├── LibrosApiApplication.java
    ├── controller/LibroController.java
    ├── service/LibroService.java
    ├── model/Libro.java
    └── repository/LibroRepository.java
```

Los datos se almacenan en una lista en memoria (`ArrayList`), precargada con
5 libros al iniciar la aplicación.

## Modelo: Libro
| Campo | Tipo |
|---|---|
| id | Long (autogenerado) |
| titulo | String |
| autor | String |
| isbn | String |
| anioPublicacion | Integer |
| estado | String (`DISPONIBLE`, `PRESTADO`, `BAJA`; por defecto `DISPONIBLE`) |

## Endpoints
| Método | Ruta | Descripción | HTTP OK | HTTP error |
|--------|------|-------------|---------|------------|
| POST   | /api/libros | Registrar libro | 201 | 400 si faltan datos |
| GET    | /api/libros | Consultar todos los libros | 200 | — |
| GET    | /api/libros/titulo/{titulo} | Consultar libro por título | 200 | 404 si no existe |
| PUT    | /api/libros/{id} | Actualizar libro | 200 | 400 / 404 |
| DELETE | /api/libros/{id} | Eliminar libro | 204 | 404 si no existe |

## Ejemplos de JSON

Registrar libro (`POST /api/libros`):
```json
{
  "titulo": "Fahrenheit 451",
  "autor": "Ray Bradbury",
  "isbn": "978-1451673319",
  "anioPublicacion": 1953
}
```

Respuesta (`201 Created`):
```json
{
  "id": 6,
  "titulo": "Fahrenheit 451",
  "autor": "Ray Bradbury",
  "isbn": "978-1451673319",
  "anioPublicacion": 1953,
  "estado": "DISPONIBLE"
}
```

## Cómo probarlo
```
./mvnw spring-boot:run
```
Luego, con `curl` (o Postman/Insomnia):
```
curl http://localhost:8081/api/libros
curl http://localhost:8081/api/libros/titulo/1984
curl -X POST http://localhost:8081/api/libros -H "Content-Type: application/json" \
  -d '{"titulo":"Fahrenheit 451","autor":"Ray Bradbury","isbn":"978-1451673319","anioPublicacion":1953}'
curl -X PUT http://localhost:8081/api/libros/1 -H "Content-Type: application/json" \
  -d '{"titulo":"Cien años de soledad","autor":"Gabriel García Márquez","isbn":"978-0307474728","anioPublicacion":1967,"estado":"PRESTADO"}'
curl -X DELETE http://localhost:8081/api/libros/2
```

> Validado: compila con `mvnw compile`, arranca con `mvnw spring-boot:run` y
> se probaron manualmente los 5 endpoints (POST, GET, GET por título, PUT,
> DELETE), incluyendo casos 400 (datos inválidos) y 404 (recurso inexistente).
