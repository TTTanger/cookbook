package g.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import g.utils.DBUtil;

public class CategoryRecipeDAO {

    public boolean addToCategory(List<Integer> categoryIds, int recipeId) {
        String sql = "INSERT INTO category_recipe (category_id, recipe_id) VALUES (?, ?)";
        
        try (Connection conn = DBUtil.getConnection();PreparedStatement stmt = conn.prepareStatement(sql)) {

            for (int categoryId : categoryIds) {
                stmt.setInt(1, categoryId);
                stmt.setInt(2, recipeId);
                stmt.addBatch(); // Add to batch
            }

            int[] rowsAffected = stmt.executeBatch(); // Execute all inserts at once
            System.out.println("Rows affected: " + rowsAffected);
            // Check if all inserts were successful
            for (int count : rowsAffected) {
                if (count == 0) return false;
            }
            return true;
            
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean clearCategoriesForRecipe(int recipeId) {
        String sql = "DELETE FROM category_recipe WHERE recipe_id = ?";
        try (Connection conn = DBUtil.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, recipeId);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean removeAllRecipesFromCategory(int categoryId) {
        String sql = "DELETE FROM category_recipe WHERE category_id = ?";
        try (Connection conn = DBUtil.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, categoryId);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Integer> getRecipeIdsByCategoryId(int categoryId) {
        String sql = "SELECT * FROM category_recipe WHERE category_id = ?";
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

    public List<Integer> getCategoryIdsByRecipeId(int recipeId) {
        String sql = "SELECT category_id FROM category_recipe WHERE recipe_id = ?";
        List<Integer> categoryIds = new ArrayList<>();

        try (Connection conn = DBUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, recipeId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int categoryId = rs.getInt("category_id");
                    categoryIds.add(categoryId);
                }
            }

            return categoryIds;

        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }


    public static void main(String[] args) {

        // CategoryRecipeDAO dao = new CategoryRecipeDAO();

        // List<Integer> categoryIds = new ArrayList<>();
        // categoryIds.add(1);
        // int recipeId = 1;

        // boolean result = dao.addToCategory(categoryIds, recipeId);
        // System.out.println("Add Result: " + result);

        // int recipeId = 1;
        // boolean result = dao.clearCategoriesForRecipe(recipeId);
        // System.out.println("Remove From All Categories Result: " + result);

        // int categoryId = 14;
        // boolean result = dao.removeAllRecipesFromCategory(categoryId);
        // System.out.println("Remove All Recipes From Category Result: " + result);

        // int categoryId = 14;
        // List<Integer> recipeIds = dao.getRecipeIdsByCategoryId(categoryId);
        // System.out.println("Recipe IDs for Category " + categoryId + ": ");
        // if (recipeIds != null) {
        //     for (int id : recipeIds) {
        //         System.out.println(id);
        //     }
        // } else {
        //     System.out.println("Query failed or returned null.");
        // }
    }

}

