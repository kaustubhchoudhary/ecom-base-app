# Document 2 - Data Model / ER Schema

## Mini E-Commerce Application

_Purpose:_ Define the data entities, attributes, relationships, cardinality, and important data constraints required by the BRD.

---

# 1. Purpose

The BRD defines:

> _What should the business application do?_

This document defines:

> _What data must the application store to support those requirements?_

This document focuses on:

- Entities
- Attributes
- Primary keys
- Foreign keys
- Relationships
- Cardinality
- Important data constraints

It does _not_ define REST APIs or application-layer implementation.

---

# 2. Entities

The application will contain the following entities/tables:

```text
1. USER
2. ROLE
3. CATEGORY
4. PRODUCT
5. ORDER
6. ORDER_ITEM
7. PAYMENT
8. TRANSACTION
9. CANCELLATION
10. CANCELLATION_REASON
```
