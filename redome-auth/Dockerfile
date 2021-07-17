#FROM maven:3.6-jdk-8 as build
#COPY lib/ojdbc7.jar /usr/src/redome-tarefa/lib/ojdbc7.jar
#RUN mvn install:install-file -Dfile=/usr/src/redome-tarefa/lib/ojdbc7.jar -DgroupId=com.oracle -DartifactId=ojdbc7 -Dversion=12.1.0 -Dpackaging=jar

#COPY src /usr/src/redome-tarefa/src
#COPY pom.xml /usr/src/redome-tarefa
#RUN mvn -f /usr/src/redome-tarefa/pom.xml package -Dmaven.test.skip package

#FROM openjdk:8
FROM gcr.io/distroless/java:8
#RUN apt update && apt upgrade curl -y
#COPY --from=build /usr/src/redome-tarefa/target/redome-tarefa.jar /usr/redome/redome-tarefa.jar
COPY  target/redome-auth.jar /usr/redome/redome-auth.jar

ENV profile=default

EXPOSE 9000

ENV NODE_ENV production
ENV NUXT_HOST 0.0.0.0
ENV NUXT_PORT 9000

ENTRYPOINT [ "java", "-Dspring.profiles.active=${profile}", "-jar", "/usr/redome/redome-auth.jar", ">>", "null" ]