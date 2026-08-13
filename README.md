# java-be

A simple **Java 17 / Spring Boot 3** REST service for learning and DevOps testing.
It manages `Product` records with full CRUD, a flexible query endpoint, and an
exposed health endpoint. Data lives in an in-memory H2 database (seeded on start).

## Requirements

- Java 17+ (Java 21 works too — the build targets bytecode level 17)
- Maven 3.9+ **or** Docker

## Run

### With Maven
```bash
mvn spring-boot:run
```

### With Docker
```bash
docker build -t java-be .
docker run -p 9090:9090 java-be
```

The app listens on **http://localhost:9090**.

## Endpoints

| Method | Path                     | Description                    |
|--------|--------------------------|--------------------------------|
| GET    | `/api/products`          | List all products              |
| GET    | `/api/products/{id}`     | Get one product                |
| POST   | `/api/products`          | Create a product               |
| PUT    | `/api/products/{id}`     | Update a product               |
| DELETE | `/api/products/{id}`     | Delete a product               |
| GET    | `/api/products/search`   | Query (name, category, price)  |
| GET    | `/actuator/health`       | Health check                   |
| GET    | `/actuator/metrics`      | Metrics                        |

### Examples

```bash
# List
curl localhost:9090/api/products

# Create
curl -X POST localhost:9090/api/products \
  -H 'Content-Type: application/json' \
  -d '{"name":"Webcam","category":"electronics","price":59.99,"quantity":40}'

# Update
curl -X PUT localhost:9090/api/products/1 \
  -H 'Content-Type: application/json' \
  -d '{"name":"Wireless Mouse","category":"electronics","price":19.99,"quantity":100}'

# Delete
curl -X DELETE localhost:9090/api/products/1

# Query: electronics between 40 and 90
curl "localhost:9090/api/products/search?category=electronics&minPrice=40&maxPrice=90"

# Health
curl localhost:9090/actuator/health
```

## Notes

- H2 console: http://localhost:9090/h2-console (JDBC URL `jdbc:h2:mem:productsdb`, user `sa`, no password).
- To switch to Postgres/MySQL later, swap the H2 dependency in `pom.xml` and update
  the `spring.datasource.*` values in `src/main/resources/application.properties`.
- Sample seed data is in `src/main/resources/data.sql`.
# java-be git init git add README.md git commit -m first commit git branch -M main git remote add origin https://github.com/Tao-shi/java-be.git git push -u origin main
# java-be
