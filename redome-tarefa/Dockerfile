FROM gcr.io/distroless/java:8
COPY  target/redome-tarefa.jar /usr/redome/redome-tarefa.jar

ENV profile=default

EXPOSE 9001
ENTRYPOINT [ "java", "-Dspring.profiles.active=${profile}", "-jar", "/usr/redome/redome-tarefa.jar", ">>", "null" ]