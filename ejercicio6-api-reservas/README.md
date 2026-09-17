# Ejercicio 6 — API de Reservas de Hotel (Spring Boot)

## Objetivo
Implementar en Spring Boot la API diseñada en
[ejercicio3-diseno-reservas/reservas-api.yaml](../ejercicio3-diseno-reservas/reservas-api.yaml):
un CRUD (sin base de datos) para administrar reservas de habitaciones, con
arquitectura por capas.

Proyecto Spring Boot independiente y autocontenido (propio `pom.xml`), puerto
por defecto `8083`.

```
ejercicio6-api-reservas/
├── pom.xml
├── mvnw / mvnw.cmd
└── src/main/java/com/repaso/reservas/
    ├── ReservasApiApplication.java
    ├── controller/ReservaController.java
    ├── service/ReservaService.java
    ├── model/Reserva.java
    └── repository/ReservaRepository.java
```

Los datos se almacenan en una lista en memoria (`ArrayList`), precargada con
5 reservas al iniciar la aplicación.

## Modelo: Reserva
| Campo | Tipo |
|---|---|
| id | Long (autogenerado) |
| nombreCliente | String |
| habitacion | String |
| fechaEntrada | LocalDate (ISO `yyyy-MM-dd`) |
| fechaSalida | LocalDate (ISO `yyyy-MM-dd`, no puede ser anterior a `fechaEntrada`) |
| estado | String (`CONFIRMADA`, `CANCELADA`, `FINALIZADA`; por defecto `CONFIRMADA`) |

## Endpoints
| Método | Ruta | Descripción | HTTP OK | HTTP error |
|--------|------|-------------|---------|------------|
| POST   | /api/reservas | Crear reserva | 201 | 400 si faltan datos o fechas inválidas |
| GET    | /api/reservas | Consultar todas las reservas | 200 | — |
| GET    | /api/reservas/{id} | Consultar reserva por id | 200 | 404 si no existe |
| PUT    | /api/reservas/{id} | Actualizar reserva | 200 | 400 / 404 |
| DELETE | /api/reservas/{id} | **Cancelar** reserva | 200 | 404 si no existe |

**Nota sobre DELETE:** no elimina físicamente el registro. Cambia su `estado`
a `CANCELADA` y devuelve la reserva actualizada (por eso responde `200`, no
`204`), de forma que la reserva sigue siendo consultable con
`GET /api/reservas/{id}`. Queda documentado igual en `reservas-api.yaml`.

## Ejemplos de JSON

Crear reserva (`POST /api/reservas`):
```json
{
  "nombreCliente": "Jorge Díaz",
  "habitacion": "420",
  "fechaEntrada": "2026-08-01",
  "fechaSalida": "2026-08-05"
}
```

Respuesta (`201 Created`):
```json
{
  "id": 6,
  "nombreCliente": "Jorge Díaz",
  "habitacion": "420",
  "fechaEntrada": "2026-08-01",
  "fechaSalida": "2026-08-05",
  "estado": "CONFIRMADA"
}
```

Cancelar reserva (`DELETE /api/reservas/2`) → `200 OK`:
```json
{
  "id": 2,
  "nombreCliente": "Luis Fernández",
  "habitacion": "102",
  "fechaEntrada": "2026-04-01",
  "fechaSalida": "2026-04-05",
  "estado": "CANCELADA"
}
```

## Cómo probarlo
```
./mvnw spring-boot:run
```
Luego, con `curl`:
```
curl http://localhost:8083/api/reservas
curl http://localhost:8083/api/reservas/1
curl -X POST http://localhost:8083/api/reservas -H "Content-Type: application/json" \
  -d '{"nombreCliente":"Jorge Díaz","habitacion":"420","fechaEntrada":"2026-08-01","fechaSalida":"2026-08-05"}'
curl -X PUT http://localhost:8083/api/reservas/1 -H "Content-Type: application/json" \
  -d '{"nombreCliente":"Ana Torres","habitacion":"101","fechaEntrada":"2026-03-10","fechaSalida":"2026-03-20","estado":"CONFIRMADA"}'
curl -X DELETE http://localhost:8083/api/reservas/2
```

> Validado: compila con `mvnw compile`, arranca con `mvnw spring-boot:run` y
> se probaron manualmente los 5 endpoints, incluyendo 400 por
> `fechaSalida` anterior a `fechaEntrada`, 400 por datos faltantes, 404 por
> reserva inexistente y la serialización ISO (`yyyy-MM-dd`) de las fechas.
