# verification-service-rest
This Spring Boot app:
1. Exposes a RESTful API, that kick starts the simulation of an asyncronous process
2. Sends a Message to RabbitMQ Message Broker topic, using Spring Cloud Stream and RabbitMQ binder

## Config Server Client
This project uses Spring Cloud Config Server to externalize the configurations. Following dependency has been added in the pom.xml, the precense of which would enable this application to be considered as a 'Spring Cloud Config Server Client':

```
<dependencies>
    ...
	<dependency>
		<groupId>io.pivotal.spring.cloud</groupId>
		<artifactId>spring-cloud-services-starter-config-client</artifactId>
	</dependency>
    ...
</dependencies>        
<dependencyManagement>
    ...
	<dependency>
		<groupId>io.pivotal.spring.cloud</groupId>
	   	<artifactId>spring-cloud-services-dependencies</artifactId>
		<version>${spring-cloud-services.version}</version>
		<type>pom</type>
		<scope>import</scope>
	</dependency>
    ...
</dependencyManagement>
```
We are letting it use its default configurations to detect and connect to a Config Server instance running at `http://localhost:8888`.

Note: We are using:

```
<dependency>
		<groupId>io.pivotal.spring.cloud</groupId>
		<artifactId>spring-cloud-services-starter-config-client</artifactId>
</dependency>
```
instead of:

```
<dependency>
	<groupId>org.springframework.cloud</groupId>
	<artifactId>spring-cloud-starter-config</artifactId>
</dependency>
```
to enable the app to be deployed in Pivotal Cloud Foundry (PCF) and make use of a Config Server Service instance provisioned in the Space where the app will be deployed. PCF's Config Server service instances are secured with OAuth, and the above mentioned config-client library from Pivotal will help deal with the authentication related stuff behind-the-scenes for us. Also, since it bundles with the Spring Security libraries, it will automatically apply security to the application as a whole, and our APIs will also be secured with a username/password combination the library auto-generates at runtime. Since this being a demo app, to make things simpler, we are excluding the Security auto-configurations, so that the APIs exposed can be invoked without providing user credentials. This exclusion is done in `VerificationServiceRestDemoApplication.java`:

```
@SpringBootApplication(
    exclude = {SecurityAutoConfiguration.class, ManagementWebSecurityAutoConfiguration.class})
@EnableFeignClients
public class VerificationServiceRestDemoApplication {

  public static void main(String[] args) {
    SpringApplication.run(VerificationServiceRestDemoApplication.class, args);
  }
}
```
The app's configuration is externalized in a GitHub repo: `https://github.com/nanda-dev/spring-cloud-config-repo`, under the folder named: `verification-service-rest` (which is the same as the `spring.application.name` property value given to the app in the `application.yml`/`bootstrap.yml` file).

If we change the configurations in the .yml file in the GitHub config repo, those changes can be reflected in the application by using the `/actuator/refresh` Actuator endpoint. It's a **POST** API, and expects **no payload**. So, we just need to call: `http://<app-url>/actuator/refresh` using `curl` or a REST client such as Postman, to fetch the latest configurations using Config Server.

Normally, Beans marked with `@RefreshScope` and having `@Value` fields would get refreshed with the new values, as well as `@ConfigurationProperties` Beans, Data source configs and Log levels.

### Bus Refresh
Since this app is intended to be pushed to a Cloud platform where it can get scaled horizontally (multiple instances), it would not be practical to hit the `/actuator/refresh` endpoint against all running instances, so that the config changes are reflected properly in all of them. To help with this, we are including Spring Cloud Bus library, and use a RabbitMQ message broker (which is why we are using `spring-cloud-starter-bus-amqp` starter specifically), and make use of the `/actuator/bus-refresh` endpoint (a **POST** endpoint, similar to `/actuator/refresh` noted above) to trigger the refresh on all running instances of the app.

Dependency added in pom.xml:

```
<dependencies>
    ...
    <dependency>
		<groupId>org.springframework.cloud</groupId>
		<artifactId>spring-cloud-starter-bus-amqp</artifactId>			
	</dependency>
    ...
</dependencies>        
<dependencyManagement>
    ...
	<dependency>
		<groupId>io.pivotal.spring.cloud</groupId>
	   	<artifactId>spring-cloud-services-dependencies</artifactId>
		<version>${spring-cloud-services.version}</version>
		<type>pom</type>
		<scope>import</scope>
	</dependency>
    ...
</dependencyManagement>
```

## Eureka Service Discovery Client
To make this app a Eureka Discovery Client, to register itself with the Dicovery Server and enable locating downstream services by looking them up using their service-id from a Eureka Discovery Server, the following dependency is added:

