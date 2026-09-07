# Delivery API

A REST API that simulates the core of a food delivery system: restaurants publish
their menus, customers place orders, orders move through a validated status
lifecycle until delivery, and the system calculates the delivery fee.

This is a learning project, built as my first experience with Kotlin and Spring Boot.

## Stack

- Kotlin 2.3
- Spring Boot 4.1 (Spring Web MVC, Spring Data JPA)
- Gradle (Kotlin DSL)
- H2 in-memory database
- JUnit 5
- Java 21
- springdoc-openapi (Swagger UI)

## Running

Requires JDK 21.
Interactive API documentation is available at http://localhost:8080/swagger-ui.html

```bash
./gradlew bootRun
```

The application starts on port 8080. Data lives in an in-memory H2 database and
is reset on every restart.

```bash
./gradlew test
```

## Endpoints

| Method | Path                           | Description                                  |
|--------|--------------------------------|----------------------------------------------|
| GET    | `/health`                      | Returns whether the API is up                |
| POST   | `/restaurants`                 | Creates a restaurant                         |
| GET    | `/restaurants`                 | Lists all restaurants                        |
| GET    | `/restaurants/{id}`            | Returns one restaurant, or 404               |
| POST   | `/restaurants/{id}/menu-items` | Adds an item to a restaurant's menu          |
| GET    | `/restaurants/{id}/menu-items` | Lists a restaurant's menu items              |
| POST   | `/orders`                      | Places an order, calculating total and fee   |
| GET    | `/orders/{id}`                 | Returns one order, or 404                    |
| GET    | `/orders?status=`              | Lists orders, optionally filtered by status  |
| POST   | `/orders/{id}/status`          | Advances the order status, or 409            |
| POST   | `/orders/{id}/cancel`          | Cancels the order, or 409                    |

### Example

Create a restaurant, add a menu item, then place an order:

```bash
curl -X POST localhost:8080/restaurants \
  -H "Content-Type: application/json" \
  -d '{"name":"Cantina da Nona","address":"Rua XV, 100","deliveryBaseFeeInCents":500,"latitude":-25.45,"longitude":-49.23}'

curl -X POST localhost:8080/restaurants/1/menu-items \
  -H "Content-Type: application/json" \
  -d '{"name":"Pastel de queijo","description":"Fried to order","priceInCents":900,"available":true}'

curl -X POST localhost:8080/orders \
  -H "Content-Type: application/json" \
  -d '{"restaurantId":1,"customerName":"Fernanda","deliveryAddress":"Rua XV, 100","latitude":-25.43,"longitude":-49.27,"items":[{"menuItemId":1,"quantity":2}]}'
```

## Order lifecycle

```
RECEIVED -> CONFIRMED -> IN_PREPARATION -> OUT_FOR_DELIVERY -> DELIVERED
    |            |              |
    +------------+--------------+--> CANCELLED
```

The state machine is validated so that steps cannot be skipped throughout the process, with `DELIVERED` and `CANCELLED` as terminal states. Attempting an invalid transition will return 409.

## Design decisions

### Delivery fee

The delivery fee is the restaurant's base fee plus the Euclidean distance between the coordinates, converted to km by a set factor (111 km per degree), at 200 cents/km.

This is a simplification, since using geolocation would require an external service and fall outside this project's scope.

### Money as integer cents

Prices and fees are stored as integer cents, never as floating point, so that
money is never subject to binary rounding error.

### DTOs instead of entities at the API boundary

Requests and responses use dedicated DTOs rather than the JPA entities, which
keeps the API contract independent from the database schema and prevents
clients from setting server-controlled fields such as `id`.

### Status transitions live in the enum

The controller is responsible only for the HTTP side, while the service takes care of business rules. The transition rules live in the enum in order to maintain a single source of truth. Every code path that changes a status must go through the same rule, including ones that never touch HTTP.

### Order items store the price at purchase time

This may seem like a duplicate value. However, `unitPriceInCents` stores a snapshot of the price in the restaurant at the time the order was created. A past order should not change its value because the restaurant changed its prices.

### Errors are raised in the service and translated in one place

`@RestControllerAdvice` maps domain exceptions to HTTP responses. 400 is returned for combinations that exist but are invalid (example: ordering an unavailable item), and 409 is used to indicate the combination is valid but the moment is not (invalid state machine change). Resources that do not exist return 404.

### H2 instead of PostgreSQL

H2 was used instead of PostgreSQL because it can keep the project running with a single command and without external dependencies. This makes sense since this is a study project. Changing from H2 to PostgreSQL afterwards would be a configuration change rather than a code change.

## Tests

Business rules are covered by plain unit tests that do not start a Spring
context, so the suite runs in milliseconds:

- `OrderStatusTest` — the status state machine: valid transitions, skipped
  steps, and the two terminal states
- `OrderServiceTest` — the delivery fee formula, including the base-fee-only case

The goal with the tests was to cover core business rules rather than chase full coverage. Order creation validations and endpoint tests were left out on purpose and verified manually for now.


## What I would do with more time

1. Include tests for endpoints with MockMvc
2. Include tests for order creation
3. Add PostgreSQL with Docker Compose
4. Add a history of status changes

