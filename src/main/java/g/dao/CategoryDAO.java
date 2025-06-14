package g.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import g.utils.DBUtil;

public class CategoryDAO {
    public void getAllCategories() {
        String sql = "SELECT * FROM category";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                System.out.println("Category Name: " + rs.getString("category_name"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        CategoryDAO categoryDAO = new CategoryDAO();
        categoryDAO.getAllCategories();
    }
}