Yes. For **Document 3**, save it as:

```text
docs/03_api_specifications.md
```

Below is the Markdown conversion of your uploaded API Specification, preserving its structure and content.

````markdown
# Document 3 - API Specifications

## Mini E-Commerce Application

_Purpose:_ Define the REST APIs required by the application.

---

# 1. Purpose

The API layer provides communication between the client and the e-commerce application.

The APIs define:

- Resources
- HTTP methods
- Endpoints
- Expected response data
- HTTP status codes
- REST naming conventions
- Success response format
- Error response format

Authorization is intentionally excluded.

Authorization will be defined separately in:

_Document 4 - Protected APIs Based on Roles_

---

# 2. API Base Path

All APIs use the following base path:

```text
/api/v1
```
````

Therefore, the endpoints in this document are written relative to:

```text
/api/v1
```

For example:

```text
/api/v1/products
```

is represented in the tables as:

```text
/products
```

---

# 3. HTTP Methods

| HTTP Method | Purpose                                   |
| ----------- | ----------------------------------------- |
| GET         | Retrieve data                             |
| POST        | Create a resource or perform an operation |
| PUT         | Update a resource                         |
| PATCH       | Partially update a resource               |
| DELETE      | Deactivate/remove a resource              |

---

# 4. Standard Response Status Codes

- **200 - OK:** Request processed successfully.
- **201 - Created:** New resource created successfully.
- **204 - No Content:** Request processed successfully with no response body.
- **400 - Bad Request:** Invalid request or validation failure.
- **401 - Unauthorized:** Authentication is required.
- **403 - Forbidden:** User is authenticated but not authorized.
- **404 - Not Found:** Requested resource does not exist.
- **409 - Conflict:** Request conflicts with existing data or a business rule.
- **500 - Internal Server Error:** Unexpected server-side error.

---

# 5. Standard Success Response

Successful APIs should follow a consistent structure.

For a collection:

```json
{
    "message": "Products fetched successfully",
    "data": [...]
}
```

For a single resource:

```json
{
    "message": "Product fetched successfully",
    "data": {...}
}
```

For an operation without response data:

```json
{
  "message": "Product deleted successfully",
  "data": null
}
```

---

# 6. Standard Error Response

Application errors should follow a consistent structure.

```json
{
  "statusCode": 404,
  "statusMessage": "Not Found",
  "message": "Product with ID 101 not found",
  "data": null
}
```

The `data` field can contain additional validation or error information when required.

---

# 7. Authentication APIs

### Business Requirements

- A new customer should be able to register.
- A registered user should be able to login.
- An authenticated user should be able to logout.
- Passwords must never be returned in API responses.

| HTTP Verb | Endpoint         | Status | Expected Response                      |
| --------- | ---------------- | -----: | -------------------------------------- |
| POST      | `/auth/register` |    201 | Registered user                        |
| POST      | `/auth/login`    |    200 | Authenticated user/session information |
| POST      | `/auth/logout`   |    204 | No response                            |

---

# 8. User APIs

## 8.1 Roles

| Operation  | HTTP Verb | Endpoint          | Meaning              |
| ---------- | --------- | ----------------- | -------------------- |
| View All   | GET       | `/roles`          | Get roles            |
| View By ID | GET       | `/roles/{roleId}` | Get role             |
| Add        | POST      | `/roles`          | Create role          |
| Update     | PUT       | `/roles/{roleId}` | Update role          |
| Deactivate | DELETE    | `/roles/{roleId}` | Set `active = false` |

Sample Role Data:

{
"name": "ADMIN",
"description": "Administrator with access to manage the application"
}

{
"name": "CUSTOMER",
"description": "Customer who can browse products and place orders"
}

## 8.2 Users

### Business Requirements

- A user should be able to view their own profile.
- A user should be able to update permitted profile information.
- The `/me` endpoint represents the currently authenticated user.

| Operation  | HTTP Verb | Endpoint          | Meaning              |
| ---------- | --------- | ----------------- | -------------------- |
| View All   | GET       | `/users`          | Get users            |
| View By ID | GET       | `/users/{userId}` | Get user             |
| Add        | POST      | `/users`          | Create user          |
| Update     | PUT       | `/users/{userId}` | Update user          |
| Deactivate | DELETE    | `/users/{userId}` | Set `active = false` |

