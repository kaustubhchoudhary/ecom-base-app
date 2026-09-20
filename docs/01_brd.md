# Document 1 - Business Requirements Document (BRD)

## Mini E-Commerce Application

_Purpose:_ Define what the business expects the application to do.

---

## 1. Business Objective

The objective is to build a _minimalist E-Commerce application_ that allows customers to register, browse products, place orders, make simulated payments, view their transactions, and track/cancel their orders.

The application will also provide administrative capabilities for managing products, monitoring orders, managing order status, and viewing payment/transaction information.

The application is intentionally limited in scope. It is designed to represent a realistic backend business application that can subsequently be extended with various supporting technologies.

---

## 2. Actors

The application has two primary actors.

### 2.1 Customer

A customer can:

- Register
- Login
- View available products
- View product details
- Place an order
- Make a simulated payment
- View their orders
- View payment information
- View transaction information
- Track order status
- Request order cancellation
- View their profile
- Update their profile

### 2.2 Administrator

An administrator can:

- Login
- Manage categories
- Manage products
- View products including unavailable products
- View customer orders
- Update order status
- View payments
- View transactions
- View cancellations
- View business reports based on available order/payment/transaction data

---

# 3. Core Business Modules

The application consists of the following business modules:

```text
User Management
       ↓
Category Management
       ↓
Product Management
       ↓
Order Management
       ↓
Payment Management
       ↓
Transaction Management
       ↓
Cancellation Management
       ↓
Reports / History
```

````

These are _business modules_. Technologies such as Security, AOP, Testing, Actuator, Docker, Microservices, etc. are supporting technologies and are not business modules.

---

# 4. User Management

The system shall maintain customer and administrator information.

### Customer Registration

A new customer shall be able to register using:

- Name
- Email
- Password
- Phone number

The email address shall uniquely identify a customer account.

A customer shall not be allowed to create multiple accounts using the same email address.

A newly registered user shall be assigned the `CUSTOMER` role.

### Login

A registered user shall be able to login using their credentials.

The system shall verify the credentials before allowing access to protected functionality.

### Profile

An authenticated user shall be able to:

- View their profile
- Update permitted profile information

A customer shall only be able to access their own profile.

---

# 5. Category Management

The system shall organize products using categories.

Customers shall be able to:

- View categories
- View products belonging to a category

Administrators shall be able to:

- Create categories
- Update categories
- Deactivate categories

Deactivated categories shall remain in the system where historical information requires their retention.

---

# 6. Product Management

The system shall maintain products available for sale.

A product may contain:

- Name
- Description
- Price
- Available quantity
- Category
- Brand
- Image
- Specifications
- Availability status

### Customer

Customers shall be able to:

- Browse available products
- View product details
- View product price
- View product specifications
- View available quantity where appropriate

Unavailable products shall not normally be offered for purchase.

### Administrator

Administrators shall be able to:

- Create products
- Update products
- Change product availability
- View products

A product shall _not be physically deleted_ when it is no longer available for sale.

This is required to preserve historical information associated with previous orders.

---

# 7. Order Management

A customer shall be able to purchase one or more products by creating an order.

An order shall contain:

- Customer
- Ordered products
- Quantity of each product
- Price of each product at the time of purchase
- Total order amount
- Order status
- Order date/time

### Placing an Order

Before creating an order, the system shall verify:

1. The customer is registered.
2. The selected product exists.
3. The product is available.
4. Sufficient quantity is available.

The system shall calculate the order amount.

The customer shall _not manually provide the final order amount_.

After successful order creation, the ordered quantity shall be deducted from available product quantity.

---

# 8. Order Status

Every order shall have a status.

The supported statuses are:

```text
PLACED
   ↓
CONFIRMED
   ↓
SHIPPED
   ↓
DELIVERED
```

An order may also become:

```text
CANCELLED
```

The administrator shall be able to update the order status according to the defined business rules.

---

# 9. Order History

A customer shall be able to view their previous orders.

Order information shall include, where applicable:

- Order ID
- Order date
- Products
- Quantities
- Unit prices
- Total amount
- Current status

A customer shall only be able to access their own orders.

The administrator shall be able to view customer orders for operational purposes.

---

# 10. Payment Management

The application shall support _simulated payments_.

No real payment gateway shall be integrated.

The purpose of payment functionality is to represent a realistic payment workflow for the application and for training purposes.

A customer shall be able to initiate payment for an order.

Supported payment methods may include:

```text
COD
CARD
UPI
NET_BANKING
WALLET
```

Supported payment statuses shall include:

```text
PENDING
SUCCESS
FAILED
REFUNDED
```

A payment shall be associated with an order.

The payment amount shall be based on the order amount and shall be determined by the system.

---

# 11. Transaction Management

The application shall maintain transaction information associated with payments.

A transaction shall represent the processing record of a payment.

Transaction information may include:

- Transaction ID
- Payment
- Transaction reference
- Amount
- Gateway
- Status
- Response code
- Response message
- Transaction date/time

Transaction information shall be retained for historical purposes.

This information shall also provide the basis for transaction/payment reporting.

---

# 12. Order Cancellation

