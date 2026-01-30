package com.vitlaovuong.controller;

import com.vitlaovuong.dal.NotificationDAO;
import com.vitlaovuong.model.Notification;
import com.vitlaovuong.model.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "NotificationServlet", urlPatterns = { "/notifications" })
public class NotificationServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("account");

        if (user == null) {
            response.getWriter().write("{\"errorCode\": 401, \"message\": \"Unauthorized\"}");
            return;
        }

        NotificationDAO dao = new NotificationDAO();
        List<Notification> list;
        int unreadCount = 0;

        if ("ADMIN".equals(user.getRole())) {
            list = dao.getNotificationsForAdmin(user.getId());
            unreadCount = dao.getUnreadCountForAdmin(user.getId());
        } else {
            list = dao.getNotificationsByUser(user.getId());
            unreadCount = dao.getUnreadCount(user.getId());
        }

        // Manual JSON construction to avoid dependency issues if Gson is missing
        StringBuilder json = new StringBuilder();
        json.append("{");
        json.append("\"unreadCount\":").append(unreadCount).append(",");
        json.append("\"notifications\":[");
        for (int i = 0; i < list.size(); i++) {
            Notification n = list.get(i);
            json.append("{");
            json.append("\"id\":").append(n.getId()).append(",");
            json.append("\"title\":\"").append(escape(n.getTitle())).append("\",");
            json.append("\"message\":\"").append(escape(n.getMessage())).append("\",");
            json.append("\"isRead\":").append(n.isRead()).append(",");
            String link = n.getLink();
            json.append("\"link\":\"").append(link != null ? escape(link) : "").append("\",");
            json.append("\"createdAt\":\"").append(n.getCreatedAt()).append("\"");
            json.append("}");
            if (i < list.size() - 1) {
                json.append(",");
            }
        }
        json.append("]");
        json.append("}");

        response.getWriter().write(json.toString());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("markRead".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            new NotificationDAO().markAsRead(id);
            response.setStatus(HttpServletResponse.SC_OK);
        } else if ("markAllRead".equals(action)) {
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("account");
            if (user != null) {
                NotificationDAO dao = new NotificationDAO();
                if ("ADMIN".equals(user.getRole())) {
                    dao.markAllAsReadForAdmin(user.getId());
                } else {
                    dao.markAllAsReadForUser(user.getId());
                }
                response.setStatus(HttpServletResponse.SC_OK);
            } else {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            }
        }
    }

    private String escape(String s) {
        if (s == null)
            return "";
        return s.replace("\"", "\\\"").replace("\n", " ").replace("\r", " ");
    }
}
