<%@include file="header.jsp" %>

    <section class="section" style="padding: 100px 0;">
        <div class="container" style="text-align: center;">
            <i class="fas fa-check-circle" style="font-size: 80px; color: #2ecc71; margin-bottom: 30px;"></i>
            <h1 style="color: #2ecc71; margin-bottom: 20px;">Đặt Hàng Thành Công!</h1>
            <p style="font-size: 18px; margin-bottom: 10px;">Cảm ơn bạn đã đặt món tại Vịt Lão Vương.</p>
            <p style="font-size: 18px; margin-bottom: 30px;">Mã đơn hàng của bạn là: <strong>#${orderId}</strong></p>
            <p>Chúng tôi sẽ liên hệ sớm nhất để xác nhận đơn hàng.</p>

            <div style="margin-top: 50px;">
                <a href="home" class="btn">Về Trang Chủ</a>
                <a href="menu" class="btn btn-secondary">Tiếp Tục Mua Sắm</a>
            </div>
        </div>
    </section>

    <%@include file="footer.jsp" %>