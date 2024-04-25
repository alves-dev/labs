FROM amazoncorretto:21

COPY build/libs/labs-0.0.1-SNAPSHOT.jar /app/application.jar

# Set the timezone to São Paulo
RUN ln -snf /usr/share/zoneinfo/America/Sao_Paulo /etc/localtime && echo America/Sao_Paulo > /etc/timezone

EXPOSE 8080

CMD java -jar app/application.jar