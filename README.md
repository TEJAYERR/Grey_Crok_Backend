# Grey_Crok_Backend

Backend service for **Grey Crok**, an e-commerce platform built with **Spring Boot**. The application provides secure REST APIs for authentication, product management, order processing, payments, refunds, inventory management, and Razorpay webhook integration.

---

## Tech Stack

- Java 21
- Spring Boot
- Spring Security
- Spring Data JPA (Hibernate)
- PostgreSQL
- Maven
- JWT Authentication
- Razorpay Payment Gateway
- Docker

---

## Features

### Authentication
- User Registration
- User Login
- JWT Authentication
- Role-based Authorization
- BCrypt Password Encryption

### Product Management
- Product CRUD Operations
- Product Images
- Product Availability
- Inventory Management

### Cart
- Add to Cart
- Update Quantity
- Remove Items
- View Cart

### Order Management
- Place Orders
- View Order History
- View Individual Orders
- Cancel Orders
- Order Status Tracking

### Payments
- Razorpay Integration
- Cash on Delivery (COD)
- Payment Verification
- Secure Signature Validation

### Refunds
- Refund Initiation
- Refund Webhook Processing
- Automatic Stock Restoration
- Refund Status Tracking

### Security
- JWT Filter
- Stateless Authentication
- Protected REST APIs
- Global Exception Handling

---

## Architecture

- RESTful API Design
- Layered Architecture
- DTO-Based Request & Response Models
- Transaction Management
- Repository Pattern
- Production-Oriented Error Handling

---

## Project Structure

```
src
├── config
├── controller
├── dto
├── entities
├── exception
├── filter
├── repository
├── service
└── util
```

---

## Environment Variables

Configure the following environment variables before running the application.

| Variable | Description |
|----------|-------------|
| DB_URL | PostgreSQL JDBC URL |
| DB_USERNAME | Database Username |
| DB_PASSWORD | Database Password |
| JWT_SECRET | JWT Secret Key |
| RAZORPAY_KEY | Razorpay Key ID |
| RAZORPAY_SECRET | Razorpay Secret |
| RAZORPAY_WEBHOOK_SECRET | Razorpay Webhook Secret |
| PORT | Server Port (Optional for local development) |

---

## Running Locally

### Clone the repository

```bash
git clone https://github.com/TEJAYERR/Grey_Crok_Backend.git
cd Grey_Crok_Backend
```

### Configure Environment Variables

Create your local environment configuration using the variables listed above.

### Run the application

```bash
./mvnw spring-boot:run
```

The backend will be available at:

```
http://localhost:8080
```

---

## Docker

Build the image

```bash
docker build -t grey-crok-backend .
```

Run the container

```bash
docker run -p 8080:8080 \
-e DB_URL=... \
-e DB_USERNAME=... \
-e DB_PASSWORD=... \
-e JWT_SECRET=... \
-e RAZORPAY_KEY=... \
-e RAZORPAY_SECRET=... \
-e RAZORPAY_WEBHOOK_SECRET=... \
grey-crok-backend
```

---

## API Modules

- Authentication
- Products
- Cart
- Orders
- Payments
- Refunds
- Razorpay Webhooks
- Health Check

---

## Order Status

- PENDING
- CONFIRMED
- SHIPPED
- DELIVERED
- CANCELLED

---

## Payment Status

- PENDING
- NOT_PAID
- PAID
- REFUND_INITIATED
- REFUNDED
- FAILED
- ABANDONED

---

## Deployment

| Component | Platform |
|----------|----------|
| Backend | Render |
| Database | PostgreSQL |
| Frontend | Vercel |
| Payment Gateway | Razorpay |

---

## Roadmap

- Shiprocket Integration
- Admin Dashboard APIs
- Email Notifications
- Order Tracking
- Logging & Auditing
- API Documentation (OpenAPI / Swagger)
- Monitoring & Metrics

---

## Contributors

- **Teja Yerriboyina**
- **Grey Crok Team**
