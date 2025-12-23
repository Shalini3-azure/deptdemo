FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

# ---- OpenTelemetry Java Agent ----
ADD https://github.com/open-telemetry/opentelemetry-java-instrumentation/releases/latest/download/opentelemetry-javaagent.jar /otel/opentelemetry-javaagent.jar

# ---- Application ----
COPY target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java","-javaagent:/otel/opentelemetry-javaagent.jar","-jar","/app/app.jar"]
