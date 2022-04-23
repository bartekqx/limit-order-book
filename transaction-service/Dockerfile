# Build stage
FROM maven:3.6.0-jdk-11-slim AS build
COPY src /app/src
COPY pom.xml /app
RUN mvn -f /app/pom.xml clean package

# Package stage
FROM openjdk:11-jre-slim
ARG JAR_FILE=target/*.jar
COPY --from=build /app/${JAR_FILE} app.jar
EXPOSE 8904
ENTRYPOINT ["java","-jar","/app.jar"]