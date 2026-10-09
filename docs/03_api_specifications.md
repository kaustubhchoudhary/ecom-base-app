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

- _200 - OK:_ Request processed successfully.
- _201 - Created:_ New resource created successfully.
- _204 - No Content:_ Request processed successfully with no response body.
- _400 - Bad Request:_ Invalid request or validation failure.
- _401 - Unauthorized:_ Authentication is required.
- _403 - Forbidden:_ User is authenticated but not authorized.
- _404 - Not Found:_ Requested resource does not exist.
- _409 - Conflict:_ Request conflicts with existing data or a business rule.
- _500 - Internal Server Error:_ Unexpected server-side error.

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
  "message": "Product with ID 101 not found",
  "data": null
}
```

The `data` field can contain additional validation or error information when required.

---

# 7. Authentication APIs

## Business Requirements

- A new customer should be able to register.
- A registered user should be able to login.
- An authenticated user should be able to logout.
- Passwords must never be returned in API responses.

| HTTP Verb | Endpoint         | Status | Expected Response                      |
| --------- | ---------------- | -----: | -------------------------------------- |
| POST      | `/auth/register` |    201 | Registered user                        |
| POST      | `/auth/login`    |    200 | Authenticated user/session information |
| POST      | `/auth/logout`   |    204 | No response                            |

- Note that
  - `register, login and logout are here only for reference.`
  - `They are not implemented in this project`
  - `You can implement them as a part of your learning of Spring Security`

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

### Sample Role Data:

**API End Point** - `POST: /api/v1/roles`

**Role 1**

```json
{
  "name": "ADMIN",
  "description": "Administrator with access to manage the application"
}
```

**Role 2**

```json
{
  "name": "CUSTOMER",
  "description": "Customer who can browse products and place orders"
}
```

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

### Sample User data:

**API End Point** - `POST: /api/v1/users`

**User 1: Admin**

```json
{
  "name": "Rahul Sharma",
  "email": "rahul.sharma@example.com",
  "password": "Admin@123",
  "phone": "9876543210",
  "roleId": 1
}
```

**User 2: Customer 1**

```json
{
  "name": "Priya Verma",
  "email": "priya.verma@example.com",
  "password": "Customer@123",
  "phone": "9876543211",
  "roleId": 2
}
```

**User 3: Customer 2**

```json
{
  "name": "Amit Patel",
  "email": "amit.patel@example.com",
  "password": "Customer@456",
  "phone": "9876543212",
  "roleId": 2
}
```

---

# 9. Category APIs

## Business Requirements

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

### Sample Categories:

**API End Point** - `POST: /api/v1/categories`

**Category 1:**

```json
{
  "name": "Electronics",
  "description": "Mobiles, laptops, tablets and electronic accessories"
}
```

**Category 2:**

```json
{
  "name": "Clothing",
  "description": "Men's, women's and kids' clothing and fashion products"
}
```

**Category 3:**

```json
{
  "name": "Home & Kitchen",
  "description": "Home appliances, kitchen products and household essentials"
}
```

---

# 10. Product APIs

## Business Requirements

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

### Testing Product Creation API

To test the Product creation API using Postman:

1. Open the `E-Com Base API` collection.
2. Open the `Products` folder.
3. Select _Add a Product_.
4. Set the request method to `POST`.
5. Set the URL to:

```text
   {{baseUrl}}/products
```

6. Select _Body → form-data_.

7. Add a field named `product` with type _Text_.

8. Set the `Content-Type` of the `product` part to:

```text
   application/json
