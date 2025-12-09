# ---------- Build Stage ----------
FROM gradle:8.14-jdk17 AS build
WORKDIR /app
COPY . .
RUN gradle clean build -x test

# ---------- Run Stage ----------
FROM eclipse-temurin:17-jdk
WORKDIR /app

# Copy the jar from the build stage
COPY --from=build /app/build/libs/*.jar app.jar

# Render will provide PORT, default 8080
ENV PORT=8080
EXPOSE 8080

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]