FROM openjdk:15

MAINTAINER Samuel Ngo <sammywu@gmx.de>

ADD backend/target/game-night-tracker.jar app.jar

CMD ["sh", "-c", "java -jar /app.jar"]