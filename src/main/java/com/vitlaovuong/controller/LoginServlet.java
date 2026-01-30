package com.vitlaovuong.controller;

import com.vitlaovuong.dal.UserDAO;
import com.vitlaovuong.model.User;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "LoginServlet", urlPatterns = { "/login" })
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String u = request.getParameter("username");
        String p = request.getParameter("password");

        UserDAO dao = new UserDAO();
        User user = dao.checkLogin(u, p);

        if (user != null) {
            HttpSession session = request.getSession();
            session.setAttribute("account", user);

            String redirectUrl = (String) session.getAttribute("redirectUrl");
            if (redirectUrl != null) {
                session.removeAttribute("redirectUrl");
                response.sendRedirect(redirectUrl);
            } else {
                response.sendRedirect("home");
            }
        } else {
            request.setAttribute("error", "Sai ten dang nhap hoac mat khau!");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}
