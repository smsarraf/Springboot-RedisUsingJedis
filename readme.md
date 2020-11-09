#Spring Boot with Redis Tutorial

## Introduction
Before going further in this tutorial we will take a look at the common terminology such as introduction to Spring boot, Docker, and Redis.

###What is Spring boot?
Spring boot is a module that provides rapid application development feature to the spring framework including auto-configuration, standalone-code, and production-ready code
It creates applications that are packaged as jar and are directly started using embedded servlet container (such as Tomcat, Jetty or Undertow). Thus, no need to deploy the war files
It simplifies the maven configuration by providing the starter template and helps to resolve the dependency conflicts. It automatically identifies the required dependencies and imports them in the application
It helps in removing the boilerplate code, extra annotations, and XML configurations
It provides a powerful batch processing and manages the rest endpoints
It provides an efficient JPA-starter library to effectively connect the application with the relational databases
It offers a Micro-service architecture and cloud configuration that manages all the application related configuration properties in a centralized manner.
### What is Docker?
In the present world, Docker is an important term,

Often used in CI/CD platform that packages and runs the application with its dependencies inside a container
Is a standard for Linux Containers
A Container is a runtime that runs under any Linux kernel and provides a private machine-like space under Linux
spring boot redis
#### Docker Terminology
Image: Representation of Docker container i.e. a JAR or WAR file in Java
Container: Runtime of Docker i.e. a deployed and running Docker image. For example, an executable Spring Boot jar
Engine: The code that manages, creates and runs the Docker containers
Hub: A public developers registry to distribute their code
Repository: A collection of Docker related images i.e. different versions of the same application
### What is Redis?
Redis is an open-source in-memory data store written in the C programming language
Offers a distributed, in-memory key-value database with optional durability
Often used as a database, cache, or as a message broker and supports different kinds of database types, such as strings, lists, maps, sets or sorted sets, etc.
It is fast and operations are atomic in nature (i.e. two clients can concurrently access the data and Redis server will receive the updated value)
Offers utilities like caching and messaging-queues
In this tutorial, we will create a spring boot application and integrate it with Redis to understand its basic concepts. But before going any further I’m assuming that readers are aware of the concepts of creating and running a basic spring boot application.

## Spring Boot with Redis Tutorial
### Application Pre-requisite
To start with this tutorial, we are hoping that users at present have the Docker installation completed. If someone needs to go through the Docker installation, please watch this video.

### Tools Used and Project Structure
We are using Eclipse, JDK 8, Maven, and Docker. In case you’re confused about where you should create the corresponding files or folder, let us review the project structure of the spring boot application.

spring boot redis - Project structure
Let us start building the application!

## Pulling the Redis image from Docker Hub & Starting it
To have the Redis up and working on the localhost environment, we will pull the Redis image from Docker and start the container. Users can refer the following commands to pull the image and later start the container.

Docker commands
```
## Docker COmmands
## step1 - Pulling redis image from docker hub
docker pull redis

## step 2 - Running the container
docker run -d -p 6379:6379 --name my-redis redis
``` 
If everything goes well, the Docker image will be successfully pulled from the and started successfully as shown in the below image. Developers can use the docker ps -a command to verify if the container was successfully started or not. Developers can go through this link to understand the Docker basic terminology.

##Creating Spring boot application
Below are the steps involved in developing the application.

### Maven Dependency
Here, we specify the dependency for the Spring boot, Redis, and Jedis. Maven will automatically resolve the other dependencies. The updated file will have the following code.

pom.xml

