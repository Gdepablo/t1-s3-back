FROM maven:3.9.4-eclipse-temurin-17-alpine AS build

LABEL org.opencontainers.image.source=https://github.com/disilab-frba-utn-edu-ar/t2-s1-socios-back

WORKDIR /build

COPY pom.xml /build

RUN ["/usr/local/bin/mvn-entrypoint.sh", "mvn", "verify", "clean", "--fail-never"]

COPY . /build

RUN mvn package

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /build/target/gestion-socios-0.0.1-SNAPSHOT.jar /app/
COPY --from=build /build/uploads /app/uploads

EXPOSE 80

ENTRYPOINT ["java", "-jar", "gestion-socios-0.0.1-SNAPSHOT.jar"]
