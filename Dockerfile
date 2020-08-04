# Start with a base image containing Java runtime
FROM maven:3.6.0-jdk-8-alpine AS MAVEN_BUILD

# Add Maintainer Info
LABEL maintainer="sladynnunes98@gmail.com"

# Creates a build directory in the image and copies the pom.xml into it
COPY pom.xml /build/

# Copies the src directory into the build directory in the image.
COPY src /build/src/

# Set Workdir
WORKDIR /build/

# Runs the mvn package command to compile and package the application as an executable JAR
RUN mvn clean package spring-boot:repackage

FROM openjdk:8-jre-alpine

# Create a new working directory in the image called /app
WORKDIR /app

COPY --from=MAVEN_BUILD /build/target/custom-distribution-service-0.0.1.jar /app/

RUN ls

ENTRYPOINT ["java", "-jar", "/app/custom-distribution-service-0.0.1.jar"]
