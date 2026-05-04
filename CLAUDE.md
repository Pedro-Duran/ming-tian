# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

**Mientien** is a Spring Boot application for learning Mandarin Chinese. It translates Portuguese text to Chinese (with Pinyin and character-level vocabulary breakdown) and generates TTS audio via OpenAI.

## Commands

```bash
# Build
mvn clean package

# Run
mvn spring-boot:run

# Run tests
mvn test

# Run a single test class
mvn test -Dtest=MientienApplicationTests
```

## Prerequisites

The app requires a running Oracle XE container and a `.env` file at the project root.

**Start Oracle DB:**
```bash
docker run -d --name oracle-db -p 1521:1521 -e ORACLE_PWD=mientien123 \
  container-registry.oracle.com/database/express:21.3.0-xe
```

**Required `.env` variables:**
```
OPENAI_API_KEY=<key>
JWT_SECRET=<base64-encoded secret, min 256 bits>
```
The dev default for `JWT_SECRET` is already set in `application-env.properties`. Replace it before any non-local deployment.

## Architecture

Layered Spring Boot app: **Controller → Service → Repository → Oracle DB (JPA + Flyway)**

### Key Flows

1. **Auth** (`POST /auth/register`, `POST /auth/login`): `AuthController` → `AuthService` → returns a JWT. Register hashes the password with BCrypt; login uses `AuthenticationManager`. All other endpoints require `Authorization: Bearer <token>`.

2. **Translation** (`POST /api/traduzir`): `TraducaoController` → `OpenAiDiario` (uses Spring AI `ChatClient`) → saves a `Dia` entity with Portuguese/Chinese text, Pinyin, JSON word map, and audio path.

3. **Exercises** (`GET /exercicios/palavrasPorDia`): `ExerciciosController` → `ExerciciosService` → native SQL queries via `ExerciciosRepositoryImpl`.

### Notable Patterns

- **JWT auth**: `JwtAuthFilter` (extends `OncePerRequestFilter`) extracts `Authorization: Bearer` headers, validates the token via `JwtService`, and sets the `SecurityContext`. `SecurityConfig` wires it stateless (no sessions) and permits only `/auth/**` without a token.
- **OpenAI access**: Spring AI `ChatClient` (via `SpringAIConfig`) for translation and TTS. Uses `OPENAI_API_KEY`.
- **Environment loading**: `DotenvEnvironmentPostProcessor` reads `.env` into Spring's environment before `application.properties` is processed. Variables from `.env` override nothing — they are additive.
- **JSON in Oracle CLOB**: `PalavrasTraduzidasConverter` serializes `Map<String,String>` (character → Portuguese) to/from JSON stored in the `PALAVRAS_TRADUZIDAS` CLOB column.
- **Repository split**: `DiaRepository` (JPA interface) + `DiaRepositoryImpl` (static native SQL constants). Custom queries use named parameters with `@Query` or `EntityManager`.
- **ExerciciosServiceUtils**: Contains character-grouping logic that merges adjacent characters sharing the same meaning — used before persisting word translations.

### Database

Schema managed by Flyway migrations in `src/main/resources/db/migration/`.

```
DIA: ID | TEXTO_PT | TEXTO_ZH | PING_YING | CAMINHO_AUDIO | DATA | PALAVRAS_TRADUZIDAS (CLOB)
```

Connection: `jdbc:oracle:thin:@localhost:1521/XEPDB1`, user `system`, password `mientien123`.