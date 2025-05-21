FROM maven:3.9.6-eclipse-temurin-17-alpine AS builder

WORKDIR /app

COPY . .

RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jre-alpine

RUN adduser -h /home/decepticons -s /bin/sh -D decepticons

WORKDIR /home/decepticons

COPY --from=builder /app/target/*.jar app.jar

RUN chown decepticons:decepticons app.jar

USER decepticons

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]