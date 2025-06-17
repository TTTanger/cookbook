package g.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import g.utils.DBUtil;

public class CategoryRecipeDAO {

    public boolean addToCategory(int categoryId, int recipeId) {
        String sql = "INSERT INTO category_recipe (category_id, recipe_id) VALUES (?, ?)";
        try (Connection conn = DBUtil.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, categoryId);
            stmt.setInt(2, recipeId);
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean removeFromCategory(int categoryId, int recipeId) {
        String sql = "DELETE FROM category_recipe WHERE category_id = ? AND recipe_id = ?";
        try (Connection conn = DBUtil.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, categoryId);
            stmt.setInt(2, recipeId);
            int rowsAffected = stmt.executeUpdate();
            System.out.println("Rows affected: " + rowsAffected);
            return rowsAffected > 0;

        } catch (Exception e) {
            return false;
        }
    }

    public List<Integer> getRecipeIdsByCategoryId(int categoryId) {
        String sql = "SELECT * FROM category_recipe WHERE category_name = ?";
        List<Integer> recipeIds = new ArrayList<>();

        try (Connection conn = DBUtil.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, categoryId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int recipeId = rs.getInt("recipe_id");
                recipeIds.add(recipeId);
            }
            return recipeIds;
        } catch (Exception e) {
            return null;
        }
    }
}
