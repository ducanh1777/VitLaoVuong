<%@page contentType="text/html" pageEncoding="UTF-8" %>
    <%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
        <%@include file="header.jsp" %>

            <section class="hero-banner">
                <img src="assets/img/banner-tet.png" alt="Chào Tết 2026">
            </section>

            <section class="section">
                <div class="container">
                    <div class="section-title">
                        <h2>Món Ngon Nổi Bật</h2>
                        <div class="line"></div>
                    </div>

                    <div class="product-grid">
                        <c:forEach items="${products}" var="p">
                            <div class="product-card">
                                <div class="product-img">
                                    <c:choose>
                                        <c:when test="${p.imageUrl.startsWith('http')}">
                                            <img src="${p.imageUrl}" alt="${p.name}">
                                        </c:when>
                                        <c:otherwise>
                                            <img src="${p.imageUrl}" alt="${p.name}">
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                                <div class="product-info">
                                    <h3>${p.name}</h3>
                                    <p>${p.description}</p>
                                    <fmt:formatNumber value="${p.price}" pattern="#,###" /> ₫
                                    </span>
                                    <div
                                        style="margin-bottom: 10px; font-size: 13px; color: ${p.quantity > 0 ? '#166534' : '#991b1b'};">
                                        ${p.quantity > 0 ? 'Còn hàng: ' : 'Hết hàng'} ${p.quantity > 0 ? p.quantity :
                                        ''}
                                    </div>
                                    <c:choose>
                                        <c:when test="${p.quantity > 0}">
                                            <a href="menu" class="btn">Xem Thêm</a>
                                        </c:when>
                                        <c:otherwise>
                                            <a href="menu" class="btn" style="background: #ccc;">Xem Thêm</a>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </div>
                        </c:forEach>
                    </div>

                    <div style="text-align: center; margin-top: 40px;">
                        <a href="menu" class="btn btn-secondary">Xem Tất Cả Menu</a>
                    </div>
                </div>
            </section>

            <%@include file="footer.jsp" %>