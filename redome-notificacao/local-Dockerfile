FROM gcr.io/distroless/java:8
COPY target/redome-notificacao.jar /usr/redome/redome-notificacao.jar

ENV profile=default

EXPOSE 9002
ENTRYPOINT [ "java", "-Dspring.profiles.active=${profile}", "-jar", "/usr/redome/redome-notificacao.jar", ">>", "null" ]
