# Squer Trading - Prices Mock
This application will mock an external pricing provider, that publishes new (random) prices for all our available assets.

## Setup
The price mock application depends on a running kafka instance. 
The kafka server url can be set in the application.yml file.

## Usage via docker compose
You can also add the price mock application to your local docker compose setup.
Use the following template to get started:
```
prices-mock:
    container_name: prices-mock
    hostname: price-mock
    build: // change according to the project location
        context: ./trading-mock
        dockerfile: Dockerfile
    environment:
        KAFKA_BOOTSTRAP_SERVERS: {{kafka_server_url}}
    ports:
        - "8090:8081"
```