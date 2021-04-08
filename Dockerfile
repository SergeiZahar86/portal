FROM adoptopenjdk/openjdk11:alpine-jre
ADD target/portal-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]

