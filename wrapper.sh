#!/bin/bash

wget https://s1.stackify.com/Account/AgentDownload/Linux --output-document=stackify.tar.gz \
     && tar -zxvf stackify.tar.gz stackify-agent-install-32bit && cd stackify-agent-install-32bit \
     && sudo ./agent-install.sh --key 3Yr0Mq3Vt4As8Eq5Jw5Al0Rz1Ka0Tr7Fo8Mu3Rz --environment Production


while ! exec 6<>/dev/tcp/${DATABASE_HOST}/${DATABASE_PORT}; do
    echo "Trying to connect to MySQL at ${DATABASE_PORT}..."
    sleep 10
done

java  -Djava.security.egd=file:/dev/./urandom -Dspring.profiles.active=container -jar /app.jar