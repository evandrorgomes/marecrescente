FROM gcr.io/distroless/java:8
COPY  target/redome-courier.jar /usr/redome/redome-courier.jar

ENV profile=default

EXPOSE 9005
ENTRYPOINT [ "java", "-Dspring.profiles.active=${profile}", "-jar", "/usr/redome/redome-courier.jar", ">>", "null" ]