```
<project xmlns="http://maven.apache.org/POM/4.0.0"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0<modelVersion>
 
    <!-- project properties -->
    <groupId>com.springboot.redis<groupId>
    <artifactId>SpringbootRedisUsingJedis<artifactId>
    <version>0.0.1-SNAPSHOT<version>
 
    <!-- application name -->
    <name>Springboot Redis tutorial Using Jedis<name>
    <description>Springboot and Redis tutorial<description>
 
    <!-- spring boot starter parent dependency -->
    <parent>
        <groupId>org.springframework.boot<groupId>
        <artifactId>spring-boot-starter-parent<artifactId>
        <version>2.3.1.RELEASE<version>
    <parent>
 
    <properties>
        <!-- setting the java version as jdk1.8 -->
        <java.version>1.8<java.version>
    <properties>
 
    <dependencies>
        <!-- spring data redis dependency -->
        <dependency>
            <groupId>org.springframework.boot<groupId>
            <artifactId>spring-boot-starter-data-redis<artifactId>
        <dependency>
        <dependency>
            <groupId>org.springframework.boot<groupId>
            <artifactId>spring-boot-starter-web<artifactId>
        <dependency>
        <dependency>
            <groupId>redis.clients<groupId>
            <artifactId>jedis<artifactId>
        <dependency>
    <dependencies>
 
    <build>
        <!-- final jar name -->
        <finalName>SpringbootRedisUsingJedis<finalName>
        <!-- to make the application as fat jar so that spring boot libraries are 
            included -->
        <plugins>
            <plugin>
                <groupId>org.springframework.boot<groupId>
                <artifactId>spring-boot-maven-plugin<artifactId>
            <plugin>
        <plugins>
    <build>
<project>
```
##Application Properties
Create a new properties file at the location: SpringbootRedisUsingJedis/src/main/resources/ and add the Application and Redis configuration to this file.

application.properties
```
# Application configuration.
## Reader can change the server port configuration as per their configuration idea.
server.port=10091
 
# Redis configuration.
## As we are running Redis on Docker we are setting up its configuration.
spring.redis.host=localhost
spring.redis.port=6379
```

###4.3 Java Classes
Let us write all the java classes involved in this application.

####4.3.1 Implementation/Main class
Add the following code to the main class to bootstrap the application from the main method. Always remember, the entry point of the spring boot application is the class containing @SpringBootApplication annotation and the static main method.

SpringbootRedis.java

```
package org.jcg.springboot.redis;
 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
 
@SpringBootApplication
public class SpringbootRedis {
 
    private static final Logger LOG = LoggerFactory.getLogger(SpringbootRedis.class);
     
    // Main program to start up the spring boot application.
    public static void main(String[] args) {
        SpringApplication.run(SpringbootRedis.class, args);
        LOG.info("Springboot redis application is started successfully.");
    }
}
```
####Model class
Add the following code to the Employee model class where we’ll define the basic attributes for this class.

Employee.java

```
package org.jcg.springboot.redis.model;
 
import java.io.Serializable;
 
import org.springframework.stereotype.Component;
 
// Employee model class has basic employee-related attributes.
@Component
public class Employee implements Serializable {
 
    private static final long serialVersionUID = 1L;
 
    private String id;
    private String name;
    private int age;
    private Double salary;
     
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
     
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
     
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
     
    public Double getSalary() {
        return salary;
    }
    public void setSalary(Double salary) {
        this.salary = salary;
    }
}
```
#### Configuration class
Add the following code to the configuration class where we define the Jedis Connection Factory and the Redis Template.

RedisConfig.java
```
package org.jcg.springboot.redis.config;
 
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
 
// Configuration class to set up the Redis configuration.
@Configuration
public class RedisConfig {
 
    // Setting up the Jedis connection factory.
    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        return new JedisConnectionFactory();
    }
      
    // Setting up the Redis template object.
    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        final RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
        redisTemplate.setConnectionFactory(jedisConnectionFactory());
        redisTemplate.setValueSerializer(new GenericToStringSerializer<Object>(Object.class));
        return redisTemplate;
    }
}
```
#### Service class
Add the following code to the service class where we will perform the operations using the Redis Template and Hash Operations. Here this class implements the Employeerepo interface. It is a simple interface that declares the methods overridden in this class.

EmployeeService.java

