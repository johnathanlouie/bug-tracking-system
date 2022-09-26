FROM maven:3-jdk-8-alpine AS build-stage

WORKDIR /app
COPY ./src /app/src
COPY ./pom.xml /app/pom.xml
RUN mvn install


FROM tomcat:8.0.15-jre8

RUN rm -r /usr/local/tomcat/webapps/ROOT
COPY --from=build-stage /app/target/bug-tracking-system-0.0.5.war /usr/local/tomcat/webapps/ROOT.war

EXPOSE 8080
