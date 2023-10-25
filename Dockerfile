FROM maven:3.9.4-eclipse-temurin-17-alpine AS MAVEN_BUILD

LABEL org.opencontainers.image.source=https://github.com/disilab-frba-utn-edu-ar/t2-s1-socios-back

COPY pom.xml /build/
COPY src /build/src/
COPY .mvn /build/.mvn/

WORKDIR /build/
RUN mvn package

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=MAVEN_BUILD /build/target/gestion-socios-0.0.1-SNAPSHOT.jar /app/

EXPOSE 80

ENTRYPOINT ["java", "-jar", "gestion-socios-0.0.1-SNAPSHOT.jar"]
