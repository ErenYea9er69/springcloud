# Spring Cloud Microservices (Rayen) Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Build the complete Spring Cloud microservices suite from course slides 01-06 (Tournament service, Team service, Eureka Server, API Gateway, Config Server, external config repository, and Dockerfiles), replacing `Department` with `Tournament`, `Teacher` with `Team`, and `nadhem` with `rayen`.

**Architecture:** A distributed microservices architecture on Spring Boot 3.4.x and Spring Cloud 2024.x consisting of:
- `tournament-microservice` (port 8080): Tournament catalog service with H2 and JPA.
- `team-microservice` (port 8081): Team service communicating with Tournament service via WebClient and OpenFeign.
- `eurekaserver` (port 8761): Service registry with Eureka Server.
- `gatewayserver` (port 8888): Reactive API Gateway with dynamic discovery routing and RouteLocator.
- `configserver` (port 9999): Centralized configuration server serving configurations from classpath and `config-repo`.
- Docker containerization for deployment.

**Tech Stack:**
- Java 21, Spring Boot 3.4.1, Spring Cloud 2024.0.0
- Spring Web, Spring Data JPA, H2 In-Memory DB
- Spring Cloud Netflix Eureka Server & Client
- Spring Cloud OpenFeign, Spring WebFlux (WebClient)
- Spring Cloud Gateway (Reactive)
- Spring Cloud Config Server & Client, Spring Boot Actuator
- Docker, Eclipse Temurin 21 JDK runtime

**Spec:** The 6 course PDF documents (01 to 06) provided in workspace root.

## Global Constraints

- Author/Professor name substituted from `nadhem` / `nadhemb` to `rayen` / `rayenb`.
- Base package name: `com.rayen` (e.g., `com.rayen.tournament`, `com.rayen.team`, `com.rayen.eurekaserver`, etc.).
- Entity 1: `Tournament` on port 8080 with fields `id` (Long), `tournamentName` (String), `tournamentCode` (String).
- Entity 2: `Team` on port 8081 with fields `id` (Long), `teamName` (String), `coachName` (String), `tournamentCode` (String).
- Endpoint naming: `/api/tournaments` and `/api/teams`.
- Code and annotations must strictly reflect the patterns from the course PDFs.

## Review Focus

1. Team service endpoint `/api/teams/{id}` returns combined `APIResponseDto` with both `TeamDto` (including `tournamentName`) and `TournamentDto`.
2. Tournament lookup by code `/api/tournaments/{code}` correctly returns `TournamentDto` with status 200 OK.
3. Centralized config endpoints `/api/tournaments/version` and `/api/tournaments/author` read properties from Config Server/active profile.
4. Gateway routes correctly forward traffic to `lb://TOURNAMENT` and `lb://TEAM` via Eureka discovery.
5. Dockerfiles match Eclipse Temurin 21 JDK specifications with correct jar copy paths and entrypoints.

---

### Task 1: Parent Maven Project & Build Setup

**Files:**
- Create: `pom.xml` (root aggregator)
- Create: `.mvn/wrapper/maven-wrapper.properties`
- Create: `mvnw`, `mvnw.cmd`

**Interfaces:**
- Produces: Root multi-module build configuration linking `tournament-microservice`, `team-microservice`, `eurekaserver`, `gatewayserver`, and `configserver`.

- [ ] **Step 1: Create root Maven POM (`pom.xml`) with modules and dependency management for Spring Boot 3.4.1 and Spring Cloud 2024.0.0**
- [ ] **Step 2: Add Maven wrapper scripts (`mvnw`, `mvnw.cmd`) and wrapper properties to ensure seamless CLI builds without requiring external Maven**
- [ ] **Step 3: Verify the root project structure parses cleanly**

---

### Task 2: Tournament Microservice (`tournament-microservice`)

**Files:**
- Create: `tournament-microservice/pom.xml`
- Create: `tournament-microservice/src/main/java/com/rayen/tournament/TournamentMicroserviceApplication.java`
- Create: `tournament-microservice/src/main/java/com/rayen/tournament/entities/Tournament.java`
- Create: `tournament-microservice/src/main/java/com/rayen/tournament/repos/TournamentRepository.java`
- Create: `tournament-microservice/src/main/java/com/rayen/tournament/dto/TournamentDto.java`
- Create: `tournament-microservice/src/main/java/com/rayen/tournament/service/TournamentService.java`
- Create: `tournament-microservice/src/main/java/com/rayen/tournament/service/TournamentServiceImpl.java`
- Create: `tournament-microservice/src/main/java/com/rayen/tournament/restControllers/TournamentController.java`
- Create: `tournament-microservice/src/main/java/com/rayen/tournament/config/Configuration.java`
- Create: `tournament-microservice/src/main/resources/application.yml`
- Create: `tournament-microservice/src/main/resources/application-prod.yml`
- Create: `tournament-microservice/src/main/resources/application-qa.yml`
- Create: `tournament-microservice/Dockerfile`
- Create: `tournament-microservice/src/test/java/com/rayen/tournament/TournamentMicroserviceApplicationTests.java`

