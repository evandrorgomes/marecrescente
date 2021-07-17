FROM gcr.io/distroless/java:8
COPY  target/redome-workup.jar /usr/redome/redome-workup.jar

ENV profile=default

EXPOSE 9004
ENTRYPOINT [ "java", "-Dspring.profiles.active=${profile}", "-jar", "/usr/redome/redome-workup.jar", ">>", "null" ]