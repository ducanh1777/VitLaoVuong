# Hướng Dẫn Cài Đặt Dự Án "Vịt Lão Vương"

Dự án này là một Java Web Application sử dụng Servlet, JSP, và MySQL, được cấu hình quản lý dependency bằng Maven.

## Yêu Cầu
- **JDK**: Phiên bản 1.8 trở lên.
- **IDE**: NetBeans (khuyên dùng) hoặc IntelliJ IDEA / Eclipse.
- **Server**: Apache Tomcat 9 hoặc GlassFish.
- **Database**: MySQL Server.

## Các Bước Cài Đặt

### Bước 1: Thiết Lập Cơ Sở Dữ Liệu
1.  Mở công cụ quản lý MySQL (MySQL Workbench, phpMyAdmin, hoặc HeidiSQL).
2.  Mở file `database.sql` nằm trong thư mục gốc của dự án.
3.  Chạy toàn bộ script để tạo database `vitlaovuong_db` và các bảng dữ liệu mẫu.

### Bước 2: Cấu Hình Kết Nối Database
1.  Mở file `src/main/java/com/vitlaovuong/config/DBContext.java`.
2.  Tìm đến dòng:
    ```java
    private static final String USER = "root";
    private static final String PASS = "password";
    ```
3.  Thay đổi `USER` và `PASS` tương ứng với tài khoản MySQL của bạn.

### Bước 3: Mở Dự Án Trong NetBeans
1.  Mở NetBeans.
2.  Chọn **File** -> **Open Project**.
3.  Trỏ đến thư mục chứa dự án `VitLaoVuong` (thư mục có chứa file `pom.xml`).
4.  NetBeans sẽ tự động nhận diện đây là Maven project và tải các thư viện cần thiết.

### Bước 4: Chạy Dự Án
1.  Chuột phải vào Project -> chọn **Run**.
2.  Nếu được hỏi chọn Server, hãy chọn Apache Tomcat hoặc GlassFish đã cài đặt.
3.  Trình duyệt sẽ tự động mở trang chủ tại địa chỉ `http://localhost:8080/VitLaoVuong/`.

## Cấu Trúc Dự Án
- `src/main/java`: Mã nguồn Java (Model, DAO, Controller).
- `src/main/webapp`: Giao diện (JSP, CSS, Images).
- `src/main/webapp/assets/css`: File `style.css` chứa thiết kế giao diện.

## Ghi Chú
- Nếu gặp lỗi font chữ tiếng Việt, hãy đảm bảo database MySQL được tạo với charset `utf8mb4` (Script đã bao gồm điều này nhưng cần kiểm tra server).
- Để chỉnh sửa menu, bạn có thể sửa trực tiếp trong bảng `Products` và `Categories` trong database.
