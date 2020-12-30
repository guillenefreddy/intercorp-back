FROM openjdk:8

ARG artifact_id
ARG artifact_version

# Create app directory
RUN mkdir -p /usr/src/app/intercorp-back/logs
RUN mkdir -p /data
WORKDIR /usr/src/app/intercorp-back

# Install app dependencies
COPY target/intercorp-back-*.jar /usr/src/app/intercorp-back/app.jar

EXPOSE 8000

CMD exec java -Duser.timezone=America/Lima -XX:+PrintFlagsFinal $JAVA_OPTIONS -jar app.jar