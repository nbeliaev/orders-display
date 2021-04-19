# Orders display

The SPA application which allows manage process of dishes cooking in restaurant/caffe/eatery. 
The app should be used together with the application 1C:Retail chain.

## Features

This project makes it easy to:
* Split dishes by workplaces: kitchen, bar etc.
* Manage dish statuses: new, in-process, completed

## Related projects

Here's a list of other related projects where you can find detail info about 1C:Retail chain:

- [1C:Retail chain](https://1c.com.vn/en/products/1c_retail_chain)

## Installation
###Run from jar

**Requirements**
* Java 13
* Maven  
* MongoDB

Make sure that you are in the root directory of the app and then run
> mvn package

To run jar file
>java -jar backend-{version}.jar


###Run from container

**Requirements**
* Maven
* Docker

Make sure that you are in the root directory of the app and then run
> mvn package

Build the docker image
> docker build -t java-api .

Build the docker compose container
> docker-compose build --no-cache

Run the container
> docker-compose up

## Configuration

The app configuration can be changed in ./src/main/resources/application.properties

**arguments**

Values could be configured by providing arguments:

For example

`java -jar backend-{version}.jar --server.port=8081`

**Property file**

`java -jar backend-{version}.jar --spring.config.location=file:{somewhere}/application-external.properties`

## Licensing

This project is licensed under Unlicense license. This license does not require
you to take the license with you to your project.