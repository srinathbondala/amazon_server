# Amazon Clone Backend

Welcome to the Amazon Clone Backend! This repository contains the backend code for the Amazon Clone project, implemented using Spring Boot and MongoDB. It includes JWT authentication and Spring Security for secure API calls and provides all the necessary routes to support the frontend functionalities.

## Features

- User Authentication (JWT)
- Secure API Endpoints with Spring Security
- Product Management
- Shopping Cart Management
- Order Processing
- User Profile Management
- Product Reviews
- Payment Processing

## Project Structure

- `src/main/java`: Contains the Java source code
  - `com.example.amazon_server`: Base package
    - `controller`: REST controllers
    - `models`: Entity classes
    - `repository`: Data access layer
    - `services`: Business logic
    - `security`: Security configurations and JWT utility classes
- `src/main/resources`: Contains configuration files
  - `application.properties`: Main configuration file

## Getting Started

### Prerequisites

- Java Development Kit (JDK) installed
- Maven installed
- MongoDB installed and running

### Installation

1. **Clone the repository**

    ```bash
    git clone https://github.com/srinathbondala/amazon_server
    cd amazon_server
    ```

2. **Configure the database**

    Update the `application.properties` file with your MongoDB configuration.

    ```properties
    spring.data.mongodb.uri=mongodb://localhost:27017/yourdbname
    ```

3. **Build and run the application**

    ```bash
    mvn clean install
    mvn spring-boot:run
    ```

### Usage

The backend provides the following routes to support the frontend functionalities:

### Amazon Controller Routes

- **Welcome Message**
  - `GET /amazon/`: Returns a welcome message.

- **Fetch All Products**
  - `GET /amazon/data`: Returns a list of all products.

- **Fetch Products by Category**
  - `GET /amazon/dataByCategory/{category}`: Returns a list of products based on the main category.
  - `GET /amazon/dataByCategory/{category}/{feature}`: Returns a list of products based on the main category and a specific feature.

- **Fetch Product by ID**
  - `GET /amazon/dataByid/{id}`: Returns product details by ID.

### Admin Controller Routes

- **Fetch All Products with Full Details**
  - `GET /admin/data`: Returns a list of all products with full details.

- **Add Category**
  - `POST /admin/addCategory`: Adds a new product category.

- **Add Multiple Categories**
  - `POST /admin/addManyCategory`: Adds multiple product categories.

- **Add Product**
  - `POST /admin/addProduct`: Adds a new product.

- **Add Multiple Products**
  - `POST /admin/addManyProducts`: Adds multiple products.

- **Delete All Products**
  - `DELETE /admin/deleteAllProducts`: Deletes all products.

- **Delete Category**
  - `DELETE /admin/deleteCategory/{category}`: Deletes a specific category.

- **Delete Product by ID**
  - `DELETE /admin/deleteproduct/{id}`: Deletes a product by ID.

- **Update Product by ID**
  - `PUT /admin/updateproduct/{id}`: Updates a product by ID.

- **Update Product Rating**
  - `PUT /admin/updaterating/{id}`: Updates the rating of a product.

### Authentication Controller Routes

- **User Sign-in**
  - `POST /auth1/signin`: Authenticates a user and returns a JWT token.

- **User Sign-up**
  - `POST /auth1/signup`: Registers a new user.

- **User Logout**
  - `POST /auth1/logout`: Logs out the user.

- **Fetch User Details**
  - `GET /auth1/details`: Fetches user details based on the JWT token.

- **Check Email**
  - `GET /auth1/checkemail/{email}`: Checks if an email is already registered.

- **Validate User**
  - `POST /auth1/validateUser`: Validates a user based on JWT token and password.

- **Delete User**
  - `DELETE /auth1/deleteuser/{email}`: Deletes a user by email.

## JWT Authentication and Spring Security

The application uses JWT authentication and Spring Security to secure API endpoints. Upon successful login, a JWT token is generated and must be included in the `Authorization` header of subsequent requests.

```http
Authorization: Bearer <token>
```

### Example Configuration for JWT

- **Security Configuration**: Configures HTTP security and JWT filters.
- **JWT Utility Class**: Handles JWT token creation and validation.

## Contributing

Contributions are welcome! Please fork this repository and submit a pull request for any changes or improvements.

## License

This project is licensed under the MIT License.

---

For more details, refer to the full source code on [GitHub](https://github.com/srinathbondala/amazon_server).
