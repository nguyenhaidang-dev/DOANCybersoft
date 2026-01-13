-- MySQL schema for Drug Store migrated from NodeJS MongoDB

CREATE DATABASE IF NOT EXISTS drugstore;
USE drugstore;

-- Users table
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    is_admin BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Categories table
CREATE TABLE categories (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    is_show BOOLEAN DEFAULT FALSE,
    parent_category VARCHAR(255),
    is_parent BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Products table
CREATE TABLE products (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    ma VARCHAR(255),
    name VARCHAR(255) NOT NULL,
    image VARCHAR(255),
    description TEXT NOT NULL,
    rating DECIMAL(3,2) DEFAULT 0,
    num_reviews INT DEFAULT 0,
    category_id BIGINT,
    price DECIMAL(10,2) NOT NULL DEFAULT 0,
    count_in_stock INT NOT NULL DEFAULT 0,
    loan_price DECIMAL(10,2) DEFAULT 0,
    is_bought BOOLEAN,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (category_id) REFERENCES categories(id)
);

-- Product reviews table
CREATE TABLE product_reviews (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    rating INT NOT NULL,
    comment TEXT NOT NULL,
    user_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (product_id) REFERENCES products(id)
);

-- PDFs table
CREATE TABLE pdfs (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    image VARCHAR(255) NOT NULL,
    file VARCHAR(255) NOT NULL,
    rating DECIMAL(3,2) DEFAULT 0,
    num_reviews INT DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- PDF reviews table
CREATE TABLE pdf_reviews (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    rating INT NOT NULL,
    comment TEXT NOT NULL,
    user_id BIGINT NOT NULL,
    pdf_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (pdf_id) REFERENCES pdfs(id)
);

-- Orders table
CREATE TABLE orders (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    payment_method VARCHAR(255) NOT NULL DEFAULT 'Paypal',
    tax_price DECIMAL(10,2) NOT NULL DEFAULT 0.0,
    shipping_price DECIMAL(10,2) NOT NULL DEFAULT 0.0,
    total_price DECIMAL(10,2) NOT NULL DEFAULT 0.0,
    type_pay VARCHAR(255) NOT NULL,
    is_paid BOOLEAN DEFAULT FALSE,
    paid_at TIMESTAMP NULL,
    is_delivered BOOLEAN DEFAULT FALSE,
    delivered_at TIMESTAMP NULL,
    status VARCHAR(255),
    is_receive BOOLEAN,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

-- Order items table
CREATE TABLE order_items (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id BIGINT NOT NULL,
    name VARCHAR(255) NOT NULL,
    qty INT NOT NULL,
    image VARCHAR(255) NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    loan_price DECIMAL(10,2) NOT NULL,
    product_id BIGINT NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders(id),
    FOREIGN KEY (product_id) REFERENCES products(id)
);

-- Shipping addresses table
CREATE TABLE shipping_addresses (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id BIGINT NOT NULL,
    address VARCHAR(255) NOT NULL,
    city VARCHAR(255) NOT NULL,
    postal_code VARCHAR(255) NOT NULL,
    country VARCHAR(255) NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders(id)
);

-- Payment results table
CREATE TABLE payment_results (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id BIGINT NOT NULL,
    payment_id VARCHAR(255),
    status VARCHAR(255),
    update_time VARCHAR(255),
    email_address VARCHAR(255),
    FOREIGN KEY (order_id) REFERENCES orders(id)
);

-- Banners table
CREATE TABLE banners (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    link_img VARCHAR(255),
    link_page VARCHAR(255),
    is_show BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);