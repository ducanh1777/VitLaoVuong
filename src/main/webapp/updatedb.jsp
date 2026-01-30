<%@page import="com.vitlaovuong.config.DBContext" %>
    <%@page import="java.sql.Statement" %>
        <%@page import="java.sql.Connection" %>
            <%@page contentType="text/html" pageEncoding="UTF-8" %>
                <!DOCTYPE html>
                <html>

                <head>
                    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
                    <title>Update Database</title>
                </head>

                <body>
                    <h1>Cap nhat CSDL...</h1>
                    <% try (Connection conn=DBContext.getConnection(); Statement stmt=conn.createStatement()) { // Alter
                        Products table to change image_url to TEXT stmt.executeUpdate("ALTER TABLE Products MODIFY
                        COLUMN image_url TEXT"); out.println("<h2 style='color:green'>Thanh cong! Da cap nhat cot
                        image_url thanh TEXT.</h2>");
                        out.println("<a href='admin/products'>Quay lai trang quan tri</a>");

                        } catch (Exception e) {
                        e.printStackTrace();
                        out.println("<h2 style='color:red'>Loi: " + e.getMessage() + "</h2>");
                        }
                        %>
                </body>

                </html>