# Spring, Docker & Mongo DB - Simple project

My primary goals are:

* Learn Spring Boot
* Provide a sandbox Spring Boot project using Docker and MongoDB.
* Provide Bash script for both Linux and Windows to be executed in order to build custom Docker image based on Mongo official image and run a container with all volume, port mappinp and name set up
* Use MongoRepositories and MongoTemplate to insert and retrieve data


## Installation and Getting Started
To install this project, just fork the repos or download it from https://github.com/waadou/waadou-spring-docker-mongo.git

At the root of the project, the **db** folder contains the **Dockerfile.mongo** file that can be customized(MongoDB port, root user, root user password and database name).

Remember to edit **mongo-server.cmd** and **mongo-server.sh** at the root of th project accordingly, since the declared variables correspond to those used in **Dockerfile.mongo** in **db** folder.

You may need to initialize the database with your data or create your own users with their roles when launching your container, fell free to read the official documentation on https://hub.docker.com/_/mongo.

Please, be aware to shut down you MonDb instances installed on your physical machine!!!!

## Build from source
To build this simple project, at least your need to have JDK 1.8 installed on your machine. The version of Spring Boot is **2.7.7** and **Log4j2** is the configured logging framework.

You don't need to add a dependency for the MongoDB Java Driver, it is already included by **spring-boot-starter-data-mongodb** dependency.

If using an IDE like **Eclipse, Netbeans, Intellij** or **Spring Tool Suite**, just clean and build your project. You can also use **Maven** in command line to build your project.

Enjoy !!!!
