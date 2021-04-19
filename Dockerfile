FROM openjdk:13
COPY /backend/target/backend-1.0-SNAPSHOT.jar backend-1.0-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "--spring.profiles.active=prod", "backend-1.0-SNAPSHOT.jar"]
