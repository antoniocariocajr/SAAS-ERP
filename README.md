# Bill-ERP - SaaS ERP System

Bill-ERP is a modern, scalable SaaS Enterprise Resource Planning (ERP) system designed to manage core business operations including customers, products, categories, invoices, and sales reports.

## 🚀 Features

- **User Authentication**: Secure JWT-based authentication and authorization.
- **Customer Management**: Comprehensive CRUD operations for managing customer data.
- **Product & Category Management**: Organize products with structured categories.
- **Invoicing System**: Generate and manage business invoices.
- **Sales Reports**: Insights and analytics on sales performance.
- **Responsive Architecture**: Built with clean code principles and high scalability in mind.

## 🛠️ Tech Stack

- **Backend**: Java 17+, Spring Boot 3.x
- **Database**: MongoDB (NoSQL)
- **Security**: Spring Security, JWT (JSON Web Token)
- **Documentation**: Swagger UI / OpenAPI 3.0
- **Containerization**: Docker, Docker Compose
- **Build Tool**: Gradle

## 🏗️ Architecture

The project follows a standard multi-layer architecture with clear separation of concerns:

- **Controllers**: API entry points using RESTful principles.
- **DTOs**: Data Transfer Objects for secure and decoupled data exchange.
- **Services**: Business logic encapsulated in interfaces and implementations.
- **Domain**: Core entities and business models.
- **Infrastructure**: Database repositories and external configurations.
- **Mappers**: Dedicated logic for converting between Entities and DTOs.

For more details, see [ARCHITECTURE.md](ARCHITECTURE.md).

## 🚀 Getting Started

### Prerequisites

- Docker and Docker Compose
- Java 17 or higher
- Node.js (if frontend is present)

### Running with Docker

The easiest way to run the entire stack is using Docker Compose:

```bash
docker-compose up -d
```

### Running Backend Locally

1. Navigate to the backend directory:

   ```bash
   cd backend
   ```

2. Run the application using Gradle:

   ```bash
   ./gradlew bootRun
   ```

The API will be available at `http://localhost:8080`.
Swagger documentation can be accessed at `http://localhost:8080/swagger-ui.html`.

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
