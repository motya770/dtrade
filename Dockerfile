FROM java:8
RUN apt update && apt install -y vim && apt install less
VOLUME /tmp
ARG JAR_FILE
ADD ${JAR_FILE} app.jar
ADD wrapper.sh wrapper.sh
RUN bash -c 'chmod +x /wrapper.sh'
RUN bash -c 'touch /app.jar'
ENTRYPOINT ["/bin/bash", "/wrapper.sh"]