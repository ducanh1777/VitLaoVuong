package com.vitlaovuong.dal;

import com.vitlaovuong.config.DBContext;
import com.vitlaovuong.model.Notification;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class NotificationDAO {

    public void addNotification(Integer userId, String roleTarget, String title, String message, String link) {
        String sql = "INSERT INTO Notifications (user_id, role_target, title, message, link) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBContext.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            if (userId != null) {
                ps.setInt(1, userId);
            } else {
                ps.setNull(1, java.sql.Types.INTEGER);
            }
            ps.setString(2, roleTarget);
            ps.setString(3, title);
            ps.setString(4, message);
            ps.setString(5, link);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Keep old method for compatibility (calls new one with null link)
    public void addNotification(Integer userId, String roleTarget, String title, String message) {
        addNotification(userId, roleTarget, title, message, null);
    }

    // Get notifications for a specific user (Personal + ALL)
    public List<Notification> getNotificationsByUser(int userId) {
        List<Notification> list = new ArrayList<>();
        String sql = "SELECT * FROM Notifications WHERE (user_id = ? OR role_target = 'ALL') ORDER BY created_at DESC";
        try (Connection conn = DBContext.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new Notification(
                            rs.getInt("id"),
                            rs.getInt("user_id"),
                            rs.getString("role_target"),
                            rs.getString("title"),
                            rs.getString("message"),
                            rs.getBoolean("is_read"),
                            rs.getTimestamp("created_at"),
                            getSafeString(rs, "link")));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // Get notifications for Admin (ADMIN + ALL + Personal)
    public List<Notification> getNotificationsForAdmin(int userId) {
        List<Notification> list = new ArrayList<>();
        String sql = "SELECT * FROM Notifications WHERE (role_target = 'ADMIN' OR role_target = 'ALL' OR user_id = ?) ORDER BY created_at DESC";
        try (Connection conn = DBContext.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new Notification(
                            rs.getInt("id"),
                            rs.getInt("user_id"),
                            rs.getString("role_target"),
                            rs.getString("title"),
                            rs.getString("message"),
                            rs.getBoolean("is_read"),
                            rs.getTimestamp("created_at"),
                            getSafeString(rs, "link")));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    private String getSafeString(ResultSet rs, String column) {
        try {
            return rs.getString(column);
        } catch (Exception e) {
            return null; // Column might not exist or be null
        }
    }

    public void markAsRead(int id) {
        String sql = "UPDATE Notifications SET is_read = 1 WHERE id = ?";
        try (Connection conn = DBContext.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void markAllAsReadForUser(int userId) {
        String sql = "UPDATE Notifications SET is_read = 1 WHERE (user_id = ? OR role_target = 'ALL') AND is_read = 0";
        try (Connection conn = DBContext.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void markAllAsReadForAdmin(int userId) {
        String sql = "UPDATE Notifications SET is_read = 1 WHERE (role_target = 'ADMIN' OR role_target = 'ALL' OR user_id = ?) AND is_read = 0";
        try (Connection conn = DBContext.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getUnreadCount(int userId) {
        String sql = "SELECT COUNT(*) FROM Notifications WHERE (user_id = ? OR role_target = 'ALL') AND is_read = FALSE";
        try (Connection conn = DBContext.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getUnreadCountForAdmin(int userId) {
        String sql = "SELECT COUNT(*) FROM Notifications WHERE (role_target = 'ADMIN' OR role_target = 'ALL' OR user_id = ?) AND is_read = FALSE";
        try (Connection conn = DBContext.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