```
package org.jcg.springboot.redis.service;
 
import java.util.Map;
 
import javax.annotation.PostConstruct;
 
import org.jcg.springboot.redis.dao.Employeerepo;
import org.jcg.springboot.redis.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
 
@Service
public class EmployeeService implements Employeerepo {
 
    private final String EMPLOYEE_CACHE = "EMPLOYEE";
 
    @Autowired
    RedisTemplate<String, Object> redisTemplate;
    private HashOperations<String, String, Employee> hashOperations;
 
    // This annotation makes sure that the method needs to be executed after 
    // dependency injection is done to perform any initialization.
    @PostConstruct
    private void intializeHashOperations() {
        hashOperations = redisTemplate.opsForHash();
    }
 
    // Save operation.
    @Override
    public void save(final Employee employee) {
        hashOperations.put(EMPLOYEE_CACHE, employee.getId(), employee);
    }
 
    // Find by employee id operation.
    @Override
    public Employee findById(final String id) {
        return (Employee) hashOperations.get(EMPLOYEE_CACHE, id);
    }
 
    // Find all employees' operation.
    @Override
    public Map<String, Employee> findAll() {
        return hashOperations.entries(EMPLOYEE_CACHE);
    }
 
    // Delete employee by id operation.
    @Override
    public void delete(String id) {
        hashOperations.delete(EMPLOYEE_CACHE, id);
    }
}
```
#### Controller class
Add the following code to the controller class designed to handle the incoming requests. The class is annotated with the @RestController annotation where every method returns a domain object as a JSON response instead of a view.

EmployeeController.java

```
package org.jcg.springboot.redis.controller;
 
import java.util.Map;
 
import org.jcg.springboot.redis.model.Employee;
import org.jcg.springboot.redis.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
 
// In this class, we have left the caching approach for tutorial simplicity.
// If users which they can enable caching in this application.
@RestController
@RequestMapping(value = "/api/redis/employee")
public class EmployeeController {
 
    private static final Logger LOG = LoggerFactory.getLogger(EmployeeController.class);
 
    @Autowired
    EmployeeService service;
 
    // Save a new employee.
    // Url - http://localhost:10091/api/redis/employee
    @PostMapping
    public String save(@RequestBody final Employee employee) {
        LOG.info("Saving the new employee to the redis.");
        service.save(employee);
        return "Successfully added. Employee with id= " + employee.getId();
    }
 
    // Get all employees.
    // Url - http://localhost:10091/api/redis/employee/getall
    @GetMapping("/getall")
    public Map<String, Employee> findAll() {
        LOG.info("Fetching all employees from the redis.");
        final Map<String, Employee> employeeMap = service.findAll();
        // Todo - If developers like they can sort the map (optional).
        return employeeMap;
    }
 
    // Get employee by id.
    // Url - http://localhost:10091/api/redis/employee/get/<employee_id>
    @GetMapping("/get/{id}")
    public Employee findById(@PathVariable("id") final String id) {
        LOG.info("Fetching employee with id= " + id);
        return service.findById(id);
    }
 
    // Delete employee by id.
    // Url - http://localhost:10091/api/redis/employee/delete/<employee_id>
    @DeleteMapping("/delete/{id}")
    public Map<String, Employee> delete(@PathVariable("id") final String id) {
        LOG.info("Deleting employee with id= " + id);
        // Deleting the employee.
        service.delete(id);
        // Returning the all employees (post the deleted one).
        return findAll();
    }
}
```
##Run the Application
To execute the application, right-click on the SpringbootRedis.java class, Run As -> Java Application.

## Project Demo
Open the Postman tool and hit the following URLs to display the data in the JSON format.
```sh
// Save a new employee.
// Url - http://localhost:10091/api/redis/employee
 
// Get all employees.
// Url - http://localhost:10091/api/redis/employee/getall
 
// Get employee by id.
// Url - http://localhost:10091/api/redis/employee/get/<employee_id>
 
// Delete employee by id.
// Url - http://localhost:10091/api/redis/employee/delete/<employee_id>
That is all for this tutorial and I hope the article served you whatever you were looking for. Happy Learning and do not forget to share!
```
