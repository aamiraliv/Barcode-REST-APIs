FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY target/product-service-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8082
CMD ["java", "-jar", "app.jar"]