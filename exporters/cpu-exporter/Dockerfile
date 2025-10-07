# ---------- Build stage ----------
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /src

# Only copy pom first to leverage Docker layer cache
COPY pom.xml .
RUN mvn -q -DskipTests dependency:go-offline

# Now copy sources and build
COPY src ./src
RUN mvn -q -DskipTests package \
 && mvn -q -DskipTests dependency:copy-dependencies -DoutputDirectory=target/deps

# ---------- Runtime stage ----------
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /src/target/classes ./classes
COPY --from=build /src/target/deps ./deps

EXPOSE 9900
# Run the exporter main class
ENTRYPOINT ["java","-cp","/app/classes:/app/deps/*","com.exporters.cpuexporter.CpuExporterApplication"]