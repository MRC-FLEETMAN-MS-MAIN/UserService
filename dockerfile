FROM openjdk:8-jdk-alpine
COPY JWTMS-0.0.1-SNAPSHOT.jar PhotoAppApiUsers.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","PhotoAppApiUsers.jar"]
