FROM openjdk:11
WORKDIR /app
COPY target/*.jar /app/app.jar
ENTRYPOINT [ "java", "-jar", "app.jar" ]