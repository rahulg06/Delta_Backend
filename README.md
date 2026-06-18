# Deltaclause Intelligent Project Academy - Enterprise Spring Boot Backend

A secure, enterprise-ready, high-throughput full-stack backend module built using **Spring Boot 3.2+**, **Hibernate JPA**, **MySQL**, **Redis**, and **Spring Security 6.x** featuring JSON Web Token (JWT) authorization and secure OTP-based student signup validation.

---

## 🛠️ Technological Architecture

*   **JVM Engine**: Java 17 / Spring Boot 3.2.x Starter Lifecycle.
*   **Database Access Layer**: Hibernate Core 6.x + Spring Data JPA.
*   **Persistent Storage**: MySQL Server 8.x (Auto-indexing, DDL Schema generation, long-text storage for transaction receipts).
*   **High-Speed Cache Store**: Redis Server (Caches random 6-digit invitation tokens utilizing TTL expiries of 5 minutes).
*   **Security clearance**: Spring Security 6.x + Stateless JWT Filter Pipeline (HMAC-SHA512 cryptographic verification keys).
*   **Mail Dispatcher**: JavaMailSender + SMTP integration (HTML email dispatch supporting customizable corporate themes).

---

## 📂 Project Directory Structure

```text
/backend-springboot
├── pom.xml                                  # Maven dependencies descriptors
├── README.md                                # Deployment and developer guidelines
└── src
    └── main
        ├── java
        │   └── com
        │       └── deltaclause
        │           └── academy
        │               ├── Application.java # Main Runner Bootstrapping Class
        │               ├── config           # Security Filters, CORS, and Redis Configurations
        │               │   ├── JwtAuthenticationFilter.java
        │               │   ├── JwtTokenProvider.java
        │               │   ├── RedisConfig.java
        │               │   └── WebSecurityConfig.java
        │               ├── controller       # Rest Endpoint Controllers (Public vs. Client Shielded)
        │               │   ├── AuthController.java
        │               │   ├── CertificateController.java
        │               │   ├── EnrollmentController.java
        │               │   └── InternshipController.java
        │               ├── domain           # Hibernate Entities (Mapped directly to MySQL schemas)
        │               │   ├── Certificate.java
        │               │   ├── Enrollment.java
        │               │   ├── Internship.java
        │               │   ├── Referral.java
        │               │   ├── Role.java
        │               │   ├── SupportTicket.java
        │               │   ├── SupportTicketReply.java
        │               │   └── User.java
        │               ├── dto              # Data Transfer Objects
        │               │   ├── AuthResponseDto.java
        │               │   ├── OtpRequestDto.java
        │               │   ├── OtpVerificationDto.java
        │               │   └── SignInRequestDto.java
        │               ├── repository       # Spring Data JPA Interfaces
        │               └── service          # Transactional Services and OTP Generators
        │                   ├── EmailService.java
        │                   ├── OtpService.java
        │                   └── UserService.java
        └── resources
            └── application.properties       # Complete system credentials config file
```

---

## 🚀 Step-by-Step Deployment Guide

### Phase 1: Set Up MySQL Schema
Create a new MySQL schema locally or in the cloud. Under your database server CLI, execute:
```sql
CREATE DATABASE deltaclause_academy CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

### Phase 2: Start Redis Cache
Ensure a local Redis server is executing on host port `6379`.
```bash
# via Docker
docker run -d --name dc-redis -p 6379:6379 redis:alpine
```

### Phase 3: Update `application.properties`
Modify `/src/main/resources/application.properties` with your credentials:
```properties
spring.datasource.username=root
spring.datasource.password=your_mysql_secure_password
spring.mail.username=your_gmail_username@gmail.com
spring.mail.password=your_google_app_password
```

### Phase 4: Compile & Run
Execute standard Maven lifecycle scripts to build and start the server:
```bash
# Clean project and compile binaries
mvn clean install

# Launch Spring Boot Server
mvn spring-boot:run
```
The server starts on host port `8080`, logging active context pipelines, Hibernate auto-indexes, and SMTP connections.

---

## 🔒 Security & Endpoint Mappings

### 📨 1. JWT & OTP Authentication Flow (`/api/auth/*`)
*   `POST /api/auth/signup/otp-request`: Matches client emails to prevent duplicate registrants, creates a secure random 6-digit OTP, stores it inside Redis with a **5-minute TTL**, and dispatches a themed HTML validation email using the Spring Mail engine.
*   `POST /api/auth/signup/otp-verify`: Validates key-value logs from the Redis store. If matched, initiates transaction, persists student entity records, issues a secure JWT token, and logs out the generated authorization details.
*   `POST /api/auth/signin`: Validates credentials, checks secure passcodes, and issues dynamic JWT tokens.

### 📝 2. Internships and Courses (`/api/internships/*`)
*   `GET /api/internships/catalog`: [Public] Retrieves the active academy course and internship lists.
*   `POST /api/internships/admin/create`: [Authority: ROLE_ADMIN] Creates and populates new curriculum listings.

### 💳 3. Enrollments & Submissions (`/api/enrollments/*`)
*   `GET /api/enrollments/my`: [Authority: ROLE_STUDENT] Custom tracker listing of the user's courses, status codes, and task progress.
*   `POST /api/enrollments/apply`: [Authority: ROLE_STUDENT] Applies for a course, sending UPI records to database.
*   `POST /api/enrollments/{id}/submit`: [Authority: ROLE_STUDENT] Submits codebase repositories for evaluator verification.
*   `POST /api/enrollments/admin/{id}/evaluate`: [Authority: ROLE_ADMIN] Grants completion status or triggers re-do states.

### 🎓 4. Verified Certifications (`/api/certificates/*`)
*   `GET /api/certificates/verify/{id}`: [Public] Lookups and verifies authenticity seals of student certificates.
*   `POST /api/certificates/admin/issue`: [Authority: ROLE_ADMIN] Digitally signs and registers certificate records.
