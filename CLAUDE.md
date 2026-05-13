# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

QuickChat is a real-time chat and social platform built with a full-stack architecture:

- **Backend**: Spring Boot 2.7 (Java 17) with Maven
- **Frontend**: Angular 19 with TypeScript and SCSS
- **Database**: PostgreSQL
- **Caching**: Redis
- **Search**: Elasticsearch
- **Real-time Communication**: WebSocket (STOMP protocol) with SockJS fallback
- **Deployment**: Docker Compose for local development, Docker + Railway for backend, Vercel for frontend

## Repository Structure

```
quick-chat/
├── quick-chat-backend/
│   └── quickChat/                    # Spring Boot application (Maven)
│       ├── pom.xml
│       ├── Dockerfile
│       └── src/main/java/com/hasebul/quickChat/
│           ├── QuickChatApplication.java
│           ├── controller/            # REST API endpoints
│           ├── service/               # Business logic
│           ├── model/                 # JPA entities
│           ├── dto/                   # Data transfer objects
│           ├── repository/            # JPA repositories
│           ├── config/                # Spring configurations (WebSocket, Redis, etc.)
│           ├── event/                 # Event handlers
│           └── utils/                 # Utility classes
├── quick-chat-client/                # Angular application
│   ├── angular.json
│   ├── tsconfig.json
│   ├── package.json
│   ├── Dockerfile
│   ├── vercel.json
│   └── src/app/
│       ├── app.routes.ts             # Route definitions
│       ├── app.config.ts             # Angular configuration
│       ├── app.component.ts          # Root component
│       ├── service/                   # HTTP and WebSocket services
│       ├── chat-room/                # Chat messaging feature
│       ├── chat-box/                 # Chat UI component
│       ├── home/                     # Feed/posts display
│       ├── postview/                 # Individual post view
│       ├── postedit/                 # Post creation/editing
│       ├── user-login/               # Login page
│       ├── user-registration/        # Registration page
│       ├── user-profile/             # User profile page
│       ├── user-status/              # Active users list
│       ├── profile-section/          # Profile info component
│       ├── trending-post/            # Trending posts component
│       ├── navbar/                   # Navigation component
│       └── auth-guard-service.service.ts  # Route protection
└── docker-compose.yml                # Full stack orchestration
```

## Build and Run Commands

### Full Stack (Docker Compose)
```bash
npm start              # docker compose up --build
npm run up             # docker compose up (without rebuild)
```

### Frontend (Angular)
```bash
cd quick-chat-client

# Development
npm start              # ng serve (runs on http://localhost:4200)
npm run watch         # Continuous build during development

# Production build
npm run build         # Standard production build
npm run build:vercel  # Production build for Vercel deployment

# Testing
npm test              # ng test (Karma test runner)
```

### Backend (Spring Boot)
```bash
cd quick-chat-backend/quickChat

# Development
./mvnw spring-boot:run              # Run Spring Boot application
./mvnw clean package -DskipTests    # Build JAR without tests
./mvnw clean package                # Build JAR with tests

# Testing
./mvnw test                         # Run all tests
./mvnw test -Dtest=SomeTest        # Run specific test class
```

## Key Architecture Concepts

### Real-time Communication
- **STOMP WebSocket**: All real-time features use the STOMP protocol over SockJS
- **Frontend**: `SocketService` and `StompService` manage WebSocket connections
- **Backend**: `WebSocketConfig` configures STOMP endpoints at `/ws`
- **Message Flow**: Frontend subscribes to `/topic/public` and `/user/*` destinations; publishes to `/app/*` endpoints

### Authentication
- **Session Storage**: User stored in browser's sessionStorage after login
- **Route Guards**: `AuthGuardServiceService` protects authenticated routes (home, chatroom, profile, posts)
- **Login Flow**: `UserAuthController.login()` validates credentials, frontend stores user session
- **Logout**: `AuthService.logout()` notifies backend and clears sessionStorage

### Data Persistence & Caching
- **Primary DB**: PostgreSQL via JPA/Hibernate
- **Redis Cache**: Stores active users and temporary messages with scheduled persistence
- **Elasticsearch**: Powers full-text search on chat messages and posts
- **Custom Key**: `activeuser` key tracks active users in Redis

