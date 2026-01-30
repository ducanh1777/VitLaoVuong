<%@page contentType="text/html" pageEncoding="UTF-8" %>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

            <%@include file="header.jsp" %>

                <style>
                    /* Tabs Styling */
                    .tab {
                        overflow: hidden;
                        border: 1px solid #ccc;
                        background-color: #f1f1f1;
                        border-radius: 8px 8px 0 0;
                    }

                    .tab button {
                        background-color: inherit;
                        float: left;
                        border: none;
                        outline: none;
                        cursor: pointer;
                        padding: 14px 16px;
                        transition: 0.3s;
                        font-size: 16px;
                        font-weight: bold;
                        color: #555;
                    }

                    .tab button:hover {
                        background-color: #ddd;
                    }

                    .tab button.active {
                        background-color: #fff;
                        color: #d32f2f;
                        border-bottom: 2px solid #d32f2f;
                    }

                    .tabcontent {
                        display: none;
                        padding: 20px;
                        border: 1px solid #ccc;
                        border-top: none;
                        border-radius: 0 0 8px 8px;
                        background-color: #fff;
                        animation: fadeEffect 0.5s;
                    }

                    @keyframes fadeEffect {
                        from {
                            opacity: 0;
                        }

                        to {
                            opacity: 1;
                        }
                    }

                    .stats-card {
                        background: #fff;
                        border-radius: 8px;
                        padding: 20px;
                        box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
                        text-align: center;
                        margin-bottom: 20px;
                    }

                    .stats-number {
                        font-size: 24px;
                        font-weight: bold;
                        color: #d32f2f;
                        margin: 10px 0;
                    }
                </style>

                <section class="section">
                    <div class="container">
                        <div class="section-title">
                            <h2>Báo Cáo Doanh Thu</h2>
                            <div class="line"></div>
                        </div>

                        <div class="tab">
                            <button class="tablinks active" onclick="openReport(event, 'Daily')">Theo Ngày</button>
                            <button class="tablinks" onclick="openReport(event, 'Monthly')">Theo Tháng</button>
                            <button class="tablinks" onclick="openReport(event, 'Quarterly')">Theo Quý</button>
                            <button class="tablinks" onclick="openReport(event, 'Yearly')">Theo Năm</button>
                        </div>

                        <!-- Daily Report -->
                        <div id="Daily" class="tabcontent" style="display: block;">
                            <h3>Doanh Thu 30 Ngày Gần Nhất</h3>

                            <div style="display: flex; gap: 20px; margin-top: 20px;">
                                <!-- Calendar Section -->
                                <div
                                    style="flex: 2; background: #fff; padding: 20px; border-radius: 8px; box-shadow: 0 0 10px rgba(0,0,0,0.1);">
                                    <div id='calendar'></div>
                                </div>

                                <!-- Table Section -->
                                <div style="flex: 1;">
                                    <table class="cart-table">
                                        <thead>
                                            <tr>
                                                <th>Ngày</th>
                                                <th>Đơn</th>
                                                <th>Doanh Thu</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach items="${daily}" var="r">
                                                <tr>
                                                    <td>
                                                        <fmt:formatDate value="${java.sql.Date.valueOf(r.timeLabel)}"
                                                            pattern="dd/MM" />
                                                    </td>
                                                    <td class="text-center">${r.orderCount}</td>
                                                    <td style="color: #166534; font-weight: bold;">
                                                        <fmt:formatNumber value="${r.revenue}" pattern="#,###" /> ₫
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                            <c:if test="${empty daily}">
                                                <tr>
                                                    <td colspan="3" class="text-center">Chưa có dữ liệu.</td>
                                                </tr>
                                            </c:if>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>

                        <!-- Monthly Report -->
                        <div id="Monthly" class="tabcontent">
                            <h3>Doanh Thu Theo Tháng</h3>
                            <table class="cart-table" style="margin-top: 20px;">
                                <thead>
                                    <tr>
                                        <th>Tháng/Năm</th>
                                        <th>Tổng Đơn Hàng</th>
                                        <th>Doanh Thu</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${monthly}" var="r">
                                        <tr>
                                            <td>${r.timeLabel}</td>
                                            <td class="text-center">${r.orderCount}</td>
                                            <td style="color: #166534; font-weight: bold;">
                                                <fmt:formatNumber value="${r.revenue}" pattern="#,###" /> ₫
                                            </td>
                                        </tr>
                                    </c:forEach>
                                    <c:if test="${empty monthly}">
                                        <tr>
                                            <td colspan="3" class="text-center">Chưa có dữ liệu.</td>
                                        </tr>
                                    </c:if>
                                </tbody>
                            </table>
                        </div>

                        <!-- Quarterly Report -->
                        <div id="Quarterly" class="tabcontent">
                            <h3>Doanh Thu Theo Quý</h3>
                            <table class="cart-table" style="margin-top: 20px;">
                                <thead>
                                    <tr>
                                        <th>Quý/Năm</th>
                                        <th>Tổng Đơn Hàng</th>
                                        <th>Doanh Thu</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${quarterly}" var="r">
                                        <tr>
                                            <td>${r.timeLabel}</td>
                                            <td class="text-center">${r.orderCount}</td>
                                            <td style="color: #166534; font-weight: bold;">
                                                <fmt:formatNumber value="${r.revenue}" pattern="#,###" /> ₫
                                            </td>
                                        </tr>
                                    </c:forEach>
                                    <c:if test="${empty quarterly}">
                                        <tr>
                                            <td colspan="3" class="text-center">Chưa có dữ liệu.</td>
                                        </tr>
                                    </c:if>
                                </tbody>
                            </table>
                        </div>

                        <!-- Yearly Report -->
                        <div id="Yearly" class="tabcontent">
                            <h3>Doanh Thu Theo Năm</h3>
                            <table class="cart-table" style="margin-top: 20px;">
                                <thead>
                                    <tr>
                                        <th>Năm</th>
                                        <th>Tổng Đơn Hàng</th>
                                        <th>Doanh Thu</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${yearly}" var="r">
                                        <tr>
                                            <td>${r.timeLabel}</td>
                                            <td class="text-center">${r.orderCount}</td>
                                            <td style="color: #166534; font-weight: bold;">
                                                <fmt:formatNumber value="${r.revenue}" pattern="#,###" /> ₫
                                            </td>
                                        </tr>
                                    </c:forEach>
                                    <c:if test="${empty yearly}">
                                        <tr>
                                            <td colspan="3" class="text-center">Chưa có dữ liệu.</td>
                                        </tr>
                                    </c:if>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </section>

                <!-- FullCalendar CDN -->
                <script src='https://cdn.jsdelivr.net/npm/fullcalendar@6.1.10/index.global.min.js'></script>
                <script src='https://cdn.jsdelivr.net/npm/fullcalendar@6.1.10/locales-all.global.min.js'></script>
                <script>
                    document.addEventListener('DOMContentLoaded', function () {
                        var calendarEl = document.getElementById('calendar');
                        var calendar = new FullCalendar.Calendar(calendarEl, {
                            initialView: 'dayGridMonth',
                            locale: 'vi',
                            buttonText: {
                                today: 'Hôm nay',
                                month: 'Tháng',
                                week: 'Tuần',
                                day: 'Ngày',
                                list: 'Danh sách'
                            },
                            height: 500,
                            headerToolbar: {
                                left: 'prev,next today',
                                center: 'title',
                                right: 'dayGridMonth,listWeek'
                            },
                            events: [
                                <c:forEach items="${daily}" var="r">
                                    {
                                        title: '${r.orderCount} Đơn\n' + new Intl.NumberFormat('vi-VN').format(${r.revenue}) + 'đ',
                                    start: '${r.timeLabel}',
                                    backgroundColor: '#d32f2f',
                                    borderColor: '#d32f2f',
                                    display: 'block'
                },
                                </c:forEach>
                            ],
                            eventContent: function (arg) {
                                return { html: '<div style="text-align:center; font-size:12px;">' + arg.event.title.replace(/\n/g, '<br>') + '</div>' };
                            }
                        });
                        calendar.render();

                        // Fix render when tab switches
                        window.openReport = function (evt, reportName) {
                            var i, tabcontent, tablinks;
                            tabcontent = document.getElementsByClassName("tabcontent");
                            for (i = 0; i < tabcontent.length; i++) {
                                tabcontent[i].style.display = "none";
                            }
                            tablinks = document.getElementsByClassName("tablinks");
                            for (i = 0; i < tablinks.length; i++) {
                                tablinks[i].className = tablinks[i].className.replace(" active", "");
                            }
                            document.getElementById(reportName).style.display = "block";
                            evt.currentTarget.className += " active";

                            if (reportName === 'Daily') {
                                setTimeout(() => calendar.render(), 100);
                            }
                        };
                    });
                </script>

                <%@include file="footer.jsp" %>