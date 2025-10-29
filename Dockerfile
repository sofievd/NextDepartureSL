FROM eclipse-temurin:21-noble
LABEL authors="Sofie"

RUN mkdir /opt/app
WORKDIR /opt/app

ENV TZ=Europe/Stockholm
RUN date

COPY target/NextDepartureSL-0.0.1-SNAPSHOT.jar /opt/app/app.jar

CMD ["java", "-Xmx2g","-jar","/opt/app/app.jar"]
