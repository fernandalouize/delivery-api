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

| Method | Path      | Description                     |
|--------|-----------|---------------------------------|
| GET    | `/health` | Returns whether the API is up   |

```bash
curl localhost:8080/health
```

## Status

Work in progress. The project is being built in milestones:

- [x] **M0** - Project setup and health endpoint
- [ ] **M1** - Restaurants and menu items: entities, repositories, DTOs, persistence
- [ ] **M2** - Orders: creation rules, total and delivery fee calculation, and a status state machine
- [ ] **M3** - Tests covering the business rules
- [ ] **M4** - PostgreSQL with Docker Compose, Swagger UI, and documented design
      decisions
