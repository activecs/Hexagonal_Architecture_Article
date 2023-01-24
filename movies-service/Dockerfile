FROM maven:3.8.7-amazoncorretto-17 as build
WORKDIR /workspace

COPY pom.xml .
COPY movies-app/pom.xml ./movies-app/
COPY movies-common/pom.xml ./movies-common/
COPY movies-domain/pom.xml ./movies-domain/
COPY movies-infra-api/pom.xml ./movies-infra-api/
COPY movies-test-report/pom.xml ./movies-test-report/

RUN mvn -B -e -C -T 1C org.apache.maven.plugins:maven-dependency-plugin:3.5.0:go-offline

COPY . .

RUN mvn clean install -Dmaven.test.skip=true
RUN mvn -f movies-app/pom.xml clean package -Dmaven.test.skip=true

FROM amazoncorretto:17-alpine-jdk as production
VOLUME /tmp
COPY --from=build /workspace/movies-app/target/movies-app-*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar", "app.jar"]