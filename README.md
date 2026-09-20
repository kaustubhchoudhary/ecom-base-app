# ecom-base-app

Mini E-Commerce REST API built with Spring Boot.

## 1. Project Overview

`ecom-base-app` is a backend REST API for a mini e-commerce application.

The application is designed as a practical base project covering the core backend flow of an e-commerce system:

- User management
- Category management
- Product management
- Order management
- Payment processing
- Transaction management
- Order cancellation
- Role-based API authorization

The project is intentionally kept modular so that additional technologies and concepts can be introduced progressively.

## 2. Technology Stack

- Java 25
- Spring Boot
- Spring Data JPA
- Hibernate
- MySQL 8.0
- Maven
- REST APIs

## 3. Prerequisites

Make sure the following are installed on your Windows system:

- JDK 25
- MySQL 8.0
- Git
- VS Code or any Java-compatible IDE

Verify Java:

```text
java -version
```

Verify Maven Wrapper:

```text
mvnw.cmd -version
```

## 4. Project Setup

### Step 1: Clone the Repository

```bash
git clone https://github.com/kaustubhchoudhary/ecom-base-app.git
```

Move into the project directory:

```bash
cd ecom-base-app
```

### Step 2: Create the Database

Open MySQL and execute:

```sql
CREATE DATABASE ecommerce_db;
```

### Step 3: Configure Database Connection

Open:

```text
src/main/resources/application.properties
```

Configure the MySQL connection:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/ecommerce_db
spring.datasource.username=root
spring.datasource.password=1234
```

Update the username and password according to your local MySQL setup.

The application uses:

```properties
spring.jpa.hibernate.ddl-auto=update
```

Hibernate will automatically create or update the required database tables.

### Step 4: Run the Application

On Windows:

```bash
mvnw.cmd spring-boot:run
```

The application will start on:

```text
http://localhost:9192
```

## 5. API Base URL

All application APIs use:

```text
http://localhost:9192/api/v1
```

Example:

```text
GET http://localhost:9192/api/v1/categories
```

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