**Interfaces:**
- Consumes: None (root data provider)
- Produces:
  - `GET /api/tournaments/{code}` -> `ResponseEntity<TournamentDto>`
  - `GET /api/tournaments/version` -> `ResponseEntity<String>`
  - `GET /api/tournaments/author` -> `ResponseEntity<String>`

- [ ] **Step 1: Write unit/integration test for `TournamentController` and repository lookup**
- [ ] **Step 2: Implement `Tournament` entity with `@Id`, `@GeneratedValue`, `tournamentName`, and `tournamentCode`**
- [ ] **Step 3: Implement `TournamentRepository` with `findByTournamentCode(String code)`**
- [ ] **Step 4: Implement `TournamentDto`, `TournamentService`, and `TournamentServiceImpl`**
- [ ] **Step 5: Implement `Configuration` class (`@ConfigurationProperties(prefix = "author")`) and `TournamentController`**
- [ ] **Step 6: Implement startup `CommandLineRunner` in main class saving "Premier League" (PL) and "Champions League" (CL)**
- [ ] **Step 7: Configure `application.yml`, `application-prod.yml`, `application-qa.yml` with port 8080, H2 database, Eureka client, and Config Server client**
- [ ] **Step 8: Add `Dockerfile` based on `eclipse-temurin:21-jdk` with author `rayen.com`**
- [ ] **Step 9: Run tests to verify `tournament-microservice` passes**

---

### Task 3: Team Microservice (`team-microservice`)

**Files:**
- Create: `team-microservice/pom.xml`
- Create: `team-microservice/src/main/java/com/rayen/team/TeamMicroserviceApplication.java`
- Create: `team-microservice/src/main/java/com/rayen/team/entities/Team.java`
- Create: `team-microservice/src/main/java/com/rayen/team/repos/TeamRepository.java`
- Create: `team-microservice/src/main/java/com/rayen/team/dto/TeamDto.java`
- Create: `team-microservice/src/main/java/com/rayen/team/dto/TournamentDto.java`
- Create: `team-microservice/src/main/java/com/rayen/team/dto/APIResponseDto.java`
- Create: `team-microservice/src/main/java/com/rayen/team/service/APIClient.java`
- Create: `team-microservice/src/main/java/com/rayen/team/service/TeamService.java`
- Create: `team-microservice/src/main/java/com/rayen/team/service/TeamServiceImpl.java`
- Create: `team-microservice/src/main/java/com/rayen/team/restControllers/TeamController.java`
- Create: `team-microservice/src/main/resources/application.yml`
- Create: `team-microservice/Dockerfile`
- Create: `team-microservice/src/test/java/com/rayen/team/TeamMicroserviceApplicationTests.java`

**Interfaces:**
- Consumes: `TournamentDto` from Tournament service via `APIClient` (`@FeignClient(name = "TOURNAMENT")`) and `WebClient`
- Produces:
  - `GET /api/teams/{id}` -> `ResponseEntity<APIResponseDto>`

- [ ] **Step 1: Write integration test for `TeamController` returning `APIResponseDto`**
- [ ] **Step 2: Implement `Team` entity with `id`, `teamName`, `coachName`, and `tournamentCode`**
- [ ] **Step 3: Implement `TeamRepository` extends `JpaRepository<Team, Long>`**
- [ ] **Step 4: Implement DTOs (`TeamDto`, `TournamentDto`, `APIResponseDto`)**
- [ ] **Step 5: Implement `APIClient` feign client interface (`@FeignClient(name = "TOURNAMENT")`)**
- [ ] **Step 6: Implement `TeamService` and `TeamServiceImpl` supporting both WebClient and OpenFeign communication**
- [ ] **Step 7: Implement `TeamController` with `@GetMapping("{id}")`**
- [ ] **Step 8: Implement `CommandLineRunner` in main class with initial team data: "Arsenal", coach "Mikel Arteta", code "PL"**
- [ ] **Step 9: Configure `application.yml` with port 8081, H2, Eureka client, and Config Server client**
- [ ] **Step 10: Add `Dockerfile` based on `eclipse-temurin:21-jdk` with author `rayen.com`**
- [ ] **Step 11: Run tests to verify `team-microservice` passes**

---

### Task 4: Eureka Service Registry (`eurekaserver`)

