package me.divkix.cse360project;

// Importing necessary libraries
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// Class to handle SQL queries
public class sqlHelper {

    // Method to establish a connection to the database
    private static Connection getConnection() throws SQLException, ClassNotFoundException {
        // Registering the MySQL JDBC driver
        Class.forName("com.mysql.cj.jdbc.Driver");
        // Returning a connection to the database
        return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/healnet_health_system?useSSL=false",
                "root",
                "password"
        );
    }

    // Method to save data to the database
    public static void saveData(String tableName, Object... args) {
        // Building the SQL query
        String sql = buildSqlQuery(tableName, args); // INSERT INTO table_name (column1, column2, ...) VALUES (?, ?, ...)
        List<Object> values = buildValuesList(args); // [value1, value2, ...]

        // Establishing a connection to the database
        try (Connection conn = getConnection();
             // Creating a prepared statement
             PreparedStatement statement = conn.prepareStatement(sql)) {

            // Setting the values in the prepared statement
            for (int i = 0; i < values.size(); i++) {
                statement.setObject(i + 1, values.get(i));
            }

            // Executing the query
            int rowsInserted = statement.executeUpdate();
            // Printing the result
            System.out.println(rowsInserted > 0 ? "Data saved successfully!" : "Failed to save data.");
        } catch (SQLException | ClassNotFoundException e) {
            // Printing the exception
            e.printStackTrace();
        }
    }

    // Method to build the SQL query
    private static String buildSqlQuery(String tableName, Object... args) {
        // INSERT INTO table_name (column1, column2, ...) VALUES (?, ?, ...)
        StringBuilder sql = new StringBuilder("INSERT INTO " + tableName + " (");

        // Building the columns list
        for (int i = 0; i < args.length; i += 2) {
            sql.append((String) args[i]).append(", ");
        }

        sql.setLength(sql.length() - 2); // Removing the last comma and space
        sql.append(") VALUES ("); // Appending the VALUES keyword
        sql.append("?, ".repeat(Math.max(0, args.length / 2))); // Appending the placeholders
        sql.setLength(sql.length() - 2); // Removing the last comma and space
        sql.append(")"); // Closing the query

        // Returning the SQL query
        return sql.toString();
    }

    // Method to build the values list
    private static List<Object> buildValuesList(Object... args) {
        // [value1, value2, ...]
        List<Object> values = new ArrayList<>();

        // Building the values list
        for (int i = 1; i < args.length; i += 2) {
            values.add(args[i]);
        }

        // Returning the values list
        return values;
    }

    // Method to get data from the database
    public static ResultSet getData(String tableName, String patientId) {
        // Building the SQL query
        String sql = "SELECT * FROM " + tableName + " WHERE patient_id = ?";

        // Establishing a connection to the database
        try (Connection conn = getConnection();
             // Creating a prepared statement
             PreparedStatement statement = conn.prepareStatement(sql)) {

            // Setting the patient ID in the prepared statement
            statement.setString(1, patientId);
            // Executing the query
            return statement.executeQuery();
        } catch (SQLException | ClassNotFoundException e) {
            // Printing the exception
            e.printStackTrace();
        }

        // Returning null if an exception occurs
        return null;
    }
}
