FROM openjdk:8
ADD build/libs/habitat-0.0.1-SNAPSHOT.jar habitat.jar
EXPOSE $PORT

ENTRYPOINT ["java", "-jar", "habitat.jar"]