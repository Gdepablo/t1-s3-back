FROM maven:3.8.5-openjdk-17-slim AS MAVEN_BUILD

MAINTAINER Matteo Calbi

COPY pom.xml /build/
COPY src /build/src/
COPY .mvn /build/.mvn/

WORKDIR /build/
RUN mvn package

FROM openjdk:17-jdk-slim-bullseye

WORKDIR /app

COPY --from=MAVEN_BUILD /build/target/gestion-socios-0.0.1-SNAPSHOT.jar /app/

EXPOSE 3000

ENTRYPOINT ["java", "-jar", "gestion-socios-0.0.1-SNAPSHOT.jar"]