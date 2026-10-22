# Copilot instructions for java-spring-lab

When you are working on this repository, please follow these instructions to ensure that your contributions align with the project's conventions and architecture. Please always respond in Italiano.

## Project overview

This repository is a minimal Spring Boot application built with Java 25 and Spring Boot 4.1.1. The app boots from `JavaSpringLabApplication`, exposes a small MVC API, and uses Actuator for health checks. The project is intentionally lightweight: there is no persistence layer, no multi-module build, and no custom lint or packaging pipeline beyond Maven.

Key source layout:
- `src/main/java/dev/frizio/lab/JavaSpringLabApplication.java` — app entry point and startup logging.
- `src/main/java/dev/frizio/lab/controller/` — HTTP endpoints.
- `src/main/java/dev/frizio/lab/service/` — business logic and runtime metadata assembly.
- `src/main/java/dev/frizio/lab/model/` — DTO/response models.
- `src/main/resources/application.yml` — app name, port, and servlet context path.
- `src/test/java/dev/frizio/lab/JavaSpringLabApplicationTests.java` — end-to-end Spring Boot tests.

## Build, test, and validation

Use Maven wrapper from the repository root:

- `./mvnw clean test` — full project test suite.
- `./mvnw -Dtest=JavaSpringLabApplicationTests test` — run the Spring Boot integration test class.
- `./mvnw -Dtest=JavaSpringLabApplicationTests#healthEndpointShouldReturnUp test` — run one test method.
- `./mvnw -Dtest=JavaSpringLabApplicationTests#runtimeInfoEndpointShouldReturnJvmAndSpringDetails test` — run the runtime-info endpoint test.
- `./mvnw spring-boot:run` — start the app locally.
- `./mvnw -q -DskipTests package` — build the jar without running tests when needed.

There is no dedicated lint script or static-analysis plugin configured in `pom.xml`; Maven `test` and `package` are the repository's main validation path.

## Architecture notes

The application follows a simple Spring Boot MVC pattern:

- `JavaSpringLabApplication` is the bootstrap class and is annotated with `@SpringBootApplication`.
- `RuntimeInfoController` exposes the `GET /runtime-info` endpoint and delegates to `RuntimeInfoService`.
- `RuntimeInfoService` creates a `RuntimeInfo` record containing JVM and Spring metadata.
- `RuntimeInfo` is a Java record, which is the primary DTO style used in this codebase for API responses.
- Actuator health is available via `/actuator/health`; because `application.yml` sets a context path of `/api/demo-services`, the effective route is `/api/demo-services/actuator/health`.
- The server is configured to run on port `18080`.

Keep changes aligned with this minimal layering: controller -> service -> model, and avoid introducing persistence or extra architectural layers unless the task explicitly requires them.

## Conventions specific to this repo

- The project package name is `dev.frizio.lab`; the Maven artifact remains `java-spring-lab`.
- The repository targets Java 25 (`<java.version>25</java.version>` in `pom.xml`), so Java 25 is the expected runtime for local development and tests.
- Prefer constructor injection (`RuntimeInfoController` and `RuntimeInfoService` are both written that way) and keep fields `final` when appropriate.
- Tests use `@SpringBootTest` with `RestTestClient` and validate HTTP behavior against the application's actual endpoints.
- When changing application configuration or routes, update the corresponding tests in `JavaSpringLabApplicationTests` because they assert the runtime metadata and health endpoint behavior directly.
- The app is a runtime introspection lab; avoid adding unrelated infrastructure, data stores, or broad frameworks unless the task explicitly requires them.
