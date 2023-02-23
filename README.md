# dev-environments-workshop
Workshop for experiencing and comparing various different approaches to creating local development environments.

## Testcontainers
When using the testcontainers setup we have to make sure, that the trading-mock image is available either on some remote
registry or locally. To run the application locally, we can just build the image from the Dockerfile in the trading-mock
project.

This can be done by switching to the trading-mock projects root directory and building the application,
so everything is available to build the image:
```
./gradlew build dockerfile
```
_(If you are using an M1 you might need to change the base image in the generated Dockerfile to `openjdk:17.0.2`)_

Then we can build the docker image itself:
```
docker build -f build/docker/main/Dockerfile -t squer-trading-mock ./build/docker/main
```

