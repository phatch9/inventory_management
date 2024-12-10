USE inventory_system;

INSERT INTO suppliers (Name, ContactInfo) VALUES
('Supplier A', 'supplier_a@example.com'),
('Supplier B', 'supplier_b@example.com'),
('Supplier C', 'supplier_c@example.com'),
('Supplier D', 'supplier_d@example.com'),
('Supplier E', 'supplier_e@example.com'),
('Supplier F', 'supplier_f@example.com'),
('Supplier G', 'supplier_g@example.com'),
('Supplier H', 'supplier_h@example.com'),
('Supplier I', 'supplier_i@example.com'),
('Supplier J', 'supplier_j@example.com'),
('Supplier K', 'supplier_k@example.com'),
('Supplier L', 'supplier_l@example.com'),
('Supplier M', 'supplier_m@example.com'),
('Supplier N', 'supplier_n@example.com'),
('Supplier O', 'supplier_o@example.com');


INSERT INTO products (name, description, quantity, supplier_id) VALUES
('Product 1', 'Description for Product 1', 50, 1),
('Product 2', 'Description for Product 2', 100, 2),
('Product 3', 'Description for Product 3', 200, 3),
('Product 4', 'Description for Product 4', 150, 4),
('Product 5', 'Description for Product 5', 75, 5),
('Product 6', 'Description for Product 6', 120, 6),
('Product 7', 'Description for Product 7', 90, 7),
('Product 8', 'Description for Product 8', 300, 8),
('Product 9', 'Description for Product 9', 250, 9),
('Product 10', 'Description for Product 10', 110, 10),
('Product 11', 'Description for Product 11', 130, 11),
('Product 12', 'Description for Product 12', 180, 12),
('Product 13', 'Description for Product 13', 160, 13),
('Product 14', 'Description for Product 14', 220, 14),
('Product 15', 'Description for Product 15', 140, 15);

INSERT INTO orders (OrderDate, product_id, Status, OrderQuantity, TotalAmount) VALUES
('2024-01-01', 1, 'Pending', 10, 500.00),
('2024-01-02', 2, 'Shipped', 20, 2000.00),
('2024-01-03', 3, 'Delivered', 30, 3000.00),
('2024-01-04', 4, 'Cancelled', 15, 1500.00),
('2024-01-05', 5, 'Pending', 25, 1875.00),
('2024-01-06', 6, 'Shipped', 12, 1200.00),
('2024-01-07', 7, 'Delivered', 18, 1620.00),
('2024-01-08', 8, 'Cancelled', 8, 800.00),
('2024-01-09', 9, 'Pending', 22, 2750.00),
('2024-01-10', 10, 'Shipped', 14, 1540.00),
('2024-01-11', 11, 'Delivered', 19, 2470.00),
('2024-01-12', 12, 'Cancelled', 16, 1920.00),
('2024-01-13', 13, 'Pending', 21, 3360.00),
('2024-01-14', 14, 'Shipped', 11, 1760.00),
('2024-01-15', 15, 'Delivered', 17, 2380.00);
