# Architecture Standards

This document outlines the architectural standards for the SAAS-ERP system. All new development must adhere to these guidelines to ensure consistency, maintainability, and scalability.

## 1. Data Transfer Objects (DTOs)

All data entering or leaving the system boundary (Controllers) must be encapsulated in DTOs. Entities should never be exposed directly in API responses or accepted in API requests.

- **Request DTOs**: Use for data coming into the API (e.g., `CategoryCreateDTO`, `CategoryUpdateDTO`).
- **Response DTOs**: Use for data returned by the API (e.g., `CategoryResponse`).
- **Immutability**: Prefer Java `record` for DTOs to ensure immutability and reduce boilerplate.

## 2. Service Layer

The business logic must be encapsulated in the Service Layer.

- **Interface-Implementation Pattern**: Every service must have an interface (e.g., `CategoryService`) and an implementation class (e.g., `CategoryServiceImpl`).
- **Dependency Injection**: Inject the interface, not the implementation, into Controllers or other Services.

## 3. Exception Handling

Exceptions must be handled globally and gracefully.

- **Custom Exceptions**: Create specific exceptions for business rules (e.g., `CategoryNotFoundException`, `BusinessException`).
- **Global Exception Handler**: Use `@ControllerAdvice` to catch exceptions globally and return standard `ErrorResponse` objects with appropriate HTTP status codes.

## 4. Mappers

Conversion between DTOs and Entities should be handled by dedicated Mapper classes.

- **Location**: `com.billerp.service.mapper`
- **Pattern**: Create a Component (e.g., `CategoryMapper`) with methods like `toResponse(Entity)` and `toModel(DTO)`.
- **Avoid Logic**: Mappers should only handle data transformation, not business logic.

## 5. Validation

Validation should be enforced at the entry point and within the service layer.

- **Bean Validation**: Use standard annotations (`@NotNull`, `@Size`, etc.) on DTOs.
- **Business Validation**: Use dedicated Validator classes (e.g., `CategoryValidator`) or methods within the Service for complex business rules (e.g., checking for duplicates).
