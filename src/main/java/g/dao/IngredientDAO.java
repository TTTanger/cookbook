package g.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import g.model.Ingredient;
import g.utils.DBUtil;

public class IngredientDAO {

    public boolean addIngredient(int recipeId, String ingredientName, int ingredientAmount,String ingredientUnit) {
        String sql = "INSERT INTO ingredient (recipe_id, ingredient_name, ingredient_amount, ingredient_unit) VALUES (?, ?, ?,?)";
        try (Connection conn = DBUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, recipeId);
            stmt.setString(2, ingredientName);
            stmt.setInt(1, ingredientAmount);
            stmt.setString(3, ingredientUnit);

            int rowsAffected = stmt.executeUpdate();
            System.out.println("Rows affected: " + rowsAffected);

            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int pairId = generatedKeys.getInt(1);
                        System.out.println("Generated Recipe ID: " + pairId);
                        return rowsAffected > 0;
                    }
                }
            }

            return false;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean deleteIngredient(int pairId, int recipeId) {
        String sql = "DELETE FROM ingredient WHERE pair_id = ? AND recipe_id = ?";
        try (Connection conn = DBUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, pairId);
            stmt.setInt(2, recipeId);

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

    public boolean updateIngredient(int pairId, int recipeId, String ingredientName, int ingredientAmount,String ingredientUnit) {
        String sql = "UPDATE ingredient SET ingredient_name = ?, ingredient_amount = ?, ingredient_unit = ? WHERE pair_id = ? AND recipe_id = ?";
        try (Connection conn = DBUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, ingredientName);
            stmt.setInt(2, ingredientAmount);            
            stmt.setString(3, ingredientUnit);
            stmt.setInt(4, pairId);
            stmt.setInt(5, recipeId);


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
                    ingredient.setIngredientAmount(rs.getInt("ingredient_amount"));
                    ingredient.setIngredientUnit(rs.getString("ingredient_unit"));

                    ingredients.add(ingredient);
                    System.out.println("Ingredient found: " + ingredient.getIngredientName());
                }
            }

            return ingredients;

        } catch (Exception e) {
            e.printStackTrace();
            return ingredients; 
        }
    }


    public static void main(String[] args) {

        // IngredientDAO dao = new IngredientDAO();

        // boolean addResult = dao.addIngredient(4, "Letter", 4);
        // boolean addResult1 = dao.addIngredient(4, "Carrot", 2);

        // System.out.println("Add ingredient result: " + addResult);

        // boolean deleteOneResult = dao.deleteIngredient(2, 1);
        // System.out.println("Delete single ingredient result: " + deleteOneResult);

        // List<Ingredient> ingredients = dao.getIngredientsByRecipeId(1);
        // for (Ingredient ingredient : ingredients) {
        //     System.out.println(" - " + ingredient.getIngredientName() + ": " + ingredient.getIngredientValue());
        // }

        // boolean updateResult = dao.updateIngredientValue(1,1, "Potato", 5);
        // System.out.println("Update ingredient value result: " + updateResult);

        // boolean deleteAllResult = dao.deleteIngredientsByRecipeId(1);
        // System.out.println("Delete all ingredients result: " + deleteAllResult);
    }
}
