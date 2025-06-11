# syntax=docker/dockerfile:1

# === Stage 1: Build dependencies ===
FROM eclipse-temurin:17-jdk-jammy AS builder

WORKDIR /build

# Copy Maven wrapper & config
COPY --chmod=0755 mvnw mvnw
COPY .mvn/ .mvn/
COPY pom.xml .

# Pre-download dependencies
RUN --mount=type=cache,target=/root/.m2 ./mvnw dependency:go-offline -B -DskipTests

# === Stage 2: Package application ===
FROM builder AS package

COPY src/ src/


# Add encoding config
ENV MAVEN_OPTS="-Dfile.encoding=UTF-8"

RUN --mount=type=cache,target=/root/.m2 \
    ./mvnw clean package -DskipTests -B && \
    ARTIFACT=$(./mvnw help:evaluate -Dexpression=project.build.finalName -q -DforceStdout) && \
    mv target/$ARTIFACT.jar target/app.jar

# === Stage 3: Extract Spring Boot layers ===
FROM package as extract

RUN java -Djarmode=layertools -jar target/app.jar extract --destination target/extracted

# === Stage 4: Final runtime image ===
FROM eclipse-temurin:17-jre-jammy AS final

ARG UID=10001
RUN adduser --disabled-password --gecos "" --home "/nonexistent" \
    --shell "/sbin/nologin" --no-create-home --uid "${UID}" appuser
USER appuser

WORKDIR /app

COPY --from=extract /build/target/extracted/dependencies/ ./
COPY --from=extract /build/target/extracted/spring-boot-loader/ ./
COPY --from=extract /build/target/extracted/snapshot-dependencies/ ./
COPY --from=extract /build/target/extracted/application/ ./

EXPOSE 9876
ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]
