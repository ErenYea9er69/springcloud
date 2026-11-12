# Spring Cloud Course Sections Execution & Verification Plan

**Goal:** Execute, verify, and document all 13 sections/runbooks from `Spring_Cloud_Sections_Guide.html`, ensuring all microservices, Eureka discovery, Spring Cloud Gateway, Config Server, Docker builds, MySQL persistence, Spring Cloud Bus with RabbitMQ, Resilience4J circuit breakers, Keycloak security, and RabbitMQ event-driven asynchronous messaging work as expected.

## Environment Baseline
- **OS:** Windows 11
- **Java:** OpenJDK 21.0.13
- **Maven:** Apache Maven 3.9.6
- **Docker:** Engine 29.9.2 (Active)
- **Base Directory:** `C:\Users\hp\Desktop\Spring Cloud`

---

## Task Checklist

- [x] **Section 1 (Runbook 1 / PDF 01): Create the Microservices (SECTION_02_CREA_MS)**
  - [x] 1.1 Start Tournament microservice on port 8080 (`mvn spring-boot:run`)
  - [x] 1.2 Test Tournament endpoints: `http://localhost:8080/api/tournaments/PL` and `http://localhost:8080/api/tournaments/CL`
  - [x] 1.3 Start Team microservice on port 8081 (`mvn spring-boot:run`)
  - [x] 1.4 Test Team endpoint: `http://localhost:8081/api/teams/1`
  - [x] 1.5 Terminate Section 1 processes and free ports 8080 & 8081

- [x] **Section 2 (Runbook 2 / PDF 02): Service-to-Service Communication (SECTION_03_COMM_MS)**
  - [x] 2.1 Start Tournament microservice on port 8080
  - [x] 2.2 Start Team microservice on port 8081 (uses Feign/WebClient to fetch Tournament)
  - [x] 2.3 Test Team endpoint: `http://localhost:8081/api/teams/1` (verifying combined Team + Tournament DTO)
  - [x] 2.4 Test Tournament endpoint directly: `http://localhost:8080/api/tournaments/PL`
  - [x] 2.5 Terminate Section 2 processes and free ports

- [x] **Section 3 (Runbook 3 / PDF 03): Service Discovery with Eureka (SECTION_04_EUREKA)**
  - [x] 3.1 Start Eureka Server on port 8761 (`mvn -pl eurekaserver spring-boot:run`)
  - [x] 3.2 Verify Eureka dashboard: `http://localhost:8761/`
  - [x] 3.3 Start Tournament instance 1 on port 8080
  - [x] 3.4 Build and start Tournament instance 2 on port 8082
  - [x] 3.5 Start Team microservice on port 8081
  - [x] 3.6 Test Team endpoint: `http://localhost:8081/api/teams/1` resolving Tournament via Eureka
  - [x] 3.7 Terminate Section 3 processes and free ports

- [x] **Section 4 (Runbook 4 / PDF 04): API Gateway (SECTION_05_GATEWAY)**
  - [x] 4.1 Start Eureka Server on port 8761
  - [x] 4.2 Start Tournament on port 8080
  - [x] 4.3 Start Team on port 8081
  - [x] 4.4 Start Gateway Server on port 8888
  - [x] 4.5 Test Eureka registry on port 8761
  - [x] 4.6 Test Gateway route to Tournament: `http://localhost:8888/api/tournaments/PL`
  - [x] 4.7 Test Gateway route to Team: `http://localhost:8888/api/teams/1`
  - [x] 4.8 Terminate Section 4 processes and free ports

- [x] **Section 5 (Runbook 5 / PDF 05): Centralized Configuration (SECTION_06_CONFIGSERVER)**
  - [x] 5.1 Start Config Server on port 9999
  - [x] 5.2 Start Eureka Server on port 8761
  - [x] 5.3 Start Tournament on port 8080
  - [x] 5.4 Test Config Server profiles: `http://localhost:9999/tournament/prod` and `http://localhost:9999/team/prod`
  - [x] 5.5 Test Tournament property injection: `http://localhost:8080/api/tournaments/version` and `http://localhost:8080/api/tournaments/author`
  - [x] 5.6 Terminate Section 5 processes and free ports

- [x] **Section 6 (Runbook 6 / PDF 06): Standalone Docker Image (SECTION_07_Intro_to_Docker)**
  - [x] 6.1 Package Tournament JAR: `mvn clean package -DskipTests`
  - [x] 6.2 Build Docker image: `rayen/tournament:v1`
  - [x] 6.3 Run container `tournament-container` mapping port 8083:8080
  - [x] 6.4 Test container endpoints: `http://localhost:8083/api/tournaments/PL` and `http://localhost:8083/api/tournaments/CL`
  - [x] 6.5 Stop and remove `tournament-container`

