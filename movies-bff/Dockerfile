FROM maven:3.8.7-amazoncorretto-17 as build
WORKDIR /workspace

COPY pom.xml .
RUN mvn -B -e -C -T 1C org.apache.maven.plugins:maven-dependency-plugin:3.5.0:go-offline

COPY . .
RUN mvn clean package -Dmaven.test.skip=true

FROM amazoncorretto:17-alpine-jdk as production
VOLUME /tmp
COPY --from=build /workspace/target/moviesbff-*.jar app.jar
EXPOSE 80
ENTRYPOINT ["java","-jar", "app.jar"]