**Files:**
- Create: `eurekaserver/pom.xml`
- Create: `eurekaserver/src/main/java/com/rayen/eurekaserver/ServiceRegistryApplication.java`
- Create: `eurekaserver/src/main/resources/application.properties`
- Create: `eurekaserver/Dockerfile`
- Create: `eurekaserver/src/test/java/com/rayen/eurekaserver/ServiceRegistryApplicationTests.java`

**Interfaces:**
- Consumes: Registration heartbeats from `TOURNAMENT`, `TEAM`, and `GATEWAYSERVER`
- Produces: Eureka registry dashboard and discovery API on port 8761

- [ ] **Step 1: Write test to verify Eureka application context loads**
- [ ] **Step 2: Implement `ServiceRegistryApplication` with `@EnableEurekaServer` and `@SpringBootApplication`**
- [ ] **Step 3: Configure `application.properties` with port 8761, `register-with-eureka=false`, `fetch-registry=false`, `spring.cloud.config.enabled=false`**
- [ ] **Step 4: Add `Dockerfile`**
- [ ] **Step 5: Run tests to verify `eurekaserver` loads**

---

### Task 5: API Gateway Server (`gatewayserver`)

**Files:**
- Create: `gatewayserver/pom.xml`
- Create: `gatewayserver/src/main/java/com/rayen/gatewayserver/GatewayserverApplication.java`
- Create: `gatewayserver/src/main/resources/application.yml`
- Create: `gatewayserver/Dockerfile`
- Create: `gatewayserver/src/test/java/com/rayen/gatewayserver/GatewayserverApplicationTests.java`

**Interfaces:**
- Consumes: Eureka service registry routes `lb://TOURNAMENT` and `lb://TEAM`
- Produces: Central entry point on port 8888 for `/api/tournaments/**` and `/api/teams/**`

- [ ] **Step 1: Write test to verify Gateway routes and application context load**
- [ ] **Step 2: Implement `GatewayserverApplication` with `RouteLocator` bean routing `/api/tournaments/**` to `lb://TOURNAMENT` and `/api/teams/**` to `lb://TEAM`**
- [ ] **Step 3: Configure `application.yml` with port 8888, discovery locator, actuator gateway endpoints, and YAML route specifications as in PDF 04**
- [ ] **Step 4: Add `Dockerfile`**
- [ ] **Step 5: Run tests to verify `gatewayserver` passes**

---

### Task 6: Spring Cloud Config Server (`configserver`) & `config-repo`

**Files:**
- Create: `configserver/pom.xml`
- Create: `configserver/src/main/java/com/rayen/configserver/ConfigserverApplication.java`
- Create: `configserver/src/main/resources/application.yml`
- Create: `configserver/src/main/resources/config/tournament.yml`
- Create: `configserver/src/main/resources/config/tournament-prod.yml`
- Create: `configserver/src/main/resources/config/tournament-qa.yml`
- Create: `configserver/src/main/resources/config/team.yml`
- Create: `configserver/src/main/resources/config/team-prod.yml`
- Create: `configserver/src/main/resources/config/team-qa.yml`
- Create: `config-repo/tournament.yml`
- Create: `config-repo/tournament-prod.yml`
- Create: `config-repo/tournament-qa.yml`
- Create: `config-repo/team.yml`
- Create: `config-repo/team-prod.yml`
- Create: `config-repo/team-qa.yml`
- Create: `configserver/Dockerfile`
- Create: `configserver/src/test/java/com/rayen/configserver/ConfigserverApplicationTests.java`

**Interfaces:**
- Consumes: Local classpath config files, file system `config-repo`, and Git URI
- Produces: Centralized config HTTP API on port 9999 for `{application}/{profile}`

- [ ] **Step 1: Write test to verify Config Server context loads and serves configuration**
- [ ] **Step 2: Implement `ConfigserverApplication` with `@EnableConfigServer` and `@SpringBootApplication`**
- [ ] **Step 3: Configure `configserver/src/main/resources/application.yml` with port 9999 and `native` profile pointing to `classpath:/config`**
- [ ] **Step 4: Create configuration files in `configserver/src/main/resources/config/` with Rayen author metadata and build versions**
- [ ] **Step 5: Create external file system repository folder `config-repo/` containing configuration files**
- [ ] **Step 6: Add `Dockerfile`**
- [ ] **Step 7: Run tests to verify `configserver` passes**

---

### Task 7: Full System Verification & End-to-End Build

**Files:**
- Modify: `README.md` (comprehensive instructions for building, running, testing with curl/Postman, and Docker commands)

- [ ] **Step 1: Run full Maven build (`mvn clean package -DskipTests` or test run) across all modules**
- [ ] **Step 2: Verify Docker build commands and Dockerfile compliance**
- [ ] **Step 3: Document complete testing guide in `README.md` replicating all verification steps from slides 01-06**
- [ ] **Step 4: Commit changes to Git**

