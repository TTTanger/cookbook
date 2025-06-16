package g.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import g.model.Ingredient;
import g.utils.DBUtil;

public class IngredientDAO {

    public boolean addIngredient(int recipeId, String ingredientName, int ingredientValue) {
        String sql = "INSERT INTO ingredient (recipe_id, ingredient_name, ingredient_value) VALUES (?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, recipeId);
            stmt.setString(2, ingredientName);
            stmt.setInt(3, ingredientValue);

            int rowsAffected = stmt.executeUpdate();
            System.out.println("Rows affected: " + rowsAffected);
            return rowsAffected > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean deleteIngredient(int recipeId, String ingredientName) {
        String sql = "DELETE FROM ingredient WHERE recipe_id = ? AND ingredient_name = ?";
        try (Connection conn = DBUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, recipeId);
            stmt.setString(2, ingredientName);

            int rowsAffected = stmt.executeUpdate();
            System.out.println("Rows affected: " + rowsAffected);
            return rowsAffected > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteIngredientsByRecipeId(int recipeId) {
        String sql = "DELETE FROM ingredient WHERE recipe_id = ?";
        try (Connection conn = DBUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, recipeId);

            int rowsAffected = stmt.executeUpdate();
            System.out.println("Rows affected: " + rowsAffected);
            return rowsAffected > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateIngredientValue(int recipeId, String ingredientName, int newValue) {
        String sql = "UPDATE ingredient SET ingredient_value = ? WHERE recipe_id = ? AND ingredient_name = ?";
        try (Connection conn = DBUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, newValue);
            stmt.setInt(2, recipeId);
            stmt.setString(3, ingredientName);

            int rowsAffected = stmt.executeUpdate();
            System.out.println("Rows affected: " + rowsAffected);
            return rowsAffected > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Ingredient> getIngredientsByRecipeId(int recipeId) {
        String sql = "SELECT * FROM ingredient WHERE recipe_id = ?";
        List<Ingredient> ingredients = new ArrayList<>();

        try (Connection conn = DBUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, recipeId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Ingredient ingredient = new Ingredient();
                    ingredient.setIngredientName(rs.getString("ingredient_name"));
                    ingredient.setIngredientValue(rs.getInt("ingredient_value"));

                    ingredients.add(ingredient);
                    System.out.println("Ingredient found: " + ingredient.getIngredientName());
                }
            }

            return ingredients;

        } catch (Exception e) {
            e.printStackTrace();
            return ingredients; // return empty list if exception occurs
        }
    }


    public static void main(String[] args) {

        IngredientDAO dao = new IngredientDAO();

        // boolean addResult = dao.addIngredient(1, "Sugar", 3);
        // System.out.println("Add ingredient result: " + addResult);

        // List<Ingredient> ingredients = dao.getIngredientsByRecipeId(1);
        // for (Ingredient ingredient : ingredients) {
        //     System.out.println(" - " + ingredient.getIngredientName() + ": " + ingredient.getIngredientValue());
        // }

        // boolean deleteOneResult = dao.deleteIngredient(6, "Salt");
        // System.out.println("Delete single ingredient result: " + deleteOneResult);


        boolean updateResult = dao.updateIngredientValue(1, "Sugar", 5);
        System.out.println("Update ingredient value result: " + updateResult);
        // boolean deleteAllResult = dao.deleteIngredientsByRecipeId(1);
        // System.out.println("Delete all ingredients result: " + deleteAllResult);
    }
}
