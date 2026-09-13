# 🚀 Microservices Backend Architecture

A production-oriented **Spring Boot Microservices Architecture** demonstrating service discovery, centralized configuration, API Gateway routing, JWT-based authentication, inter-service communication, fault tolerance, rate limiting, and polyglot persistence.

The project is designed as a modular backend system where individual business capabilities are separated into independently deployable services.

## 🏗️ Architecture Overview
                           ┌──────────────────────┐
                           │       Client         │
                           │ Web / Mobile / API   │
                           └──────────┬───────────┘
                                      │
                                      ▼
                           ┌──────────────────────┐
                           │     API Gateway      │
                           │       :8081          │
                           │                      │
                           │ • Routing            │
                           │ • JWT Validation     │
                           │ • Load Balancing     │
                           │ • Circuit Breaker    │
                           └──────────┬───────────┘
                                      │
              ┌───────────────────────┼────────────────────────┐
              │                       │                        │
              ▼                       ▼                        ▼
   ┌──────────────────┐   ┌──────────────────┐    ┌──────────────────┐
   │  User Service    │   │ Company Service  │    │  Rating Service  │
   │      :8881       │   │      :8882       │    │      :8883       │
   │                  │   │                  │    │                  │
   │ PostgreSQL       │   │ PostgreSQL       │    │ MongoDB          │
   └────────┬─────────┘   └────────┬─────────┘    └────────┬─────────┘
            │                      │                       │
            └──────────────┬───────┴───────────────────────┘
                           │
                           ▼
                 ┌─────────────────────┐
                 │ Service Registry     │
                 │ Eureka Server        │
                 │       :8084          │
                 └─────────────────────┘

                 ┌─────────────────────┐
                 │   Config Server     │
                 │       :8082         │
                 │                     │
                 │ Centralized Config   │
                 └─────────────────────┘

                 ┌─────────────────────┐
                 │ Authentication      │
                 │ Service             │
                 │       :8083         │
                 │                     │
                 │ JWT Authentication  │
                 └─────────────────────┘

                 ┌─────────────────────┐
                 │  common-jwt-lib     │
                 │                     │
                 │ Shared JWT Utility  │
                 └─────────────────────┘
