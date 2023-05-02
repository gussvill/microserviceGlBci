FROM openjdk:8-jdk-alpine
VOLUME /tmp
EXPOSE 8081
ARG JAR_FILE=build/libs/bci-microservice-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/bci-microservice-0.0.1-SNAPSHOT.jar"]