FROM openjdk:15

MAINTAINER Samuel Ngo <sammywu@gmx.de>

ADD backend/target/game-night-tracker.jar game-night-tracker.jar

CMD ["sh", "-c", "java -Dserver.port=$PORT -Dspring.data.mongodb.uri=$MONGODB_URI -Dsecurity.jwt.secret=$JWT_SECRET -jar /game-night-tracker.jar"]