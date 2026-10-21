FROM maven:3.9-eclipse-temurin-25 AS build

WORKDIR /workspace

COPY pom.xml .
COPY src ./src

RUN mvn -B -ntp -DskipTests \
    -Dspring-boot.repackage.mainClass=com.example.java_spring_lab.JavaSpringLabApplication \
    package

FROM eclipse-temurin:25-jre-alpine

WORKDIR /app

RUN addgroup -S spring && adduser -S spring -G spring

COPY --from=build --chown=spring:spring /workspace/target/*.jar app.jar

USER spring:spring

EXPOSE 18080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
