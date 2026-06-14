# Spring Boot + JPA + PostgreSQL + Swagger Demo

A minimal CRUD REST API for a `Product` resource, built with Spring Boot 3,
Spring Data JPA, PostgreSQL, and springdoc-openapi for Swagger UI.

## Project structure

```
src/main/java/com/example/demo
├── DemoApplication.java
├── config/OpenApiConfig.java
├── controller/ProductController.java
├── dto/ProductRequest.java
├── dto/ProductResponse.java
├── entity/Product.java
├── exception/GlobalExceptionHandler.java
├── exception/ResourceNotFoundException.java
├── repository/ProductRepository.java
└── service/ProductService.java
src/main/resources/application.yml
pom.xml
docker-compose.yml
```

## Prerequisites

- Java 17+
- Maven 3.8+
- A running PostgreSQL instance (or Docker, see below)

## 1. Start PostgreSQL

The easiest way is via Docker Compose:

```bash
docker-compose up -d
```

This starts PostgreSQL on `localhost:5432` with:
- database: `demodb`
- user: `postgres`
- password: `postgres`

If you already have a PostgreSQL instance, just update the connection
details in `src/main/resources/application.yml`:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/demodb
    username: postgres
    password: postgres
```

Hibernate is configured with `ddl-auto: update`, so the `products` table
will be created automatically on startup.

## 2. Run the application

```bash
mvn spring-boot:run
```

The app starts on `http://localhost:8080`.

## 3. Swagger / OpenAPI

- Swagger UI: http://localhost:8080/swagger-ui.html
- Raw OpenAPI JSON: http://localhost:8080/v3/api-docs

Use Swagger UI to explore and try out all endpoints interactively.

## API Endpoints

| Method | Path                     | Description           |
|--------|--------------------------|-----------------------|
| GET    | /api/v1/products         | List all products     |
| GET    | /api/v1/products/{id}    | Get product by id      |
| POST   | /api/v1/products         | Create a new product   |
| PUT    | /api/v1/products/{id}    | Update an existing product |
| DELETE | /api/v1/products/{id}    | Delete a product       |

### Sample request body (POST/PUT)

```json
{
  "name": "Wireless Mouse",
  "description": "Ergonomic wireless mouse with USB receiver",
  "price": 19.99,
  "quantity": 100
}
```

## Notes

- Validation errors and "not found" cases return structured JSON via
  `GlobalExceptionHandler`.
- `ddl-auto: update` is convenient for development; for production, use a
  proper migration tool (Flyway or Liquibase) and set `ddl-auto: validate`
  or `none`.
