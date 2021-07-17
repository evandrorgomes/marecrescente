#FROM maven:3.6-jdk-8 as build
#COPY RedomeLib/src /usr/src/redomelib/src
#COPY RedomeLib/pom.xml /usr/src/redomelib
#RUN mvn -f /usr/src/redomelib/pom.xml -B package -Dmaven.test.skip=true
#RUN mvn install:install-file -Dfile=/usr/src/redomelib/target/RedomeLib-0.0.1-SNAPSHOT.jar -DgroupId=br.org.cancer.modred -DartifactId=RedomeLib -Dversion=0.0.1-SNAPSHOT -Dpackaging=jar

#COPY lib/ojdbc7.jar /usr/src/redome/lib/ojdbc7.jar
#RUN mvn install:install-file -Dfile=/usr/src/redome/lib/ojdbc7.jar -DgroupId=com.oracle -DartifactId=ojdbc7 -Dversion=12.1.0 -Dpackaging=jar

#COPY src /usr/src/redome/src
#COPY pom.xml /usr/src/redome
#RUN mvn -f /usr/src/redome/pom.xml -P buildCompleto -Dmaven.test.skip

FROM gcr.io/distroless/java:8
#FROM openjdk:8
#RUN apt update && apt upgrade curl -y
#COPY --from=build /usr/src/redome/target/redome.jar /usr/redome/redome.jar
COPY  target/redome.jar /usr/redome/redome.jar

ENV profile=default

EXPOSE 9080
ENTRYPOINT [ "java", "-Dspring.profiles.active=${profile}", "-jar", "/usr/redome/redome.jar", ">>", "null" ]