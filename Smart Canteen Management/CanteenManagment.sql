use CSE9490



-- Admin credentials
CREATE TABLE canteen_admin (
    username VARCHAR(50) PRIMARY KEY,
    password VARCHAR(50)
);
INSERT INTO canteen_admin VALUES ('admin','1234');


-- Menu and Stock Management
CREATE TABLE menu (
    itemId INT IDENTITY(1,1) PRIMARY KEY,
    itemName VARCHAR(100),
    price DECIMAL(10,2),
    stock INT
);

-- Default Menu Items
INSERT INTO menu (itemName, price, stock) VALUES 
('Coffee', 20.00, 50),
('Sandwich', 45.00, 30),
('Samosa', 15.00, 100),
('Fried Rice', 80.00, 20);
INSERT INTO menu (itemName, price, stock) VALUES 
('Tea', 10.00, 100),
('Green Tea', 25.00, 40),
('Cold Coffee', 40.00, 30),
('Fruit Juice', 35.00, 25),
('Veg Burger', 60.00, 20),
('Chicken Burger', 85.00, 15),
('French Fries', 50.00, 45),
('Paneer Wrap', 70.00, 25),
('Veg Maggi', 30.00, 60),
('Egg Maggi', 40.00, 50),
('Pasta Red Sauce', 90.00, 15),
('Veg Biryani', 100.00, 20),
('Chicken Biryani', 140.00, 25),
('Idli (2pcs)', 30.00, 80),
('Masala Dosa', 55.00, 40),
('Chocolate Muffin', 45.00, 20);
Select * from menu
-- Billing/Order Records
CREATE TABLE billing (
    billId INT IDENTITY(1,1) PRIMARY KEY,
    customerName VARCHAR(50),
    itemName VARCHAR(100),
    quantity INT,
    totalPrice DECIMAL(10,2),
    billDate DATETIME DEFAULT GETDATE()
);
select * from billing
EXEC sp_rename 'bills', 'billing';
GO

EXEC sp_rename 'admin', 'canteen_admin';
GO

SELECT TABLE_NAME 
FROM INFORMATION_SCHEMA.TABLES 
WHERE TABLE_TYPE = 'BASE TABLE';