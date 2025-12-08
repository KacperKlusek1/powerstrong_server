# --- Dockerfile designed to pre-build backend application
#     before running the Docker-Compose environment

# --- Java Spring Boot application builder
FROM eclipse-temurin:17-jdk-alpine AS builder

WORKDIR /app

COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .

COPY src src

# --- Adding line breaks converter (CRLF to LF)
RUN apk add --no-cache dos2unix && \
    dos2unix gradlew && \
    chmod +x gradlew

RUN ./gradlew bootJar -x test --no-daemon

FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]