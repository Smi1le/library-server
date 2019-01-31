FROM openjdk:latest
WORKDIR /
ADD server-id.jar server-id.jar
EXPOSE 9000

CMD ["java", "-version"]
CMD ["java", "-jar", "server-id.jar"]
