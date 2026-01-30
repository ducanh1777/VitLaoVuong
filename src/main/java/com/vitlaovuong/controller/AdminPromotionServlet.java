package com.vitlaovuong.controller;

import com.vitlaovuong.dal.NotificationDAO;
import com.vitlaovuong.model.User;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "AdminPromotionServlet", urlPatterns = { "/admin/promotion" })
public class AdminPromotionServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Simple UI for sending promotion
        request.getRequestDispatcher("/admin_promotion.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("account");

        if (user == null || !"ADMIN".equals(user.getRole())) {
            response.sendRedirect("../login");
            return;
        }

        String title = request.getParameter("title");
        String message = request.getParameter("message");

        if (title != null && message != null) {
            NotificationDAO dao = new NotificationDAO();
            dao.addNotification(null, "ALL", title, message);
            request.setAttribute("message", "Đã gửi thông báo khuyến mãi thành công!");
        } else {
            request.setAttribute("error", "Vui lòng nhập đầy đủ tiêu đề và nội dung!");
        }

        request.getRequestDispatcher("/admin_promotion.jsp").forward(request, response);
    }
}
