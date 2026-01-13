# Drug Store Backend - Spring Boot Migration

This project is a migration of the NodeJS/Express/MongoDB backend to Java/Spring Boot/MySQL.

## Original NodeJS Project Analysis

### APIs Listed:
- **User APIs**: Login, Register, Profile, Update Profile, Get All Users, Get User by Email
- **Product APIs**: Get All Products (paginated), Admin Get All, Search, Get Single, Review, CRUD operations
- **Order APIs**: Create Order, Get Orders, Get Order by ID, Pay Order, Deliver Order, Search/Filter Orders
- **Category APIs**: CRUD Categories, Banner management
- **PDF APIs**: CRUD PDFs, Reviews
- **Config**: PayPal client ID

### Database Models:
- User: name, email, password, isAdmin
- Product: ma, name, image, description, reviews, rating, category, price, countInStock, loanPrice, isBought
- Category: name, description, isShow, parentCategory, isParent
- Order: user, orderItems, shippingAddress, paymentMethod, paymentResult, prices, status, etc.
- Pdf: name, image, file, reviews, rating
- Banner: linkImg, linkPage, isShow

### Auth: JWT with Bearer token, roles USER/ADMIN

## MySQL Database Schema

Run the SQL script in `src/main/resources/schema.sql` to create the database.

Tables:
- users
- categories
- products
- product_reviews
- pdfs
- pdf_reviews
- orders
- order_items
- shipping_addresses
- payment_results
- banners

## Project Structure

```
backend-spring/
├── src/main/java/com/nhom91/drugstore/
│   ├── config/          # Configuration classes
│   ├── controller/      # REST Controllers
│   ├── dto/             # Data Transfer Objects
│   ├── entity/          # JPA Entities
│   ├── exception/       # Exception Handlers
│   ├── repository/      # JPA Repositories
│   ├── security/        # Security Configuration
│   ├── service/         # Business Logic Services
│   ├── utils/           # Utility Classes
│   └── DrugstoreApplication.java
├── src/main/resources/
│   ├── application.yml  # Application Configuration
│   └── schema.sql       # Database Schema
├── src/test/            # Tests
├── pom.xml              # Maven Dependencies
└── README.md
```

## Technologies Used

- Java 17
- Spring Boot 3.2.0
- Spring Data JPA
- MySQL 8
- Spring Security
- JWT (JJWT)
- Lombok
- Validation

## Configuration

Update `src/main/resources/application.yml` with your MySQL credentials:

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/drugstore?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC
    username: your_username
    password: your_password
```

## Running the Application

1. Ensure MySQL is running
2. Run the SQL schema: `mysql -u username -p < src/main/resources/schema.sql`
3. Build the project: `mvn clean install`
4. Run the application: `mvn spring-boot:run`
5. Server starts on http://localhost:8080

## Migration Notes

- Converted MongoDB collections to MySQL tables with proper relationships
- Embedded arrays (reviews) moved to separate tables
- JWT authentication migrated from NodeJS jsonwebtoken
- Password hashing uses BCrypt instead of bcryptjs
- Async operations replaced with Spring transactions
- Error handling centralized in GlobalExceptionHandler

## API Endpoints

Same as original NodeJS project, prefixed with `/api/`

- Users: `/api/users/*`
- Products: `/api/products/*`
- Orders: `/api/orders/*`
- Categories: `/api/category/*`
- PDFs: `/api/pdf/*`
- Config: `/api/config/paypal`

## Security

- JWT Bearer token authentication
- Role-based authorization (USER/ADMIN)
- Password encryption with BCrypt

## Notes

- This is a complete migration maintaining all original functionality
- Clean code principles applied with Spring Boot best practices
- Comments indicate migrated components from NodeJS