# Prices API

This project provides a REST API to retrieve applicable pricing information for a given product, brand, and application date. It is developed using **Hexagonal Architecture**, following **Clean Architecture** principles, **CQRS pattern**, and an **API First** approach powered by OpenAPI and code generation.

---

## 🧱 Architecture Overview

### ✅ Hexagonal / Clean Architecture
- **Domain-centric** design, with clear separation between domain logic, infrastructure, and application services.
- All dependencies point inward, enabling easier testing and long-term maintainability.

### ✅ CQRS (Command Query Responsibility Segregation)
- Read operations (queries) are handled separately via dedicated handlers.
- Separation between intent (query object) and execution (handler).

### ✅ API First + OpenAPI
- Contract defined in `src/main/resources/static/openapi/prices-api.yaml`.
- Code for API interfaces and DTOs is automatically generated using the OpenAPI Maven plugin.

---

## 🚀 How to Run the Application

### 🧪 Prerequisites
- Java 21+
- Maven 3.8+
- (Optional) Docker for running the app in a container

### 🔧 Build and Start

```bash
# Compile and run tests
./mvnw clean verify

# Run the application
./mvnw spring-boot:run
```

### 🐳 Dockerized version

```bash
docker build -t prices-api .
docker run -p 8001:8001 prices-api
```

---

## 🔍 API Endpoints

| Resource           | URL                                      | Description                                  |
|

---
## ⚙️ Database – H2 Configuration

The application uses an **in-memory H2 database**, preconfigured for use during runtime and testing. You can access the H2 web console to inspect the data.

### 🔐 Connection parameters:

- **URL**: `jdbc:h2:mem:pricesdb`
- **Username**: `sa`
- **Password**: *(empty)*
- **Web Console**: [`http://localhost:8001/h2-console`](http://localhost:8001/h2-console)

> Make sure to set the JDBC URL in the console to `jdbc:h2:mem:pricesdb` when connecting.

---
## 📑 Logging – Configuration and Output

Logging is configured using a **logback.xml** file located at:

```
src/main/resources/logback-spring.xml
```

### 🗂️ Log output:

- **Log directory**: Logs are written to the root directory under the `logs/` folder.
- **Log filename**: `prices-api.log`
- **Rotation policy**:
  - One file per day
  - Up to **7 days** of log history
  - Older files are renamed with the date, e.g., `prices-api-2024-04-15.log`

### ✨ Default log levels:

- `INFO` for the application
- `DEBUG` for internal packages (`com.inditex.prices`)

> You can adjust log levels in the `logback-spring.xml` file depending on the environment.

---

## 🔍 API Endpoints

| Resource           | URL                                      | Description                                  |
|--------------------|------------------------------------------|----------------------------------------------|
| Application URL    | `http://localhost:8001`                  | Root of the application                      |
| Swagger UI         | `http://localhost:8001/swagger-ui.html`  | API documentation interface                  |
| H2 Console         | `http://localhost:8001/h2-console`       | Web interface for H2 in-memory database      |
| Actuator - Health  | `http://localhost:8001/actuator/health`  | Health check endpoint                        |
| Actuator - Info    | `http://localhost:8001/actuator/info`    | Application info (from build metadata)       |
| Actuator - Metrics | `http://localhost:8001/actuator/metrics` | Application metrics endpoint (basic metrics) |

---

## 🧪 Testing Strategy – Test Pyramid

This project embraces a **testing pyramid** structure:

### 1. ✅ Unit Tests (bottom layer)
- Libraries: **JUnit 5** + **Mockito**
- Tested: Service logic, query handlers, mappers

### 2. 🧩 Integration Tests (middle layer)
- Repository adapters with real H2 database
- REST API controllers with real Spring context

#### ✅ Cucumber
- Located in `src/test/java/com/inditex/prices/integration`
- Covers business scenarios using Gherkin language

### 3. 🌐 Acceptance Tests (top layer)

#### ✅ Postman + Newman
- Directory: `/postman`
- Executable via CLI for full acceptance test automation

---

## 📦 Running Postman Acceptance Tests with Newman

### 🧰 Prerequisites
Make sure Node.js and Newman are installed:

```bash
npm install -g newman newman-reporter-html
```

### ▶️ Run Tests

```bash
newman run postman/prices-acceptance.postman_collection.json \
  --reporters cli,html \
  --reporter-html-export postman/report.html
```

### 📄 Output

- Terminal summary of all test cases
- HTML report generated in `postman/report.html`

---

## 📁 Project Structure

```
prices/
├── src/
│   ├── main/
│   │   ├── domain/         # Business models, exceptions
│   │   ├── application/    # Services, query handlers
│   │   ├── infrastructure/ # Adapters: REST, DB, mappers
│   │   └── resources/
│   └── test/
│       ├── unit/           # Unit tests
│       ├── integration/    # Integration tests
│       └── features/       # Cucumber & Postman
├── postman/                # Postman collections & reports
├── pom.xml
└── README.md
```

---

## 🧩 Tools & Frameworks Used

- Spring Boot 3.4.4
- MapStruct 1.6.3
- SpringDoc OpenAPI 2.8.6
- JUnit 5, Mockito, Cucumber, Postman
- H2 in-memory DB (for testing)

---