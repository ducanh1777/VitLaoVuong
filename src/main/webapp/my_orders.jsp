<%@page contentType="text/html" pageEncoding="UTF-8" %>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

            <%@include file="header.jsp" %>

                <section class="section">
                    <div class="container">
                        <div class="section-title">
                            <h2>Đơn Hàng Của Tôi</h2>
                            <div class="line"></div>
                        </div>

                        <table class="cart-table">
                            <thead>
                                <tr>
                                    <th>Mã ĐH</th>
                                    <th>Ngày Đặt</th>
                                    <th>Tổng Tiền</th>
                                    <th>Trạng Thái</th>
                                    <th>Ghi Chú</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${orders}" var="o">
                                    <tr id="order-${o.id}">
                                        <td>#${o.id}</td>
                                        <td>
                                            <fmt:formatDate value="${o.createdAt}" pattern="dd/MM/yyyy HH:mm" />
                                        </td>
                                        <td style="color: var(--primary-color); font-weight: bold;">
                                            <fmt:formatNumber value="${o.totalAmount}" pattern="#,###" /> ₫
                                        </td>
                                        <td>
                                            <span
                                                style="padding: 5px 10px; border-radius: 4px; font-weight: 500; font-size: 13px;
                                      background: ${o.status == 'Confirmed' ? '#dcfce7' : (o.status == 'Cancelled' ? '#fee2e2' : '#fef9c3')};
                                      color: ${o.status == 'Confirmed' ? '#166534' : (o.status == 'Cancelled' ? '#991b1b' : '#854d0e')};">
                                                ${o.status == 'Pending' ? 'Chờ duyệt' : (o.status == 'Confirmed' ? 'Đã
                                                xác nhận' : 'Đã hủy')}
                                            </span>
                                        </td>
                                        <td>${o.note != null ? o.note : ''}</td>
                                    </tr>
                                </c:forEach>
                                <c:if test="${empty orders}">
                                    <tr>
                                        <td colspan="5" style="text-align: center; padding: 30px;">Bạn chưa có đơn hàng
                                            nào. <a href="menu"
                                                style="color: var(--primary-color); font-weight: bold;">Đặt hàng
                                                ngay</a></td>
                                    </tr>
                                </c:if>
                            </tbody>
                        </table>
                        <!-- Pagination -->
                        <div class="pagination" style="margin-top: 30px; text-align: center;">
                            <c:if test="${endPage > 1}">
                                <c:choose>
                                    <c:when test="${index > 1}">
                                        <a href="my-orders?page=${index - 1}" class="page-link">&laquo; Trước</a>
                                    </c:when>
                                </c:choose>

                                <c:forEach begin="1" end="${endPage}" var="i">
                                    <a href="my-orders?page=${i}"
                                        class="page-link ${i == index ? 'active' : ''}">${i}</a>
                                </c:forEach>

                                <c:choose>
                                    <c:when test="${index < endPage}">
                                        <a href="my-orders?page=${index + 1}" class="page-link">Sau &raquo;</a>
                                    </c:when>
                                </c:choose>
                            </c:if>
                        </div>
                    </div>
                </section>

                <script>
                    document.addEventListener("DOMContentLoaded", function () {
                        const urlParams = new URLSearchParams(window.location.search);
                        const orderId = urlParams.get('id');
                        if (orderId) {
                            const element = document.getElementById("order-" + orderId);
                            if (element) {
                                element.style.backgroundColor = "#fff3cd"; // Highlight color
                                element.scrollIntoView({ behavior: "smooth", block: "center" });
                            }
                        }
                    });
                </script>

                <%@include file="footer.jsp" %>