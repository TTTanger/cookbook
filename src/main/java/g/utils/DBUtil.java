package g.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Database utility class that provides methods for managing database
 * connections. This class handles MySQL database connections and connection
 * closing operations.
 *
 * @author Junzhe Luo
 * @version 1.0
 * @since 2025-06-14
 */
public class DBUtil {
    /**
     * The JDBC URL for the MySQL database connection
     */
    private static final String URL = "jdbc:mysql://localhost:3306/cookbook?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC&characterEncoding=UTF-8";

    /**
     * The username for database authentication
     */
    private static final String USER = "cookbook";

    /**
     * The password for database authentication
     */
    private static final String PASSWORD = "admin123";

    /**
     * Establishes and returns a new connection to the MySQL database. Uses
     * predefined URL, username, and password to create the connection.
     *
     * @return A Connection object representing the database connection
     * @throws SQLException if a database access error occurs or the URL is null
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    /**
     * Safely closes a database connection. If the connection is null, no action
     * is taken. Any SQLExceptions that occur during closing are caught and
     * printed to stderr.
     *
     * @param conn The Connection object to be closed
     */
    public static void close(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                System.err.println("Error closing connection!");
            }
        }
    }

    public static void main(String[] args) {
        try {
            Connection conn = DBUtil.getConnection();
            System.out.println("Connection successful!");
            DBUtil.close(conn);
        } catch (SQLException e) {
            System.err.println("Connection failed!");
        }
    }
}
