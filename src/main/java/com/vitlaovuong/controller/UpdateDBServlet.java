package com.vitlaovuong.controller;

import com.vitlaovuong.config.DBContext;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "UpdateDBServlet", urlPatterns = { "/updatedb" })
public class UpdateDBServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (Connection conn = DBContext.getConnection();
                Statement stmt = conn.createStatement()) {

            // Alter Products table to change image_url to TEXT
            try {
                stmt.executeUpdate("ALTER TABLE Products MODIFY COLUMN image_url TEXT");
            } catch (Exception e) {
                // Ignore if already text
            }

            // Alter Notifications table to add link
            try {
                stmt.executeUpdate("ALTER TABLE Notifications ADD COLUMN link VARCHAR(255) DEFAULT NULL");
            } catch (Exception e) {
                // Ignore if exists
            }

            response.getWriter().println("<h1>Cap nhat CSDL thanh cong! (Added link to Notifications)</h1>");
            response.getWriter().println("<a href='home'>Ve trang chu</a>");

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("<h1>Loi: " + e.getMessage() + "</h1>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}
