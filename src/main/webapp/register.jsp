<%@page contentType="text/html" pageEncoding="UTF-8" %>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <!DOCTYPE html>
        <html lang="vi">

        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>Đăng Ký - Vịt Lão Vương</title>
            <link rel="stylesheet" href="assets/css/style.css">
            <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
            <style>
                .login-container {
                    max-width: 400px;
                    margin: 50px auto;
                    padding: 30px;
                    background: #fff;
                    border-radius: 10px;
                    box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);
                }

                .login-container h2 {
                    text-align: center;
                    margin-bottom: 20px;
                    color: var(--primary-color);
                }

                .form-group {
                    margin-bottom: 20px;
                }

                .form-control {
                    width: 100%;
                    padding: 10px;
                    border: 1px solid #ddd;
                    border-radius: 5px;
                }

                .btn-login {
                    width: 100%;
                    padding: 10px;
                    background: var(--primary-color);
                    color: white;
                    border: none;
                    border-radius: 5px;
                    cursor: pointer;
                    font-weight: bold;
                }

                .btn-login:hover {
                    background: #991b1b;
                }

                .error {
                    color: red;
                    text-align: center;
                    margin-bottom: 15px;
                    font-size: 14px;
                }

                .register-link {
                    text-align: center;
                    margin-top: 15px;
                    font-size: 14px;
                }

                .register-link a {
                    color: var(--primary-color);
                    text-decoration: none;
                }
            </style>
        </head>

        <body>
            <%@include file="header.jsp" %>

                <div class="login-container">
                    <h2>Đăng Ký Tài Khoản</h2>

                    <c:if test="${not empty error}">
                        <div class="error">${error}</div>
                    </c:if>

                    <form action="register" method="post">
                        <div class="form-group">
                            <label>Họ và Tên</label>
                            <input type="text" name="fullname" class="form-control" placeholder="Nhập họ và tên..."
                                required>
                        </div>
                        <div class="form-group">
                            <label>Tên đăng nhập</label>
                            <input type="text" name="username" class="form-control" placeholder="Nhập tên đăng nhập..."
                                required>
                        </div>
                        <div class="form-group">
                            <label>Mật khẩu</label>
                            <input type="password" name="password" class="form-control" placeholder="Nhập mật khẩu..."
                                required>
                        </div>
                        <div class="form-group">
                            <label>Nhập lại mật khẩu</label>
                            <input type="password" name="repassword" class="form-control"
                                placeholder="Nhập lại mật khẩu..." required>
                        </div>
                        <button type="submit" class="btn-login">Đăng Ký</button>
                    </form>

                    <div class="register-link">
                        Đã có tài khoản? <a href="login">Đăng nhập ngay</a>
                    </div>
                </div>

                <%@include file="footer.jsp" %>
        </body>

        </html>