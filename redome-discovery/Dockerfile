FROM gcr.io/distroless/java:8
COPY target/redome-discovery.jar /usr/redome/redome-discovery.jar

ENV profile=default

EXPOSE 9082
ENTRYPOINT [ "java", "-Dspring.profiles.active=${profile}", "-jar", "/usr/redome/redome-discovery.jar", ">>", "null" ]