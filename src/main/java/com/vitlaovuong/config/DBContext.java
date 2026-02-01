package com.vitlaovuong.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBContext {
    private static final String URL = "jdbc:mysql://localhost:3306/vitlaovuong_db?useUnicode=true&characterEncoding=UTF-8";
    private static final String USER = "root";
    private static final String PASS = "123456"; // Thu lai mat khau 123456

    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        String dbUrl = System.getenv("DB_URL");
        String dbUser = System.getenv("DB_USER");
        String dbPass = System.getenv("DB_PASS");

        if (dbUrl == null || dbUrl.isEmpty()) {
            dbUrl = URL;
        }
        if (dbUser == null || dbUser.isEmpty()) {
            dbUser = USER;
        }
        if (dbPass == null || dbPass.isEmpty()) {
            dbPass = PASS;
        }

        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(dbUrl, dbUser, dbPass);
    }
}
