FROM eclipse-temurin:21-ubi9-minimal

WORKDIR /app

COPY demo-0.0.1-SNAPSHOT.jar /app/demo.jar
COPY application.properties /app/config/application.properties

ENTRYPOINT ["java", "-jar", "demo.jar"]