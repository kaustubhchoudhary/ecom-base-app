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

This project is developed using **Visual Studio Code (VS Code)** on Windows. Before running the application, install and configure the following tools.

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

### 3.4 Install Visual Studio Code

VS Code is the recommended editor for exploring and working with this project.

1. Download VS Code from the [official download page](https://code.visualstudio.com/Download).
2. Run the Windows installer and complete the installation.
3. Open VS Code and install the **Extension Pack for Java** from the Extensions view.
4. Open the cloned `ecom-base-app` folder in VS Code.

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

1. Open MySQL Workbench and connect to your local MySQL Server.
2. Open a new SQL query tab and execute:

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

### 4.4 Run the Application

Ensure that MySQL Server is running. Open the VS Code terminal in the project root directory and execute:

```powershell
.\mvnw.cmd spring-boot:run
```

Wait for Spring Boot to finish starting. Check the terminal logs for a successful startup message.

### 4.5 Verify the Application

#### Test the Server:

After starting the application, check the terminal logs to ensure that Spring Boot has started successfully.

#### Test the API:

All REST APIs in this application use the following base URL:

```text
http://localhost:9192/api/v1
```

To verify that an API is responding, open Postman and send the following request:

```http
GET http://localhost:9192/api/v1/categories
```

If the database contains no categories, the API returns the following response:

```json
{
  "message": "No Categories found in the database",
  "data": null
}
```

This response indicates that no categories are currently available. You can create categories using the available API endpoints and retrieve them using the corresponding GET request.

## 6. Project Documentation

Detailed project documentation is available inside the `docs/` directory.

| Document              | Location                          | Purpose                              |
| --------------------- | --------------------------------- | ------------------------------------ |
| Business Requirements | `docs/01_brd.md`                  | Business requirements and scope      |
| ER Schema             | `docs/02_er_schema.md`            | Database entities and relationships  |
| API Specifications    | `docs/03_api_specifications.md`   | API contract and endpoints           |
| API Authorization     | `docs/04_protected_apis.md`       | Authentication, roles and API access |
| Postman Collection    | `docs/05_postman_collection.json` | API requests and test data           |

## 7. Project Structure

```text
ecom-base-app/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   └── resources/
│   │       └── application.properties
│   │
│   └── test/
│
├── docs/
│   ├── 01_brd.md
│   ├── 02_er_schema.md
│   ├── 03_api_specifications.md
│   ├── 04_protected_apis.md
│   └── 05_postman_collection.json
│
├── pom.xml
├── mvnw
├── mvnw.cmd
└── README.md
```

## 8. Import Postman Collection

The project includes a Postman collection for testing the APIs.

The collection is available at:

```text
docs/05_postman_collection.json
```

### Import into Postman

1. Open Postman.
2. Click **Import**.
3. Select **05_postman_collection.json** from the project's `docs/` folder.
4. Postman will import the complete **E-Com Base API** collection.
5. Open the imported collection and use the required API requests.

The collection uses the following base URL:

```text
http://localhost:9192/api/v1
```

Make sure the Spring Boot application is running before sending requests.

````

## 9. Adding data containing image

### Testing Product Creation API

To test the Product creation API using Postman:

1. Open the `E-Com Base API` collection.
2. Open the `Products` folder.
3. Select **Add a Product**.
4. Set the request method to `POST`.
5. Set the URL to:

```text
   {{baseUrl}}/products
````

6. Select **Body → form-data**.

7. Add a field named `product` with type **Text**.

8. Set the `Content-Type` of the `product` part to:

   ```text
   application/json
   ```

9. Enter the Product JSON in the `product` field.

10. Add a field named `image` with type **File**.

11. Select the product image file.

12. Click **Send**.

13. Verify that the API returns `201 Created`.

```

That captures the exact steps someone needs to successfully test your multipart Product API.
```

## 10. Current Status

The project currently provides the base application structure and the initial Category functionality.

Additional modules will be implemented progressively.

## 10. Future Enhancements

The project can be extended with technologies and concepts such as:

- Spring Security
- JWT Authentication
- Validation
- Exception Handling
- AOP
- Actuator
- JUnit 5
- Mockito
- Spring Boot Testing
- Swagger / OpenAPI
- Microservices
- Kafka
- Docker
- CI/CD

```

```

```

```
