package com.vitlaovuong.controller;

import com.vitlaovuong.config.DBContext;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
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

            // Alter Products table to add sale_price
            try {
                String sql = "ALTER TABLE Products ADD COLUMN sale_price DOUBLE DEFAULT 0;";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.execute();
                response.getWriter().println("Added sale_price column to Products table.<br>");
            } catch (Exception e) {
                response.getWriter().println("sale_price column might already exist.<br>");
            }

            // Modify quantity to DOUBLE for Products and OrderDetails
            try {
                String sql1 = "ALTER TABLE Products MODIFY quantity DOUBLE;";
                PreparedStatement ps1 = conn.prepareStatement(sql1);
                ps1.execute();
                response.getWriter().println("Modified Products.quantity to DOUBLE.<br>");

                String sql2 = "ALTER TABLE OrderDetails MODIFY quantity DOUBLE;";
                PreparedStatement ps2 = conn.prepareStatement(sql2);
                ps2.execute();
                response.getWriter().println("Modified OrderDetails.quantity to DOUBLE.<br>");
            } catch (Exception e) {
                response.getWriter().println("Error modifying quantity columns: " + e.getMessage() + "<br>");
            }

            response.getWriter().println("<h1>Cap nhat CSDL thanh cong!</h1>");
            response.getWriter().println(
                    "<p>Updated: Products (sale_price, quantity), Notifications (link), OrderDetails (quantity)</p>");
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
