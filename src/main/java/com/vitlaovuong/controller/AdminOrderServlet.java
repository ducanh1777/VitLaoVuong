package com.vitlaovuong.controller;

import com.vitlaovuong.dal.OrderDAO;
import com.vitlaovuong.model.Order;
import com.vitlaovuong.model.User;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "AdminOrderServlet", urlPatterns = { "/admin/orders" })
public class AdminOrderServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Check Admin
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("account");
        if (user == null || !"ADMIN".equals(user.getRole())) {
            response.sendRedirect("../login");
            return;
        }

        String action = request.getParameter("action");
        OrderDAO dao = new OrderDAO();
        try {
            if ("confirm".equals(action)) {
                int id = Integer.parseInt(request.getParameter("id"));
                dao.updateStatus(id, "Confirmed");

                // Notify User
                Order order = dao.getOrderById(id);
                if (order != null) {
                    com.vitlaovuong.dal.NotificationDAO nDao = new com.vitlaovuong.dal.NotificationDAO();
                    nDao.addNotification(order.getAccountId(), "USER", "Đơn Hàng Được Duyệt",
                            "Đơn hàng #" + id + " của bạn đã được xác nhận và đang chuẩn bị.", "my-orders?id=" + id);
                }

                response.sendRedirect("orders");
                return;
            } else if ("cancel".equals(action)) {
                int id = Integer.parseInt(request.getParameter("id"));
                String note = request.getParameter("note");
                dao.updateStatus(id, "Cancelled", note);

                // Notify User
                Order order = dao.getOrderById(id);
                if (order != null) {
                    com.vitlaovuong.dal.NotificationDAO nDao = new com.vitlaovuong.dal.NotificationDAO();
                    nDao.addNotification(order.getAccountId(), "USER", "Đơn Hàng Bị Hủy",
                            "Đơn hàng #" + id + " đã bị hủy. Lý do: " + note, "my-orders?id=" + id);
                }

                response.sendRedirect("orders");
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Phan trang
        int index = 1;
        String indexPage = request.getParameter("page");
        if (indexPage != null) {
            try {
                index = Integer.parseInt(indexPage);
            } catch (NumberFormatException e) {
                index = 1;
            }
        }

        int count = dao.count();
        int pageSize = 10; // 10 don hang moi trang
        int endPage = count / pageSize;
        if (count % pageSize != 0) {
            endPage++;
        }

        List<Order> list = dao.getPaging(index, pageSize);

        request.setAttribute("orders", list);
        request.setAttribute("endPage", endPage);
        request.setAttribute("index", index);

        request.getRequestDispatcher("/admin_orders.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}
