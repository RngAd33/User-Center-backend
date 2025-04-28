# Docker 镜像构建
FROM maven:3.9.9-jdk-21 AS builder

# Copy local code to the container image.
WORKDIR /app
COPY pom.xml .
COPY src ./src

# Build a release artifact(Only 1 time).
RUN mvn package -DskipTests

# Run the web service on container startup.
CMD ["java","-jar","/app/target/user-center-1.0-SNAPSHOT.jar","--spring.profiles.active=prod"]