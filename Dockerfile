FROM dockerfile/java

RUN mkdir /app
ADD https://s3.amazonaws.com/srmbuildartifact/target/SRM-API.jar /app/server.jar

CMD ["java", "-jar", "/app/server.jar", "server" ]

EXPOSE 8080
EXPOSE 8081