package com.vitlaovuong.controller;

import com.vitlaovuong.dal.OrderDAO;
import com.vitlaovuong.model.Cart;
import com.vitlaovuong.model.Order;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.vitlaovuong.model.User;

@WebServlet(name = "CheckoutServlet", urlPatterns = { "/checkout" })
public class CheckoutServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8"); // Handle Vietnamese inputs

        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");

        if (cart == null || cart.getItems().isEmpty()) {
            response.sendRedirect("cart");
            return;
        }

        // Kiem tra dang nhap
        User user = (User) session.getAttribute("account");
        if (user == null) {
            session.setAttribute("redirectUrl", "cart");
            response.sendRedirect("login");
            return;
        }

        String name = request.getParameter("name");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        String note = request.getParameter("note");

        Order order = new Order(name, phone, address, cart.getTotalMoney());
        order.setDetails(cart.getItems());

        // Set ID tai khoan
        order.setAccountId(user.getId());
        order.setNote(note);

        OrderDAO oDao = new OrderDAO();
        int orderId = oDao.createOrder(order);

        if (orderId > 0) {
            // Notify Admin
            com.vitlaovuong.dal.NotificationDAO nDao = new com.vitlaovuong.dal.NotificationDAO();
            nDao.addNotification(null, "ADMIN", "Đơn Hàng Mới",
                    "Khách hàng " + user.getFullName() + " vừa đặt đơn hàng #" + orderId, "admin/orders?id=" + orderId);

            session.removeAttribute("cart");
            request.setAttribute("orderId", orderId);
            request.getRequestDispatcher("order_success.jsp").forward(request, response);
        } else {
            request.setAttribute("error", "Có lỗi xảy ra khi đặt hàng!");
            request.getRequestDispatcher("cart.jsp").forward(request, response);
        }
    }
}
