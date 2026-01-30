<%@page contentType="text/html" pageEncoding="UTF-8" %>
    <%@include file="header.jsp" %>

        <section class="section">
            <div class="container">
                <div class="section-title">
                    <h2>Đăng Nhập</h2>
                    <div class="line"></div>
                </div>

                <div
                    style="max-width: 400px; margin: 0 auto; background: #fff; padding: 30px; border-radius: 10px; box-shadow: 0 0 20px rgba(0,0,0,0.1);">
                    <c:if test="${param.register == 'success'}">
                        <div
                            style="color: #155724; background-color: #d4edda; border-color: #c3e6cb; padding: 10px; border-radius: 5px; margin-bottom: 20px; text-align: center;">
                            <i class="fas fa-check-circle"></i> Đăng ký thành công! Vui lòng đăng nhập.
                        </div>
                    </c:if>
                    <c:if test="${not empty error}">
                        <div style="color: red; margin-bottom: 20px; text-align: center;">${error}</div>
                    </c:if>

                    <form action="login" method="post">
                        <div class="form-group">
                            <label>Tên đăng nhập</label>
                            <input type="text" name="username" class="form-control" required>
                        </div>
                        <div class="form-group">
                            <label>Mật khẩu</label>
                            <input type="password" name="password" class="form-control" required>
                        </div>
                        <button type="submit" class="btn" style="width: 100%;">Đăng Nhập</button>

                        <div style="text-align: center; margin-top: 20px;">
                            <p style="margin-bottom: 10px;">Chưa có tài khoản? <a href="register"
                                    style="color: var(--primary-color); font-weight: bold;">Đăng ký ngay</a></p>
                            <a href="home" style="color: #666; font-size: 14px;">Quay lại trang chủ</a>
                        </div>
                    </form>
                </div>
            </div>
        </section>

        <%@include file="footer.jsp" %>