```

9. Enter the Product JSON in the `product` field.

10. Add a field named `image` with type _File_.

11. Select the product image file.

12. Click _Send_.

13. Verify that the API returns `201 Created`.

That captures the exact steps someone needs to successfully test your multipart Product API.

---

### Sample Products' Data (Only for Category - 'Electronics'):

**API End Point** - `POST: /api/v1/products`

**Product 1 - Category 1:**

```json
{
  "name": "Dell Inspiron 15",
  "description": "15-inch laptop for everyday computing",
  "price": 54999.0,
  "quantity": 10,
  "categoryId": 1,
  "brand": "Dell",
  "specifications": "{\"processor\":\"Intel Core i5\",\"ram\":\"16GB\",\"storage\":\"512GB SSD\",\"display\":\"15.6 inch\",\"operatingSystem\":\"Windows 11\"}",
  "active": true
}
```

**Image:** `product_1.png`

- find the images in folder `product_images` in project root

---

**Product 2 - Category 1:**

```json
{
  "name": "Samsung Galaxy A",
  "description": "Modern smartphone with AMOLED display",
  "price": 24999.0,
  "quantity": 20,
  "categoryId": 1,
  "brand": "Samsung",
  "specifications": "{\"display\":\"6.5 inch AMOLED\",\"ram\":\"8GB\",\"storage\":\"128GB\",\"camera\":\"50MP\",\"battery\":\"5000mAh\"}",
  "active": true
}
```

**Image:** `product_2.png`

---

**Product 3 - Category 1:**

```json
{
  "name": "Bose QuietComfort",
  "description": "Wireless noise cancelling headphones",
  "price": 29999.0,
  "quantity": 15,
  "categoryId": 1,
  "brand": "Bose",
  "specifications": "{\"type\":\"Wireless\",\"noiseCancellation\":true,\"connectivity\":\"Bluetooth\",\"batteryLife\":\"24 hours\",\"microphone\":true}",
  "active": true
}
```

**Image:** `product_3.png`

---

# 11. Order APIs

## Business Requirements

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

| Action               | HTTP Verb | Endpoint                         | Status | Expected Response             |
| -------------------- | --------- | -------------------------------- | -----: | ----------------------------- |
| Create Order         | POST      | `/orders`                        |    201 | Created order                 |
| View Customer Orders | GET       | `/users/{userId}/orders`         |    200 | List of customer's orders     |
| View Order           | GET       | `/orders/{orderId}`              |    200 | Order details                 |
| View Order Items     | GET       | `/orders/{orderId}/items`        |    200 | List of order items           |
| Change Order Status  | PATCH     | `/orders/{orderId}/status`       |    200 | Updated order with new status |
| Cancel Order         | POST      | `/orders/{orderId}/cancellation` |    201 | Cancellation details          |

---

# 12. Order Item APIs

Order items are dependent on an order.

They are created as part of order creation rather than through an independent create API.

## Business Requirements

- An order should contain one or more order items.
- Each order item represents a product purchased in an order.
- The `unitPrice` should represent the product price at the time of purchase.
- Order items should be retrieved through their parent order.

| Action               | HTTP Verb | Endpoint                         | Status | Expected Response             |
| -------------------- | --------- | -------------------------------- | -----: | ----------------------------- |
| Create Order         | POST      | `/orders`                        |    201 | Created order                 |
| View Customer Orders | GET       | `/users/{userId}/orders`         |    200 | List of customer's orders     |
| View Order           | GET       | `/orders/{orderId}`              |    200 | Order details                 |
| View Order Items     | GET       | `/orders/{orderId}/items`        |    200 | List of order items           |
| Change Order Status  | PATCH     | `/orders/{orderId}/status`       |    200 | Updated order with new status |
| Cancel Order         | POST      | `/orders/{orderId}/cancellation` |    201 | Cancellation details          |

### Sample Data Orders

**API End Point** - `POST: /api/v1/orders`

**Order 1**

```json
{
  "userId": 2,
  "items": [
    {
      "productId": 1,
      "quantity": 2
    },
    {
      "productId": 2,
      "quantity": 1
    }
  ]
}
```

**Order 2**

```json
{
  "userId": 3,
  "items": [
    {
      "productId": 2,
      "quantity": 2
    },
    {
      "productId": 3,
      "quantity": 1
    }
  ]
}
```

**Order 3**

```json
{
  "userId": 2,
  "items": [
    {
      "productId": 1,
      "quantity": 1
    },
    {
      "productId": 3,
      "quantity": 3
    }
  ]
}
```

### Change Order Status

**API End Point** - `PATCH: /api/v1/orders/1/status`

**Order 1**

```json
{
  "status": "CONFIRMED"
}
```

**API End Point** - `PATCH: /api/v1/orders/2/status`

**Order 2**

```json
{
  "status": "CONFIRMED"
}
```

---

# 13. Payment APIs

## Business Requirements

- A customer should be able to make a payment for an order.
- Payment is simulated in this project.
- No real payment gateway is integrated.
- Supported payment methods are:
  - `COD`
  - `CARD`
  - `UPI`
  - `NET_BANKING`
  - `WALLET`
- A successful payment should create:
  - A payment record.
  - A `PAYMENT` transaction with `SUCCESS` status.
- The payment amount should be derived from the order amount.
- A customer should be able to request a refund for a payment.
- A refund should create a `REFUND` transaction.
- Partial refunds should be supported.
- Transaction status can be:
  - `SUCCESS`
  - `FAILED`

| HTTP Verb | Endpoint                    | Status | Expected Response |
| --------- | --------------------------- | -----: | ----------------- |
| POST      | `/orders/{orderId}/payment` |    201 | Created payment   |
| GET       | `/orders/{orderId}/payment` |    200 | Payment details   |

### Sample Payment Data:

**API End Point** - `POST: /api/v1/orders/1/payments`

**OrderId: 1**

```json
{
  "paymentMethod": "UPI"
}
```

**API End Point** - `POST: /api/v1/orders/2/payments`

**OrderId: 2**

```json
{
  "paymentMethod": "COD"
}
```

**OrderId: 3**

```json
{
  "paymentMethod": "WALLET"
}
```

---

# 14. Transaction APIs

A payment can have multiple transaction records.

Transactions can represent activities such as:

- Payment
- Refund

## Business Requirements

- A transaction should be associated with a payment.
- A transaction should contain the transaction amount.
- A transaction should identify its type.
- A successful payment should create a `PAYMENT` transaction.
- A refund should create a `REFUND` transaction.
- Partial refunds should be supported.
- Transaction records should be retrievable for a payment.

| HTTP Verb | Endpoint                             | Status | Expected Response                       |
| --------- | ------------------------------------ | ------ | --------------------------------------- |
| POST      | `/orders/{orderId}/payment`          | 201    | Created payment and payment transaction |
| POST      | `/payments/{paymentId}/refund`       | 201    | Created refund transaction              |
| GET       | `/payments/{paymentId}/transactions` | 200    | List of payment transactions            |

- Note that
  - `Every payment will make 2 entries - one in the payment and another one in the transaction table with type - PAYMENT`

### Refund payment

## Sample JSON

Assume:

```text
Payment ID = 1
Payment Amount = ₹136,997
```

### Full refund

**API End Point** - `POST: /api/v1/payments/1/refund`

```json
{
  "amount": 136997.0
}
```

### Partial refund

```json
{
  "amount": 20000.0
}
```

This creates a new transaction:

```text
Transaction Type : REFUND
Amount           : 20000.00
Status           : SUCCESS
```

If you subsequently refund another ₹30,000, you'll have:

```text
PAYMENT   ₹136,997
REFUND     ₹20,000
REFUND     ₹30,000
```

and the remaining refundable amount is:

```text
₹86,997
```

This also prevents the customer from refunding more than the amount actually paid.

---

# 15. Cancellation APIs

## Business Requirements

- A customer should be able to request cancellation of an order when permitted.
- An order can have zero or one cancellation record.
- Cancellation should reference a cancellation reason.
- Cancellation details should be retrievable for an order.
- Cancellation rules will be handled by the business/service layer.

| HTTP Verb | Endpoint                         | Status | Expected Response            |
| --------- | -------------------------------- | ------ | ---------------------------- |
| POST      | `/orders/{orderId}/cancellation` | 201    | Created cancellation request |
| GET       | `/orders/{orderId}/cancellation` | 200    | Cancellation details         |

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
