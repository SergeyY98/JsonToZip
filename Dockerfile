FROM openjdk:17-jdk-slim
COPY /target/JsonToZip-1.0-SNAPSHOT-jar-with-dependencies.jar /app/app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]