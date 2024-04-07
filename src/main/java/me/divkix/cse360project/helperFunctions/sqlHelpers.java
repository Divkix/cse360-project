package me.divkix.cse360project.helperFunctions;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class sqlHelpers {

    // Database name
    private static final String databaseName = "healnet.db";
    private static final String connectionString = "jdbc:sqlite:" + databaseName;

    // Method to create a new database
    public static void createNewDatabase() {
        try (Connection conn = DriverManager.getConnection(connectionString)) {
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
        try (Connection conn = DriverManager.getConnection(connectionString);
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
        // get the primary key
        String primaryKey = data.get("username");

        // Check if primary key already exists
        try (Connection conn = DriverManager.getConnection(connectionString);
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
        try (Connection conn = DriverManager.getConnection(connectionString);
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

    public static Map<String, String> getDataUsingUsername(String tableName, String username) {
        Map<String, String> data = new HashMap<>();

        // Try to connect to the database and get the data
        try (Connection conn = DriverManager.getConnection(connectionString);
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

    // similar function like getDataUsingUsername but returns a list of maps
    public static List<Map<String, String>> getMultipleData(String tableName, String columnName, String columnValue) {
        List<Map<String, String>> dataList = new ArrayList<>();

        // Try to connect to the database and get the data
        try (Connection conn = DriverManager.getConnection(connectionString);
             // Create a new prepared statement
             PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM " + tableName + " WHERE " + columnName + " = ?")) {

            // Set the column value
            pstmt.setString(1, columnValue);

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
            while (rs.next()) {
                Map<String, String> data = new HashMap<>();
                for (String cn : columnNames) {
                    data.put(cn, rs.getString(cn));
                }
                dataList.add(data);
            }
        } catch (SQLException e) {
            // If there is an error, print the error message
            System.out.println(e.getMessage());
        }

        // Return the list of maps
        return dataList;
    }

    // create a updateData function that takes in the table name, the primary key, and a map of the new data
    public static void updateData(String tableName, String primaryKey, Map<String, String> newData) {
        // Build the SQL statement dynamically
        StringBuilder sql = new StringBuilder("UPDATE " + tableName + " SET ");

        // Add the columns to the SQL statement
        for (Map.Entry<String, String> entry : newData.entrySet()) {
            sql.append(entry.getKey()).append(" = ?, ");
        }

        // Remove the trailing comma and space
        sql.setLength(sql.length() - 2);

        // Add the WHERE clause
        sql.append(" WHERE username = ?;");

        // Try to connect to the database and update the data
        try (Connection conn = DriverManager.getConnection(connectionString);
             // Create a new prepared statement
             PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {

            // Set the values dynamically
            int i = 1;
            for (String value : newData.values()) {
                pstmt.setString(i++, value);
            }

            // Set the primary key value
            pstmt.setString(i, primaryKey);

            // Update the data
            pstmt.executeUpdate();
            // Print that the data has been updated
            System.out.println("Data updated successfully.");
        } catch (SQLException e) {
            // If there is an error, print the error message
            System.out.println(e.getMessage());
        }
    }
}
