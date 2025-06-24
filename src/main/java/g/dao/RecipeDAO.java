package g.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import g.model.Recipe;
import g.utils.DBUtil;

public class RecipeDAO {
    
    public int createRecipe(String title, int prepTime, int cookTime, String instruction, String imgAddr, int serve) {
        String sql = "INSERT INTO recipe (title, prep_time, cook_time, instruction, img_addr, serve) VALUES (?, ?, ?, ?, ?,?)";
        
        try (Connection conn = DBUtil.getConnection(); 
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, title);
            stmt.setInt(2, prepTime);
            stmt.setInt(3, cookTime);
            stmt.setString(4, instruction);
            stmt.setString(5, imgAddr);
            stmt.setInt(6, serve);

            int rowsAffected = stmt.executeUpdate();
            System.out.println("Rows affected: " + rowsAffected);
            
            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int recipeId = generatedKeys.getInt(1);
                        System.out.println("Generated Recipe ID: " + recipeId);
                        return recipeId;
                    }
                }
            }

            return -1;

        } catch (Exception e) {
            e.printStackTrace();
            return -1;
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

    public boolean updateRecipe(int recipeId, String title, int prepTime, int cookTime, String instruction, String imgAddr, int serve) {
        String sql = "UPDATE recipe SET title = ?, prep_time = ?, cook_time = ?, instruction = ?, img_addr = ?, serve = ? WHERE recipe_id = ?";
        try (Connection conn = DBUtil.getConnection();PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, title);
            stmt.setInt(2, prepTime);
            stmt.setInt(3, cookTime);
            stmt.setString(4, instruction);
            stmt.setString(5, imgAddr);
            stmt.setInt(6, serve);
            stmt.setInt(7, recipeId);

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
                    recipe.setImgAddr(rs.getString("img_addr"));
                    recipe.setServe(rs.getInt("serve"));

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

    public Recipe getRecipeSummaryById(int recipeId) {
        String sql = "SELECT recipe_id, title, img_addr FROM recipe WHERE recipe_id = ?";

        try (Connection conn = DBUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, recipeId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Recipe recipe = new Recipe();
                    recipe.setRecipeId(rs.getInt("recipe_id"));
                    recipe.setTitle(rs.getString("title"));
                    recipe.setImgAddr(rs.getString("img_addr"));
                    return recipe;
                } else {
                    System.out.println("No recipe found for ID: " + recipeId);
                    return null;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null; 
        }
    }

    public List<Recipe> getRecipeSummaryByTitle(String keyword) {
        String sql = "SELECT recipe_id, title, img_addr FROM recipe WHERE title LIKE ?";
        List<Recipe> recipes = new ArrayList<>();

        try (Connection conn = DBUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + keyword + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Recipe recipe = new Recipe();
                    recipe.setRecipeId(rs.getInt("recipe_id"));
                    recipe.setTitle(rs.getString("title"));
                    recipe.setImgAddr(rs.getString("img_addr"));
                    
                    recipes.add(recipe);
                    System.out.println("Recipe found: " + recipe.getTitle());
                }
            }

            return recipes;

        } catch (Exception e) {
            e.printStackTrace();
            return recipes; 
        }
    }


    public List<Recipe> getAllRecipeSummary() {
        String sql = "SELECT recipe_id, title, img_addr FROM recipe";
        List<Recipe> recipes = new ArrayList<>();

        try (Connection conn = DBUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Recipe recipe = new Recipe();
                    recipe.setRecipeId(rs.getInt("recipe_id"));
                    recipe.setTitle(rs.getString("title"));
                    recipe.setImgAddr(rs.getString("img_addr"));

                    recipes.add(recipe);
                    System.out.println("Recipe found: " + recipe.getTitle());
                }
            }

            return recipes;

        } catch (Exception e) {
            e.printStackTrace();
            return recipes; 
        }
    }
   
    // public boolean existsByTitle(String title) {
    //     String sql = "SELECT COUNT(*) FROM recipe WHERE title = ?";
    //     try (Connection conn = DBUtil.getConnection();
    //         PreparedStatement stmt = conn.prepareStatement(sql)) {

    //         stmt.setString(1, title);

    //         try (ResultSet rs = stmt.executeQuery()) {
    //             if (rs.next()) {
    //                 int count = rs.getInt(1);
    //                 System.out.println("Recipe title '" + title + "' count: " + count);
    //                 return count > 0;
    //             }
    //         }

    //         return false;

    //     } catch (Exception e) {
    //         e.printStackTrace();
    //         return false;
    //     }
    // }

    public static void main(String[] args) {

        // RecipeDAO recipeDAO = new RecipeDAO();

        // recipeDAO.createRecipe("tr2", 6, 30, "Instructions for test2 recipe", "img/test_recipe_2.jpg",2);

        // recipeDAO.deleteRecipe(3);

        // recipeDAO.updateRecipe(4, "Updated Recipe", 25, 55, "Updated instructions", "img/updated_recipe.jpg",90);

        // int recipeId =4;
        // Recipe recipe = recipeDAO.getRecipeById(recipeId);
        // if (recipe != null && recipe.getTitle() != null) {
        //     System.out.println("Found recipe: " + recipe.getTitle());
        //     System.out.println("Prep time: " + recipe.getPrepTime());
        //     System.out.println("Cook time: " + recipe.getCookTime());
        //     System.out.println("Instruction: " + recipe.getInstruction());
        //     System.out.println("ImgAddr: " + recipe.getImgAddr());
        //     System.out.println("Serve: " + recipe.getServe());
        // } else {
        //     System.out.println("No recipe found for ID: " + recipeId);
        // }

        // String keyword = "Test";
        // List<Recipe> recipes = recipeDAO.getRecipesByTitle(keyword);
        // if (recipes.isEmpty()) {
        //     System.out.println("No recipes found.");
        // } else {
        //     for (Recipe recipe : recipes) {
        //         System.out.println("Found recipe: " + recipe.getTitle());
        //         System.out.println("Prep time: " + recipe.getPrepTime());
        //         System.out.println("Cook time: " + recipe.getCookTime());
        //         System.out.println("Instruction: " + recipe.getInstruction());
        //         System.out.println("ImgAddr: " + recipe.getImgAddr());
        //         System.out.println("Serve: " + recipe.getServe());
        //     }
        // }

        // List<Recipe> recipes = recipeDAO.getAllRecipes();
        // if (recipes != null && !recipes.isEmpty()) {
        //     for (Recipe recipe : recipes) {
        //         System.out.println("Recipe ID: " + recipe.getRecipeId());
        //         System.out.println("Title: " + recipe.getTitle());
        //         System.out.println("Prep Time: " + recipe.getPrepTime());
        //         System.out.println("Cook Time: " + recipe.getCookTime());
        //         System.out.println("Instruction: " + recipe.getInstruction());
        //         System.out.println("ImgAddr: " + recipe.getImgAddr());
        //         System.out.println("Serve: " + recipe.getServe());
        //         System.out.println("-----------------------------");
        //     }
        // } else {
        //     System.out.println("No recipes found.");
        // }

    }
}
