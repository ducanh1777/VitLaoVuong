package com.vitlaovuong.dal;

import com.vitlaovuong.config.DBContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class ReportDAO {

    public static class ReportItem {
        private String timeLabel;
        private int orderCount;
        private double revenue;

        public ReportItem(String timeLabel, int orderCount, double revenue) {
            this.timeLabel = timeLabel;
            this.orderCount = orderCount;
            this.revenue = revenue;
        }

        public String getTimeLabel() {
            return timeLabel;
        }

        public int getOrderCount() {
            return orderCount;
        }

        public double getRevenue() {
            return revenue;
        }
    }

    // Daily Report (Last 30 days)
    public List<ReportItem> getDailyReport() {
        List<ReportItem> list = new ArrayList<>();
        String sql = "SELECT DATE(created_at) as report_date, COUNT(*) as total_orders, SUM(total_amount) as total_revenue "
                +
                "FROM Orders WHERE status = 'Confirmed' " +
                "GROUP BY DATE(created_at) " +
                "ORDER BY report_date DESC LIMIT 30";
        try (Connection conn = DBContext.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new ReportItem(
                        rs.getDate("report_date").toString(),
                        rs.getInt("total_orders"),
                        rs.getDouble("total_revenue")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // Monthly Report (Last 12 months)
    public List<ReportItem> getMonthlyReport() {
        List<ReportItem> list = new ArrayList<>();
        String sql = "SELECT CONCAT(MONTH(created_at), '/', YEAR(created_at)) as report_month, YEAR(created_at) as y, MONTH(created_at) as m, "
                +
                "COUNT(*) as total_orders, SUM(total_amount) as total_revenue " +
                "FROM Orders WHERE status = 'Confirmed' " +
                "GROUP BY YEAR(created_at), MONTH(created_at) " +
                "ORDER BY y DESC, m DESC LIMIT 12";
        try (Connection conn = DBContext.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new ReportItem(
                        rs.getString("report_month"),
                        rs.getInt("total_orders"),
                        rs.getDouble("total_revenue")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // Quarterly Report
    public List<ReportItem> getQuarterlyReport() {
        List<ReportItem> list = new ArrayList<>();
        String sql = "SELECT CONCAT('Q', QUARTER(created_at), '/', YEAR(created_at)) as report_quarter, YEAR(created_at) as y, QUARTER(created_at) as q, "
                +
                "COUNT(*) as total_orders, SUM(total_amount) as total_revenue " +
                "FROM Orders WHERE status = 'Confirmed' " +
                "GROUP BY YEAR(created_at), QUARTER(created_at) " +
                "ORDER BY y DESC, q DESC LIMIT 8";
        try (Connection conn = DBContext.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new ReportItem(
                        rs.getString("report_quarter"),
                        rs.getInt("total_orders"),
                        rs.getDouble("total_revenue")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // Yearly Report
    public List<ReportItem> getYearlyReport() {
        List<ReportItem> list = new ArrayList<>();
        String sql = "SELECT YEAR(created_at) as report_year, " +
                "COUNT(*) as total_orders, SUM(total_amount) as total_revenue " +
                "FROM Orders WHERE status = 'Confirmed' " +
                "GROUP BY YEAR(created_at) " +
                "ORDER BY report_year DESC LIMIT 5";
        try (Connection conn = DBContext.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new ReportItem(
                        String.valueOf(rs.getInt("report_year")),
                        rs.getInt("total_orders"),
                        rs.getDouble("total_revenue")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
