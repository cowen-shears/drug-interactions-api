// speckit.constitution - Core development principles

/* Architecture */
1. Follow SOLID principles and hexagonal architecture
    - Services: Use ports/adapters pattern for external dependencies
    - Domains: Keep pure business logic isolated from infrastructure
    - Dependencies: Flow inward via interfaces (ports)

/* API Design */
2. REST API Standards
    - Use UUIDs for resource identifiers
    - Implement Bean Validation for request DTOs
    - Include accessibility hints in OpenAPI docs
    - Return meaningful HTTP status codes
    - API versioning in URL path (/v1/...)

/* Implementation */
3. Spring Web Practices
    - Use WebClient instead of RestTemplate
    - Configure timeouts and circuit breakers
    - Handle errors consistently using @ControllerAdvice

/* Testing */
4. Test Coverage Requirements
    - Service Layer: 80%+ line and branch coverage
    - Unit Tests: Mockito & AssertJ
    - Integration Tests: WireMock for external services
    - Test naming: given_when_then pattern

/* Security */
5. Security Baseline
    - Input validation on all endpoints
    - HTTPS only in production
    - No sensitive data in logs
    - Proper secret management

/* Build & Style */
6. Build Standards
    - Reproducible builds with locked dependencies
    - Spotless for code formatting
    - No warnings policy
    - Ban list: raw types, deprecated APIs

/* Documentation */
7. Documentation Requirements
    - OpenAPI annotations with examples
    - Accessibility considerations in API docs
    - Architecture decision records (ADRs)
    - Up-to-date README.md
