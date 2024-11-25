FROM openjdk:17-jdk-slim

WORKDIR /app

COPY receipt-processor.jar /app/receipt-processor.jar

# Expose the port your application will run on (default for Spring Boot is 8080)
EXPOSE 8080

# Command to run the jar file when the container starts
ENTRYPOINT ["java", "-jar", "receipt-processor.jar"]