Profile Specific APIs

| HTTP Verb | Endpoint    | Status | Expected Response    |
| --------- | ----------- | ------ | -------------------- |
| GET       | `/users/me` | 200    | Current user details |
| PUT       | `/users/me` | 200    | Updated user details |

---

# 9. Category APIs

### Business Requirements

- Categories are used to organize products.
- A category can be created.
- A category can be updated.
- A category can be deactivated.
- Products belonging to a category can be retrieved.

| Operation           | HTTP Verb | Endpoint                            | Status | Expected Response         |
| ------------------- | --------- | ----------------------------------- | ------ | ------------------------- |
| View                | GET       | `/categories`                       | 200    | List of categories        |
| View                | GET       | `/categories/{categoryId}`          | 200    | Category details          |
| Add                 | POST      | `/categories`                       | 201    | Added category            |
| Update              | PUT       | `/categories/{categoryId}`          | 200    | Updated category          |
| Activate/Deactivate | PATCH     | `/categories/{categoryId}`          | 204    | No response               |
| View                | GET       | `/categories/{categoryId}/products` | 200    | List of category products |

Sample Categories:

{
"name": "Electronics",
"description": "Mobiles, laptops, tablets and electronic accessories"
}

{
"name": "Clothing",
"description": "Men's, women's and kids' clothing and fashion products"
}

{
"name": "Home & Kitchen",
"description": "Home appliances, kitchen products and household essentials"
}

---

# 10. Product APIs

### Business Requirements

- Products should be available for browsing.
- A particular product should be retrievable using its ID.
- Products can be created.
- Products can be updated.
- Products can be deactivated rather than physically deleted.
- Inactive products should normally not appear in customer-facing product listings.

| Operation           | HTTP Verb | Endpoint                | Status | Expected Response |
| ------------------- | --------- | ----------------------- | ------ | ----------------- |
| View                | GET       | `/products`             | 200    | List of products  |
| View                | GET       | `/products/{productId}` | 200    | Product details   |
| Add                 | POST      | `/products`             | 201    | Added product     |
| Update              | PUT       | `/products/{productId}` | 200    | Updated product   |
| Activate/Deactivate | PATCH     | `/products/{productId}` | 204    | No response       |

Sample Product Data:

{
"name": "Dell Inspiron 15",
"description": "15-inch laptop for everyday computing",
"price": 54999.00,
"quantity": 10,
"categoryId": 6,
"brand": "Dell",
"specifications": "{\"processor\":\"Intel Core i5\",\"ram\":\"16GB\",\"storage\":\"512GB SSD\",\"display\":\"15.6 inch\",\"operatingSystem\":\"Windows 11\"}",
"active": true
}

Image: product_1.png

---

{
"name": "Samsung Galaxy A",
"description": "Modern smartphone with AMOLED display",
"price": 24999.00,
"quantity": 20,
"categoryId": 6,
"brand": "Samsung",
"specifications": "{\"display\":\"6.5 inch AMOLED\",\"ram\":\"8GB\",\"storage\":\"128GB\",\"camera\":\"50MP\",\"battery\":\"5000mAh\"}",
"active": true
}

Image: product_2.png

---

{
"name": "Bose QuietComfort",
"description": "Wireless noise cancelling headphones",
"price": 29999.00,
"quantity": 15,
"categoryId": 6,
"brand": "Bose",
"specifications": "{\"type\":\"Wireless\",\"noiseCancellation\":true,\"connectivity\":\"Bluetooth\",\"batteryLife\":\"24 hours\",\"microphone\":true}",
"active": true
}

Image: product_3.png

---

# 11. Order APIs

### Business Requirements

- A customer should be able to create an order.
- An order contains one or more products.
- The server determines product prices and calculates the final order amount.
- The client must not determine the final order amount.
- A user should be able to retrieve their orders.
- A particular order should be retrievable using its ID.
- Order items should be retrievable for a particular order.

### Order Creation

When an order is created, the server should:

- Identify the customer.
- Verify the products.
- Verify product availability.
- Verify stock.
- Determine current product prices.
- Calculate item subtotals.
- Calculate the order total.
- Create the order.
- Create the order items.
- Update available product quantity.

