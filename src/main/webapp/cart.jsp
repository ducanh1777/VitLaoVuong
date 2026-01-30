<%@include file="header.jsp" %>
    <%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

        <section class="section">
            <div class="container">
                <div class="section-title">
                    <h2>Giỏ Hàng Của Bạn</h2>
                    <div class="line"></div>
                </div>

                <c:choose>
                    <c:when test="${not empty sessionScope.cart && not empty sessionScope.cart.items}">
                        <div style="display: grid; grid-template-columns: 2fr 1fr; gap: 30px;">
                            <!-- Cart Items -->
                            <div>
                                <table class="cart-table">
                                    <thead>
                                        <tr>
                                            <th>Sản Phẩm</th>
                                            <th>Giá</th>
                                            <th>Số Lượng</th>
                                            <th>Tổng</th>
                                            <th>Hành Động</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${sessionScope.cart.items}" var="item">
                                            <tr>
                                                <td>${item.product.name}</td>
                                                <td>
                                                    <fmt:formatNumber value="${item.price}" pattern="#,###" /> ₫
                                                </td>
                                                <td>
                                                    <form action="cart" method="POST" style="display: inline;">
                                                        <input type="hidden" name="action" value="update">
                                                        <input type="hidden" name="pid" value="${item.productId}">
                                                        <input type="number" name="quantity" value="${item.quantity}"
                                                            min="1" style="width: 50px; padding: 5px;">
                                                        <button type="submit"
                                                            style="background: none; border: none; cursor: pointer; color: var(--secondary-color);"><i
                                                                class="fas fa-sync"></i></button>
                                                    </form>
                                                </td>
                                                <td>
                                                    <fmt:formatNumber value="${item.price * item.quantity}"
                                                        pattern="#,###" /> ₫
                                                </td>
                                                <td>
                                                    <a href="cart?action=remove&pid=${item.productId}"
                                                        style="color: var(--primary-color);"><i
                                                            class="fas fa-trash"></i></a>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>

                                <a href="menu" class="btn btn-secondary"><i class="fas fa-arrow-left"></i> Tiếp Tục Mua
                                    Hàng</a>
                            </div>

                            <!-- Checkout Form -->
                            <div class="cart-summary">
                                <h3>Tổng Cộng: <span style="color: var(--primary-color); float: right;">
                                        <fmt:formatNumber value="${sessionScope.cart.totalMoney}" pattern="#,###" /> ₫
                                    </span></h3>
                                <hr style="margin: 20px 0; border: none; border-top: 1px solid #ddd;">

                                <h4 style="margin-bottom: 20px;">Thông Tin Giao Hàng</h4>
                                <form action="checkout" method="POST">
                                    <c:if test="${not empty error}">
                                        <div style="color: red; margin-bottom: 10px;">${error}</div>
                                    </c:if>

                                    <div class="form-group">
                                        <label for="name">Họ Tên:</label>
                                        <input type="text" id="name" name="name" class="form-control" required
                                            placeholder="Nhập họ tên...">
                                    </div>
                                    <div class="form-group">
                                        <label for="phone">Số Điện Thoại:</label>
                                        <input type="text" id="phone" name="phone" class="form-control" required
                                            placeholder="Nhập số điện thoại...">
                                    </div>
                                    <div class="form-group">
                                        <label for="address">Địa Chỉ Nhận Hàng:</label>
                                        <textarea id="address" name="address" class="form-control" rows="3" required
                                            placeholder="Nhập địa chỉ..."></textarea>
                                    </div>
                                    <div class="form-group">
                                        <label for="note">Ghi Chú (Tùy chọn):</label>
                                        <textarea id="note" name="note" class="form-control" rows="2"
                                            placeholder="Ví dụ: Ít cay, nhiều nước sốt..."></textarea>
                                    </div>

                                    <button type="submit" class="btn" style="width: 100%;">Đặt Hàng Ngay</button>
                                </form>
                            </div>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div style="text-align: center; padding: 50px;">
                            <i class="fas fa-shopping-cart"
                                style="font-size: 50px; color: #ddd; margin-bottom: 20px;"></i>
                            <h3>Giỏ hàng của bạn đang trống</h3>
                            <p>Hãy chọn những món ăn ngon từ thực đơn nhé!</p>
                            <a href="menu" class="btn" style="margin-top: 20px;">Xem Thực Đơn</a>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
        </section>

        <%@include file="footer.jsp" %>