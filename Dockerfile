FROM openjdk:8u191-jre-alpine3.8

LABEL maintainerEmail="filipe.alves.pinheiro@gmail.com"

LABEL maintainerName="Filipe Pinheiro"

ENV PROJECT_NAME spring-batch

ENV JAR_FILE_NAME spring-batch-0.1.0.jar

ENV ENVIRONMENT dev

# Moving jar file
WORKDIR /opt

RUN mkdir $PROJECT_NAME

COPY ./target/$JAR_FILE_NAME ./$PROJECT_NAME

# Configuring CRONTAB
WORKDIR /opt/$PROJECT_NAME

RUN /usr/bin/crontab -l > mycron

RUN echo "*/1 * * * * /usr/bin/java -Dspring.profiles.active="$ENVIRONMENT " -jar /opt/"$PROJECT_NAME/$JAR_FILE_NAME >> mycron

RUN /usr/bin/crontab mycron && rm mycron

CMD ["/usr/sbin/crond", "-f", "-l", "8"]