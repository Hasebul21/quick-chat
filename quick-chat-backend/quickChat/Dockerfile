# Use Maven to build the application
FROM maven:3.9.4-eclipse-temurin-17 AS build
WORKDIR /app

# Copy the Maven project files
COPY pom.xml .
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# Use a lightweight JDK image for the final container
FROM openjdk:17-jdk-alpine
MAINTAINER quickchat.com

# Install curl
RUN apk update && apk add --no-cache curl bash

# Copy the built JAR file from the build stage
COPY --from=build /app/target/quickChat-0.0.1-SNAPSHOT.jar quickChat-0.0.1-SNAPSHOT.jar

# Entry point to wait for Elasticsearch and run the application
ENTRYPOINT ["/bin/sh", "-c", "while ! curl -s http://elasticsearch:9200; do echo 'Waiting for Elasticsearch...'; sleep 2; done; exec java -jar /quickChat-0.0.1-SNAPSHOT.jar"]