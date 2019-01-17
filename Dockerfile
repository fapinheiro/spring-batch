FROM openjdk:11.0.1-jdk-slim-stretch   

MAINTAINER Filipe Pinheiro "filipe.alves.pinheiro@gmail.com"

ENV PROJECT_NAME spring-batch

ENV JAR_FILE_NAME spring-batch-0.1.0.jar

WORKDIR /etc/apt

RUN echo "deb-src http://deb.debian.org/debian stretch main" >> sources.list

RUN apt update && apt-get install -y cron && apt-get install -y vim

WORKDIR /opt

RUN mkdir $PROJECT_NAME

COPY ./target/$JAR_FILE_NAME .

CMD /usr/bin/java -Dspring.profiles.active=dev -jar $JAR_FILE_NAME