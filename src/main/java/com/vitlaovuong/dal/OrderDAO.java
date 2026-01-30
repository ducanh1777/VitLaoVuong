package com.vitlaovuong.dal;

import com.vitlaovuong.config.DBContext;
import com.vitlaovuong.model.Order;
import com.vitlaovuong.model.OrderDetail;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {

    private String getSafeString(ResultSet rs, String column) {
        try {
            return rs.getString(column);
        } catch (Exception e) {
            return ""; // Default if column missing
        }
    }

    public int createOrder(Order order) {
        int orderId = 0;
        String sqlOrder = "INSERT INTO Orders (customer_name, customer_phone, customer_address, total_amount, account_id, note) VALUES (?, ?, ?, ?, ?, ?)";
        String sqlDetail = "INSERT INTO OrderDetails (order_id, product_id, quantity, price) VALUES (?, ?, ?, ?)";

        Connection conn = null;
        try {
            conn = DBContext.getConnection();
            conn.setAutoCommit(false); // Transaction

            // Insert Order
            try (PreparedStatement psOrder = conn.prepareStatement(sqlOrder, Statement.RETURN_GENERATED_KEYS)) {
                psOrder.setString(1, order.getCustomerName());
                psOrder.setString(2, order.getCustomerPhone());
                psOrder.setString(3, order.getCustomerAddress());
                psOrder.setDouble(4, order.getTotalAmount());
                if (order.getAccountId() > 0) {
                    psOrder.setInt(5, order.getAccountId());
                } else {
                    psOrder.setNull(5, java.sql.Types.INTEGER);
                }
                psOrder.setString(6, order.getNote());
                psOrder.executeUpdate();

                ResultSet rs = psOrder.getGeneratedKeys();
                if (rs.next()) {
                    orderId = rs.getInt(1);
                }
            }

            // Insert Details
            if (orderId > 0 && order.getDetails() != null) {
                try (PreparedStatement psDetail = conn.prepareStatement(sqlDetail)) {
                    for (OrderDetail detail : order.getDetails()) {
                        psDetail.setInt(1, orderId);
                        psDetail.setInt(2, detail.getProductId());
                        psDetail.setInt(3, detail.getQuantity());
                        psDetail.setDouble(4, detail.getPrice());
                        psDetail.addBatch();
                    }
                    psDetail.executeBatch();
                }
            }

            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                if (conn != null)
                    conn.rollback();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return 0;
        } finally {
            try {
                if (conn != null)
                    conn.setAutoCommit(true);
                if (conn != null)
                    conn.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return orderId;
    }

    public List<Order> getOrdersByAccountId(int accountId) {
        List<Order> list = new ArrayList<>();
        String sql = "SELECT * FROM Orders WHERE account_id = ? ORDER BY created_at DESC";
        try (Connection conn = DBContext.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, accountId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Order o = new Order();
                o.setId(rs.getInt("id"));
                o.setCustomerName(rs.getString("customer_name"));
                o.setCustomerPhone(rs.getString("customer_phone"));
                o.setCustomerAddress(rs.getString("customer_address"));
                o.setTotalAmount(rs.getDouble("total_amount"));
                o.setStatus(rs.getString("status"));
                o.setNote(getSafeString(rs, "note"));
                o.setCreatedAt(rs.getTimestamp("created_at"));
                list.add(o);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public int countByAccountId(int accountId) {
        String sql = "SELECT COUNT(*) FROM Orders WHERE account_id = ?";
        try (Connection conn = DBContext.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, accountId);
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

    public List<Order> getOrdersByAccountId(int accountId, int index, int size) {
        List<Order> list = new ArrayList<>();
        String sql = "SELECT * FROM Orders WHERE account_id = ? ORDER BY created_at DESC LIMIT ?, ?";
        try (Connection conn = DBContext.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, accountId);
            ps.setInt(2, (index - 1) * size);
            ps.setInt(3, size);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Order o = new Order();
                o.setId(rs.getInt("id"));
                o.setCustomerName(rs.getString("customer_name"));
                o.setCustomerPhone(rs.getString("customer_phone"));
                o.setCustomerAddress(rs.getString("customer_address"));
                o.setTotalAmount(rs.getDouble("total_amount"));
                o.setStatus(rs.getString("status"));
                o.setNote(getSafeString(rs, "note"));
                o.setCreatedAt(rs.getTimestamp("created_at"));
                list.add(o);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // ADMIN: Lay tat ca don hang
    public List<Order> getAllOrders() {
        List<Order> list = new ArrayList<>();
        String sql = "SELECT * FROM Orders ORDER BY created_at DESC";
        try (Connection conn = DBContext.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Order o = new Order();
                o.setId(rs.getInt("id"));
                o.setCustomerName(rs.getString("customer_name"));
                o.setCustomerPhone(rs.getString("customer_phone"));
                o.setCustomerAddress(rs.getString("customer_address"));
                o.setTotalAmount(rs.getDouble("total_amount"));
                o.setStatus(rs.getString("status"));
                o.setNote(getSafeString(rs, "note"));
                o.setCreatedAt(rs.getTimestamp("created_at"));
                list.add(o);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void updateStatus(int orderId, String status) {
        updateStatus(orderId, status, null);
    }

    public void updateStatus(int orderId, String status, String note) {
        String sql = "UPDATE Orders SET status = ?, note = ? WHERE id = ?";
        try (Connection conn = DBContext.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setString(2, note);
            ps.setInt(3, orderId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // DEM TONG SO DON HANG
    public int count() {
        String sql = "SELECT COUNT(*) FROM Orders";
        try (Connection conn = DBContext.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    // PHAN TRANG DON HANG
    public List<Order> getPaging(int index, int size) {
        List<Order> list = new ArrayList<>();
        String sql = "SELECT * FROM Orders ORDER BY created_at DESC LIMIT ?, ?";
        try (Connection conn = DBContext.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, (index - 1) * size);
            ps.setInt(2, size);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Order o = new Order();
                o.setId(rs.getInt("id"));
                o.setCustomerName(rs.getString("customer_name"));
                o.setCustomerPhone(rs.getString("customer_phone"));
                o.setCustomerAddress(rs.getString("customer_address"));
                o.setTotalAmount(rs.getDouble("total_amount"));
                o.setStatus(rs.getString("status"));
                o.setNote(getSafeString(rs, "note"));
                o.setCreatedAt(rs.getTimestamp("created_at"));
                list.add(o);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public Order getOrderById(int id) {
        String sql = "SELECT * FROM Orders WHERE id = ?";
        try (Connection conn = DBContext.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Order order = new Order();
                    order.setId(rs.getInt("id"));
                    order.setCustomerName(rs.getString("customer_name"));
                    order.setCustomerPhone(rs.getString("customer_phone"));
                    order.setCustomerAddress(rs.getString("customer_address"));
                    order.setTotalAmount(rs.getDouble("total_amount"));
                    order.setStatus(rs.getString("status"));
                    order.setNote(rs.getString("note"));
                    order.setCreatedAt(rs.getTimestamp("created_at"));
                    order.setAccountId(rs.getInt("account_id"));
                    return order;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
