# wholesome-spring-cloud
Collection of Spring Boot applications that demonstrate the usages of the following Spring Cloud projects: Spring Cloud Config, Spring Cloud Netflix (Eureka Service Discvovery), Spring Cloud Stream (using RabbitMQ Binder)


# Apps Included

## account-service
This Spring Boot app:
1. Exposes a set of RESTful APIs, to perform some basic operations
2. Interacts with table(s) in a MySQL Database, using Spring Data JPA
3. Communicates with the RESTful API exposed by the `verification-service-rest` application, using Eureka Service Discovery and Feign Client


## verification-service-rest
This Spring Boot app:
1. Exposes a RESTful API, that kick starts the simulation of an asyncronous process
2. Sends a Message to RabbitMQ Message Broker topic, using Spring Cloud Stream and RabbitMQ binder

## verification-status-listener
This Spring Boot app:
1. Receives a Message from RabbitMQ Message Broker topic, using Spring Cloud Stream and RabbitMQ binder
2. Communicates with the RESTful API exposed by the `account-service` application, using Eureka Service Discovery and Feign Client

Details about each application and its configuration can be viewed from the respective Readme docs. The apps can be run in a local development environment, where you have an instance of MySQL server (8+), RabbitMQ server, Spring Cloud Config Server and Spring Cloud Eureka Discovery Server are running, or have access to these services running elsewhere and can be connected directly (as of now, the app dependencies make use of default configurations, so if you have these servers running in a different machine, you would need to fiddle around a little bit to ensure connectivity from the apps).

# Common Dependencies [Local environment]
## Spring Cloud Config Server
To run a Spring Cloud Config Server, with a remote GitHub repo to store the application config, you can either:
* Stand-up one from scratch, by creating and running a Spring Boot app with the following dependency:
```
<dependencies>
    ...
	<dependency>
	    <groupId>org.springframework.cloud</groupId>
		<artifactId>spring-cloud-config-server</artifactId>
	</dependency>
    ...
</dependencies>        
<dependencyManagement>
    ...
	<dependencies>
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-dependencies</artifactId>
			<version>${spring-cloud.version}</version>
			<type>pom</type>
			<scope>import</scope>
		</dependency>
	</dependencies>
    ...
</dependencyManagement>
```
Use the following config in application.yml to run the Server on port 8888 (normally, Config Clients would try to connect to Config Server instance at this port, unless explicitly told otherwise), and to use a GitHub public repo where configurations for each applicaition is stored under corresponding folder. The `searchPaths` field's value, in the below configuration, represents this setting. The folder name within the repo assigned to an application should be haivng the same name as the app's `spring.application.name` property.

`application.yml`
```
spring:
  cloud:
    config:
      server:
        git:
          uri: https://github.com/nanda-dev/spring-cloud-config-repo
          searchPaths: '{application}'
```

* Use [Spring Cloud CLI](https://cloud.spring.io/spring-cloud-cli/reference/html/) and start the Spring Cloud Config Server
```
$ spring cloud configserver
```
This project makes use of the following configuration in setting up the Config Server, started with Spring Cloud CLI:

`cloudconfig.yml`
```
spring:
  profiles:
    active: git
  cloud:
    config:
      server:
        git:
          uri: https://github.com/nanda-dev/spring-cloud-config-repo
          searchPaths: '{application}'
```
The above noted `cloudconfig.yml` must be placed in the directory from where the `$ spring cloud configserver` command was issued from your terminal. Please update the GitHub repo with yours as needed.

By default, Config Server would be running at: `http://localhost:8888`

## Spring Cloud Eureka Discovery Server
To run a Spring Cloud Eureka Discovery Server, with a remote GitHub repo to store the application config, you can either:
* Stand-up one from scratch, by creating and running a Spring Boot app with the following dependency:
```
<dependencies>
        ...
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
		</dependency>
		...
	</dependencies>

	<dependencyManagement>
		<dependencies>
			<dependency>
				<groupId>org.springframework.cloud</groupId>
				<artifactId>spring-cloud-dependencies</artifactId>
				<version>${spring-cloud.version}</version>
				<type>pom</type>
				<scope>import</scope>
			</dependency>
		</dependencies>
	</dependencyManagement>
```

* Use [Spring Cloud CLI](https://cloud.spring.io/spring-cloud-cli/reference/html/) and start the Eureka Server
```
$ spring cloud eureke
```


By default, Eureka Server would be running at: `http://localhost:8761`

## MySQL Server
You can either [download and install MySQL server](https://dev.mysql.com/downloads/), or if you have Docker, simply run the image using: 

`$ sudo docker run --name mysql-server -d -p 3306:3306 -e MYSQL_ROOT_PASSWORD=root-password mysql`

Note: Since this project is just for demo purpose, we are not creating any additional user credentials, but sticking with the **root** user when connecting from the apps as well, which is not advisable in real-world scenarios.

## RabbitMQ Server
You may either [download and install RabbitMQ server](https://www.rabbitmq.com/download.html), or if you have Docker, simply run the image using:

`$ sudo docker run -it --rm --name rabbitmq -p 5672:5672 -p 15672:15672 -d rabbitmq:3-management`

Default credentials: 
```
user: guest
password: guest
```

RabbitMQ management console/dashboard can be accessed at: `http://localhost:15672`. Default **guest** credentials noted above can be used to access the console/dashboard.