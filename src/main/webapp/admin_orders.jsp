<%@page contentType="text/html" pageEncoding="UTF-8" %>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

            <%@include file="header.jsp" %>

                <section class="section">
                    <div class="container">
                        <div class="section-title">
                            <h2>Quản Lý Đơn Hàng</h2>
                            <div class="line"></div>
                        </div>

                        <table class="cart-table">
                            <thead>
                                <tr>
                                    <th>Mã ĐH</th>
                                    <th>Khách Hàng</th>
                                    <th>SĐT</th>
                                    <th>Thời Gian</th>
                                    <th>Tổng Tiền</th>
                                    <th>Trạng Thái</th>
                                    <th>Ghi Chú</th>
                                    <th>Hành Động</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${orders}" var="o">
                                    <tr id="order-${o.id}">
                                        <td>#${o.id}</td>
                                        <td>
                                            <strong>${o.customerName}</strong><br>
                                            <span style="font-size: 13px; color: #666;">${o.customerAddress}</span>
                                        </td>
                                        <td>${o.customerPhone}</td>
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
                                        <td>${o.note}</td>
                                        <td>
                                            <c:if test="${o.status == 'Pending'}">
                                                <a href="orders?action=confirm&id=${o.id}" class="btn"
                                                    style="padding: 5px 10px; font-size: 12px;">Duyệt</a>
                                                <a href="javascript:void(0)" onclick="cancelOrder(${o.id})"
                                                    class="btn btn-secondary"
                                                    style="padding: 5px 10px; font-size: 12px; background: #991b1b; color: white;">Hủy</a>
                                            </c:if>
                                        </td>
                                    </tr>
                                </c:forEach>
                                <c:if test="${empty orders}">
                                    <tr>
                                        <td colspan="8" style="text-align: center; padding: 30px;">Chưa có đơn hàng nào.
                                        </td>
                                    </tr>
                                </c:if>
                            </tbody>
                        </table>

                        <!-- Pagination -->
                        <div class="pagination" style="margin-top: 20px; text-align: center;">
                            <c:if test="${endPage > 1}">
                                <c:choose>
                                    <c:when test="${index > 1}">
                                        <a href="orders?page=${index - 1}" class="page-link"
                                            style="padding: 5px 10px; border: 1px solid #ddd; border-radius: 4px; color: #333; text-decoration: none; margin: 0 2px;">&laquo;</a>
                                    </c:when>
                                </c:choose>

                                <c:forEach begin="1" end="${endPage}" var="i">
                                    <a href="orders?page=${i}" class="page-link ${i == index ? 'active' : ''}"
                                        style="padding: 5px 10px; border: 1px solid #ddd; border-radius: 4px; color: ${i == index ? '#fff' : '#333'}; background-color: ${i == index ? '#d32f2f' : '#fff'}; text-decoration: none; margin: 0 2px;">${i}</a>
                                </c:forEach>

                                <c:choose>
                                    <c:when test="${index < endPage}">
                                        <a href="orders?page=${index + 1}" class="page-link"
                                            style="padding: 5px 10px; border: 1px solid #ddd; border-radius: 4px; color: #333; text-decoration: none; margin: 0 2px;">&raquo;</a>
                                    </c:when>
                                </c:choose>
                            </c:if>
                        </div>
                    </div>
                </section>

                <script>
                    function cancelOrder(id) {
                        var note = prompt("Vui lòng nhập lý do hủy đơn:");
                        if (note != null) {
                            window.location.href = "orders?action=cancel&id=" + id + "&note=" + encodeURIComponent(note);
                        }
                    }

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