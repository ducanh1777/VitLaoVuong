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

@WebServlet(name = "MyOrderServlet", urlPatterns = { "/my-orders" })
public class MyOrderServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("account");

        if (user == null) {
            response.sendRedirect("login");
            return;
        }

        OrderDAO dao = new OrderDAO();

        // Pagination logic
        int index = 1;
        String indexPage = request.getParameter("page");
        if (indexPage != null) {
            try {
                index = Integer.parseInt(indexPage);
            } catch (NumberFormatException e) {
                index = 1;
            }
        }

        int count = dao.countByAccountId(user.getId());
        int pageSize = 5; // 5 orders per page
        int endPage = count / pageSize;
        if (count % pageSize != 0) {
            endPage++;
        }

        List<Order> list = dao.getOrdersByAccountId(user.getId(), index, pageSize);

        request.setAttribute("orders", list);
        request.setAttribute("endPage", endPage);
        request.setAttribute("index", index);
        request.getRequestDispatcher("my_orders.jsp").forward(request, response);
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
