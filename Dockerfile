# Stage 1: Build con Maven y JDK
FROM maven:3.9.0-eclipse-temurin-17 AS builder

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

# Stage 2: runtime con JRE liviano
FROM eclipse-temurin:17-jre-jammy

RUN groupadd -g 1000 location && \
    useradd -u 1000 -g location -s /bin/sh admin

USER admin:location

WORKDIR /app

COPY --from=builder /app/target/*.jar app.jar

# Limita heap y mejora performance de arranque
ENV JAVA_OPTS="-Xms256m -Xmx256m -XX:+UseG1GC -Djava.security.egd=file:/dev/./urandom"

# Usa exec para que Java sea el proceso PID 1
ENTRYPOINT ["sh", "-c", "exec java $JAVA_OPTS -jar app.jar"]