- [x] **Section 7 (Runbook 7 / PDF 07): MySQL in Docker (SECTION_08_Mysql_As_Docker)**
  - [x] 7.1 Package Tournament JAR
  - [x] 7.2 Create network `my-net` and start MySQL container `mysqldb` on port 3306
  - [x] 7.3 Wait for MySQL healthy ping
  - [x] 7.4 Build `rayen/tournament-mysql:v1` Docker image
  - [x] 7.5 Run `tournaments-ms` container on `my-net` (port 8080:8080)
  - [x] 7.6 Verify `http://localhost:8080/api/tournaments/PL` and inspect MySQL tables via `docker exec`
  - [x] 7.7 Test full stack via Docker Compose: `docker compose up --build -d`
  - [x] 7.8 Verify Compose endpoint `http://localhost:8080/api/tournaments/PL`
  - [x] 7.9 Clean up Compose stack and standalone containers

- [x] **Section 8 (Runbook 8 / PDF 08): Dockerize Full Stack with Compose (SECTION_09_DOCKERIZE_VOS_MS)**
  - [x] 8.1 Build all JARs: `mvn clean package -DskipTests`
  - [x] 8.2 Start stack via Docker Compose (`eurekaserver`, `configserver`, `tournaments-ms`, `tournaments-ms2`, `teams-ms`, `gatewayserver`)
  - [x] 8.3 Verify Eureka dashboard (8761) and Config Server (9999)
  - [x] 8.4 Verify Tournament 1 (8080), Tournament 2 (8084), Gateway Tournament route (8888), Gateway Team route (8888)
  - [x] 8.5 Test failover: stop `tournaments-ms` and verify Gateway behavior during instance downtime
  - [x] 8.6 Restart `tournaments-ms` and clean down stack

- [x] **Section 9 (Runbook 9 / PDF 09): Spring Cloud Bus with RabbitMQ (SECTION_10_Cloud Bus)**
  - [x] 9.1 Build all JARs: `mvn clean package -DskipTests`
  - [x] 9.2 Launch Compose stack with RabbitMQ
  - [x] 9.3 Verify RabbitMQ UI (15672) and initial author endpoint `http://localhost:8081/api/teams/author`
  - [x] 9.4 Update `author.name` in `team-prod.yml`
  - [x] 9.5 Trigger bus refresh via POST `http://localhost:8080/actuator/busrefresh`
  - [x] 9.6 Verify updated configuration on `http://localhost:8081/api/teams/author` without restarting Team
  - [x] 9.7 Clean down stack and revert configuration file change

- [x] **Section 10 (Runbook 10 / PDF 10): Resilience4J Circuit Breaker & Fallbacks (SECTION_11_Resilience4J)**
  - [x] 10.1 Build and launch Compose stack
  - [x] 10.2 Verify healthy baseline: `http://localhost:8081/api/teams/1` and `http://localhost:8888/api/teams/1`
  - [x] 10.3 Stop Tournament instances: `docker compose stop tournaments-ms tournaments-ms2`
  - [x] 10.4 Verify Team fallback returns `tournamentName: NOT AVAILABLE`
  - [x] 10.5 Check circuit breaker actuator state and events
  - [x] 10.6 Restart Tournament and Team, test recovery
  - [x] 10.7 Stop Team and verify Gateway circuit breaker state turns OPEN (100% failure rate)
  - [x] 10.8 Clean down stack

- [x] **Section 11 (Runbook 11 / PDF 11): Keycloak Security on Gateway (SECTION_12_Securing_gateway)**
  - [x] 11.1 Build and launch Compose stack (Keycloak on 8090, Gateway on 8888)
  - [x] 11.2 Configure Keycloak: create `ADMIN` role, create client `tournament-test` with service accounts
  - [x] 11.3 Obtain access token via OAuth2 Client Credentials grant
  - [x] 11.4 Test authenticated request to `http://localhost:8888/api/tournaments/PL` (200 OK)
  - [x] 11.5 Test unauthenticated request without token (401 Unauthorized)
  - [x] 11.6 Test non-admin token on Tournament route (403 Forbidden) and Team route (allowed)
  - [x] 11.7 Clean down stack

- [x] **Section 12 (Runbook 12 / PDF 12): Secure Services in Docker with Persistence (SECTION_13_Securing_Docker)**
  - [x] 12.1 Build and launch Section 13 stack
  - [x] 12.2 Configure Keycloak client `tournament-app` with role `ADMIN`
  - [x] 12.3 Verify token generation and authenticated Gateway routing
  - [x] 12.4 Verify persistence: `docker compose down` then `docker compose up -d`
  - [x] 12.5 Confirm Keycloak data and token validation persist across restart
  - [x] 12.6 Clean down stack

- [x] **Section 13 (Runbook 13 / PDF 13): RabbitMQ Event-Driven Microservices (SECTION_14_Event_RabbitMQ)**
  - [x] 13.1 Build and launch Section 14 stack
  - [x] 13.2 Verify RabbitMQ (15672) and Keycloak token
  - [x] 13.3 Send POST `http://localhost:8888/api/teams/create` to trigger asynchronous message creation
  - [x] 13.4 Verify message flow: GET `http://localhost:8888/api/teams/2` returns `communicationDone: true`
  - [x] 13.5 Clean down stack
