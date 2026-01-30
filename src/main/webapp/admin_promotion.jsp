<%@page contentType="text/html" pageEncoding="UTF-8" %>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <!DOCTYPE html>
        <html lang="vi">

        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>Gửi Thông Báo Khuyến Mãi - Vịt Lão Vương</title>
        </head>

        <body>
            <%@include file="header.jsp" %>

                <section class="section">
                    <div class="container">
                        <div class="section-title">
                            <h2>Gửi Thông Báo Khuyến Mãi</h2>
                            <div class="line"></div>
                        </div>

                        <div
                            style="max-width: 600px; margin: 0 auto; background: #fff; padding: 30px; border-radius: 10px; box-shadow: 0 0 20px rgba(0,0,0,0.1);">
                            <c:if test="${not empty message}">
                                <div
                                    style="color: #155724; background-color: #d4edda; border-color: #c3e6cb; padding: 10px; border-radius: 5px; margin-bottom: 20px; text-align: center;">
                                    <i class="fas fa-check-circle"></i> ${message}
                                </div>
                            </c:if>
                            <c:if test="${not empty error}">
                                <div
                                    style="color: #721c24; background-color: #f8d7da; border-color: #f5c6cb; padding: 10px; border-radius: 5px; margin-bottom: 20px; text-align: center;">
                                    <i class="fas fa-check-circle"></i> ${error}
                                </div>
                            </c:if>

                            <form action="promotion" method="post">
                                <div class="form-group">
                                    <label>Tiêu đề thông báo</label>
                                    <input type="text" name="title" class="form-control"
                                        placeholder="VD: Khuyến mãi Tết 2026" required>
                                </div>
                                <div class="form-group">
                                    <label>Nội dung</label>
                                    <textarea name="message" class="form-control" rows="5"
                                        placeholder="Nhập nội dung khuyến mãi..." required></textarea>
                                </div>
                                <button type="submit" class="btn" style="width: 100%;">Gửi Thông Báo (Toàn hệ
                                    thống)</button>
                                <div style="text-align: center; margin-top: 20px;">
                                    <a href="orders" style="color: #666; font-size: 14px;">Quay lại quản lý đơn hàng</a>
                                </div>
                            </form>
                        </div>
                    </div>
                </section>

                <%@include file="footer.jsp" %>
        </body>

        </html>