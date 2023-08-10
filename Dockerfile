FROM openjdk:17
EXPOSE 8085
COPY target/follow-service-1.0-SNAPSHOT.jar follow-service-docker.jar

ENTRYPOINT ["java", "-jar","/follow-service-docker.jar"]