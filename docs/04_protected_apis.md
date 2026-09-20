# Document 4 — API Authorization

## Mini E-Commerce Application

**Purpose:** Define which users can access which APIs.

# 1. Purpose

The application supports two user roles:

```text
CUSTOMER
ADMIN
```

Some APIs are publicly accessible.

This document defines:

- API access levels
- Role-based authorization
- Resource ownership
- Unauthorized and forbidden access
- Complete API authorization matrix

# 2. Types of API Access

| Access Type      | Authentication | Required Role     |
| ---------------- | -------------- | ----------------- |
| PUBLIC           | Not required   | None              |
| CUSTOMER         | Required       | CUSTOMER          |
| ADMIN            | Required       | ADMIN             |
| CUSTOMER + ADMIN | Required       | CUSTOMER or ADMIN |

# 3. Authentication APIs

| HTTP Verb | Endpoint         | Access           |
| --------- | ---------------- | ---------------- |
| POST      | `/auth/register` | PUBLIC           |
| POST      | `/auth/login`    | PUBLIC           |
| POST      | `/auth/logout`   | CUSTOMER + ADMIN |

### Rules

- Registration and login are publicly accessible.
- Logout requires authentication.

# 4. User APIs

| HTTP Verb | Endpoint    | Access           |
| --------- | ----------- | ---------------- |
| GET       | `/users/me` | CUSTOMER + ADMIN |
| PUT       | `/users/me` | CUSTOMER + ADMIN |

### Rules

- `/me` always represents the currently authenticated user.
- A user cannot use `/me` to access another user's profile.

# 5. Category APIs

| HTTP Verb | Endpoint                            | Access |
| --------- | ----------------------------------- | ------ |
| GET       | `/categories`                       | PUBLIC |
| GET       | `/categories/{categoryId}`          | PUBLIC |
| GET       | `/categories/{categoryId}/products` | PUBLIC |
| POST      | `/categories`                       | ADMIN  |
| PUT       | `/categories/{categoryId}`          | ADMIN  |
| DELETE    | `/categories/{categoryId}`          | ADMIN  |

### Rules

- Category browsing is public.
- Category management requires `ADMIN`.

# 6. Product APIs

| HTTP Verb | Endpoint                | Access |
| --------- | ----------------------- | ------ |
| GET       | `/products`             | PUBLIC |
| GET       | `/products/{productId}` | PUBLIC |
| POST      | `/products`             | ADMIN  |
| PUT       | `/products/{productId}` | ADMIN  |
| DELETE    | `/products/{productId}` | ADMIN  |

### Rules

- Product browsing is public.
- Product management requires `ADMIN`.

# 7. Order APIs

| HTTP Verb | Endpoint                  | Access           |
| --------- | ------------------------- | ---------------- |
| POST      | `/orders`                 | CUSTOMER         |
| GET       | `/orders`                 | CUSTOMER         |
| GET       | `/orders/{orderId}`       | CUSTOMER + ADMIN |
| GET       | `/orders/{orderId}/items` | CUSTOMER + ADMIN |

### Rules

- Creating an order requires `CUSTOMER`.
- A customer can access only their own orders.
- Administrators can view orders for operational purposes.

# 8. Payment APIs

| HTTP Verb | Endpoint                    | Access           |
| --------- | --------------------------- | ---------------- |
| POST      | `/orders/{orderId}/payment` | CUSTOMER         |
| GET       | `/orders/{orderId}/payment` | CUSTOMER + ADMIN |

### Rules

- A customer can initiate payment only for their own order.
- A customer can view payment information only for their own order.
- Administrators can view payment information.

# 9. Transaction APIs

| HTTP Verb | Endpoint                             | Access           |
| --------- | ------------------------------------ | ---------------- |
| GET       | `/payments/{paymentId}/transactions` | CUSTOMER + ADMIN |

### Rules

- A customer can view transactions associated with their own payment/order.
- Customer ownership must be verified.
- Administrators can view transaction information.

# 10. Cancellation APIs

| HTTP Verb | Endpoint                         | Access           |
| --------- | -------------------------------- | ---------------- |
| POST      | `/orders/{orderId}/cancellation` | CUSTOMER         |
| GET       | `/orders/{orderId}/cancellation` | CUSTOMER + ADMIN |

### Rules

- A customer can request cancellation of their own order.
- Customer ownership must be verified.
- Administrators can view cancellation information.

# 11. Cancellation Reason APIs

| HTTP Verb | Endpoint                | Access           |
| --------- | ----------------------- | ---------------- |
| GET       | `/cancellation-reasons` | CUSTOMER + ADMIN |

### Rules

- Customers need cancellation reasons when requesting cancellation.
- Administrators can also retrieve cancellation reasons.

# 12. Resource Ownership

For customer-specific resources, **role checking alone is not sufficient**.

Example:

```text
Customer A
    |
    +---- Order 1001
    +---- Order 1002

Customer B
    |
    +---- Order 2001
```

