# RESTful API- consumes github public api and aggregate data

Spring boot RESTful API microservices used to consume github api and aggregate data

## Prerequisites

For building and running the application you need:

- [JDK 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [Maven 3](https://maven.apache.org)


## Building from Source

Use the Maven to build the application
```shell
$ ./mvnw clean install
 ```

## Running the application locally

Use the Spring Boot Maven plugin
```shell
mvn spring-boot:run
 ```

## Exposed RESTful API endpoints

All the endpoints were protected with "basic" authentication
```shell
Username : gituser
Password: gituser
 ```

###### Get the repository list of a user
```shell
GET end point: api/repolist/<repository username >

Example : http://localhost:9999/api/repolist/rogers-repo
 ```

###### Get top 10 contributors name in last 30 days
```shell
GET end point: api/repos/<Git  username>/<Git repo name>/contributors?per_page=10&since=currentdate-30

Example : http://localhost:9999/api/contributors/rogers-repo/GitRestApi
 ```

###### Get the aggregate count based on type

```shell

- To get the count of number of open pr
GET end point: api/repos/<Git  username>/<Git repo name>/pulls?state=open

- To get the count of number of closed pr in current days
GET end point: api/repos/<Git  username>/<Git repo name>/pulls?state=closed&since=tody'sdate

- Number of committers in last 30 days
GET end point: repos/<Git  username>/<Git repo name>/commits?since=currentdate-30

Examples :

http://localhost:9999/api/statistics/rogers-repo/GitRestApi?type=openpr
http://localhost:9999/api/statistics/rogers-repo/GitRestApi?type=closedpr
http://localhost:9999/api/statistics/rogers-repo/GitRestApi?type=30daysCommit
http://localhost:9999/api/statistics/rogers-repo/GitRestApi?type=contributors
 ```



## Spring BOOT Modules

###### Swagger2
Swagger 2 is an open-source project used to describe,test and document RESTful APIs.

```shell
End point : http://localhost:9999/swagger-ui.html
 ```

######  Spring-boot-Security
 Secures all HTTP endpoints with "basic" authentication
```shell
Username : gituser
Password: gituser
 ```

######  Spring-boot-actuator
Actuator endpoints let you monitor and interact with your application.
Spring Boot Actuator provides the infrastructure required for actuator endpoints. It contains
annotation support for actuator endpoints. Out of the box, this module provides a number of endpoints
including the `HealthEndpoint`, `EnvironmentEndpoint`, `BeansEndpoint` and many more.
```shell
End point : http://localhost:9999/actuator
 ```

######  Spring-boot-test
This module contains core items and annotations that can be helpful when testing your application.

######  Spring-boot-devtools
The spring-boot-devtools module provides additional development-time features such as automatic restarts,
for a smoother application development experience. Developer tools are automatically disabled when
running a fully packaged application.

