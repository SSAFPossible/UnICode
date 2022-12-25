FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD simple-1.0-SNAPSHOT.jar simple-1.0-SNAPSHOT.jar 
ENTRYPOINT ["java","-jar","/simple-1.0-SNAPSHOT.jar"]
