FROM eclipse-temurin:17
WORKDIR /app
COPY target/TodoItem-0.0.1-SNAPSHOT.jar /app/TodoItem.jar
ENTRYPOINT [ "java", "-jar", "TodoItem.jar" ]