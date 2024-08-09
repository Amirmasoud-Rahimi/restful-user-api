# RESTful User API

**User Management REST API with Spring Boot 3.3.2 and JWT Authentication**

### Table of Contents
- [Description](#Description)
- [Installation](#Installation)
- [Set Up The Project](#Set-Up-The-Project)
- [Github](#Github)
- [Development](#Development)
- [Usage](#usage)
- [Configuration](#configuration)
- [Endpoints](#endpoints)
- [Authentication](#authentication)
- [Developer](#developer)

### Description
*As a passionate software developer, I recently crafted an exciting project using Spring Boot to be a Showcase of Modern Java Technologies. Here are the details:*

**Technology Stack:**
 - **Java 17**
 - **Spring Boot 3.3.2**: Leveraging the power of Spring Boot for rapid development.
 - **Spring Web**:Build web, including RESTful, applications using Spring MVC. Uses Apache Tomcat as the default embedded container.
 - **Spring Data JPA**: Simplifying database interactions with elegant JPA repositories.
 - **Spring Security**: Highly customizable authentication and access-control framework for Spring applications.
 - **JWT Authentication**: Ensuring secure user authentication and authorization.
 - **H2 Database**: A lightweight, in-memory database for efficient data management during development.
 - **Lombok**: Streamlining code by reducing boilerplate.
 - **Hateoas**: following the HATEOAS principle and allows you to guide clients by returning relevant information about the next potential steps 

**Features:**
 - **User Entity Management**: CRUD operations for user profiles.
 - **JWT Security**: Robust authentication and token-based authorization.
 - **Clean Code**: Utilizing Lombok to keep code concise and maintainable.
 - **H2 Database**: Easy setup for development and testing.
 - **RESTful Endpoints**: Well-designed APIs for seamless communication.

**Why This Matters:**
 - **Cutting-Edge Java**: Embrace the latest Java technologies.
 - **Scalable**: Designed with scalability in mind, ready for future enhancements.
 - **Security First**: JWT ensures robust authentication and secure user interactions.
 - **Performance**: H2 database for rapid development iterations.
 -  **Developer Experience**: Lombok simplifies code maintenance and reduces boilerplate, making code elegant.

**What I Learned:**
 - Building RESTful APIs with Spring Boot.
 - Integrating JWT for secure authentication.
 - Managing data using Spring Data JPA.
 - Keeping code clean and maintainable.
 - API documentation using swagger.
  
🔗 **Let’s connect! Feel free to reach out—I’m always up for tech discussions and collaboration and explore new opportunities.**

## Installation
**Prerequisites**
- Install Git
- Install JDK 17 or higher 
- Install Maven 3.7.9 or higher
- Add **JAVA_HOME** and **Java path** to environment variables
- Add **MAVEN_HOME** and **Maven path** to environment variables
 
**Run API**
- Clone **restful-user-api** repository using `git clone https://github.com/Amirmasoud-Rahimi/restful-user-api`
- Navigate to Project Directory
- Build the project using `mvn clean install`
- Run the project using `mvn spring-boot:run`
- The web application is accessible via <http://localhost:8585>
- Swagger UI is accessible vai <http://localhost:8585/swagger-ui/index.html>
- H2 in-memory database console is accessible vai "http://localhost:8585/h2-console"
- Use *username* as **user** and *password* as **password** to log in to h2-console
- Use *username* as *super.admin@test.com* and *password* as **super@admin** to log in with a SUPER-ADMIN user privilege.

### Set Up The Project
***Generate new Spring boot project in <https://start.spring.io/> with below dependencies and details:***
 - Project: *Maven*
 - Language: *Java*
 - Spring Boot: *3.3.2*
  ##### Project Metadata:
  - Group: *com.project.api*
  - Artifact: *restful-user-api*
  - Name: *restful-user-api*
  - Description: *RESTful API using Spring Boot 3.3.2 with JWT authentication to Manage User Entity*
  - Package Name: *com.project.restful-user-api*
  - Packaging: *Jar*
  - Java: *17*
 ##### Dependencies:
  - ###### Spring Web 
    *Build web, including RESTful, applications using Spring MVC. Uses Apache Tomcat as the default embedded container*
  - ###### Spring Data JPA 
    *Persist data in SQL stores with Java Persistence API using Spring Data and Hibernate*
  - ###### Spring Security
    *Highly customizable authentication and access-control framework for Spring applications*
  - ###### H2 Database
    *Provides a fast in-memory database that supports JDBC API. Supports embedded and server modes as well as a browser based console application*
  - ###### Lombok
    *Streamlining code by reducing boilerplate.*
  - ###### Spring HATEOAS
     *Eases the creation of RESTful APIs that follow the HATEOAS principle when working with Spring*
### Github
**Create new repository on github and add project to github repository using command line:**

 `git init`
 
 `git add .`
 
 `git commit -m "commit message"`
 
 `git remote add origin https://github.com/username/repository.git`
 
 `git remote -v`
 
 `git fetch`
 
 `git pull origin master --allow-unrelated-histories`
 
 `git push --set-upstream origin master`

## Development
- Configure the database connection
- Add swagger ui dependency and configuration
- Install json web token dependencies
- Create the User entity
- Extend the User entity with authentication details
- Create the JWT Service
- Create Application Configuration
- Create the authentication middleware
- Configure the application requester filter
- Create the authentication service
- Create user registration and authentication routes
- Create restricted endpoints to retrieve users
- Customize authentication error messages GlobalExceptionHandler

## Usage
- Sign Up to create new user and persist in database.
- Sign In using user information to get jwt token.
- To create user with ADMIN role sign in with super admin user information to get   jwt token with proper access privilege.
- Use Admin Controller to create admin user.
- Use admin to manage user in user controller.

## Configuration
**Add configuration to *application.properties***

- H2 in-memory Database configuration
 
`spring.datasource.url=jdbc:h2:mem:default`

`spring.datasource.driverClassName=org.h2.Driver`

`spring.datasource.username=user`

`spring.datasource.password=password`

`spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.H2Dialect`

`spring.jpa.show-sql=true`

`spring.h2.console.enabled=true`

- JWT Token configuration
 
`security.jwt.secret-key=3cfa76ef14937c1c0ea519f8fc057a80fcd04a7420f8e8bcd0a7567c272e007b`

`security.jwt.expiration-time=3600000`

## Endpoints
Controller | API route | Access status | Description | Role | Request Example | Expected Response
--- | --- | --- | --- | --- | --- | --- |
AuthenticationController | [POST] /auth/signup | Unprotected | Register a new User | | <http://localhost:8585/auth/signUp> | User entity
AuthenticationController |[POST] /auth/signIn | Unprotected | Authenticate User |  | <http://localhost:8585/auth/signIn> | LoginResponseDto
AuthenticationController |[POST] /auth/update | Unprotected | Update User | |<http://localhost:8585/auth/update>|User entity
AdminController |[POST] /admin/ | Protected | Create new user with SUPER_ADMIN role | SUPER_ADMIN | <http://localhost:8585/admin> | User entity
UserController |[GET] /user/me | Protected | Retrieve the current authenticated user | Authenticate User | <http://localhost:8585/user/me> | User entity
UserController |[GET] /user/users | Protected | Returns all users | ADMIN, SUPER_ADMIN | <http://localhost:8585/user/users> | List of User entites
UserController |[GET] /user/{id} | Protected | Fetche User by user id | ADMIN, SUPER_ADMIN | <http://localhost:8585/user/2> | User entity
UserController |[GET] /user/greeting/{id} | Protected | Show welcome message to user | USER | <http://localhost:8585/user/greeting/2> | String

## Authentication
##### JWT Authentication #####

 ***What is JWT?***
- JWT is a URL-safe, encoded, and cryptographically signed string.
- It empower modern authentication by providing a lightweight, secure way to manage user identity
- It serves as a token for various applications, including authentication and authorization.

 ***How It Works:***
- The client sends credentials (e.g., username and password) to the server.
- The server validates these credentials.
- If valid, the server generates a JWT containing user claims (information).
- The JWT is sent back to the client

***JWT Structure:***

- ***Header***: *Describes the token (e.g., algorithm used for signing).*
- ***Payload***: *Contains user data (claims) in a JSON format.*
- ***Signature***: *Ensures the token’s integrity and authenticity*

 ***Advantages of JWT:***
- ***Stateless***: *No need for server-side session storage.*
- ***Scalable***: *Works well in distributed or cloud-based infrastructures.*
- ***Secure***: *Cryptographically signed to prevent tampering.*

## Developer
[**Amirmasoud Rahimi**](https://github.com/Amirmasoud-Rahimi)
