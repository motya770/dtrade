FROM openjdk:8u171-jdk-alpine3.8
RUN apk update && apk upgrade && apk add bash && apk add vim && apk add vim
VOLUME /tmp
ARG JAR_FILE
ADD ${JAR_FILE} app.jar
ADD wrapper.sh wrapper.sh
RUN bash -c 'chmod +x /wrapper.sh'
RUN bash -c 'touch /app.jar'
ENTRYPOINT ["/bin/bash", "/wrapper.sh"]