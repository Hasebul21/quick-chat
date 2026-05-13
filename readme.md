# QuickChat

A real-time chat and social platform built with Spring Boot, Angular, PostgreSQL, Redis, and Elasticsearch. Features WebSocket-based messaging, user authentication, post feed with search, and full cloud deployment on Railway + Vercel.

## Features

- **Real-time Chat**: Bidirectional communication using WebSockets (STOMP over SockJS) for instant messaging.
- **User Authentication**: Login and registration with session-based auth.
- **Post Feed**: Create, like, and browse posts with a social feed interface.
- **User Profiles**: Rich profiles with image upload, bio, and portfolio links.
- **Message & Post Search**: Powered by Elasticsearch for fast full-text retrieval.
- **Performance Optimization**: In-memory caching with Redis to improve application speed.
- **Responsive Design**: Built with Angular Material and Bootstrap for a responsive UI.
- **Cloud Deployment**: Backend on Railway, frontend on Vercel.

## Tech Stack

| Layer | Technology |
|---|---|
| Backend | Spring Boot 2.7 (Java 17) |
| Frontend | Angular 19, TypeScript, SCSS |
| Database | PostgreSQL |
| Search | Elasticsearch |
| Caching | Redis |
| Real-time | WebSocket (STOMP / SockJS) |
| Deployment | Railway (backend), Vercel (frontend), Docker Compose (local) |


## Requirements

Before you start, ensure you have the following installed on your machine:

- Java 17 or higher
- Node.js and npm
- Docker
- PostgreSQL
- Elasticsearch
- Redis

## Setup Instructions

### Backend Setup (Spring Boot)

1. Clone the repository:
    ```bash
    git clone https://github.com/Hasebul21/quick-chat.git
    cd quick-chat
    ```

2. Navigate to the backend directory:
    ```bash
    cd quick-chat-backend/quickChat
    ```

3. Configure application properties (e.g., database connection, Elasticsearch):
    - Open `src/main/resources/application.properties` and update the settings as needed.

4. Run the Spring Boot application:
    ```bash
    ./mvnw spring-boot:run
    ```

5. Your Spring Boot server will start, typically on port `8080`.

### Frontend Setup (Angular)

1. Navigate to the frontend directory:
    ```bash
    cd quick-chat-client
    ```

2. Install dependencies:
    ```bash
    npm install
    ```

3. Start the Angular development server:
    ```bash
    ng serve
    ```

4. Your Angular app will be available at `http://localhost:4200`.

### Docker Setup

To run the entire application using Docker, follow these steps:

1. Ensure Docker is running.

2. Build the Docker images:
    ```bash
    docker-compose build
    ```

3. Start the application containers:
    ```bash
    docker-compose up
    ```

This will spin up the necessary containers for Spring Boot, PostgreSQL, Redis, and Elasticsearch.

## Usage

- **Login/Registration**: Users can sign up and log in using their email and password.
- **Messaging**: Once logged in, users can send and receive real-time messages in the chat interface.
- **Search**: Elasticsearch allows fast searching through messages.
- **Performance**: Redis is used for caching messages and improving response time.

## API Endpoints

### Authentication

- **POST /api/auth/register**: Register a new user
- **POST /api/auth/login**: Log in with user credentials

### Chat

- **GET /api/chats/{userId}/{receiverId}**: Get chat history between two users.
- **POST /api/chats/send**: Send a message to a user.

### Posts

- **GET /api/post/all**: Get all posts.
- **POST /api/post/create**: Create a new post.
- **PUT /api/post/update/{id}**: Update a post.
- **DELETE /api/post/delete/{id}**: Delete a post.
- **GET /api/post/search?query=**: Full-text search posts via Elasticsearch.