| HTTP Verb | Endpoint                  | Status | Expected Response         |
| --------- | ------------------------- | -----: | ------------------------- |
| POST      | `/orders`                 |    201 | Created order             |
| GET       | `/orders`                 |    200 | List of customer's orders |
| GET       | `/orders/{orderId}`       |    200 | Order details             |
| GET       | `/orders/{orderId}/items` |    200 | List of order items       |

---

# 12. Order Item APIs

Order items are dependent on an order.

They are created as part of order creation rather than through an independent create API.

### Business Requirements

- An order should contain one or more order items.
- Each order item represents a product purchased in an order.
- The `unitPrice` should represent the product price at the time of purchase.
- Order items should be retrieved through their parent order.

| HTTP Verb | Endpoint                  | Status | Expected Response   |
| --------- | ------------------------- | -----: | ------------------- |
| GET       | `/orders/{orderId}/items` |    200 | List of order items |

---

# 13. Payment APIs

### Business Requirements

- A customer should be able to initiate payment for an order.
- Payment is simulated in this project.
- No real payment gateway is integrated.
- Supported payment methods are:
  - `COD`
  - `CARD`
  - `UPI`
  - `NET_BANKING`
  - `WALLET`

- Payment status can be:
  - `PENDING`
  - `SUCCESS`
  - `FAILED`
  - `REFUNDED`

| HTTP Verb | Endpoint                    | Status | Expected Response |
| --------- | --------------------------- | -----: | ----------------- |
| POST      | `/orders/{orderId}/payment` |    201 | Created payment   |
| GET       | `/orders/{orderId}/payment` |    200 | Payment details   |

---

# 14. Transaction APIs

A payment can have multiple transaction records.

Transactions can represent activities such as:

- Payment
- Refund

### Business Requirements

- Transaction information should be associated with a payment.
- A transaction should contain the transaction amount.
- A transaction should identify its type.
- Transaction records should be retrievable for a payment.

| HTTP Verb | Endpoint                             | Status | Expected Response            |
| --------- | ------------------------------------ | -----: | ---------------------------- |
| GET       | `/payments/{paymentId}/transactions` |    200 | List of payment transactions |

---

# 15. Cancellation APIs

### Business Requirements

- A customer should be able to request cancellation of an order when permitted.
- An order can have zero or one cancellation record.
- Cancellation should reference a cancellation reason.
- Cancellation details should be retrievable for an order.
- Cancellation rules will be handled by the business/service layer.

| HTTP Verb | Endpoint                         | Status | Expected Response            |
| --------- | -------------------------------- | -----: | ---------------------------- |
| POST      | `/orders/{orderId}/cancellation` |    201 | Created cancellation request |
| GET       | `/orders/{orderId}/cancellation` |    200 | Cancellation details         |

---

# 16. Cancellation Reason APIs

### Business Requirements

- Cancellation reasons are maintained as reference/master data.
- Clients should be able to retrieve available cancellation reasons.
- A cancellation should reference a valid cancellation reason.

| HTTP Verb | Endpoint                | Status | Expected Response            |
| --------- | ----------------------- | -----: | ---------------------------- |
| GET       | `/cancellation-reasons` |    200 | List of cancellation reasons |

---

# 17. REST Naming Conventions

### Use nouns

```text
/products
/orders
/categories
/payments
```

Avoid:

```text
/getProducts
/createOrder
/deleteProduct
```

### Use plural resource names

```text
/products
/orders
/users
/categories
```

### Use HTTP methods to represent operations

```text
GET    /products
POST   /products
PUT    /products/{productId}
DELETE /products/{productId}
```

### Use path variables for specific resources

```text
GET /products/101
GET /orders/1001
GET /categories/1
```

---

# 18. Common Error Scenarios

The application should handle errors such as:

- User already exists
- Invalid credentials
- User not found
- Category not found
- Product not found
- Insufficient stock
- Product unavailable
- Order not found
- Payment not found
- Transaction not found
- Order cannot be cancelled
- Invalid order status transition
- Invalid request data
- Database conflict
- Unexpected server error

These errors should be converted into the standard error response format.

---

# 19. Complete API Summary

## Authentication

