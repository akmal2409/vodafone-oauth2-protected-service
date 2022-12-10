# vodafone-oauth2-protected-service
Assignment 2.4

# Quick start (One touch set up with docker compose)
    mvn install
That will  install required dependencies and generate a jar in the target folder

    docker compose up
Will spin up postgres image and build an image of the proxy service locally.

## Create confidential client
Navigate to

    http://localhost:8080/admin

And enter username "admin" and password "admin"

Create client with service credentials enabled (needed for client_credentials grant)

Create custom scope vz_test

Add the scope vz_test to the client

In postman 'Token Request (Client Credentials)' request, in the body tab select x-www-form-url-encoded

Set the values of client_id, client_secret, scope (vz_test), grant_type (client_credentials)

### Launching only keycloak and echo server locally
You can also use docker-compose-dev.yaml that only spins up the keycloak and echo instance.

    docker compose -f docker-compose-dev.yaml up

## Manual launch
    mvn install

And then use the spring-boot maven plugin to run the application

    mvn spring-boot:run

## Run tests (Unit)
    mvn test

## Run integration tests (only)
    mvn verify

## Package into a jar
    mvn clean package

## Build a docker image
    docker build -t <image_name>:<tag> .
