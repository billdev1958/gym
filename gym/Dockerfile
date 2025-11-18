FROM eclipse-temurin:25.0.1_8-jdk-noble AS builder

WORKDIR /app

COPY mvnw .
COPY .mvn .mvn/

COPY pom.xml .

COPY src /app/src

RUN chmod +x mvnw

RUN ./mvnw package -DskipTests

FROM eclipse-temurin:25.0.1_8-jre-noble AS final-jre-stage

WORKDIR /app

COPY --from=builder /app/target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