```
<dependencies>
    ...
	<dependency>
		<groupId>io.pivotal.spring.cloud</groupId>
		<artifactId>spring-cloud-services-starter-service-registry</artifactId>
	</dependency>
    ...
</dependencies>        
<dependencyManagement>
    ...
	<dependency>
		<groupId>io.pivotal.spring.cloud</groupId>
	   	<artifactId>spring-cloud-services-dependencies</artifactId>
		<version>${spring-cloud-services.version}</version>
		<type>pom</type>
		<scope>import</scope>
	</dependency>
    ...
</dependencyManagement>
```
We are leaving it use its default configuration, and hence our app (when run in local dev environment) will connect to a Eureka Discovery Server running at: `http://localhost:8761`. Once the app starts running, navigate to `http://localhost:8761` to open Eureka Discovery Server dashboard, and the app should be listed there with the `spring.application.name` property value if everything is setup correctly.

Note: We are using:

```
<dependency>
		<groupId>io.pivotal.spring.cloud</groupId>
		<artifactId>spring-cloud-services-starter-service-registry</artifactId>
</dependency>
```
instead of:

```
<dependency>
      <groupId>org.springframework.cloud</groupId>
      <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
</dependency>
```
to enable the app to be deployed in Pivotal Cloud Foundry (PCF) and make use of a Eureka Server Service instance provisioned in the Space where the app will be deployed. PCF's Eureka Server service instances are secured with OAuth, and the above mentioned config-client library from Pivotal will help deal with the authentication related stuff behind-the-scenes for us. Also, since it bundles with the Spring Security libraries, it will automatically apply security to the application as a whole, and our APIs will also be secured with a username/password combination the library auto-generates at runtime. Since this being a demo app, to make things simpler, we are excluding the Security auto-configurations, so that the APIs exposed can be invoked without providing user credentials. This exclusion is done in `VerificationServiceRestDemoApplication.java`:

```
@SpringBootApplication(
    exclude = {SecurityAutoConfiguration.class, ManagementWebSecurityAutoConfiguration.class})
@EnableFeignClients
public class VerificationServiceRestDemoApplication {

  public static void main(String[] args) {
    SpringApplication.run(VerificationServiceRestDemoApplication.class, args);
  }
}
```

## RabbitMQ
Following dependencies are added to use Spring Cloud Stream abstractions to send/receive messages with RabbitMQ server:

```
<dependencies>
    ...
	<dependency>
		<groupId>org.springframework.cloud</groupId>
		<artifactId>spring-cloud-stream</artifactId>
	</dependency>
	<dependency>
		<groupId>org.springframework.cloud</groupId>
		<artifactId>spring-cloud-stream-binder-rabbit</artifactId>
	</dependency>
    ...
</dependencies>        
<dependencyManagement>
    ...
	<dependency>
		<groupId>org.springframework.cloud</groupId>
		<artifactId>spring-cloud-dependencies</artifactId>
		<version>${spring-cloud.version}</version>
		<type>pom</type>
		<scope>import</scope>
	</dependency>
    ...
</dependencyManagement>
```
By default, a RabbitMQ server running at `http://localhost:5672` will be connected to.

The following configuration in `application.yml` enables binding to the `topic-verificationstatus` topic in RabbitMQ server:
```
spring:  
  cloud:
    bus:
    stream:
      bindings:
        verificationstatus:
          destination: topic-verificationstatus
```

# Getting Started

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.2.7.RELEASE/maven-plugin/)
* [Spring Web](https://docs.spring.io/spring-boot/docs/2.3.0.RELEASE/reference/htmlsingle/#boot-features-developing-web-applications)
* [Spring Boot Actuator](https://docs.spring.io/spring-boot/docs/2.3.0.RELEASE/reference/htmlsingle/#production-ready)
* [Spring Data JPA](https://docs.spring.io/spring-boot/docs/2.3.0.RELEASE/reference/htmlsingle/#boot-features-jpa-and-spring-data)
* [Config Client (PCF)](https://docs.pivotal.io/spring-cloud-services/)
* [Service Registry (PCF)](https://docs.pivotal.io/spring-cloud-services/)
* [Spring for RabbitMQ](https://docs.spring.io/spring-boot/docs/2.3.0.RELEASE/reference/htmlsingle/#boot-features-amqp)
* [Spring Cloud Bus](https://spring.io/projects/spring-cloud-bus#overview)

### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/bookmarks/)
* [Building a RESTful Web Service with Spring Boot Actuator](https://spring.io/guides/gs/actuator-service/)
* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)
* [Accessing data with MySQL](https://spring.io/guides/gs/accessing-data-mysql/)
* [Messaging with RabbitMQ](https://spring.io/guides/gs/messaging-rabbitmq/)
* [Spring Cloud Bus](https://cloud.spring.io/spring-cloud-bus/2.2.x/reference/html/)

