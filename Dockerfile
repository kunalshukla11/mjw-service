FROM maven:3.9-eclipse-temurin-21 AS builder
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src src
RUN mvn clean package -DskipTests
RUN java -Djarmode=layertools -jar target/mjw-service-0.0.1-SNAPSHOT.jar extract

FROM eclipse-temurin:21-jre-alpine
RUN addgroup -S docker && adduser -S usr_mjw_be -G docker
USER usr_mjw_be
WORKDIR /app
COPY --from=builder app/dependencies/ ./
COPY --from=builder app/spring-boot-loader/ ./
COPY --from=builder app/snapshot-dependencies/ ./
COPY --from=builder app/application/ ./
ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]