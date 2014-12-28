FROM dockerfile/java

RUN mkdir /app
RUN curl -O https://s3.amazonaws.com/srmbuildartifact/target/SRM-API.jar
ADD SRM-API.jar /app/server.jar

CMD ["java", "-jar", "/app/server.jar", "server" ]

EXPOSE 8080
