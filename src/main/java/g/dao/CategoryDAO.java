package g.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import g.model.Category;
import g.utils.DBUtil;

public class CategoryDAO {

    public boolean addToCategory(String recipeTitle, String categoryName) {
        String sql = "INSERT INTO category_recipe (recipe_title, category_name) VALUES (?, ?)";
        try (Connection conn = DBUtil.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, recipeTitle);
            stmt.setString(2, categoryName);
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean removeFromCategory(String recipeTitle, String categoryName) {
        String sql = "DELETE FROM category_recipe WHERE recipe_title = ? AND category_name = ?";
        try (Connection conn = DBUtil.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, recipeTitle);
            stmt.setString(2, categoryName);
            int rowsAffected = stmt.executeUpdate();
            System.out.println("Rows affected: " + rowsAffected);
            return rowsAffected > 0;

        } catch (Exception e) {
            return false;
        }
    }

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

    public List<String> getRecipesByCategory(String categoryName) {
        String sql = "SELECT * FROM category_recipe WHERE category_name = ?";
        List<String> recipes = new ArrayList<>();

        try (Connection conn = DBUtil.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, categoryName);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String recipeTitle = rs.getString("recipe_title");
                recipes.add(recipeTitle);
            }
            return recipes;
        } catch (Exception e) {
            return null;
        }
    }

    public ArrayList<Category> getAllCategories() {
        String sql = "SELECT * FROM category";

        try (Connection conn = DBUtil.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            ArrayList<Category> categories = new ArrayList<>();
            while (rs.next()) {
                categories.add(new Category(rs.getString("category_name"), rs.getInt("category_id"), null));
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

    public static void main(String[] args) {
        // Test
    }
}
