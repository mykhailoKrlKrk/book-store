# 📚Book Store 🔖
 ![Java Badge](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white) ![Spring Boot Badge](https://img.shields.io/badge/Spring_Boot-F2F4F9?style=for-the-badge&logo=spring-boot)  ![Spring Security](https://img.shields.io/badge/Spring_Security-6DB33F?style=for-the-badge&logo=Spring-Security&logoColor=white) ![Hibernate Badge](https://img.shields.io/badge/Hibernate-59666C?style=for-the-badge&logo=Hibernate&logoColor=white) ![MySQL Badge](https://img.shields.io/badge/MySQL-005C84?style=for-the-badge&logo=mysql&logoColor=white) ![AWSBadge](https://img.shields.io/badge/Amazon_AWS-FF9900?style=for-the-badge&logo=amazonaws&logoColor=white)    ![Swagger](https://img.shields.io/badge/Swagger-85EA2D?style=for-the-badge&logo=Swagger&logoColor=white)



### 📖 Project Overview 
- This web application showcases a full-fledged online bookstore, enabling users to easily select items and complete orders.
### 🌅 Technical Abilities
| Action                     | User Role               | Description                    |
|---------------------------|-------------------------|--------------------------------|
| Authenticate/register      | User                    | Register and authenticate users |
| Update book by id         | Admin                   | Update book details             |
| Get all books             | User/Admin              | Retrieve all books              |
| Create book               | Admin                   | Add a new book                  |
| Find book by id           | User/Admin              | Retrieve a book by its ID       |
| Delete book               | Admin                   | Remove a book                   |
| Search books by params    | User                    | Search for books using parameters |
| Create category           | Admin                   | Add a new category              |
| Get all categories        | User/Admin              | Retrieve all categories         |
| Update category           | Admin                   | Modify category details         |
| Get category by id        | User/Admin              | Retrieve category by ID         |
| Delete category           | Admin                   | Remove a category               |
| Get book by category id   | User                    | Retrieve books by category ID   |
| Update order status       | Admin                   | Update order status             |
| Add book to shopping cart | User                    | Add a book to the shopping cart |
| Update shopping cart      | User                    | Modify items in the shopping cart |
| Delete book from cart     | User                    | Remove a book from the cart     |
| Create order              | User                    | Place a new order               |
| Update order status       | Admin                   | Update order status             |
| Get order items           | User                    | Retrieve order items            |
| Get order item by id      | User                    | Retrieve an order item by its ID|



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
| Mapstruct and Lombok              |Mapstruct and Lombok are two useful tools that can simplify the process of mapping objects between Java beans. By generating mapping code and providing annotations for common methods like getters, setters, and constructors, these tools can help to reduce boilerplate code and make Java code more concise and readable. When used together in Spring Boot applications, they can enhance code maintainability and improve developer productivity.|





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
10. 📊 Testing layer using testcontainers: The project has implemented 50 integration tests to assess the functionality of the program under various conditions and exceptional situations at the repository, service, and controller levels. An additional plugin (jacoco) is utilized to show the Coverage results in a schematic manner.

### 🛠️ Integration and Usage
---
List of technologies required for use: 

- Docker
- MySQL
- Additional: Postman (for testing endpoints)  
#### 🚀 Run Project

Please make sure that you have all the required technologies installed before proceeding. Once that's done, follow the steps below:

1. Run Docker.
2. Change the .env file to match your database settings.
3. Open your terminal and navigate to the appropriate directory. Run the command `docker compose up`.
4. For testing using Swagger, open your browser and enter the URL: `http://localhost:8080/api/swagger-ui/index.html#/`. To authenticate, use the following credentials: User: bob@mail.com, Password: BobOne (this is the default user created by Liquibase, but you can change the details if needed).
5. To test using Postman, open the Postman web or desktop client and enter the URL `http://localhost:8088/api/{endpoint}`. For example, to test the books endpoint, enter `http://localhost:8088/api/books`.
