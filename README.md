# Delivery API

A REST API that simulates the core of a food delivery system: restaurants publish
their menus, customers place orders, orders move through a validated status
lifecycle until delivery, and the system calculates the delivery fee.

This is a learning project, built while studying Kotlin and Spring Boot. Design
decisions will be documented as the project grows.

## Stack

- Kotlin 2.3
- Spring Boot 4.1 (Spring Web MVC, Spring Data JPA)
- Gradle (Kotlin DSL)
- H2 in-memory database (PostgreSQL planned)
- Java 21

## Running

Requires JDK 21. 

```bash
./gradlew bootRun
```

The application starts on port 8080.

## Endpoints

| Method | Path                                | Description                          |
|--------|-------------------------------------|--------------------------------------|
| GET    | `/health`                           | Returns whether the API is up        |
| POST   | `/restaurants`                      | Creates a restaurant                 |
| GET    | `/restaurants`                      | Lists all restaurants                |
| GET    | `/restaurants/{id}`                 | Returns one restaurant, or 404       |
| POST   | `/restaurants/{id}/menu-items`      | Adds an item to a restaurant's menu  |
| GET    | `/restaurants/{id}/menu-items`      | Lists a restaurant's menu items      |

```bash
curl -X POST localhost:8080/restaurants \
  -H "Content-Type: application/json" \
  -d '{"name":"Cantina da Nona","address":"Rua XV, 100","deliveryBaseFeeInCents":500,"latitude":-25.42,"longitude":-49.27}'
```

Prices and fees are stored as integer cents, never as floating point, so that
money is never subject to binary rounding error.

Requests and responses use dedicated DTOs rather than the JPA entities, which
keeps the API contract independent from the database schema and prevents
clients from setting server-controlled fields such as `id`.

## Status

Work in progress. The project is being built in milestones:

- [x] **M0** - Project setup and health endpoint
- [x] **M1** - Restaurants and menu items: entities, repositories, DTOs, persistence
- [ ] **M2** - Orders: creation rules, total and delivery fee calculation, and a status state machine
- [ ] **M3** - Tests covering the business rules
- [ ] **M4** - PostgreSQL with Docker Compose, Swagger UI, and documented design
      decisions
