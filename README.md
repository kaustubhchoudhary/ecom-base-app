# ecom-base-app

## 1. Project Overview

Welcome to **ecom-base-app**!

`ecom-base-app` is a Mini E-Commerce REST API application built using Java, Spring Boot, Spring Data JPA, Hibernate, and MySQL.

Use this project to explore the backend of a basic e-commerce system and understand how its components work together. The application covers core e-commerce concepts such as:

- User management
- Category and product management
- Order management
- Payment and transaction management
- Order cancellation

The project provides a foundation that you can explore, test, and extend as you learn additional Java and Spring technologies.

### Explore the Project Resources

Use the following resources to understand the application and get started:

- **Business Requirements:** [`docs/01_brd.md`](docs/01_brd.md)
- **Database Design:** [`docs/02_er_schema.md`](docs/02_er_schema.md)
- **API Specifications:** [`docs/03_api_specifications.md`](docs/03_api_specifications.md)
- **API Authorization:** [`docs/04_protected_apis.md`](docs/04_protected_apis.md)
- **Postman Collection:** [`docs/05_postman_collection.json`](docs/05_postman_collection.json)
- **Learning Resources:** [`learning_resources/`](learning_resources/)
- **Product Images for API Testing:** [`product_images/`](product_images/)

Start by reviewing the business requirements and database design. Then set up the application, explore the API specifications, and use the Postman collection to test the available endpoints.

## 2. Technology Stack

- Java 25
- Spring Boot
- Spring Data JPA
- Hibernate
- MySQL 8.0
- Maven
- REST APIs

## 3. Prerequisites and Installation

Before running the application, install and configure the following tools.

### 3.1 Install Java Development Kit (JDK)

The application requires **JDK 25**.

