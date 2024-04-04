package me.divkix.cse360project;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class sql_helper {
    private static final String db_name = "healnet_health_system";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/" + db_name;
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "password";

    private static Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    public static void saveData(String tableName, Object... args) {
        String sql = buildSqlQuery(tableName, args);
        List<Object> values = buildValuesList(args);

        try (Connection conn = getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            for (int i = 0; i < values.size(); i++) {
                statement.setObject(i + 1, values.get(i));
            }

            int rowsInserted = statement.executeUpdate();
            System.out.println(rowsInserted > 0 ? "Data saved successfully!" : "Failed to save data.");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static String buildSqlQuery(String tableName, Object... args) {
        StringBuilder sql = new StringBuilder("INSERT INTO " + tableName + " (");

        for (int i = 0; i < args.length; i += 2) {
            sql.append((String) args[i]).append(", ");
        }

        sql.setLength(sql.length() - 2);
        sql.append(") VALUES (");
        sql.append("?, ".repeat(Math.max(0, args.length / 2)));
        sql.setLength(sql.length() - 2);
        sql.append(")");

        return sql.toString();
    }

    private static List<Object> buildValuesList(Object... args) {
        List<Object> values = new ArrayList<>();

        for (int i = 1; i < args.length; i += 2) {
            values.add(args[i]);
        }

        return values;
    }

    public static ResultSet getData(String tableName, String patientId) {
        String sql = "SELECT * FROM " + tableName + " WHERE patient_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setString(1, patientId);
            return statement.executeQuery();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }
}