package com.vitlaovuong.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBContext {
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=vitlaovuong_db;encrypt=true;trustServerCertificate=true;";
    private static final String USER = "sa";
    private static final String PASS = "123456";

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

        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        return DriverManager.getConnection(dbUrl, dbUser, dbPass);
    }
}
