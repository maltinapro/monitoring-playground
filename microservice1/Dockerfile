FROM eclipse-temurin:21-ubi9-minimal

WORKDIR /app

COPY build/libs/demo-0.0.1-SNAPSHOT.jar /app/demo.jar

ENTRYPOINT ["java", "-jar", "demo.jar"]