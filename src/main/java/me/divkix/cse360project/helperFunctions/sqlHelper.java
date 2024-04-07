package me.divkix.cse360project.helperFunctions;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class sqlHelper {

    // Database name
    private static final String databaseName = "healnet.db";

    // Method to create a new database
    public static void createNewDatabase() {
        // SQLite connection string
        String url = "jdbc:sqlite:" + databaseName;

        // Try to connect to the database
        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                // If the connection is successful, print a message
                System.out.println("A new database has been created.");
            }

        } catch (SQLException e) {
            // If there is an error, print the error message
            System.out.println(e.getMessage());
        }
    }

    // Method to create a new table with the given columns
    public static void createNewTable(String tableName, Map<String, String> columns) {
        // SQLite connection string
        String url = "jdbc:sqlite:" + databaseName;

        // Build the SQL statement dynamically
        StringBuilder sql = new StringBuilder("CREATE TABLE IF NOT EXISTS " + tableName + " (\n");

        // Add the columns to the SQL statement
        for (Map.Entry<String, String> column : columns.entrySet()) {
            sql.append(column.getKey()).append(" ").append(column.getValue()).append(",\n");
        }

        // Remove the trailing comma and newline
        sql.setLength(sql.length() - 2);

        // Add the closing parenthesis
        sql.append("\n);");

        // Try to connect to the database and create the table
        try (Connection conn = DriverManager.getConnection(url);
             // Create a new statement
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql.toString());
        } catch (SQLException e) {
            // If there is an error, print the error message
            System.out.println(e.getMessage());
        }
    }

    // Method to insert data into a table
    public static void insertData(String tableName, Map<String, String> data) {
        // SQLite connection string
        String url = "jdbc:sqlite:" + databaseName;

        // get the primary key
        String primaryKey = data.get("username");

        // Check if primary key already exists
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM " + tableName + " WHERE username = ?")) {

            // Set the primary key value
            pstmt.setString(1, data.get(primaryKey));

            // Execute the query
            ResultSet rs = pstmt.executeQuery();

            // If a result is returned, the primary key already exists
            if (rs.next()) {
                System.out.println("Primary key already exists. Data not inserted.");
                return;
            }
        } catch (SQLException e) {
            // If there is an error, print the error message
            System.out.println(e.getMessage());
        }

        // Build the SQL statement dynamically
        StringBuilder sql = new StringBuilder("INSERT INTO " + tableName + " (");

        // Add the columns to the SQL statement
        for (String column : data.keySet()) {
            sql.append(column).append(",");
        }

        // Remove the trailing comma
        sql.setLength(sql.length() - 1);
        // Add the values placeholder
        sql.append(") VALUES (");

        // Add the values placeholder
        for (int i = 0; i < data.size(); i++) {
            sql.append("?,");
        }

        // Remove the trailing comma
        sql.setLength(sql.length() - 1);

        // Add the closing parenthesis
        sql.append(")");

        // Try to connect to the database and insert the data
        try (Connection conn = DriverManager.getConnection(url);
             // Create a new prepared statement
             PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {

            // Set the values dynamically
            int i = 1;
            for (String value : data.values()) {
                pstmt.setString(i++, value);
            }

            // Insert the data
            pstmt.executeUpdate();
            // Print that the data has been inserted
            System.out.println("Data inserted successfully.");
        } catch (SQLException e) {
            // If there is an error, print the error message
            System.out.println(e.getMessage());
        }
    }

    public static Map<String, String> getData(String tableName, String username) {
        // SQLite connection string
        String url = "jdbc:sqlite:" + databaseName;
        Map<String, String> data = new HashMap<>();

        // Try to connect to the database and get the data
        try (Connection conn = DriverManager.getConnection(url);
             // Create a new prepared statement
             PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM " + tableName + " WHERE username = ?")) {

            // Set the username value
            pstmt.setString(1, username);

            // Execute the query
            ResultSet rs = pstmt.executeQuery();

            // Get the column names
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();
            List<String> columnNames = new ArrayList<>();
            for (int i = 1; i <= columnCount; i++) {
                columnNames.add(rsmd.getColumnName(i));
            }

            // If a result is returned, add each column to the map
            if (rs.next()) {
                for (String columnName : columnNames) {
                    data.put(columnName, rs.getString(columnName));
                }
            }
        } catch (SQLException e) {
            // If there is an error, print the error message
            System.out.println(e.getMessage());
        }

        // Return the map
        return data;
    }
}
