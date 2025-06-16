package g.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import g.model.Recipe;
import g.utils.DBUtil;

public class RecipeDAO {
    
    public boolean createRecipe(String title, int prepTime, int cookTime, String instruction, String imgAddr) {
        String sql = "INSERT INTO recipe (title, prep_time, cook_time, instruction, img_addr) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, title);
            stmt.setInt(2, prepTime);
            stmt.setInt(3, cookTime);
            stmt.setString(4, instruction);
            stmt.setString(5, imgAddr);

            int rowsAffected = stmt.executeUpdate();
            System.out.println("Rows affected: " + rowsAffected);
            return rowsAffected > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteRecipe(int recipeId) {
        String sql = "DELETE FROM recipe WHERE recipe_id = ?";
        try (Connection conn = DBUtil.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, recipeId);

            int rowsAffected = stmt.executeUpdate();
            System.out.println("Rows affected: " + rowsAffected);
            return rowsAffected > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateRecipe(int recipeId, String title, int prepTime, int cookTime, String instruction, String imgAddr) {
        String sql = "UPDATE recipe SET title = ?, prep_time = ?, cook_time = ?, instruction = ?, img_addr = ? WHERE recipe_id = ?";
        try (Connection conn = DBUtil.getConnection();PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, title);
            stmt.setInt(2, prepTime);
            stmt.setInt(3, cookTime);
            stmt.setString(4, instruction);
            stmt.setString(5, imgAddr);
            stmt.setInt(6, recipeId);

            int rowsAffected = stmt.executeUpdate();
            System.out.println("Rows affected: " + rowsAffected);
            return rowsAffected > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Recipe getRecipeById(int recipeId) {
        String sql = "SELECT * FROM recipe WHERE recipe_id = ?";
        Recipe recipe = new Recipe();

        try (Connection conn = DBUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, recipeId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    recipe.setRecipeId(rs.getInt("recipe_id"));
                    recipe.setTitle(rs.getString("title"));
                    recipe.setPrepTime(rs.getInt("prep_time"));
                    recipe.setCookTime(rs.getInt("cook_time"));
                    recipe.setInstruction(rs.getString("instruction"));

                    System.out.println("Recipe found: " + recipe.getTitle());
                } else {
                    System.out.println("No recipe found for ID: " + recipeId);
                }
            }

            return recipe;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Recipe getRecipeByTitle(String title) {
        String sql = "SELECT * FROM recipe WHERE title = ?";
        Recipe recipe = null;

        try (Connection conn = DBUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, title);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    recipe = new Recipe();
                    recipe.setRecipeId(rs.getInt("recipe_id"));
                    recipe.setTitle(rs.getString("title"));
                    recipe.setPrepTime(rs.getInt("prep_time"));
                    recipe.setCookTime(rs.getInt("cook_time"));
                    recipe.setInstruction(rs.getString("instruction"));

                    System.out.println("Recipe found: " + recipe.getTitle());
                } else {
                    System.out.println("No recipe found with title: " + title);
                }
            }

            return recipe;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Recipe> getAllRecipes() {
        String sql = "SELECT * FROM recipe";
        List<Recipe> recipes = new ArrayList<>();

        try (Connection conn = DBUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Recipe recipe = new Recipe();
                    recipe.setRecipeId(rs.getInt("recipe_id"));
                    recipe.setTitle(rs.getString("title"));
                    recipe.setPrepTime(rs.getInt("prep_time"));
                    recipe.setCookTime(rs.getInt("cook_time"));
                    recipe.setInstruction(rs.getString("instruction"));

                    recipes.add(recipe);
                    System.out.println("Recipe found: " + recipe.getTitle());
                }
            }

            return recipes;

        } catch (Exception e) {
            e.printStackTrace();
            return recipes; // Return empty list if exception occurs
        }
    }
   
    public boolean existsByTitle(String title) {
        String sql = "SELECT COUNT(*) FROM recipe WHERE title = ?";
        try (Connection conn = DBUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, title);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1);
                    System.out.println("Recipe title '" + title + "' count: " + count);
                    return count > 0;
                }
            }

            return false;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        //RecipeDAO recipeDAO = new RecipeDAO();
        //recipeDAO.createRecipe("New Recipe", 30, 60, "Instructions for new recipe", "img/new_recipe.jpg");
        //recipeDAO.deleteRecipe(4);
        //recipeDAO.updateRecipe(2, "Updated Recipe", 25, 55, "Updated instructions", "img/updated_recipe.jpg");
        // Recipe recipe = recipeDAO.getRecipeById(6);
        // if (recipe != null && recipe.getTitle() != null) {
        //     System.out.println("Found recipe: " + recipe.getTitle());
        //     System.out.println("Prep time: " + recipe.getPrepTime());
        //     System.out.println("Cook time: " + recipe.getCookTime());
        //     System.out.println("Instruction: " + recipe.getInstruction());
        // } else {
        //     System.out.println("No recipe found for ID: " + 1);
        // }
        // Recipe recipe = recipeDAO.getRecipeByTitle("Test");
        // if (recipe != null && recipe.getTitle() != null) {
        //     System.out.println("Found recipe: " + recipe.getTitle());
        //     System.out.println("Prep time: " + recipe.getPrepTime());
        //     System.out.println("Cook time: " + recipe.getCookTime());
        //     System.out.println("Instruction: " + recipe.getInstruction());
        // } else {
        //     System.out.println("No recipe found for Title: " + "Test");
        // }
        // List<Recipe> recipes = recipeDAO.getAllRecipes();
        // if (recipes != null && !recipes.isEmpty()) {
        //     for (Recipe recipe : recipes) {
        //         System.out.println("Recipe ID: " + recipe.getRecipeId());
        //         System.out.println("Title: " + recipe.getTitle());
        //         System.out.println("Prep Time: " + recipe.getPrepTime());
        //         System.out.println("Cook Time: " + recipe.getCookTime());
        //         System.out.println("Instruction: " + recipe.getInstruction());
        //         System.out.println("-----------------------------");
        //     }
        // } else {
        //     System.out.println("No recipes found.");
        // }
        // RecipeDAO dao = new RecipeDAO();
        // boolean exists = dao.existsByTitle("Test");
        // System.out.println(exists ? "Title already exists!" : "Title is unique.");
    }
}
