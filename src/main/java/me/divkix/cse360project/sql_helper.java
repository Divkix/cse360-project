package me.divkix.cse360project;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class sql_helper {
    // Database Connection class
    // Helper class to connect to the local mysql database
    // The mysql database is called healnet_health_system and is hosted on localhost (using docker)
    public static class DatabaseConnection {
        private static final String db_name = "healnet_health_system";
        private static final String DB_URL = "jdbc:mysql://localhost:3306/" + db_name;
        private static final String DB_USER = "root";
        private static final String DB_PASSWORD = "password";

        public static Connection getConnection() {
            Connection conn = null;
            try {
                // Register the JDBC driver
                Class.forName("com.mysql.cj.jdbc.Driver");

                // Open a connection
                conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
            return conn;
        }
    }

    // Helper function to save data to the database using prepared statements
    // The function takes in a variable number of arguments
    // The arguments are expected to be in pairs of column name and value
    // example: saveData("first_name", "John", "last_name", "Doe", "email", "john.doe@example.com")
    public static void saveData(String tableName, Object... args) {
        // Get the database connection
        Connection conn = Healnet.DatabaseConnection.getConnection();

        // Build the SQL query using StringBuilder
        StringBuilder sql = new StringBuilder("INSERT INTO " + tableName + " (");
        List<Object> values = new ArrayList<>(); // Create a list to store the values

        // create a loop to iterate over the arguments and build the SQL query and values list by pairs
        // add them to the prepared statement
        for (int i = 0; i < args.length; i += 2) {
            sql.append((String) args[i]).append(", ");
            values.add(args[i + 1]);
        }

        sql.setLength(sql.length() - 2); // Remove the trailing ", "
        sql.append(") VALUES ("); // Add the VALUES, close and open parenthesis

        // Add the required number of "?" placeholders
        sql.append("?, ".repeat(Math.max(0, values.size())));

        // Remove the trailing ", "
        sql.setLength(sql.length() - 2);
        sql.append(")"); // add the closing parenthesis

        // use try catch block to handle the prepared statement
        try {
            PreparedStatement statement = conn.prepareStatement(sql.toString());

            // loop over the values list and set the values in the prepared statement
            for (int i = 0; i < values.size(); i++) {
                statement.setObject(i + 1, values.get(i));
            }
            int rowsInserted = statement.executeUpdate(); // execute the prepared statement
            if (rowsInserted > 0) {
                // print success message if data is saved
                System.out.println("Data saved successfully!");
            } else {
                // print error message if data is not saved
                System.out.println("Failed to save data.");
            }
        } catch (SQLException e) {
            e.printStackTrace(); // print the stack trace
        } finally {
            try {
                conn.close(); // close the connection
            } catch (SQLException e) {
                e.printStackTrace(); // print the stack trace
            }
        }
    }

    // class to get data from the database
    // The function takes in a table name and patient ID
    public static ResultSet getData(String tableName, String patientId) {
        // Get the database connection
        Connection conn = Healnet.DatabaseConnection.getConnection();

        // Create the SQL query
        String sql = "SELECT * FROM " + tableName + " WHERE patient_id = ?";

        try {
            // Prepare the SQL query
            PreparedStatement statement = conn.prepareStatement(sql);

            // Set the patient ID parameter
            statement.setString(1, patientId);

            // Execute the query and get the result set
            ResultSet resultSet = statement.executeQuery();

            // Return the result set
            return resultSet;
        } catch (SQLException e) {
            // Print the stack trace if an exception occurs
            e.printStackTrace();
        }

        // Return null if the result set is empty
        // this means that the patient ID was not found in the database
        return null;
    }
}
