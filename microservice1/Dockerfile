FROM eclipse-temurin:21-ubi9-minimal

WORKDIR /app

COPY build/libs/demo-0.0.1-SNAPSHOT.jar /app/demo.jar
COPY src/main/resources/application.properties /app/config/application.properties

ENTRYPOINT ["java", "-jar", "demo.jar"]