A customer shall be able to request cancellation of their own order when cancellation is permitted by the business rules.

A cancellation shall maintain information such as:

- Order
- Customer/user who initiated the cancellation
- Cancellation reason
- Customer comment/reason details
- Cancellation status
- Cancellation date/time

The system shall maintain cancellation information rather than simply changing the order status and losing the cancellation history.

An order shall have at most one cancellation record.

---

# 13. Cancellation Reasons

The application shall maintain predefined cancellation reasons.

Examples include:

```text
Product no longer required
Ordered by mistake
Found a better price
```

The list of cancellation reasons shall be maintained separately so that the same reason can be reused for multiple cancellation records.

---

# 14. Reports and History

The application shall retain sufficient business information to support basic reports.

The administrator should be able to derive information such as:

- Orders
- Order status
- Sales/order amounts
- Payments
- Payment status
- Transactions
- Transaction status
- Product-related information

The application does not require a sophisticated reporting/dashboard system.

The purpose is to maintain the underlying business data required for reporting.

---

# 15. Data Retention

Important business information shall not be unnecessarily removed.

For example:

```text
Product
   ↓
No longer available
   ↓
active = false
   ↓
Historical orders remain intact
```

Similarly, order, payment, transaction and cancellation information shall be retained appropriately for historical reference.

---

# 16. Important Business Rules

### BR-01 - Unique Customer

Each customer must have a unique email address.

### BR-02 - Customer Role

A newly registered user is assigned the `CUSTOMER` role.

### BR-03 - Product Availability

Customers can purchase only available products.

### BR-04 - Stock Availability

Customers cannot order more than the available quantity.

### BR-05 - Order Amount

The system calculates the final order amount.

### BR-06 - Historical Price

The price recorded in an order item represents the product price at the time of purchase.

### BR-07 - Order Ownership

A customer can access only their own orders.

### BR-08 - Product Retention

Products should not be physically removed when they become unavailable.

### BR-09 - Order Status

Every order must have a valid status.

### BR-10 - Payment

Payment processing is simulated; no real payment gateway is integrated.

### BR-11 - Payment Amount

Payment amount is derived from the associated order.

### BR-12 - Transaction History

Payment transaction information must be retained for historical and reporting purposes.

### BR-13 - Cancellation

An order can be cancelled only when permitted by the business rules.

### BR-14 - Cancellation History

Cancellation information must be retained.

### BR-15 - Administrator Responsibilities

Administrators are responsible for managing products/categories and operational order information.

---

# 17. Primary Customer Journey

```text
Customer
   ↓
Register
   ↓
Login
   ↓
Browse Categories / Products
   ↓
View Product
   ↓
Select Product(s)
   ↓
Place Order
   ↓
Order Created
   ↓
Make Simulated Payment
   ↓
Transaction Recorded
   ↓
View Order / Payment
   ↓
Track Order Status
   ↓
Cancel Order (if permitted)
```

---

# 18. Primary Administrator Journey

```text
Administrator
      ↓
    Login
      ↓
Manage Categories
      ↓
Manage Products
      ↓
View Customer Orders
      ↓
Update Order Status
      ↓
View Payments
      ↓
View Transactions
      ↓
View Cancellations
      ↓
Generate / Analyze Reports
```

---

# 19. Scope - Included

The first version includes:

```text
✓ User Management
✓ Customer Registration & Login
✓ Profile Management
✓ Category Management
✓ Product Management
✓ Product Availability
✓ Order Management
✓ Order Status
✓ Order History
✓ Dummy Payment Processing
✓ Payment History
✓ Transaction Management
✓ Transaction History
✓ Order Cancellation
✓ Cancellation Reasons
✓ Basic Reports / Historical Data
```

---

# 20. Scope - Excluded

The following are intentionally excluded:

```text
✗ Real Payment Gateway
✗ Shopping Cart
✗ Wishlist
✗ Product Reviews / Ratings
✗ Coupons / Discounts
✗ Delivery Partner Integration
✗ Email / SMS Notifications
✗ Real Refund Processing
✗ Multiple Warehouses
✗ Multiple Sellers
✗ Product Recommendations
✗ Customer Support System
✗ Frontend Application
✗ Social Login
```

Also, the following are _not part of the initial business implementation_ but may be introduced later as supporting/architectural technologies:

```text
Spring Security
Swagger / OpenAPI
JUnit / Mockito
Spring Boot Test
AOP
Actuator
Microservices
Kafka
Docker
Jenkins / CI-CD
AWS
React
Angular
AI / Spring AI
```

---

# 21. Success Criteria

The application will satisfy the business requirements when:

1. A customer can register and login.
2. Customers can browse available products.
3. Administrators can manage categories and products.
4. Customers can place orders containing multiple products.
5. Stock quantity is correctly maintained.
6. Order amounts are calculated by the system.
7. Customers can view their own order history.
8. Administrators can view and update order status.
9. Customers can make simulated payments.
10. Payment information is retained.
11. Transaction information is retained.
12. Customers can request cancellation when permitted.
13. Cancellation information is retained.
14. Historical business information remains available.
15. Order, payment and transaction data can support basic reporting.

```

```
````
