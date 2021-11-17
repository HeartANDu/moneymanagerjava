FROM maven:3.6.3-openjdk-11-slim as build
COPY . /app
WORKDIR /app
RUN mvn clean package -DskipTests

FROM openjdk:11.0.12-jre-slim as app
WORKDIR /app
COPY --from=build /app/target/app.jar .
ENTRYPOINT ["java", "-jar", "app.jar"]
