# Use an official OpenJDK image as a base
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the built JAR file into the container
COPY target/*.jar app.jar

# Expose the port on which the app will run
EXPOSE 8080

# Run the app
ENTRYPOINT ["java", "-jar", "app.jar"]