1. Download JDK 25 from the [official Java downloads page](https://www.oracle.com/java/technologies/downloads/).
2. Select the Windows installer for your system and complete the installation.
3. Open a new terminal and verify the installation:

```text
java -version
javac -version
```

Both commands should display Java version 25.

### 3.2 Install MySQL Server

MySQL Server stores the application's data.

1. Visit the [MySQL Community Server download page](https://dev.mysql.com/downloads/mysql/).
2. Select Windows and choose a **MySQL 8.0** release.
3. Download the installer and follow the installation instructions.
4. Configure the MySQL root account password and remember it for application configuration.
5. Ensure the MySQL server is running.

The database `ecommerce_db` will be created during the project setup.

### 3.3 Install MySQL Workbench

MySQL Workbench provides a graphical interface for connecting to MySQL, executing SQL queries, and managing databases.

1. Download it from the [official MySQL Workbench page](https://dev.mysql.com/downloads/workbench/).
2. Install and open MySQL Workbench.
3. Create a connection to your local MySQL Server using the configured credentials.
4. Test the connection.

**Note:** MySQL Server and MySQL Workbench are separate tools. Installing Workbench alone does not install the database server.

### 3.4 Install Visual Studio Code and required extensions

VS Code is the recommended editor for exploring and working with this project.

1. Download VS Code from the [official download page](https://code.visualstudio.com/Download).
2. Run the Windows installer and complete the installation.
3. Open VS Code and install the **Extension Pack for Java** from the Extensions view.
4. Open the cloned `ecom-base-app` folder in VS Code.

After installing VS Code, open the Extensions view and install the following extensions:

- **Extension Pack for Java** - Provides essential Java development tools.
- **Spring Boot Extension Pack** - Provides tools for developing Spring Boot applications.

Search for each extension by name in the VS Code Extensions Marketplace and install it.

### 3.5 Install Git

Git is used to clone the repository and manage source code.

1. Download Git for Windows from the [official Git website](https://git-scm.com/install/windows).
2. Install Git using the recommended defaults.
3. Open a new terminal and verify the installation:

```text
git --version
```

### 3.6 Install Postman

Postman is used to send HTTP requests to the application's REST APIs and inspect their responses.

1. Download Postman from the [official download page](https://www.postman.com/downloads/).
2. Install and open Postman.
3. You will use it to import the project's API collection and test the endpoints.

### 3.7 Maven

This project includes the **Maven Wrapper** (`mvnw` and `mvnw.cmd`), which can download and use the configured Maven version when required.

A separate Maven installation is generally not necessary.

After cloning the repository, open a terminal in the project root directory and run:

```text
.\mvnw.cmd -version
```

This displays the Maven and Java versions used by the wrapper.

### Installation Checklist

Before proceeding to project setup, ensure that:

- [ ] JDK 25 is installed and working.
- [ ] MySQL Server 8.0 is installed and running.
- [ ] MySQL Workbench can connect to the local server.
- [ ] VS Code and the Extension Pack for Java are installed.
- [ ] Git is installed and available in the terminal.
- [ ] Postman is installed.

## 4. Project Setup

Follow these steps to clone, configure, and run the application on your local machine.

### 4.1 Clone the Repository

Open a terminal and execute the following commands:

```powershell
git clone https://github.com/kaustubhchoudhary/ecom-base-app.git
cd ecom-base-app
```

### 4.2 Create the Database

1. Open Task Manager, go to Services, locate MySQL service and run it.
2. Open MySQL Workbench and connect to your local MySQL Server.
3. Open a new SQL query tab and execute:

```sql
CREATE DATABASE ecommerce_db;
```

### 4.3 Configure Database Credentials and Server Port

Open the following file in VS Code:

`src/main/resources/application.properties`

Update the database connection settings to match your local MySQL configuration.

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/ecommerce_db
spring.datasource.username=YOUR_MYSQL_USERNAME
spring.datasource.password=YOUR_MYSQL_PASSWORD

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

server.port=9192
```

**Important:**

- Replace `YOUR_MYSQL_USERNAME` with your MySQL username.
- Replace `YOUR_MYSQL_PASSWORD` with the password configured for that MySQL account.
- The example assumes MySQL is running on port `3306`. Change it if your MySQL Server uses a different port.
- The application is configured to run on port `9192`.
- Do not commit your actual database password to a public Git repository.

Save the file before proceeding.

### 4.4 Run and Verify the Application

1. Open the VS Code terminal in the project root directory.
2. Run the application:

   ```powershell
   .\mvnw.cmd spring-boot:run
   ```

3. Check the terminal logs for successful Spring Boot startup on port `9192`.
4. If SQL logging is enabled, observe the SQL statements in the terminal.
5. Open MySQL Workbench and refresh the `ecommerce_db` schema to verify that the application tables have been created.

### 4.5 Test the API:

To test the REST APIs, import the Postman collection provided in `docs/04_postman_collection.json`.

The collection is available at:

```text
docs/04_postman_collection.json
```

### Import into Postman

1. Open Postman.
2. Click **Import**.
3. Browse and select **04_postman_collection.json** from the project's `docs/` folder.
4. Open the imported collection and expand the **E-Com Base API → Categories** folder.
5. Select **Get All Categories** and click **Send**.

The request uses the following API URL:

```text
http://localhost:9192/api/v1/categories/
```

If the database contains no categories, the API returns:

```json
{
  "message": "No Categories found in the database",
  "data": null
}
```

- This indicates that no categories are currently available.
- You can create categories using the available API requests and execute **Get All Categories** again to retrieve them.

## 5. Project Structure

The following structure highlights the main application code, configuration, and supporting resources.

```text
ecom-base-app/
├── docs/
│   ├── 01_brd.md
│   ├── 02_er_schema.md
│   ├── 03_api_specifications.md
│   ├── 04_postman_collection.json
│   └── ecommerce_db.sql
├── learning_resources/
├── product_images/
└── src/
    ├── main/
    │   ├── java/ecom/base/app/
    │   │   ├── orders/
    │   │   ├── payments/
    │   │   ├── product/
    │   │   ├── response/
    │   │   ├── role/
    │   │   ├── transactions/
    │   │   ├── user/
    │   │   └── EcomBaseAppApplication.java
    │   └── resources/
    │       └── application.properties
    └── test/
```

### Directory Overview

- **`src/main/java/ecom/base/app/`** - Java application source code.
- **`src/main/resources/application.properties`** - Server port and database configuration.
- **`src/test/`** - Test source directory.
- **`docs/`** - Project documentation, API specifications, Postman collection, and SQL script.
- **`learning_resources/`** - Learning materials and reference images.
- **`product_images/`** - Sample images for product API testing.

## 6. Project Documentation

The `docs/` directory contains the documentation required to understand the application's requirements, database design, and APIs.

| Document              | Location                          | Purpose                             |
| --------------------- | --------------------------------- | ----------------------------------- |
| Business Requirements | `docs/01_brd.md`                  | Business requirements and scope     |
| ER Schema             | `docs/02_er_schema.md`            | Database entities and relationships |
| API Specifications    | `docs/03_api_specifications.md`   | API contract and endpoints          |
| Postman Collection    | `docs/04_postman_collection.json` | API requests and test data          |

## 7. Learning Opportunities

This project serves as a foundation for exploring additional technologies and software engineering practices. Use the existing application to implement and practise the following:

- **API Documentation:** Swagger / OpenAPI
- **Testing:** JUnit 5, Mockito, Spring Boot Test
- **Security:** Spring Security, JWT, role-based access control, OAuth 2.0
- **Backend Engineering:** Validation, exception handling, logging, AOP, Actuator
- **Frontend Integration:** Angular or React
- **Microservices:** Decompose the application into independent services
- **DevOps:** Docker, Kubernetes, Jenkins, CI/CD
- **Cloud:** Deploy the application to AWS
- **AI Integration:** Spring AI, LLM APIs, and Retrieval-Augmented Generation (RAG)

Implement these features incrementally on separate Git branches so you can practise each technology independently while maintaining a working base application.