```

---

# 📋 Table of Contents

* [Overview](#-overview)
* [Key Features](#-key-features)
* [Technology Stack](#-technology-stack)
* [Microservices](#-microservices)
* [Architecture](#-architecture)
* [Service Ports](#-service-ports)
* [Request Flow](#-request-flow)
* [Authentication](#-authentication)
* [API Gateway](#-api-gateway)
* [Service Discovery](#-service-discovery)
* [Configuration Management](#-configuration-management)
* [Inter-Service Communication](#-inter-service-communication)
* [Resilience and Fault Tolerance](#-resilience-and-fault-tolerance)
* [Database Architecture](#-database-architecture)
* [API Documentation](#-api-documentation)
* [Project Structure](#-project-structure)
* [Prerequisites](#-prerequisites)
* [Database Setup](#-database-setup)
* [Configuration](#-configuration)
* [Running the Project](#-running-the-project)
* [Testing](#-testing)
* [Actuator](#-actuator)
* [Security Notes](#-security-notes)
* [Troubleshooting](#-troubleshooting)
* [Future Improvements](#-future-improvements)
* [Author](#-author)

---

# 📌 Overview

This project demonstrates how to build and organize a distributed backend system using the **Spring Cloud ecosystem**.

Instead of implementing all business functionality inside a single monolithic application, the system separates responsibilities into multiple services.

Each service has its own:

* Business responsibility
* Application lifecycle
* Configuration
* Data access layer
* Database where applicable
* API endpoints
* Service registration

The architecture also provides common distributed-system capabilities such as:

* Service discovery
* Centralized configuration
* API routing
* JWT authentication
* Client-side load balancing
* Fault tolerance
* Circuit breakers
* Rate limiting
* Service fallbacks
* Health monitoring

---

# ✨ Key Features

### 🔐 JWT Authentication

The application uses JWT tokens for authentication.

The authentication service generates tokens and the API Gateway validates incoming tokens before forwarding requests to protected services.

### 🌐 API Gateway

The API Gateway provides a single entry point for clients.

Responsibilities include:

* Request routing
* Authentication filtering
* Service discovery integration
* Load balancing
* Circuit breaker integration
* Fallback handling

### 🔎 Service Discovery

Netflix Eureka is used as the service registry.

Services register themselves with Eureka and the Gateway communicates with services using service names instead of hard-coded service addresses.

Example:

```text
lb://USERSERVICE
lb://COMPANYSERVICE
lb://RATINGSERVICE
lb://AUTHENTICATIONSERVICE
```

### ⚙️ Centralized Configuration

Spring Cloud Config Server is used to provide centralized configuration for the microservices.

### 🔄 Inter-Service Communication

The User Service uses **Spring Cloud OpenFeign** to communicate with other services.

### 🛡️ Fault Tolerance

Resilience4j is used for:

* Circuit breaking
* Retry configuration
* Rate limiting
* Fallback handling

### 💾 Polyglot Persistence

Different services use different databases according to their requirements.

```text
User Service     → PostgreSQL
Company Service  → PostgreSQL
Rating Service   → MongoDB
```

### ❤️ Health Monitoring

Spring Boot Actuator is enabled for health monitoring.

---

# 🧰 Technology Stack

| Technology             | Purpose                       |
| ---------------------- | ----------------------------- |
| Java 17                | Programming language          |
| Spring Boot            | Application framework         |
| Spring Cloud           | Microservices infrastructure  |
| Spring Cloud Gateway   | API Gateway                   |
| Netflix Eureka         | Service discovery             |
| Spring Cloud Config    | Centralized configuration     |
| Spring Cloud OpenFeign | Inter-service communication   |
| Spring Data JPA        | PostgreSQL persistence        |
| PostgreSQL             | Relational database           |
| Spring Data MongoDB    | MongoDB persistence           |
| MongoDB                | Rating data                   |
| JWT                    | Authentication                |
| Resilience4j           | Fault tolerance               |
| Lombok                 | Boilerplate reduction         |
| Maven                  | Build & dependency management |
| Actuator               | Application monitoring        |

---

# 🧩 Microservices

## 1. API Gateway

**Directory**

```text
ApiGateway/
```

**Port**

```text
8081
```

The API Gateway is the primary entry point for external clients.

### Responsibilities

* Route requests to microservices
* Validate JWT tokens
* Discover services through Eureka
* Load balance requests
* Apply circuit breakers
* Provide fallback responses

### Routes

```text
/users/**   → USERSERVICE
/auth/**    → AUTHENTICATIONSERVICE
/company/** → COMPANYSERVICE
/rating/**  → RATINGSERVICE
```

---

# 🔐 2. Authentication Service

**Directory**

```text
AuthenticationService/
```

**Port**

```text
8083
```

**Context Path**

```text
/auth
```

### Responsibilities

* User authentication
* Login
* User registration
* JWT token generation

### Endpoints

```http
POST /auth/login
POST /auth/saveUser
```

---

# 👤 3. User Service

**Directory**

```text
UserService/
```

**Port**

```text
8881
```

**Context Path**

```text
/users
```

### Responsibilities

* User management
* User creation
* User lookup
* Pagination
* Sorting
* Search
* Inter-service communication
* Rate limiting

### Endpoints

```http
POST /users/user

GET /users/getAllUsers

GET /users/getUserById

GET /users/getUserByUserName
```

### Pagination

The User Service supports:

```text
per_page
page
sort_by
sort_type
search
```

Example:

```http
GET /users/getAllUsers?page=1&per_page=10&sort_by=insert_date&sort_type=desc
```

---

# 🏢 4. Company Service

**Directory**

```text
CompanyService/
```

**Port**

```text
8882
```

**Context Path**

```text
/company
```

### Responsibilities

* Company management
* Create company
* Retrieve company list
* Pagination
* Sorting
* Company lookup

### Endpoints

```http
POST /company/saveCompany

GET /company/getAllCompany

GET /company/getCompanyById
```

Example:

```http
GET /company/getAllCompany?page=1&per_page=10&sort_by=insert_date&sort_type=desc
```

---

# ⭐ 5. Rating Service

**Directory**

```text
RatingService/
```

**Port**

```text
8883
```

**Context Path**

```text
/rating
```

### Responsibilities

* Create ratings
* Retrieve ratings
* Retrieve ratings by company
* Retrieve ratings by user
* Pagination
* Sorting

### Endpoints

```http
POST /rating/saveRating

GET /rating/getAllRating

GET /rating/getRatingByCompanyId

GET /rating/getRatingByUserId
```

---

# 🔍 6. Service Registry

**Directory**

```text
ServiceRegistry/
```

**Port**

```text
8084
```

The Service Registry uses **Netflix Eureka Server**.

It maintains information about registered microservices and allows services to locate each other dynamically.

### Eureka Dashboard

```text
http://localhost:8084
```

---

# ⚙️ 7. Config Server

**Directory**

```text
ConfigServer/
```

**Port**

```text
8082
```

The Config Server uses Spring Cloud Config Server.

It provides centralized configuration to participating services.

The current implementation is configured to retrieve configuration from a Git repository.

---

# 🔑 8. Common JWT Library

**Directory**

```text
common-jwt-lib/
```

This is a shared library used by microservices that require JWT functionality.

It contains common JWT functionality such as:

```text
JwtUtil
JwtAuthFilter
```

This avoids duplicating JWT-related code across multiple services.

---

# 🌐 API Gateway

The Gateway uses Spring Cloud Gateway's programmatic route configuration.

Current routes include:

```text
/users/**    → lb://USERSERVICE
/auth/**     → lb://AUTHENTICATIONSERVICE
/company/**  → lb://COMPANYSERVICE
/rating/**   → lb://RATINGSERVICE
```

The `lb://` prefix allows service discovery/load balancing through Eureka.

---

# 🔐 Authentication Flow

The authentication flow is approximately:

```text
Client
   │
   │ POST /auth/login
   ▼
API Gateway
   │
   ▼
Authentication Service
   │
   │ Validate credentials
   │
   ▼
Generate JWT
   │
   ▼
Client
   │
   │ Authorization: Bearer <JWT>
   ▼
API Gateway
   │
   │ Validate JWT
   │
   ▼
Target Microservice
```

Protected requests are intercepted by the Gateway's authentication filter.

The current Gateway configuration defines these public endpoint prefixes:

```text
/auth/login
/auth/register
/company/public
/rating/public
```

Other Gateway-routed endpoints are treated as secured.

---

# 🔄 Inter-Service Communication

The User Service uses OpenFeign.

Example:

```java
@FeignClient(name = "COMPANYSERVICE")
public interface CompanyService {

    @GetMapping("/company/getCompanyById")
    CompanyResponse getCompany(
        @RequestParam(value = "company_id") Long companyId
    );
}
```

This means the User Service can communicate with the Company Service using its Eureka service name rather than a fixed host/port.

```text
User Service
      │
      │ OpenFeign
      ▼
Eureka
      │
      ▼
Company Service
```

This approach makes the architecture more flexible when service instances change.

---

# 🛡️ Resilience and Fault Tolerance

The project integrates **Resilience4j**.

## Circuit Breaker

Circuit breakers are configured for distributed calls to prevent cascading failures.

The Gateway also defines fallback endpoints:

```text
/fallback/users
/fallback/company
/fallback/rating
```

Example response:

```text
User Service is currently unavailable.
```

---

## 🔁 Retry

The User Service includes a retry configuration:

```yaml
retry:
  instances:
    ratingCompanyService:
      max-attempts: 3
      wait-duration: 5s
```

This allows failed operations to be retried according to the configured policy.

---

## 🚦 Rate Limiting

The User Service has a rate limiter configured as:

```yaml
ratelimiter:
  instances:
    userRateLimiter:
      limit-refresh-period: 4s
      limit-for-period: 2
      timeout-duration: 2s
```

The `getUserById` endpoint uses the rate limiter.

This helps protect the service from excessive requests.

---

# 💾 Database Architecture

The system follows a polyglot persistence approach.

## User Service

```text
PostgreSQL
Database: userService
```

Configuration:

```text
jdbc:postgresql://localhost:5432/userService
```

---

## Company Service

```text
PostgreSQL
Database: companyService
```

Configuration:

```text
jdbc:postgresql://localhost:5432/companyService
```

---

## Rating Service

```text
MongoDB
Database: ratingService
```

Configuration:

```text
mongodb://localhost:27017
```

---

# 📡 API Reference

## Authentication

### Login

```http
POST /auth/login
Content-Type: application/json
```

Request:

```json
{
  "userName": "your_username",
  "password": "your_password"
}
```

---

### Save User

```http
POST /auth/saveUser
Content-Type: application/json
```

---

# 👤 User APIs

### Create User

```http
POST /users/user
```

### Get Users

```http
GET /users/getAllUsers
```

Supported parameters:

```text
page
per_page
sort_by
sort_type
search
```

Example:

```http
GET /users/getAllUsers?page=1&per_page=10&sort_by=insert_date&sort_type=desc
```

### Get User By ID

```http
GET /users/getUserById?user_id=1
```

### Get User By Username

```http
GET /users/getUserByUserName?user_name=tusher
```

---

# 🏢 Company APIs

### Create Company

```http
POST /company/saveCompany
```

### Get Companies

```http
GET /company/getAllCompany
```

Parameters:

```text
page
per_page
sort_by
sort_type
search
```

### Get Company

```http
GET /company/getCompanyById?company_id=1
```

---

# ⭐ Rating APIs

### Create Rating

```http
POST /rating/saveRating
```

### Get Ratings

```http
GET /rating/getAllRating
```

Parameters:

```text
page
per_page
sort_by
sort_type
search
```

### Get Ratings By Company

```http
GET /rating/getRatingByCompanyId?company_id=1
```

### Get Ratings By User

```http
GET /rating/getRatingByUserId?user_id=1
```

---

# 📁 Project Structure

```text
MicroService/
│
├── ApiGateway/
│   ├── src/
│   └── pom.xml
│
├── AuthenticationService/
│   ├── src/
│   └── pom.xml
│
├── CompanyService/
│   ├── src/
│   └── pom.xml
│
├── ConfigServer/
│   ├── src/
│   └── pom.xml
│
├── RatingService/
│   ├── src/
│   └── pom.xml
│
├── ServiceRegistry/
│   ├── src/
│   └── pom.xml
│
├── UserService/
│   ├── src/
│   └── pom.xml
│
├── common-jwt-lib/
│   ├── src/
│   └── pom.xml
│
└── pom.xml
```

---

# 🧱 Service Internal Structure

The business services generally follow a layered architecture:

```text
Controller
    │
    ▼
Service Interface
    │
    ▼
Service Implementation
    │
    ▼
Repository
    │
    ▼
Database
```

Data transfer is handled through dedicated request/response DTOs.

Exception handling is centralized using global exception handlers in the business services.

---

# 💻 Prerequisites

Install the following before running the application:

* Java 17+
* Maven 3.8+
* PostgreSQL
* MongoDB
* Git
* IDE such as IntelliJ IDEA / Eclipse / VS Code

Recommended:

```text
Java       17
Spring Boot 3.x
Maven      3.8+
PostgreSQL 14+
MongoDB    6+
```

---

# 🗄️ Database Setup

## PostgreSQL

Create the required databases:

```sql
CREATE DATABASE userService;
CREATE DATABASE companyService;
```

The current development configuration uses:

```text
Username: postgres
Password: root
Host: localhost
```

> For production, do not commit database credentials to source control.

---

## MongoDB

Start MongoDB locally.

The Rating Service uses:

```text
Database: ratingService
Host: localhost
Port: 27017
```

The application can create the required collections as data is persisted.

---

# ⚙️ Configuration

The services use Spring Cloud Config Server.

The Config Server runs on:

```text
http://localhost:8082
```

Services are configured to import configuration from:

```text
http://localhost:8082
```

The Config Server itself is configured with a Git-based configuration repository.

Before running the system, make sure the configured Git repository is accessible.

---

# 🚀 Running the Project

Because the services depend on each other, start the infrastructure services first.

## Step 1 — Start Service Registry

Navigate to:

```bash
cd ServiceRegistry
```

Run:

```bash
./mvnw spring-boot:run
```

Windows:

```cmd
mvnw.cmd spring-boot:run
```

Service:

```text
http://localhost:8084
```

---

# Step 2 — Start Config Server

```bash
cd ConfigServer
./mvnw spring-boot:run
```

Windows:

```cmd
mvnw.cmd spring-boot:run
```

Service:

```text
http://localhost:8082
```

---

# Step 3 — Start Authentication Service

```bash
cd AuthenticationService
./mvnw spring-boot:run
```

Service:

```text
http://localhost:8083
```

Context path:

```text
/auth
```

---

# Step 4 — Start User Service

```bash
cd UserService
./mvnw spring-boot:run
```

Service:

```text
http://localhost:8881
```

Context path:

```text
/users
```

---

# Step 5 — Start Company Service

```bash
cd CompanyService
./mvnw spring-boot:run
```

Service:

```text
http://localhost:8882
```

Context path:

```text
/company
```

---

# Step 6 — Start Rating Service

```bash
cd RatingService
./mvnw spring-boot:run
```

Service:

```text
http://localhost:8883
```

Context path:

```text
/rating
```

---

# Step 7 — Start API Gateway

```bash
cd ApiGateway
./mvnw spring-boot:run
```

Gateway:

```text
http://localhost:8081
```

For normal client requests, use the Gateway rather than calling the individual services directly.

---

# 🔗 Recommended Startup Order

```text
1. PostgreSQL
2. MongoDB
3. Service Registry
4. Config Server
5. Authentication Service
6. User Service
7. Company Service
8. Rating Service
9. API Gateway
```

---

# 🔍 Service Ports

| Component              |   Port | Context    |
| ---------------------- | -----: | ---------- |
| API Gateway            | `8081` | `/`        |
| Config Server          | `8082` | `/`        |
| Authentication Service | `8083` | `/auth`    |
| Service Registry       | `8084` | `/`        |
| User Service           | `8881` | `/users`   |
| Company Service        | `8882` | `/company` |
| Rating Service         | `8883` | `/rating`  |

---

# ❤️ Actuator Health

Actuator health endpoints are enabled for services where configured.

Example:

```http
GET /actuator/health
```

For the API Gateway:

```text
http://localhost:8081/actuator/health
```

A successful response can be used to verify service health.

---

# 🧪 Testing

Each service contains Spring Boot test infrastructure.

Run tests from an individual service:

```bash
./mvnw test
```

Or on Windows:

```cmd
mvnw.cmd test
```

For a complete system test, verify:

1. Eureka is running.
2. Config Server is running.
3. Required databases are available.
4. All services are registered.
5. Authentication works.
6. JWT-protected endpoints reject invalid/missing tokens.
7. Gateway routes requests correctly.
8. Fallback mechanisms work when a downstream service is unavailable.

---

# 🔒 Security Notes

The current repository contains development configuration such as:

```yaml
jwt:
  secret: key
```

and database credentials such as:

```text
username: postgres
password: root
```

These values should **not be used in production**.

For production deployment, use:

* Environment variables
* Docker secrets
* Kubernetes Secrets
* AWS Secrets Manager
* HashiCorp Vault
* Externalized configuration

A strong randomly generated JWT signing secret should also be used.

---

# ⚠️ Production Considerations

Before deploying this project to production, consider improving:

### Secrets

Move:

```text
JWT secret
Database username
Database password
Config repository credentials
```

outside source code.

### HTTPS

Use TLS between clients and the API Gateway.

### Database Migration

Instead of relying on:

```text
spring.jpa.hibernate.ddl-auto=update
```

use a migration framework such as:

```text
Flyway
```

or:

```text
Liquibase
```

### Observability

Consider adding:

```text
Prometheus
Grafana
Micrometer
Distributed tracing
Centralized logging
```

### Containerization

Dockerize each service and use Docker Compose for local development.

### API Documentation

Consider adding:

```text
Springdoc OpenAPI
Swagger UI
```

for interactive API documentation.

---

# 🐳 Dockerization Roadmap

A production deployment could be organized as:

```text
                    ┌───────────────┐
                    │ API Gateway   │
                    └───────┬───────┘
                            │
          ┌─────────────────┼─────────────────┐
          ▼                 ▼                 ▼
     User Service     Company Service    Rating Service
          │                 │                 │
          ▼                 ▼                 ▼
     PostgreSQL         PostgreSQL          MongoDB
```

Infrastructure:

```text
Eureka
Config Server
PostgreSQL
MongoDB
```

can also be managed using containers.

---

# 📈 Future Improvements

Potential improvements for the architecture include:

* [ ] Docker support
* [ ] Docker Compose
* [ ] Kubernetes deployment
* [ ] Swagger / OpenAPI
* [ ] Prometheus metrics
* [ ] Grafana dashboards
* [ ] Distributed tracing
* [ ] Centralized logging
* [ ] Kafka/RabbitMQ event-driven communication
* [ ] OAuth2 / OpenID Connect
* [ ] Refresh-token implementation
* [ ] Database migrations using Flyway
* [ ] CI/CD pipeline
* [ ] Integration tests
* [ ] Contract testing
* [ ] API versioning
* [ ] Production-ready secret management
* [ ] HTTPS/TLS
* [ ] Container health checks

---

# 🧠 Architectural Concepts Demonstrated

This project demonstrates practical implementation of several distributed-system concepts:

```text
Microservices
      │
      ├── Service Discovery
      │       └── Eureka
      │
      ├── API Gateway
      │       └── Spring Cloud Gateway
      │
      ├── Centralized Configuration
      │       └── Spring Cloud Config
      │
      ├── Authentication
      │       └── JWT
      │
      ├── Service Communication
      │       └── OpenFeign
      │
      ├── Load Balancing
      │       └── Eureka + lb://
      │
      ├── Fault Tolerance
      │       └── Resilience4j
      │
      ├── Rate Limiting
      │       └── Resilience4j
      │
      ├── Persistence
      │       ├── PostgreSQL
      │       └── MongoDB
      │
      └── Monitoring
              └── Spring Boot Actuator
```

---

# 🤝 Contributing

Contributions, improvements, and suggestions are welcome.

### Development workflow

```bash
git clone <repository-url>

cd MicroService

git checkout -b feature/your-feature

# Make your changes

git add .

git commit -m "Add your feature"

git push origin feature/your-feature
```

Then open a Pull Request.

---

# 📄 License

This project is currently provided for educational and demonstration purposes.

If you intend to distribute or use it commercially, add an appropriate license such as:

```text
MIT License
```

and create a `LICENSE` file in the repository.

---

# 👨‍💻 Author

**Tusher**

GitHub:

`https://github.com/Tusher66`

Repository:

`https://github.com/Tusher66/microservicethis`

---

## ⭐ If You Find This Project Useful

If this project helps you understand Spring Boot Microservices, consider giving the repository a ⭐ on GitHub.

---

## 📌 Important Repository Cleanup

Before pushing the final version to GitHub, remove generated/build files such as:

```text
target/
.idea/
*.iml
```

and make sure sensitive configuration values are not committed.

A suitable `.gitignore` should include:

```gitignore
# Maven
target/
!.mvn/wrapper/maven-wrapper.jar

# IntelliJ IDEA
.idea/
*.iml
*.iws
*.ipr

# Eclipse
.classpath
.project
.settings/

# VS Code
.vscode/

# Logs
*.log

# OS files
.DS_Store
Thumbs.db

# Environment / secrets
.env
*.env
application-local.yml
application-local.properties
```

---

## 🎯 Project Summary

This repository demonstrates a complete Spring Cloud microservices ecosystem with:

**API Gateway + Eureka + Config Server + JWT Authentication + OpenFeign + Resilience4j + PostgreSQL + MongoDB + Actuator**

The architecture separates business responsibilities into independently manageable services while providing common infrastructure for discovery, security, communication, configuration, resilience, and monitoring.
