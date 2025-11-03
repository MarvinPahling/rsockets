# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

This is a full-stack RSocket demonstration application with a Spring Boot backend and a React + TanStack frontend. The project showcases RSocket protocol for reactive communication between client and server.

## Project Structure

```
rsockets/
├── backend/          # Spring Boot backend with RSocket support
└── frontend/         # React frontend with TanStack Router and Query
```

## Backend (Spring Boot + RSocket)

### Technology Stack
- Java 25
- Spring Boot 3.5.7
- RSocket (WebSocket transport)
- Maven for dependency management
- SpringDoc OpenAPI for REST API documentation

### Build and Run Commands

```bash
# Build the backend
cd backend
./mvnw clean install

# Run the backend
./mvnw spring-boot:run

# Run tests
./mvnw test
```

### Backend Architecture

**Package Structure:**
- `de.marvin.rsockets.backend.controller` - Controllers for both REST and RSocket endpoints
- `de.marvin.rsockets.backend.services` - Business logic layer
- `de.marvin.rsockets.backend.domain` - Domain models (Java records)
- `de.marvin.rsockets.backend.config` - Configuration classes

**Key Components:**

1. **PlayerController** (`controller/PlayerController.java`) - RESTful HTTP endpoints for CRUD operations on players
   - Exposes endpoints at `/api/players`
   - Uses in-memory storage with AtomicLong for ID generation
   - Fully documented with OpenAPI annotations

2. **PlayerRSocketController** (`controller/PlayerRSocketController.java`) - RSocket endpoints for reactive communication
   - `players.list` - Request-Response: Returns all players
   - `players.add` - Request-Response: Adds a player
   - `players.stream` - Request-Stream: Streams player updates every 5 seconds with random players

3. **PlayerService** (`services/PlayerService.java`) - Shared service managing player state
   - Generates random player usernames from adjectives and names
   - Maintains in-memory player list

**Configuration:**
- RSocket server runs on port 7001 with WebSocket transport at `/rsocket`
- REST server runs on port 8080 (default)
- Swagger UI available at http://localhost:8080/swagger-ui.html

## Frontend (React + TanStack)

### Technology Stack
- React 19.2.0
- TanStack Router 1.132.0 (file-based routing)
- TanStack Query 5.66.5 (data fetching/caching)
- Vite 7.1.7 (build tool)
- TypeScript 5.7.2
- Tailwind CSS 4.0.6
- Biome 2.2.4 (linting/formatting)
- Vitest 3.0.5 (testing)

### Build and Run Commands

```bash
# Install dependencies
cd frontend
npm install

# Run development server (port 3000)
npm run dev

# Build for production
npm run build

# Preview production build
npm run serve

# Run tests
npm run test

# Format code
npm run format

# Lint code
npm run lint

# Check (lint + format)
npm run check
```

### Frontend Architecture

**Routing:**
- File-based routing using TanStack Router
- Routes defined in `src/routes/` directory
- Auto-generated route tree at `src/routeTree.gen.ts`
- Root route at `src/routes/__root.tsx` includes Header and TanStack DevTools

**State Management:**
- TanStack Query for server state management
- Query client configured in `src/integrations/tanstack-query/root-provider.tsx`
- Router context includes QueryClient for integration

**Key Configuration:**
- Vite config at `frontend/vite.config.ts` with TanStack Router plugin
- Path alias `@/` points to `src/` directory
- Router plugins: TanStack Router DevTools, TanStack Query DevTools

**Entry Point:**
- `src/main.tsx` - Creates router with context and renders app
- Router configured with:
  - Intent-based preloading
  - Scroll restoration
  - Structural sharing enabled

## Development Workflow

### When working on the backend:
1. Always use Maven wrapper (`./mvnw`) instead of global Maven
2. Backend uses Java records for domain models - they are immutable
3. RSocket controllers use `@MessageMapping` instead of `@RequestMapping`
4. Both controllers maintain separate in-memory state (intentional for demo purposes)
5. OpenAPI documentation should be updated when changing REST endpoints

### When working on the frontend:
1. Use Biome for code formatting and linting (not ESLint/Prettier)
2. Routes are file-based - create files in `src/routes/` to add routes
3. Router requires explicit type safety - always update router types when modifying context
4. Use the `@/` alias for imports from `src/`
5. TanStack Query DevTools and Router DevTools are included in development mode

### Running the full stack:
1. Start backend first: `cd backend && ./mvnw spring-boot:run`
2. Start frontend: `cd frontend && npm run dev`
3. Access frontend at http://localhost:3000
4. Access backend REST API at http://localhost:8080
5. Access Swagger UI at http://localhost:8080/swagger-ui.html
6. RSocket endpoint at ws://localhost:7001/rsocket

## Testing

### Backend:
- Uses JUnit and Spring Boot Test
- Reactor Test for RSocket testing
- Run with: `./mvnw test`

### Frontend:
- Uses Vitest with React Testing Library
- JSDOM for browser environment
- Run with: `npm run test`
