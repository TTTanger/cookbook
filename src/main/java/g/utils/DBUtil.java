package g.utils;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Database utility class that provides methods for managing database
 * connections. This class handles SQLite database connections and connection
 * closing operations.
 *
 * @author Junzhe Luo
 * @version 1.0
 * @since 2025-06-14
 */
public class DBUtil {
    /**
     * The JDBC URL for the SQLite database connection
     */
    private static final String DB_PATH = "./data/cookbook.db";
    private static String url;

    static {
        // Ensure data directory exists
        File dataDir = new File("./data");
        if (!dataDir.exists()) {
            dataDir.mkdirs();
        }
        url = "jdbc:sqlite:" + DB_PATH;
    }

    /**
     * Establishes and returns a new connection to the SQLite database.
     *
     * @return A Connection object representing the database connection
     * @throws SQLException if a database access error occurs or the URL is null
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url);
    }

    /**
     * Initializes the SQLite database by creating necessary tables if they do not exist.
     */
    public static void initializeDatabase() {
        try (Connection conn = getConnection();
             java.sql.Statement stmt = conn.createStatement()) {
            // Create category table
            stmt.execute("CREATE TABLE IF NOT EXISTS category (category_id INTEGER PRIMARY KEY AUTOINCREMENT, category_name TEXT NOT NULL)");
            // Create recipe table
            stmt.execute("CREATE TABLE IF NOT EXISTS recipe (recipe_id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT NOT NULL, prep_time INTEGER, cook_time INTEGER, instruction TEXT, img_addr TEXT, serve INTEGER)");
            // Create ingredient table
            stmt.execute("CREATE TABLE IF NOT EXISTS ingredient (pair_id INTEGER PRIMARY KEY AUTOINCREMENT, recipe_id INTEGER, ingredient_name TEXT, ingredient_amount INTEGER, unit TEXT, FOREIGN KEY(recipe_id) REFERENCES recipe(recipe_id))");
            // Create category_recipe table
            stmt.execute("CREATE TABLE IF NOT EXISTS category_recipe (category_id INTEGER, recipe_id INTEGER, PRIMARY KEY(category_id, recipe_id), FOREIGN KEY(category_id) REFERENCES category(category_id), FOREIGN KEY(recipe_id) REFERENCES recipe(recipe_id))");
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
            initializeDatabase();
            Connection conn = DBUtil.getConnection();
            System.out.println("Connection successful!");
            DBUtil.close(conn);
        } catch (SQLException e) {
            System.err.println("Connection failed!");
        }
    }
}
