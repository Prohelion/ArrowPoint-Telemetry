# Baseline image
FROM openjdk:8-jdk-alpine

# Move the Jar File Across
VOLUME /tmp
VOLUME /config

ARG JAR_FILE
COPY ${JAR_FILE} telemetry.jar

# Move across the configuration files for the event
COPY ./config /config

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/telemetry.jar"]