### Post & Chat Features
- **Posts**: CRUD operations in `PostService` and `PostController`, full-text searchable via Elasticsearch
- **Chat Messages**: Real-time exchange via WebSocket, persistent storage in PostgreSQL via `ChatMessageService`
- **User Profiles**: Rich profiles with image upload (resized via Thumbnailator), professional info, portfolio links
- **Likes/Dislikes**: Post engagement tracked in database with like count updates

### Frontend Architecture
- **Standalone Components**: Uses Angular 19 standalone component API (no NgModules)
- **Reactive Forms & Template-driven**: Mix of both paradigms for form handling
- **Material Design**: Angular Material for UI (toolbar, cards, dialogs, menus)
- **Styling**: SCSS (configured in angular.json), Bootstrap 5 CSS, Material prebuilt theme
- **HTTP Client**: Configured in `app.config.ts` with dependency injection
- **Environment Files**: `environment.ts` (dev) and `environment.prod.ts` (production with Railway backend URL)

### Environment Configuration
- **Development**: Frontend localhost:4200 → Backend localhost:8080
- **Production**: Frontend on Vercel → Backend on Railway (https://quick-chat-production-3aee.up.railway.app)
- **Docker**: Services communicate via docker-compose network; PostgreSQL on 5432, Redis on 6379, Elasticsearch on 9200, Kibana on 5601

## Configuration Files

### Backend (Spring Boot)
- **pom.xml**: Maven dependencies (Elasticsearch, WebSocket, Redis, PostgreSQL, JPA, etc.)
- **application.properties**: Database, Elasticsearch, Redis, and JPA settings with environment variable overrides

### Frontend (Angular)
- **angular.json**: Build configuration, asset paths, styles (Azure Blue Material theme + Bootstrap + Toastr), output budgets
- **tsconfig.json**: TypeScript compiler options (ES2022 target, strict: false for pragmatism)
- **package.json**: Angular 19, Material 19, STOMP/SockJS for WebSocket, ngx-toastr for notifications

## Important Services & Key Files

### Frontend Services
- **AuthService** (`service/auth-service.ts`): User registration, login, logout, profile updates
- **ChatService** (`service/chat.service.ts`): Retrieve chat history between users
- **PostService** (`service/post.service.ts`): CRUD posts, search, filtering, like/dislike counts
- **SocketService** (`service/socket.service.ts`): WebSocket connection and subscription management
- **StompService** (`service/stomp.service.ts`): STOMP protocol wrapper for WebSocket
- **AuthGuardServiceService**: Protects routes requiring authentication

### Backend Controllers
- **UserAuthController**: `/api/auth/*` (register, login, logout)
- **UserController**: `/api/auth/users/*` (user CRUD, profile updates)
- **PostController**: `/api/post/*` (post CRUD, filtering, search)
- **WebSocketUserController**: `/app/*` (WebSocket message handlers for active users and chat)

### Backend Services
- **UserService**: User persistence and profile management
- **PostService**: Post operations and Elasticsearch integration
- **ChatMessageService**: Message persistence and retrieval
- **RedisService**: Caching layer for active users and temporary messages

## Deployment

### Frontend (Vercel)
- Build command: `cd quick-chat-client && npm install && npm run build:vercel`
- Output directory: `quick-chat-client/dist/quick-chat/browser`
- SPA routing configured via `vercel.json` rewrites
- Environment variable: `QUICKCHAT_API_BASE` sets production API URL

### Backend (Railway)
- Dockerfile builds Maven JAR, runs with Java 17 JRE
- Exposed port: 8080
- Elasticsearch and Redis must be provisioned separately
- Environment variables configure database and external services

## WebSocket Utilities

`quick-chat-client/src/app/ws.util.ts` is the single source of truth for WebSocket configuration:
- `apiBaseUrl()` — reads from `environment.apiBaseUrl` (switches between dev/prod via `fileReplacements` in angular.json)
- `sockJsUrl()` — returns `${apiBaseUrl}/ws`, the SockJS endpoint
- `stompConnectHeaders(userId)` — builds the STOMP connect headers; the backend maps the `userId` header to the session principal for user-scoped destinations (`/user/*`)

> `routes.guard.ts` exists in the app directory but is empty/unused — the real guard is `auth-guard-service.service.ts`.
