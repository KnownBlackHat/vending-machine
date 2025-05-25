CREATE DATABASE IF NOT EXISTS vendingmachine_db;
USE vendingmachine_db;

CREATE TABLE products (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    quantity INT NOT NULL
);

-- Sample data
INSERT INTO products (name, price, quantity) VALUES
('Cola', 1.50, 10),
('Chips', 1.00, 15),
('Candy', 0.75, 20);
