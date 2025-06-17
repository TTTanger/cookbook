package g.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import g.model.Category;
import g.utils.DBUtil;

public class CategoryDAO {
    public boolean createCategory(String categoryName) {
        String sql = "INSERT INTO category (category_name) VALUES (?)";

        try (Connection conn = DBUtil.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, categoryName);
            int rowsAffected = stmt.executeUpdate();
            System.out.println("Rows affected: " + rowsAffected);
            return rowsAffected > 0;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean deleteCategory(String categoryName) {
        String sql = "DELETE FROM category WHERE category_name = ?";

        try (Connection conn = DBUtil.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, categoryName);
            int rowsAffected = stmt.executeUpdate();
            System.out.println("Rows affected: " + rowsAffected);
            return rowsAffected > 0;

        } catch (Exception e) {
            return false;
        }
    }

    public boolean updateCategory(int categoryId, String newCategoryName) {
        String sql = "UPDATE category SET category_name = ? WHERE category_id = ?";

        try (Connection conn = DBUtil.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, newCategoryName);
            stmt.setInt(2, categoryId);
            int rowsAffected = stmt.executeUpdate();
            System.out.println("Rows affected: " + rowsAffected);
            return rowsAffected > 0;
        } catch (Exception e) {
            return false;
        }
    }

    public Category getCategoryById(int categoryId) {
        String sql = "SELECT * FROM category WHERE category_id = ?";

        try (Connection conn = DBUtil.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, categoryId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Category(rs.getInt("category_id"), rs.getString("category_name"), null);
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public Category getCategoryByName(String categoryName) {
        String sql = "SELECT * FROM category WHERE category_name = ?";

        try (Connection conn = DBUtil.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, categoryName);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Category(rs.getInt("category_id"), rs.getString("category_name"), null);
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public ArrayList<Category> getAllCategories() {
        String sql = "SELECT * FROM category";

        try (Connection conn = DBUtil.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            ArrayList<Category> categories = new ArrayList<>();
            while (rs.next()) {
                categories.add(new Category(rs.getInt("category_id"), rs.getString("category_name"), null));
            }
            System.out.println("Categories: ");
            for (Category category : categories) {
                System.out.println(category);
            }
            return categories;
        } catch (Exception e) {
            return null;
        }
    }
}
