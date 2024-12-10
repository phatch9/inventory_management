
CREATE DATABASE IF NOT EXISTS inventory_system;
USE inventory_system;

CREATE TABLE suppliers (
    SupplierID INT AUTO_INCREMENT PRIMARY KEY,
    Name VARCHAR(100) NOT NULL,
    ContactInfo VARCHAR(255) NOT NULL
);


CREATE TABLE products (
    product_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(255),
    quantity INT NOT NULL DEFAULT 0,
    supplier_id INT NOT NULL,
    FOREIGN KEY (supplier_id) REFERENCES Suppliers(SupplierID) ON DELETE CASCADE
);


CREATE TABLE orders (
    OrderID INT AUTO_INCREMENT PRIMARY KEY,
    OrderDate DATE NOT NULL,
    product_id INT NOT NULL,
    Status ENUM('Pending', 'Shipped', 'Delivered', 'Cancelled') NOT NULL,
    OrderQuantity INT NOT NULL,
    TotalAmount DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (product_id) REFERENCES products(product_id) ON DELETE CASCADE 
);

