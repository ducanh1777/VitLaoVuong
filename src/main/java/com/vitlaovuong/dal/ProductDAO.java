package com.vitlaovuong.dal;

import com.vitlaovuong.config.DBContext;
import com.vitlaovuong.model.Product;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    private int getSafeInt(ResultSet rs, String column) {
        try {
            return rs.getInt(column);
        } catch (Exception e) {
            return 0; // Default
        }
    }

    private double getSafeDouble(ResultSet rs, String column) {
        try {
            return rs.getDouble(column);
        } catch (Exception e) {
            return 0.0; // Default
        }
    }

    public List<Product> getAllProducts() {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM Products";
        try (Connection conn = DBContext.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Product p = new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getDouble("price"),
                        rs.getString("image_url"),
                        rs.getInt("category_id"),
                        getSafeDouble(rs, "quantity"),
                        getSafeDouble(rs, "sale_price"));
                list.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Product> getProductsByCategoryId(int cid) {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM Products WHERE category_id = ?";
        try (Connection conn = DBContext.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, cid);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new Product(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("description"),
                            rs.getDouble("price"),
                            rs.getString("image_url"),
                            rs.getInt("category_id"),
                            getSafeDouble(rs, "quantity"),
                            getSafeDouble(rs, "sale_price")));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public int countByCategoryId(int cid) {
        String sql = "SELECT COUNT(*) FROM Products WHERE category_id = ?";
        try (Connection conn = DBContext.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, cid);
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

    public List<Product> getProductsByCategoryId(int cid, int index, int size) {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM Products WHERE category_id = ? LIMIT ?, ?";
        try (Connection conn = DBContext.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, cid);
            ps.setInt(2, (index - 1) * size);
            ps.setInt(3, size);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new Product(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("description"),
                            rs.getDouble("price"),
                            rs.getString("image_url"),
                            rs.getInt("category_id"),
                            getSafeDouble(rs, "quantity"),
                            getSafeDouble(rs, "sale_price")));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Product> getFeaturedProducts() {
        // Tạm thời lấy 3 sản phẩm đầu tiên
        List<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM Products LIMIT 3";
        try (Connection conn = DBContext.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Product p = new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getDouble("price"),
                        rs.getString("image_url"),
                        rs.getInt("category_id"),
                        getSafeDouble(rs, "quantity"),
                        getSafeDouble(rs, "sale_price"));
                list.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // DEM TONG SO SAN PHAM
    public int count() {
        String sql = "SELECT COUNT(*) FROM Products";
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

    // PHAN TRANG SAN PHAM
    public List<Product> getPaging(int index, int size) {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM Products LIMIT ?, ?";
        try (Connection conn = DBContext.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, (index - 1) * size);
            ps.setInt(2, size);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getDouble("price"),
                        rs.getString("image_url"),
                        rs.getInt("category_id"),
                        getSafeDouble(rs, "quantity"),
                        getSafeDouble(rs, "sale_price")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // ADMIN: Them san pham moi
    public void insertProduct(Product p) throws Exception {
        String sql = "INSERT INTO Products (name, description, price, image_url, category_id, quantity, sale_price) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBContext.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.getName());
            ps.setString(2, p.getDescription());
            ps.setDouble(3, p.getPrice());
            ps.setString(4, p.getImageUrl());
            ps.setInt(5, p.getCategoryId());
            ps.setDouble(6, p.getQuantity());
            ps.setDouble(7, p.getSalePrice());
            ps.executeUpdate();
        }
    }

    // ADMIN: Cap nhat san pham
    public void updateProduct(Product p) throws Exception {
        String sql = "UPDATE Products SET name=?, description=?, price=?, image_url=?, category_id=?, quantity=?, sale_price=? WHERE id=?";
        try (Connection conn = DBContext.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.getName());
            ps.setString(2, p.getDescription());
            ps.setDouble(3, p.getPrice());
            ps.setString(4, p.getImageUrl());
            ps.setInt(5, p.getCategoryId());
            ps.setDouble(6, p.getQuantity());
            ps.setDouble(7, p.getSalePrice());
            ps.setInt(8, p.getId());
            ps.executeUpdate();
        }
    }

    // ADMIN: Xoa san pham
    public void deleteProduct(int id) throws Exception {
        String sql = "DELETE FROM Products WHERE id=?";
        try (Connection conn = DBContext.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    // ADMIN: Lay product theo ID
    public Product getProductById(int id) {
        String sql = "SELECT * FROM Products WHERE id = ?";
        try (Connection conn = DBContext.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Product(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("description"),
                            rs.getDouble("price"),
                            rs.getString("image_url"),
                            rs.getInt("category_id"),
                            getSafeInt(rs, "quantity"),
                            getSafeDouble(rs, "sale_price"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
