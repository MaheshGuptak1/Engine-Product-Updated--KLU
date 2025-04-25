package com.ecommerce.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.io.File;

public class DBUtil {
    private static final String DB_NAME = "ecommerce.db";
    private static final String DB_PATH = "C:\\klu- verion-2\\klu-jsp-sqlite3-main\\src\\main\\resources\\sql\\ecommerce.db";
    private static final String URL = "jdbc:sqlite:" + DB_PATH;
    private static final String USER = "";
    private static final String PASSWORD = "";

    static {
        try {
            Class.forName("org.sqlite.JDBC");
            System.out.println("Database path: " + DB_PATH);
            DBInitializer.initialize();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
} 