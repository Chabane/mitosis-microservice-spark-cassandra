FROM openjdk:8-jre
VOLUME /tmp
ADD target/scala-2.11/mitosis-microservice-spark-cassandra_2.11-1.0.0-alpha.0.jar build/libs/app.jar
RUN sh -c 'touch build/libs/app.jar'
ENTRYPOINT ["java", "-jar", "build/libs/app.jar" ]
