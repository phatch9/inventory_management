import java.sql.*;

public class DatabaseConnection {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/inventory_system";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "@Sanjose2017";

    // Establish a connection to the database
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    // Executes a query and returns a ResultSet
    public static ResultSet executeQuery(String query) throws SQLException {
        Connection conn = getConnection();
        Statement stmt = conn.createStatement();
        return stmt.executeQuery(query);
    }

    // Executes an update (insert, update, delete)
    public static int executeUpdate(String query) throws SQLException {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            return stmt.executeUpdate(query);
        }
    }

    // Prepared statement execution for parameterized queries (insert, update, delete)
    public static int executePreparedUpdate(String query, Object... params) throws SQLException {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }
            return stmt.executeUpdate();
        }
    }
}
