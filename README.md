# 📚Book Store 🔖
 ![Java Badge](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)  ![MySQL Badge](https://img.shields.io/badge/MySQL-005C84?style=for-the-badge&logo=mysql&logoColor=white) ![AWSBadge](https://img.shields.io/badge/Amazon_AWS-FF9900?style=for-the-badge&logo=amazonaws&logoColor=white) ![Hibernate Badge](https://img.shields.io/badge/Hibernate-59666C?style=for-the-badge&logo=Hibernate&logoColor=white) ![Spring Boot Badge](https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white)



### 📖 Project Overview 
- This web application showcases a full-fledged online bookstore, enabling users to easily select items and complete orders.
### ⚙️ Technical features
---
- The main framework of the project is Spring Boot which provides various tools for developing web applications with wide functionality.

- Pom.xml dependencies overwiev:

| Feature                           | Usage                                                                  |
|----------------------------------|------------------------------------------------------------------------------|
| Spring Boot Security and JWT Token | Enable secure user authentication and data protection.                      |
| Spring Boot Data JPA                | This feature enables users to interact with the database and perform various operations. It provides a range of functionalities to manipulate and manage the data stored in the database. creation, updating, and removal.        |
| Liquibase          | Effectively manage database structure with generic scripts that can be adapted to different types of databases.  |
| Docker                  | A containerization platform simplifies deployment, improves portability, and eases management of Spring Boot applications and their dependencies by providing an isolated runtime environment for applications. |
| Integrational tests usning testcontainers                    | With Test containers, tests run against a real database instance, ensuring the validity of SQL queries and transactions.                 |
| Hibernate          | An object-relational mapping (ORM) framework that simplifies database interaction in Spring Boot applications. It automates the mapping of Java classes to database tables, streamlining data persistence and retrieval operations.                                            |
| Mapstruct and Lombok              |Mapstruct and Lombok are two useful tools that can simplify the process of mapping objects between Java beans. By generating mapping code and providing annotations for common methods like getters, setters, and constructors, these tools can help to reduce boilerplate code and make Java code more concise and readable. When used together in Spring Boot applications, they can enhance code maintainability and improve developer productivity.

## 🧱 Project structure 
--- 
This project follows a structured approach utilizing the n-tier architecture, ensuring a clear separation of concerns and distinct functional levels within the program.

1. 🎯 Controllers Layer: This layer is responsible for handling HTTP requests and crafting responses. 

2.  📦 DTO: DTO stands for Data Transfer Objects. These are objects used for transferring data between different components of the application. 

3. ⚠️ Exception: The exception component utilizes globalExceptionHandler to effectively handle exceptions and errors. This enhances error management within the program. 

4. 🗺️ Mapper: The Mapper component is responsible for implementing mapping between objects, particularly between DTOs and domain objects. 

5. 🧩 Model: The Model component contains the application's data models and related functionalities. 

6. 🏦 Repository: The Repository component is responsible for interacting with the database and executing queries. 

7. 🔒 Security: The Security component incorporates JWT token-based security for authentication and authorization. 

8. ⚙️ Service: The Service component contains the core business logic of the application. 

9. ✅ Validation: The Validation component ensures validation and verification of input data.
