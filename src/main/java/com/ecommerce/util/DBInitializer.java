package com.ecommerce.util;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.IOException;

public class DBInitializer {
    private static final String DB_PATH = "C:\\klu- verion-2\\klu-jsp-sqlite3-main\\src\\main\\resources\\sql\\ecommerce.db";
    private static final String DB_URL = "jdbc:sqlite:" + DB_PATH;
    private static final String SCHEMA_PATH = "C:\\klu- verion-2\\klu-jsp-sqlite3-main\\src\\main\\resources\\sql\\create_tables.sql";

    public static void initialize() {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            if (conn != null) {
                // Check if tables exist, if not, create them
                if (!Files.exists(Paths.get(DB_PATH)) || !hasRequiredTables(conn)) {
                    String sql = new String(Files.readAllBytes(Paths.get(SCHEMA_PATH)));
                    for (String statement : sql.split(";")) {
                        if (!statement.trim().isEmpty()) {
                            try (Statement stmt = conn.createStatement()) {
                                stmt.execute(statement);
                            }
                        }
                    }
                    System.out.println("Database and tables created/verified.");
                }
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean hasRequiredTables(Connection conn) throws SQLException {
        // Check for one or more required tables (e.g., 'orders', 'cart')
        String checkTable = "SELECT name FROM sqlite_master WHERE type='table' AND name='orders'";
        try (Statement stmt = conn.createStatement();
             java.sql.ResultSet rs = stmt.executeQuery(checkTable)) {
            return rs.next();
        }
    }
}