If Customer A requests:

```text
GET /orders/1001
```

```text
Authenticated
      ↓
CUSTOMER
      ↓
Order belongs to Customer A
      ↓
ALLOW
```

If Customer A requests:

```text
GET /orders/2001
```

```text
Authenticated
      ↓
CUSTOMER
      ↓
Order belongs to Customer B
      ↓
REJECT
```

Therefore:

```text
ROLE CHECK
    +
OWNERSHIP CHECK
```

Ownership checks apply to customer-specific:

- Orders
- Payments
- Transactions
- Cancellations

# 13. Complete API Authorization Matrix

```text
+-----------------------------------+--------+----------+-------+
| API                               | PUBLIC | CUSTOMER | ADMIN |
+-----------------------------------+--------+----------+-------+
| POST /auth/register               |   ✓    |    -     |   -   |
| POST /auth/login                  |   ✓    |    -     |   -   |
| POST /auth/logout                 |   -    |    ✓     |   ✓   |
+-----------------------------------+--------+----------+-------+
| GET  /users/me                    |   -    |    ✓     |   ✓   |
| PUT  /users/me                    |   -    |    ✓     |   ✓   |
+-----------------------------------+--------+----------+-------+
| GET  /categories                  |   ✓    |    ✓     |   ✓   |
| GET  /categories/{id}             |   ✓    |    ✓     |   ✓   |
| GET  /categories/{id}/products    |   ✓    |    ✓     |   ✓   |
| POST /categories                  |   -    |    -     |   ✓   |
| PUT  /categories/{id}             |   -    |    -     |   ✓   |
| DELETE /categories/{id}           |   -    |    -     |   ✓   |
+-----------------------------------+--------+----------+-------+
| GET  /products                    |   ✓    |    ✓     |   ✓   |
| GET  /products/{id}               |   ✓    |    ✓     |   ✓   |
| POST /products                    |   -    |    -     |   ✓   |
| PUT  /products/{id}               |   -    |    -     |   ✓   |
| DELETE /products/{id}              |   -    |    -     |   ✓   |
+-----------------------------------+--------+----------+-------+
| POST /orders                      |   -    |    ✓     |   -   |
| GET  /orders                      |   -    |    ✓     |   -   |
| GET  /orders/{id}                 |   -    |   ✓*     |   ✓   |
| GET  /orders/{id}/items           |   -    |   ✓*     |   ✓   |
+-----------------------------------+--------+----------+-------+
| POST /orders/{id}/payment         |   -    |   ✓*     |   -   |
| GET  /orders/{id}/payment         |   -    |   ✓*     |   ✓   |
+-----------------------------------+--------+----------+-------+
| GET  /payments/{id}/transactions  |   -    |   ✓*     |   ✓   |
+-----------------------------------+--------+----------+-------+
| POST /orders/{id}/cancellation    |   -    |   ✓*     |   -   |
| GET  /orders/{id}/cancellation    |   -    |   ✓*     |   ✓   |
+-----------------------------------+--------+----------+-------+
| GET  /cancellation-reasons        |   -    |    ✓     |   ✓   |
+-----------------------------------+--------+----------+-------+

* CUSTOMER must own the corresponding resource.
```

# 14. Access Denied

There are two important authorization scenarios.

### Unauthenticated User

A protected API is requested without authentication.

```text
Authentication Required
        ↓
Not Authenticated
        ↓
401 Unauthorized
```

### Wrong Role

The user is authenticated but does not have the required role.

```text
Authenticated
      ↓
Wrong Role
      ↓
403 Forbidden
```

# 15. Security Flow

```text
                  API REQUEST
                      |
                      v
                AUTHENTICATION
                      |
               +------+------+
               |             |
              NO            YES
               |             |
              401            v
                       AUTHORIZATION
                            |
                     +------+------+
                     |             |
                WRONG ROLE    CORRECT ROLE
                     |             |
                    403            |
                                   v
                              CONTROLLER
                                   |
                                   v
                              SERVICE LAYER
                                   |
                                   v
                              OWNERSHIP CHECK
                                   |
                                   v
                                DATABASE
```

# 16. Security Rules Summary

- **PUBLIC** APIs do not require authentication.
- Protected APIs require authentication.
- `CUSTOMER` APIs require the `CUSTOMER` role.
- `ADMIN` APIs require the `ADMIN` role.
- Some APIs are accessible to both `CUSTOMER` and `ADMIN`.
- Customer-specific resources require ownership verification.
- Unauthenticated access results in **401 Unauthorized**.
- Authenticated users without the required role receive **403 Forbidden**.
- Role-based authorization and resource ownership are separate checks.

# 17. Final Project Planning Sequence

```text
01. ALL IN ONE SRS
        ↓
02. BUSINESS REQUIREMENTS
        ↓
03. ER SCHEMA
        ↓
04. API SPECIFICATIONS
        ↓
05. PROTECTED APIs
        ↓
   IMPLEMENTATION
```
