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
                                    <h3 style="font-size: 18px; margin-bottom: 10px;">${p.name}</h3>
                                    <div style="margin-bottom: 15px;">
                                        <c:choose>
                                            <c:when test="${p.salePrice > 0 && p.salePrice < p.price}">
                                                <p
                                                    style="color: #d32f2f; font-weight: bold; font-size: 16px; display: inline-block; margin-right: 10px;">
                                                    <fmt:formatNumber value="${p.salePrice}" pattern="#,###" />₫
                                                </p>
                                                <p
                                                    style="color: #888; text-decoration: line-through; display: inline-block;">
                                                    <fmt:formatNumber value="${p.price}" pattern="#,###" />₫
                                                </p>
                                            </c:when>
                                            <c:otherwise>
                                                <p style="color: #d32f2f; font-weight: bold; font-size: 16px;">
                                                    <fmt:formatNumber value="${p.price}" pattern="#,###" />₫
                                                </p>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                    <div
                                        style="margin-bottom: 10px; font-size: 13px; color: ${p.quantity > 0 ? '#166534' : '#991b1b'};">
                                        ${p.quantity > 0 ? 'Còn hàng: ' : 'Hết hàng'}
                                        <c:if test="${p.quantity > 0}">
                                            <fmt:formatNumber value="${p.quantity}" pattern="#,###.##" />
                                        </c:if>
                                    </div>
                                    <a href="menu?cid=${p.categoryId}#product-${p.id}" class="btn"
                                        style="width: 100%; display: block; text-align: center;">Đặt
                                        Ngay</a>
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