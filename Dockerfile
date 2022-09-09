FROM openjdk:17
COPY ./target/spring-batch.jar /usr/app/app.jar
WORKDIR /usr/app
CMD ["java","-jar","app.jar"]