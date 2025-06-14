package g.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBTest {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3307/cookbook?useSSL=false&serverTimezone=UTC&characterEncoding=UTF-8";
        String user = "root";  // 替换为你的数据库用户名
        String password = "";  // 替换为你的数据库密码

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            System.out.println("✅ 数据库连接成功！");

            // 示例查询 ingredient 表
            String query = "SELECT * FROM recipe";
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(query)) {

                while (rs.next()) {
                    int recipeId = rs.getInt("recipe_id");
                    String name = rs.getString("title");
                    System.out.printf("ID: %d, Title: %s\n", recipeId, name);
                }
            }

        } catch (SQLException e) {
            System.out.println("❌ 数据库连接失败！");
            e.printStackTrace();
        }
    }
}
