<%@page contentType="text/html" pageEncoding="UTF-8" %>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

            <%@include file="header.jsp" %>

                <div class="container" style="display: flex; flex-direction: column; gap: 20px; padding-top: 20px;">
                    <!-- Thong bao -->
                    <c:if test="${not empty message}">
                        <div
                            style="background-color: #d4edda; color: #155724; padding: 15px; border-radius: 5px; border: 1px solid #c3e6cb;">
                            <i class="fas fa-check-circle"></i> ${message}
                        </div>
                    </c:if>
                    <c:if test="${not empty error}">
                        <div
                            style="background-color: #f8d7da; color: #721c24; padding: 15px; border-radius: 5px; border: 1px solid #f5c6cb;">
                            <i class="fas fa-exclamation-circle"></i> ${error}
                        </div>
                    </c:if>

                    <div style="display: flex; gap: 30px;">

                        <!-- Form Them/Sua -->
                        <div
                            style="flex: 1; background: #fff; padding: 20px; border-radius: 10px; box-shadow: 0 5px 15px rgba(0,0,0,0.1); height: fit-content;">
                            <h3 style="margin-bottom: 20px; color: var(--primary-color);">${product != null ? 'Sửa Món'
                                :
                                'Thêm Món Mới'}</h3>
                            <form action="products" method="post">
                                <input type="hidden" name="id" value="${product.id}">

                                <div class="form-group">
                                    <label>Tên món</label>
                                    <input type="text" name="name" class="form-control" value="${product.name}"
                                        required>
                                </div>

                                <div class="form-group">
                                    <label>Danh mục</label>
                                    <select name="categoryId" class="form-control" onchange="toggleNewCategory(this)">
                                        <c:forEach items="${categories}" var="c">
                                            <option value="${c.id}" ${product.categoryId==c.id ? 'selected' : '' }>
                                                ${c.name}
                                            </option>
                                        </c:forEach>
                                        <option value="-1">-- Thêm danh mục mới --</option>
                                    </select>
                                    <input type="text" name="newCategoryName" id="newCategoryInput" class="form-control"
                                        style="margin-top: 10px; display: none;" placeholder="Nhập tên danh mục mới">
                                </div>
                                <script>
                                    function toggleNewCategory(selectElement) {
                                        var newCategoryInput = document.getElementById('newCategoryInput');
                                        if (selectElement.value == '-1') {
                                            newCategoryInput.style.display = 'block';
                                            newCategoryInput.required = true;
                                        } else {
                                            newCategoryInput.style.display = 'none';
                                            newCategoryInput.required = false;
                                        }
                                    }
                                </script>

                                <div class="form-group">
                                    <label>Giá tiền (VNĐ)</label>
                                    <input type="number" name="price" class="form-control" value="${product.price}"
                                        required>
                                </div>

                                <div class="form-group">
                                    <label>Giảm giá (%) - Nhập 0 nếu không giảm</label>
                                    <input type="number" name="discountPercent" class="form-control"
                                        value="${product != null && product.salePrice > 0 ? (100 - (product.salePrice * 100 / product.price)).intValue() : 0}"
                                        required min="0" max="100">
                                </div>

                                <div class="form-group">
                                    <label>Số lượng tồn kho</label>
                                    <input type="number" name="quantity" class="form-control"
                                        value="${product.quantity}" required min="0" step="0.1">
                                </div>

                                <div class="form-group">
                                    <label>URL Hình ảnh (VD: assets/img/vit-quay.jpg)</label>
                                    <input type="text" name="image" class="form-control"
                                        value="${product.imageUrl != null ? product.imageUrl : 'assets/img/'}">
                                </div>

                                <div class="form-group">
                                    <label>Mô tả</label>
                                    <textarea name="description" class="form-control"
                                        rows="3">${product.description}</textarea>
                                </div>

                                <button type="submit" class="btn" style="width: 100%;">${product != null ? 'Cập Nhật' :
                                    'Thêm Món'}</button>
                                <c:if test="${product != null}">
                                    <a href="products" class="btn btn-secondary"
                                        style="display: block; text-align: center; margin-top: 10px;">Hủy Bỏ</a>
                                </c:if>
                            </form>
                        </div>

                        <!-- Danh sach -->
                        <div style="flex: 2;">
                            <h3 style="margin-bottom: 20px;">Danh Sách Món Ăn</h3>
                            <table class="cart-table" style="font-size: 14px;">
                                <thead>
                                    <tr>
                                        <th>STT</th>
                                        <th>Hình</th>
                                        <th>Tên Món</th>
                                        <th>Giá</th>
                                        <th>Giá KM</th>
                                        <th>Số Lượng</th>
                                        <th>Thao Tác</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${products}" var="p" varStatus="vs">
                                        <tr>
                                            <td>${vs.count}</td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${p.imageUrl.startsWith('http')}">
                                                        <img src="${p.imageUrl}"
                                                            style="width: 50px; height: 50px; object-fit: cover; border-radius: 4px;">
                                                    </c:when>
                                                    <c:otherwise>
                                                        <img src="${pageContext.request.contextPath}/${p.imageUrl}"
                                                            style="width: 50px; height: 50px; object-fit: cover; border-radius: 4px;">
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td>${p.name}</td>
                                            <td>
                                                <fmt:formatNumber value="${p.price}" pattern="#,###" /> ₫
                                            </td>
                                            <td>
                                                <c:if test="${p.salePrice > 0}">
                                                    <fmt:formatNumber value="${p.salePrice}" pattern="#,###" /> ₫
                                                </c:if>
                                                <c:if test="${p.salePrice == 0}">
                                                    -
                                                </c:if>
                                            </td>
                                            <td>
                                                <fmt:formatNumber value="${p.quantity}" pattern="#,###.##" />
                                            </td>
                                            <td>
                                                <a href="products?action=edit&id=${p.id}" class="btn-secondary"
                                                    style="padding: 5px 10px; border-radius: 4px; font-size: 12px; margin-right: 5px;"><i
                                                        class="fas fa-edit"></i></a>
                                                <a href="products?action=delete&id=${p.id}&page=${index}" class="btn"
                                                    style="padding: 5px 10px; border-radius: 4px; font-size: 12px; background: #991b1b;"
                                                    onclick="return confirm('Bạn chắc chắn muốn xóa món này?');"><i
                                                        class="fas fa-trash"></i></a>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>

                        <!-- Pagination -->
                        <div class="pagination" style="margin-top: 20px; text-align: center;">
                            <c:if test="${endPage > 1}">
                                <c:choose>
                                    <c:when test="${index > 1}">
                                        <a href="products?page=${index - 1}" class="page-link"
                                            style="padding: 5px 10px; border: 1px solid #ddd; border-radius: 4px; color: #333; text-decoration: none; margin: 0 2px;">&laquo;</a>
                                    </c:when>
                                </c:choose>

                                <c:forEach begin="1" end="${endPage}" var="i">
                                    <a href="products?page=${i}" class="page-link ${i == index ? 'active' : ''}"
                                        style="padding: 5px 10px; border: 1px solid #ddd; border-radius: 4px; color: ${i == index ? '#fff' : '#333'}; background-color: ${i == index ? '#d32f2f' : '#fff'}; text-decoration: none; margin: 0 2px;">${i}</a>
                                </c:forEach>

                                <c:choose>
                                    <c:when test="${index < endPage}">
                                        <a href="products?page=${index + 1}" class="page-link"
                                            style="padding: 5px 10px; border: 1px solid #ddd; border-radius: 4px; color: #333; text-decoration: none; margin: 0 2px;">&raquo;</a>
                                    </c:when>
                                </c:choose>
                            </c:if>
                        </div>
                    </div>
                </div>
                </body>

                </html>