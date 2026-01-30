CREATE DATABASE IF NOT EXISTS vitlaovuong_db;
USE vitlaovuong_db;

DROP TABLE IF EXISTS OrderDetails;
DROP TABLE IF EXISTS Orders;
DROP TABLE IF EXISTS Products;
DROP TABLE IF EXISTS Categories;

CREATE TABLE Categories (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

CREATE TABLE Products (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    price DECIMAL(10, 2) NOT NULL,
    image_url TEXT,
    category_id INT,
    quantity INT DEFAULT 0,
    FOREIGN KEY (category_id) REFERENCES Categories(id)
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

CREATE TABLE Orders (
    id INT AUTO_INCREMENT PRIMARY KEY,
    customer_name VARCHAR(255) NOT NULL,
    customer_phone VARCHAR(20) NOT NULL,
    customer_address TEXT NOT NULL,
    total_amount DECIMAL(10, 2),
    status VARCHAR(50) DEFAULT 'Pending',
    note TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

CREATE TABLE OrderDetails (
    id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT,
    product_id INT,
    quantity INT NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (order_id) REFERENCES Orders(id),
    FOREIGN KEY (product_id) REFERENCES Products(id)
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Insert Sample Data
INSERT INTO Categories (name, description) VALUES 
('Vịt Quay', 'Các món vịt quay chuẩn vị Quảng Đông'),
('Dimsum', 'Há cảo, xíu mại các loại'),
('Bánh Bao', 'Bánh bao nóng hổi'),
('Cơm - Mì', 'Cơm trộn, mì vịt quay'),
('Combo', 'Các set ăn tổng hợp hấp dẫn');

INSERT INTO Products (name, description, price, image_url, category_id, quantity) VALUES 
('Vịt Quay Quảng Đông (Con)', 'Vịt quay da giòn, thịt mềm, chuẩn vị Quảng Đông (Kèm nước chấm đặc biệt)', 350000, 'assets/img/vit-quay.jpg', 1, 50),
('Vịt Quay Quảng Đông (Nửa Con)', 'Nửa con vịt quay tẩm ướp gia truyền thơm nức mũi', 180000, 'assets/img/vit-quay.jpg', 1, 50),
('Há Cảo Tôm Thịt (Xửng)', 'Lớp vỏ mỏng dai, nhân tôm thịt tươi ngọt', 45000, 'assets/img/ha-cao.png', 2, 100),
('Bánh Bao Kim Sa / Trà Xanh', 'Vỏ bánh mềm mịn, nhân trứng muối/trà xanh tan chảy', 15000, 'assets/img/banh-bao.png', 3, 100),
('Cơm Vịt Quay (Đĩa)', 'Cơm trắng dẻo thơm ăn kèm thịt vịt quay, trứng và rau', 50000, 'assets/img/com-vit.jpg', 4, 100),
('Set Vịt Quay + Canh', 'Bữa ăn đầy đủ dinh dưỡng với Cơm, Thịt vịt quay nhiều nạc và Canh nóng', 65000, 'assets/img/set-vit.png', 5, 50);