| HTTP Verb | Endpoint         | Status | Expected Response                      |
| --------- | ---------------- | -----: | -------------------------------------- |
| POST      | `/auth/register` |    201 | Registered user                        |
| POST      | `/auth/login`    |    200 | Authenticated user/session information |
| POST      | `/auth/logout`   |    204 | No response                            |

## Users

| HTTP Verb | Endpoint    | Status | Expected Response    |
| --------- | ----------- | -----: | -------------------- |
| GET       | `/users/me` |    200 | Current user details |
| PUT       | `/users/me` |    200 | Updated user details |

## Categories

| HTTP Verb | Endpoint                            | Status | Expected Response         |
| --------- | ----------------------------------- | -----: | ------------------------- |
| GET       | `/categories`                       |    200 | List of categories        |
| GET       | `/categories/{categoryId}`          |    200 | Category details          |
| POST      | `/categories`                       |    201 | Added category            |
| PUT       | `/categories/{categoryId}`          |    200 | Updated category          |
| PATCH     | `/categories/{categoryId}`          |    204 | No response               |
| GET       | `/categories/{categoryId}/products` |    200 | List of category products |

## Products

| HTTP Verb | Endpoint                | Status | Expected Response |
| --------- | ----------------------- | -----: | ----------------- |
| GET       | `/products`             |    200 | List of products  |
| GET       | `/products/{productId}` |    200 | Product details   |
| POST      | `/products`             |    201 | Added product     |
| PUT       | `/products/{productId}` |    200 | Updated product   |
| DELETE    | `/products/{productId}` |    204 | No response       |

## Orders

| HTTP Verb | Endpoint                  | Status | Expected Response         |
| --------- | ------------------------- | -----: | ------------------------- |
| POST      | `/orders`                 |    201 | Created order             |
| GET       | `/orders`                 |    200 | List of customer's orders |
| GET       | `/orders/{orderId}`       |    200 | Order details             |
| GET       | `/orders/{orderId}/items` |    200 | List of order items       |

## Payments

| HTTP Verb | Endpoint                    | Status | Expected Response |
| --------- | --------------------------- | -----: | ----------------- |
| POST      | `/orders/{orderId}/payment` |    201 | Created payment   |
| GET       | `/orders/{orderId}/payment` |    200 | Payment details   |

## Transactions

| HTTP Verb | Endpoint                             | Status | Expected Response            |
| --------- | ------------------------------------ | -----: | ---------------------------- |
| GET       | `/payments/{paymentId}/transactions` |    200 | List of payment transactions |

## Cancellations

| HTTP Verb | Endpoint                         | Status | Expected Response            |
| --------- | -------------------------------- | -----: | ---------------------------- |
| POST      | `/orders/{orderId}/cancellation` |    201 | Created cancellation request |
| GET       | `/orders/{orderId}/cancellation` |    200 | Cancellation details         |

## Cancellation Reasons

| HTTP Verb | Endpoint                | Status | Expected Response            |
| --------- | ----------------------- | -----: | ---------------------------- |
| GET       | `/cancellation-reasons` |    200 | List of cancellation reasons |

---

# 20. API Design Summary

The API design follows:

```text
Business Requirement
        |
        v
Identify Entity
        |
        v
Identify Resource
        |
        v
Define HTTP Method
        |
        v
Define Endpoint
        |
        v
Define Response
        |
        v
Define HTTP Status
```

For example:

```text
Business Requirement:
Customer should be able to view a product.

        |
        v

Entity:
PRODUCT

        |
        v

Resource:
products

        |
        v

API:
GET /products/{productId}

        |
        v

Success:
200 OK
```

---

# 21. API Specification vs README

This document defines the _API contract at a design level_.

The `README.md` will contain practical examples such as:

- Request JSON
- Response JSON
- Sample API calls
- Postman examples
- Query parameter examples
- Sample error responses
- Testing instructions

Therefore:

```text
API Specification
        |
        +---- What APIs exist?
        +---- Which HTTP method?
        +---- Which endpoint?
        +---- What status?
        +---- What response data?

README.md
        |
        +---- How do I call the API?
        +---- What JSON should I send?
        +---- What JSON will I receive?
        +---- How do I test it?
```

---

# 22. Authorization

Authorization is intentionally _not defined in this document_.

This document defines:

> _What APIs exist?_

The next document defines:

> _Who can access those APIs?_

```

```
