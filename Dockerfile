FROM openjdk:8-jdk-alpine
MAINTAINER Rantu Nar
COPY target/monitoring-0.0.1-SNAPSHOT.jar monitoring-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/monitoring-0.0.1-SNAPSHOT.jar"]