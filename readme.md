# QuickChat

A real-time chat and social platform built with Spring Boot, Angular, PostgreSQL, Redis, and Elasticsearch. Features WebSocket-based messaging, user authentication, post feed with search, and full cloud deployment on Railway + Vercel.

## Features

- **Real-time Chat**: Bidirectional communication using WebSockets for instant messaging.
- **User Authentication**: Login and registration features for user management.
- **Message Search**: Powered by Elasticsearch for quick and efficient message retrieval.
- **Performance Optimization**: In-memory caching with Redis to improve application speed.
- **Responsive Design**: Built with Angular for a responsive user interface.
- **Dockerized Deployment**: Easily deployable using Docker for consistency across environments.

## Tech Stack

- **Backend**: Spring Boot
- **Frontend**: Angular, TypeScript
- **Database**: PostgreSQL
- **Search Engine**: Elasticsearch
- **Caching**: Redis
- **Deployment**: Docker

## 🛠 Tech Stack Overview

- **Elasticsearch** – Enables full-text search functionality for chat messages.  
- **WebSocket** – Powers real-time, bidirectional communication for instant messaging.  
- **Redis** – Used for caching active users and temporarily storing messages. A scheduled cron job persists these messages to the database.  
- **Spring Boot & Angular** – Backend and frontend frameworks for developing the application’s server-side logic and user interface.  
- **PostgreSQL** – Serves as the primary relational database for storing user data and chat history.


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

2. Navigate to the `backend` directory:
    ```bash
    cd backend
    ```

3. Configure application properties (e.g., database connection, Elasticsearch):
    - Open `src/main/resources/application.properties` and update the settings as needed.

4. Run the Spring Boot application:
    ```bash
    ./mvnw spring-boot:run
    ```

5. Your Spring Boot server will start, typically on port `8080`.

### Frontend Setup (Angular)

1. Navigate to the `frontend` directory:
    ```bash
    cd ../frontend
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
