package com.vitlaovuong.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "SetupServlet", urlPatterns = { "/setup" })
public class SetupServlet extends HttpServlet {

        // Thông tin kết nối MySQL gốc (không chỉ định DB cụ thể để có thể tạo DB)
        private static final String ROOT_URL = "jdbc:mysql://localhost:3306/?useUnicode=true&characterEncoding=UTF-8";
        private static final String USER = "root";
        private static final String PASS = "123456";

        protected void processRequest(HttpServletRequest request, HttpServletResponse response)
                        throws ServletException, IOException {
                response.setContentType("text/html;charset=UTF-8");
                try {
                        Class.forName("com.mysql.cj.jdbc.Driver");

                        try (Connection conn = DriverManager.getConnection(ROOT_URL, USER, PASS);
                                        Statement stmt = conn.createStatement()) {

                                // 1. Tạo Database nếu chưa có
                                stmt.executeUpdate(
                                                "CREATE DATABASE IF NOT EXISTS vitlaovuong_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci");
                                stmt.executeUpdate("USE vitlaovuong_db");

                                // 2. Tắt kiểm tra khóa ngoại để reset bảng
                                stmt.executeUpdate("SET FOREIGN_KEY_CHECKS=0");

                                // 3. Xóa và Tạo lại bảng Categories
                                stmt.executeUpdate("DROP TABLE IF EXISTS Categories");
                                stmt.executeUpdate("CREATE TABLE Categories (" +
                                                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                                                "name VARCHAR(255) NOT NULL, " +
                                                "description TEXT" +
                                                ") CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci");

                                // 4. Xóa và Tạo lại bảng Products
                                stmt.executeUpdate("DROP TABLE IF EXISTS Products");
                                stmt.executeUpdate("CREATE TABLE Products (" +
                                                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                                                "name VARCHAR(255) NOT NULL, " +
                                                "description TEXT, " +
                                                "price DECIMAL(10, 2) NOT NULL, " +
                                                "image_url VARCHAR(500), " +
                                                "category_id INT, " +
                                                "FOREIGN KEY (category_id) REFERENCES Categories(id)" +
                                                ") CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci");

                                // 5. Xóa và Tạo lại bảng Orders
                                stmt.executeUpdate("DROP TABLE IF EXISTS Orders");
                                stmt.executeUpdate("CREATE TABLE Orders (" +
                                                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                                                "customer_name VARCHAR(255) NOT NULL, " +
                                                "customer_phone VARCHAR(20) NOT NULL, " +
                                                "customer_address TEXT NOT NULL, " +
                                                "total_amount DECIMAL(10, 2), " +
                                                "status VARCHAR(50) DEFAULT 'Pending', " +
                                                "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                                                ") CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci");

                                // 6. Xóa và Tạo lại bảng OrderDetails
                                stmt.executeUpdate("DROP TABLE IF EXISTS OrderDetails");
                                stmt.executeUpdate("CREATE TABLE OrderDetails (" +
                                                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                                                "order_id INT, " +
                                                "product_id INT, " +
                                                "quantity INT NOT NULL, " +
                                                "price DECIMAL(10, 2) NOT NULL, " +
                                                "FOREIGN KEY (order_id) REFERENCES Orders(id), " +
                                                "FOREIGN KEY (product_id) REFERENCES Products(id)" +
                                                ") CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci");

                                // 7. Bật lại kiểm tra khóa ngoại
                                stmt.executeUpdate("SET FOREIGN_KEY_CHECKS=1");

                                // 7.1. Xóa và Tạo lại bảng Users (Mới thêm)
                                stmt.executeUpdate("DROP TABLE IF EXISTS Users");
                                stmt.executeUpdate("CREATE TABLE Users (" +
                                                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                                                "username VARCHAR(50) NOT NULL UNIQUE, " +
                                                "password VARCHAR(50) NOT NULL, " +
                                                "full_name VARCHAR(100), " +
                                                "role VARCHAR(20) NOT NULL" + // ADMIN or CUSTOMER
                                                ") CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci");

                                // 7.2. Insert Default Admin
                                stmt.executeUpdate(
                                                "INSERT INTO Users (username, password, full_name, role) VALUES ('admin', '123456', 'Quản Trị Viên', 'ADMIN')");

                                // 8. Insert Categories
                                String sqlCat = "INSERT INTO Categories (id, name, description) VALUES (?, ?, ?)";
                                try (PreparedStatement ps = conn.prepareStatement(sqlCat)) {
                                        ps.setInt(1, 1);
                                        ps.setString(2, "Vịt Quay");
                                        ps.setString(3, "Các món vịt quay cao cấp");
                                        ps.addBatch();
                                        ps.setInt(1, 2);
                                        ps.setString(2, "Dimsum");
                                        ps.setString(3, "Há cảo, xíu mại");
                                        ps.addBatch();
                                        ps.setInt(1, 3);
                                        ps.setString(2, "Bánh Bao");
                                        ps.setString(3, "Bánh bao các loại");
                                        ps.addBatch();
                                        ps.setInt(1, 4);
                                        ps.setString(2, "Cơm - Mì");
                                        ps.setString(3, "Cơm văn phòng");
                                        ps.addBatch();
                                        ps.setInt(1, 5);
                                        ps.setString(2, "Combo");
                                        ps.setString(3, "Set ăn tiết kiệm");
                                        ps.addBatch();
                                        ps.executeBatch();
                                }

                                // 9. Insert Products
                                String sqlPro = "INSERT INTO Products (name, description, price, image_url, category_id) VALUES (?, ?, ?, ?, ?)";
                                try (PreparedStatement ps = conn.prepareStatement(sqlPro)) {
                                        // Vịt
                                        ps.setString(1, "Vịt Quay Quảng Đông (Con)");
                                        ps.setString(2, "Vịt quay da giòn, thịt mềm, chuẩn vị Quảng Đông");
                                        ps.setDouble(3, 350000);
                                        ps.setString(4, "assets/img/vit-quay.jpg");
                                        ps.setInt(5, 1);
                                        ps.addBatch();

                                        ps.setString(1, "Vịt Quay Quảng Đông (Nửa Con)");
                                        ps.setString(2, "Nửa con vịt quay tẩm ướp gia truyền");
                                        ps.setDouble(3, 180000);
                                        ps.setString(4, "assets/img/vit-quay.jpg");
                                        ps.setInt(5, 1);
                                        ps.addBatch();

                                        // Dimsum
                                        ps.setString(1, "Há Cảo Tôm Thịt");
                                        ps.setString(2, "Há cảo nhân tôm thịt tươi ngon (Xửng)");
                                        ps.setDouble(3, 45000);
                                        ps.setString(4, "assets/img/ha-cao.png");
                                        ps.setInt(5, 2);
                                        ps.addBatch();

                                        // Bánh bao
                                        ps.setString(1, "Bánh Bao Kim Sa");
                                        ps.setString(2, "Bánh bao nhân trứng muối tan chảy");
                                        ps.setDouble(3, 15000);
                                        ps.setString(4, "assets/img/banh-bao.png");
                                        ps.setInt(5, 3);
                                        ps.addBatch();

                                        // Cơm
                                        ps.setString(1, "Cơm Vịt Quay (Đầy đủ)");
                                        ps.setString(2,
                                                        "Cơm trắng dẻo ăn kèm vịt quay da giòn, trứng luộc, rau cải ngọt và dưa chua giải ngấy");
                                        ps.setDouble(3, 55000);
                                        ps.setString(4, "assets/img/com-vit-new.jpg");
                                        ps.setInt(5, 4);
                                        ps.addBatch();

                                        // Set
                                        ps.setString(1, "Set Vịt Quay + Canh");
                                        ps.setString(2, "Combo cơm, vịt quay và canh");
                                        ps.setDouble(3, 65000);
                                        ps.setString(4, "assets/img/set-vit.png");
                                        ps.setInt(5, 5);
                                        ps.addBatch();

                                        ps.executeBatch();
                                }

                                response.getWriter().println("<h1 style='color:green'>SETUP FULL SUCCESS!</h1>");
                                response.getWriter()
                                                .println("<p>Da tao Database 'vitlaovuong_db', tao Bang, va nap du lieu thanh cong!</p>");
                                response.getWriter().println(
                                                "<a href='home' style='font-size:20px'>VAO TRANG CHU NGAY &gt;&gt;</a>");
                        }

                } catch (Exception e) {
                        e.printStackTrace();
                        response.getWriter().println("<h1 style='color:red'>Setup Failed</h1>");
                        response.getWriter().println("<pre>");
                        e.printStackTrace(response.getWriter());
                        response.getWriter().println("</pre>");
                }
        }

        @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response)
                        throws ServletException, IOException {
                processRequest(request, response);
        }
}
