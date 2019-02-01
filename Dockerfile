FROM maven:3.3-jdk-8
ADD . /home
WORKDIR /home
EXPOSE 9000
CMD ["mvn", "-X", "clean", "install", "exec:java"]
