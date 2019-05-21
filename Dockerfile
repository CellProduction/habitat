FROM openjdk:8
COPY build/libs/habitat-0.0.1-SNAPSHOT.jar habitat.jar
EXPOSE $PORT

CMD ["java", "-Xmx384m", "-jar", "habitat.jar"]