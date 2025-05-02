FROM openjdk:17-jdk-alpine
MAINTAINER quickchat.com

# Install curl
RUN apk update && apk add --no-cache curl bash

# Copy the JAR file into the container
COPY target/quickChat-0.0.1-SNAPSHOT.jar quickChat-0.0.1-SNAPSHOT.jar

# Entry point to wait for Elasticsearch and run the application
ENTRYPOINT ["/bin/sh", "-c", "while ! curl -s http://elasticsearch:9200; do echo 'Waiting for Elasticsearch...'; sleep 2; done; exec java -jar /quickChat-0.0.1-SNAPSHOT.jar"]
