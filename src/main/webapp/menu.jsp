<%@include file="header.jsp" %>
    <%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
        <%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
            <style>
                /* Toast đẹp hơn */
                #toast {
                    visibility: hidden;
                    min-width: 300px;
                    background-color: #04AA6D;
                    /* Green */
                    color: #fff;
                    text-align: center;
                    border-radius: 50px;
                    padding: 16px;
                    position: fixed;
                    z-index: 1000;
                    left: 50%;
                    bottom: 30px;
                    font-size: 17px;
                    transform: translateX(-50%);
                    box-shadow: 0 4px 15px rgba(0, 0, 0, 0.3);
                    opacity: 0;
                    transition: opacity 0.5s, bottom 0.5s;
                }

                #toast.show {
                    visibility: visible;
                    opacity: 1;
                    bottom: 50px;
                }

                /* Nút tăng giảm số lượng */
                .qty-container {
                    display: flex;
                    align-items: center;
                    justify-content: center;
                    margin-bottom: 15px;
                    background: #f3f3f3;
                    border-radius: 20px;
                    padding: 5px;
                    width: fit-content;
                    margin-left: auto;
                    margin-right: auto;
                }

                .qty-btn {
                    width: 30px;
                    height: 30px;
                    background: white;
                    border: 1px solid #ddd;
                    border-radius: 50%;
                    font-weight: bold;
                    cursor: pointer;
                    color: #333;
                    display: flex;
                    align-items: center;
                    justify-content: center;
                    transition: all 0.2s;
                }

                .qty-btn:hover {
                    background: #04AA6D;
                    color: white;
                    border-color: #04AA6D;
                }

                .qty-input {
                    width: 40px;
                    border: none;
                    background: transparent;
                    text-align: center;
                    font-weight: bold;
                    font-size: 16px;
                    margin: 0 5px;
                }

                /* Ẩn nút tăng giảm mặc định của input number */
                input::-webkit-outer-spin-button,
                input::-webkit-inner-spin-button {
                    -webkit-appearance: none;
                    margin: 0;
                }
            </style>


            <section class="section">
                <div class="container">
                    <div class="section-title">
                        <h2>Thực Đơn Của Chúng Tôi</h2>
                        <div class="line"></div>
                    </div>

                    <div class="category-filter">
                        <a href="menu" class="category-btn ${cid == null ? 'active' : ''}">Tất Cả</a>
                        <c:forEach items="${categories}" var="c">
                            <a href="menu?cid=${c.id}" class="category-btn ${cid == c.id ? 'active' : ''}">${c.name}</a>
                        </c:forEach>
                    </div>

                    <div class="product-grid">
                        <c:forEach items="${products}" var="p">
                            <div class="product-card" id="product-${p.id}">
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

                                    <div style="margin-top: 5px;">
                                        <c:choose>
                                            <c:when test="${p.salePrice > 0 && p.salePrice < p.price}">
                                                <span style="font-weight: bold; color: #d32f2f; font-size: 16px;">
                                                    <fmt:formatNumber value="${p.salePrice}" pattern="#,###" />₫
                                                </span>
                                                <span
                                                    style="text-decoration: line-through; color: #888; margin-left: 8px; font-size: 14px;">
                                                    <fmt:formatNumber value="${p.price}" pattern="#,###" />₫
                                                </span>
                                            </c:when>
                                            <c:otherwise>
                                                <span style="font-weight: bold; color: #d32f2f; font-size: 16px;">
                                                    <fmt:formatNumber value="${p.price}" pattern="#,###" />₫
                                                </span>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                    <p>${p.description}</p>
                                    <div
                                        style="margin-bottom: 10px; font-size: 13px; color: ${p.quantity > 0 ? '#166534' : '#991b1b'};">
                                        ${p.quantity > 0 ? 'Còn hàng: ' : 'Hết hàng'}
                                        <c:if test="${p.quantity > 0}">
                                            <fmt:formatNumber value="${p.quantity}" pattern="#,###.##" />
                                        </c:if>
                                    </div>
                                    <c:choose>
                                        <c:when test="${p.quantity > 0}">
                                            <c:set var="isDuck" value="${fn:trim(p.name) eq 'Vịt quay Quảng Đông'}" />
                                            <c:set var="step" value="${isDuck ? 0.5 : 1}" />
                                            <c:set var="min" value="${isDuck ? 0.5 : 1}" />

                                            <div class="qty-container">
                                                <div class="qty-btn" onclick="updateQty(${p.id}, -${step})">-</div>
                                                <input type="number" id="qty_${p.id}" class="qty-input" value="${min}"
                                                    min="${min}" step="${step}" max="${p.quantity}" readonly>
                                                <div class="qty-btn" onclick="updateQty(${p.id}, ${step})">+</div>
                                            </div>
                                            <button onclick="addToCart(${p.id})" class="btn"
                                                style="width: 100%; border-radius: 25px;">Thêm Vào Giỏ</button>
                                        </c:when>
                                        <c:otherwise>
                                            <button class="btn"
                                                style="background: #ccc; cursor: not-allowed; width: 100%; border-radius: 25px;"
                                                disabled>Hết Hàng</button>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </div>
                        </c:forEach>
                    </div>

                    <c:if test="${empty products}">
                        <p style="text-align: center; color: #666; margin-top: 30px;">Không tìm thấy sản phẩm nào trong
                            danh
                            mục
                            này.</p>
                    </c:if>
                    <!-- Pagination -->
                    <div class="pagination" style="margin-top: 30px; text-align: center;">
                        <c:if test="${endPage > 1}">
                            <c:set var="urlParams" value="cid=${cid != null ? cid : ''}" />

                            <c:choose>
                                <c:when test="${index > 1}">
                                    <a href="menu?${urlParams}&page=${index - 1}" class="page-link">&laquo; Trước</a>
                                </c:when>
                            </c:choose>

                            <c:forEach begin="1" end="${endPage}" var="i">
                                <a href="menu?${urlParams}&page=${i}"
                                    class="page-link ${i == index ? 'active' : ''}">${i}</a>
                            </c:forEach>

                            <c:choose>
                                <c:when test="${index < endPage}">
                                    <a href="menu?${urlParams}&page=${index + 1}" class="page-link">Sau &raquo;</a>
                                </c:when>
                            </c:choose>
                        </c:if>
                    </div>
                </div>
            </section>

            <%@include file="footer.jsp" %>

                <div id="toast"><i class="fas fa-check-circle"></i> Thêm vào giỏ hàng thành công!</div>

                <script>
                    function updateQty(pid, change) {
                        var input = document.getElementById("qty_" + pid);
                        var currentVal = parseFloat(input.value);
                        var max = parseFloat(input.getAttribute("max"));
                        var newVal = currentVal + change;

                        // Round to 1 decimal place to avoid 0.5000001
                        newVal = Math.round(newVal * 10) / 10;

                        if (newVal >= 0.5 && newVal <= max) {
                            input.value = newVal;
                        }
                    }

                    function addToCart(pid) {
                        var qtyInput = document.getElementById("qty_" + pid);
                        var qty = qtyInput ? qtyInput.value : 1;

                        if (qty < 1) {
                            alert("Số lượng phải lớn hơn 0");
                            return;
                        }

                        fetch('cart?action=add&pid=' + pid + '&quantity=' + qty + '&ajax=true')
                            .then(response => response.json())
                            .then(data => {
                                // Show toast
                                var x = document.getElementById("toast");
                                x.innerHTML = '<i class="fas fa-check-circle"></i> ' + data.message;
                                x.className = "show";
                                setTimeout(function () { x.className = x.className.replace("show", ""); }, 3000);

                                // Reset quantity to 1
                                var inputReset = document.getElementById("qty_" + pid);
                                if (inputReset) {
                                    inputReset.value = 1;
                                }

                                // Update cart count
                                var cartCount = document.getElementById("cart-qty");
                                if (cartCount) {
                                    cartCount.innerText = data.cartSize;
                                }
                            })
                            .catch(error => console.error('Error:', error));
                    }
                </script>