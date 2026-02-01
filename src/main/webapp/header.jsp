<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
        <%@page contentType="text/html" pageEncoding="UTF-8" %>
            <!DOCTYPE html>
            <html lang="vi">

            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Vịt Lão Vương - Vịt Quay Chuẩn Vị Quảng Đông</title>
                <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css?v=3">
                <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
            </head>

            <body>
                <header>
                    <div class="container">
                        <a href="${pageContext.request.contextPath}/home" class="logo">
                            <img src="${pageContext.request.contextPath}/assets/img/logo.png" alt="Vịt Lão Vương Logo">
                        </a>
                        <nav>
                            <div class="nav-menu">
                                <!-- Left Side: Main Navigation -->
                                <ul class="nav-links">
                                    <li><a href="${pageContext.request.contextPath}/home"
                                            class="${pageContext.request.requestURI.endsWith('/home') || pageContext.request.requestURI.endsWith('/index.jsp') ? 'active-link' : ''}"
                                            style="${pageContext.request.requestURI.endsWith('/home') || pageContext.request.requestURI.endsWith('/index.jsp') ? 'color: #d32f2f; font-weight: bold;' : ''}">Trang
                                            Chủ</a></li>

                                    <li><a href="${pageContext.request.contextPath}/menu"
                                            class="${pageContext.request.requestURI.contains('/menu') ? 'active-link' : ''}"
                                            style="${pageContext.request.requestURI.contains('/menu') ? 'color: #d32f2f; font-weight: bold;' : ''}">Thực
                                            Đơn</a></li>
                                </ul>

                                <!-- Right Side: User Actions -->
                                <ul class="nav-links">
                                    <c:choose>
                                        <c:when test="${sessionScope.account != null}">
                                            <c:if test="${sessionScope.account.role == 'ADMIN'}">
                                                <li><span>Xin chào, Admin</span></li>
                                                <li class="dropdown">
                                                    <a href="javascript:void(0)" class="dropbtn"
                                                        style="${pageContext.request.requestURI.contains('/admin/') ? 'color: #d32f2f; font-weight: bold;' : ''}">Quản
                                                        Trị <i class="fas fa-caret-down"></i></a>
                                                    <div class="dropdown-content">
                                                        <a href="${pageContext.request.contextPath}/admin/orders">Đơn
                                                            Hàng</a>
                                                        <a href="${pageContext.request.contextPath}/admin/products">Sản
                                                            Phẩm</a>
                                                        <a href="${pageContext.request.contextPath}/admin/promotion">Gửi
                                                            Khuyến Mãi</a>
                                                        <a href="${pageContext.request.contextPath}/admin/report"
                                                            style="${pageContext.request.requestURI.contains('/admin/report') ? 'color: #d32f2f; font-weight: bold;' : ''}">Báo
                                                            Cáo</a>
                                                    </div>
                                                </li>
                                            </c:if>
                                            <c:if test="${sessionScope.account.role != 'ADMIN'}">
                                                <li><span>Xin chào, ${sessionScope.account.fullName}</span></li>
                                            </c:if>

                                            <!-- Notification -->
                                            <li class="dropdown" id="notification-li">
                                                <a href="javascript:void(0)" class="dropbtn"
                                                    onclick="toggleNotifications()">
                                                    <i class="fas fa-bell"></i>
                                                    <span class="cart-count" id="notif-count"
                                                        style="display: none;">0</span>
                                                </a>
                                                <div class="dropdown-content" id="notif-content"
                                                    style="width: 300px; right: 0; left: auto; max-height: 400px; overflow-y: auto;">
                                                    <a href="#">Đang tải...</a>
                                                </div>
                                            </li>

                                            <c:if test="${sessionScope.account.role != 'ADMIN'}">
                                                <li><a href="${pageContext.request.contextPath}/my-orders"
                                                        class="${pageContext.request.requestURI.contains('/my-orders') ? 'active-link' : ''}"
                                                        style="${pageContext.request.requestURI.contains('/my-orders') ? 'color: #d32f2f; font-weight: bold;' : ''}">Đơn
                                                        Hàng Của Tôi</a></li>
                                            </c:if>
                                        </c:when>
                                        <c:otherwise>
                                            <li><a href="${pageContext.request.contextPath}/login"
                                                    class="${pageContext.request.requestURI.contains('/login') ? 'active-link' : ''}"
                                                    style="${pageContext.request.requestURI.contains('/login') ? 'color: #d32f2f; font-weight: bold;' : ''}">Đăng
                                                    Nhập</a></li>
                                            <li><a href="${pageContext.request.contextPath}/register"
                                                    class="${pageContext.request.requestURI.contains('/register') ? 'active-link' : ''}"
                                                    style="${pageContext.request.requestURI.contains('/register') ? 'color: #d32f2f; font-weight: bold;' : ''}">Đăng
                                                    Ký</a></li>
                                        </c:otherwise>
                                    </c:choose>

                                    <!-- Cart -->
                                    <li><a href="${pageContext.request.contextPath}/cart" class="cart-icon"
                                            style="${pageContext.request.requestURI.contains('/cart') ? 'color: #d32f2f; font-weight: bold;' : ''}">Giỏ
                                            Hàng
                                            <span class="cart-count" id="cart-qty">${sessionScope.cart != null ?
                                                sessionScope.cart.items.size() : 0}</span>
                                        </a></li>

                                    <!-- Logout -->
                                    <c:if test="${sessionScope.account != null}">
                                        <li><a href="${pageContext.request.contextPath}/logout">Đăng Xuất</a></li>
                                    </c:if>
                                </ul>
                            </div>
                        </nav>
                    </div>
                </header>
                <script>
                    function toggleNotifications() {
                        var content = document.getElementById("notif-content");
                        if (content.style.display === "block") {
                            content.style.display = "none";
                        } else {
                            content.style.display = "block";
                            // Immediately hide badge
                            updateNotifCount(0);
                            // Mark all as read in backend
                            fetch('${pageContext.request.contextPath}/notifications?action=markAllRead', { method: 'POST' });
                            // Load notifications without updating badge (assume 0)
                            loadNotifications(false);
                        }
                    }

                    function loadNotifications(updateBadge = true) {
                        fetch('${pageContext.request.contextPath}/notifications?t=' + new Date().getTime())
                            .then(response => response.json())
                            .then(data => {
                                var list = document.getElementById("notif-content");
                                list.innerHTML = "";
                                if (data.notifications && data.notifications.length > 0) {
                                    // Remove "Mark All Read" link since it's now automatic

                                    data.notifications.forEach(n => {
                                        var item = document.createElement("a");
                                        item.href = "javascript:void(0)";
                                        item.style.backgroundColor = n.isRead ? "#fff" : "#f0f9ff";
                                        item.style.borderBottom = "1px solid #eee";
                                        item.style.color = "#333";

                                        // Handle click: mark read then redirect
                                        item.onclick = function () {
                                            markAsRead(n.id, n.link);
                                        };

                                        item.innerHTML = "<strong style='color: #d32f2f;'>" + n.title + "</strong><br><span style='font-size: 12px; color: #333;'>" + n.message.replace("null", "Khách lẻ") + "</span><br><span style='font-size: 10px; color: #999;'>" + n.createdAt + "</span>";
                                        list.appendChild(item);
                                    });
                                } else {
                                    list.innerHTML = "<a href='#'>Không có thông báo nào.</a>";
                                }
                                if (updateBadge) {
                                    updateNotifCount(data.unreadCount);
                                }
                            })
                            .catch(e => console.error(e));
                    }

                    function updateNotifCount(count) {
                        var badge = document.getElementById("notif-count");
                        if (count > 0) {
                            badge.innerText = count;
                            badge.style.display = "inline-block";
                        } else {
                            badge.style.display = "none";
                        }
                    }

                    function markAsRead(id, link) {
                        fetch('${pageContext.request.contextPath}/notifications?action=markRead&id=' + id, { method: 'POST' })
                            .then(() => {
                                loadNotifications(); // Reload to update UI
                                if (link && link !== 'null' && link !== '') {
                                    window.location.href = '${pageContext.request.contextPath}/' + link;
                                }
                            });
                    }



                    // Poll every 10 seconds
                    setInterval(function () {
                        fetch('${pageContext.request.contextPath}/notifications?t=' + new Date().getTime())
                            .then(response => response.json())
                            .then(data => updateNotifCount(data.unreadCount));
                    }, 10000);

                    // Initial check
                    document.addEventListener("DOMContentLoaded", function () {
                        fetch('${pageContext.request.contextPath}/notifications?t=' + new Date().getTime())
                            .then(response => response.json())
                            .then(data => updateNotifCount(data.unreadCount));
                    });
                </script>