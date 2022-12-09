FROM gradle:jdk17 as build
WORKDIR /app
COPY . /app
RUN dir -s
RUN gradle build --no-daemon

FROM openjdk:17-ea-jdk-oracle
COPY --from=build /app/build/libs/trading-mock-*-all.jar /app/application.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "/app/application.jar"]
