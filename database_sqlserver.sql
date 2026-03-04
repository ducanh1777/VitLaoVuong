-- Tạo Database
IF NOT EXISTS (SELECT * FROM sys.databases WHERE name = 'vitlaovuong_db')
BEGIN
    CREATE DATABASE vitlaovuong_db;
END
GO

USE vitlaovuong_db;
GO

-- Xóa bảng nếu đã tồn tại (lưu ý thứ tự xóa do ràng buộc khóa ngoại)
IF OBJECT_ID('OrderDetails', 'U') IS NOT NULL DROP TABLE OrderDetails;
IF OBJECT_ID('Orders', 'U') IS NOT NULL DROP TABLE Orders;
IF OBJECT_ID('Products', 'U') IS NOT NULL DROP TABLE Products;
IF OBJECT_ID('Categories', 'U') IS NOT NULL DROP TABLE Categories;
GO

-- Tạo bảng Categories
CREATE TABLE Categories (
    id INT IDENTITY(1,1) PRIMARY KEY,
    name NVARCHAR(255) NOT NULL,
    description NVARCHAR(MAX)
);

-- Tạo bảng Products
CREATE TABLE Products (
    id INT IDENTITY(1,1) PRIMARY KEY,
    name NVARCHAR(255) NOT NULL,
    description NVARCHAR(MAX),
    price DECIMAL(10, 2) NOT NULL,
    image_url NVARCHAR(MAX),
    category_id INT,
    quantity INT DEFAULT 0,
    CONSTRAINT FK_Product_Category FOREIGN KEY (category_id) REFERENCES Categories(id)
);

-- Tạo bảng Orders
CREATE TABLE Orders (
    id INT IDENTITY(1,1) PRIMARY KEY,
    customer_name NVARCHAR(255) NOT NULL,
    customer_phone NVARCHAR(20) NOT NULL,
    customer_address NVARCHAR(MAX) NOT NULL,
    total_amount DECIMAL(10, 2),
    status NVARCHAR(50) DEFAULT 'Pending',
    note NVARCHAR(MAX),
    created_at DATETIME DEFAULT GETDATE()
);

-- Tạo bảng OrderDetails
CREATE TABLE OrderDetails (
    id INT IDENTITY(1,1) PRIMARY KEY,
    order_id INT,
    product_id INT,
    quantity INT NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    CONSTRAINT FK_OrderDetail_Order FOREIGN KEY (order_id) REFERENCES Orders(id),
    CONSTRAINT FK_OrderDetail_Product FOREIGN KEY (product_id) REFERENCES Products(id)
);
GO

-- Insert dữ liệu mẫu
INSERT INTO Categories (name, description) VALUES 
(N'Vịt Quay', N'Các món vịt quay chuẩn vị Quảng Đông'),
(N'Dimsum', N'Há cảo, xíu mại các loại'),
(N'Bánh Bao', N'Bánh bao nóng hổi'),
(N'Cơm - Mì', N'Cơm trộn, mì vịt quay'),
(N'Combo', N'Các set ăn tổng hợp hấp dẫn');

INSERT INTO Products (name, description, price, image_url, category_id, quantity) VALUES 
(N'Vịt Quay Quảng Đông (Con)', N'Vịt quay da giòn, thịt mềm, chuẩn vị Quảng Đông (Kèm nước chấm đặc biệt)', 350000, 'assets/img/vit-quay.jpg', 1, 50),
(N'Vịt Quay Quảng Đông (Nửa Con)', N'Nửa con vịt quay tẩm ướp gia truyền thơm nức mũi', 180000, 'assets/img/vit-quay.jpg', 1, 50),
(N'Há Cảo Tôm Thịt (Xửng)', N'Lớp vỏ mỏng dai, nhân tôm thịt tươi ngọt', 45000, 'assets/img/ha-cao.png', 2, 100),
(N'Bánh Bao Kim Sa / Trà Xanh', N'Vỏ bánh mềm mịn, nhân trứng muối/trà xanh tan chảy', 15000, 'assets/img/banh-bao.png', 3, 100),
(N'Cơm Vịt Quay (Đĩa)', N'Cơm trắng dẻo thơm ăn kèm thịt vịt quay, trứng và rau', 50000, 'assets/img/com-vit.jpg', 4, 100),
(N'Set Vịt Quay + Canh', N'Bữa ăn đầy đủ dinh dưỡng với Cơm, Thịt vịt quay nhiều nạc và Canh nóng', 65000, 'assets/img/set-vit.png', 5, 50);
GO
