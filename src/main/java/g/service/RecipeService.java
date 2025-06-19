package g.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import g.dao.IngredientDAO;
import g.dao.RecipeDAO;
import g.model.Ingredient;
import g.model.Recipe;
import g.DTO.RecipeDetailRequest;
import g.DTO.RecipeDetailResponse;

public class RecipeService {

    private RecipeDAO recipeDAO;
    private IngredientDAO ingredientDAO;

    public RecipeService(RecipeDAO recipeDAO, IngredientDAO ingredientDAO) {
        this.recipeDAO = recipeDAO;
        this.ingredientDAO = ingredientDAO;
    }

    public int createRecipe(RecipeDetailRequest request) {

        Recipe recipe = request.getRecipe();
        List<Ingredient> ingredients = request.getIngredients();

        int createRecipeResult = recipeDAO.createRecipe(
            recipe.getTitle(),
            recipe.getPrepTime(),
            recipe.getCookTime(),
            recipe.getInstruction(),
            recipe.getImgAddr(),
            recipe.getServe()
        );
 
        if (createRecipeResult==-1) {
            System.out.println("Failed to create recipe");
            return -1;
        }

        for (Ingredient ingredient : ingredients) {
            int createIngredientResult = ingredientDAO.addIngredient(
                createRecipeResult,
                ingredient.getIngredientName(),
                ingredient.getIngredientValue()
            );
            if (createIngredientResult == -1) {
                System.out.println("Failed to insert ingredient: " + ingredient);
                return -1;
            }
        }
    
        return createRecipeResult;
    }

    public boolean deleteRecipe(int recipeId) {

        boolean deleteIngredients = ingredientDAO.deleteIngredientsByRecipeId(recipeId);
        
        boolean deletedRecipe = recipeDAO.deleteRecipe(recipeId);

        if (!deleteIngredients) {
            return false; 
        }else if (!deletedRecipe) {
            return false; 
        }

        return true; 
    }

    public boolean updateRecipe(RecipeDetailRequest request) {

        Recipe recipe = request.getRecipe();
        List<Ingredient> ingredients = request.getIngredients();

        boolean updateRecipeResult = recipeDAO.updateRecipe(
            recipe.getRecipeId(),
            recipe.getTitle(),
            recipe.getPrepTime(),
            recipe.getCookTime(),
            recipe.getInstruction(),
            recipe.getImgAddr(),
            recipe.getServe()
        );
 
        if (!updateRecipeResult) {
            System.out.println("Failed to create recipe");
            return false;
        }

        for (Ingredient ingredient : ingredients) {
            boolean updateIngredientResult = ingredientDAO.updateIngredient(
                ingredient.getPairId(),
                recipe.getRecipeId(),
                ingredient.getIngredientName(),
                ingredient.getIngredientValue()
            );
            if (!updateIngredientResult) {
                System.out.println("Failed to insert ingredient: " + ingredient);
                return false;
            }
        }
    
        return true;
    }


    public RecipeDetailResponse getRecipeById(int recipeId) {
   
        Recipe recipe = recipeDAO.getRecipeById(recipeId);

        List<Ingredient> ingredients = ingredientDAO.getIngredientsByRecipeId(recipeId);

        RecipeDetailResponse response = new RecipeDetailResponse(recipe, ingredients);

        return response;
    }

    public List<RecipeDetailResponse> getRecipesByTitle(String keyword) {

        List<RecipeDetailResponse> responses = new ArrayList<>();

        try {
            
            List<Recipe> recipes = recipeDAO.getRecipesByTitle(keyword);

            for (Recipe recipe : recipes) {
                List<Ingredient> ingredients = ingredientDAO.getIngredientsByRecipeId(recipe.getRecipeId());
                RecipeDetailResponse response = new RecipeDetailResponse(recipe, ingredients);
                responses.add(response);
            }

            return responses;

        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }

    }

    public List<RecipeDetailResponse> getAllRecipes() {

        List<RecipeDetailResponse> responses = new ArrayList<>();

        try {
            
            List<Recipe> recipes = recipeDAO.getAllRecipes();

            for (Recipe recipe : recipes) {
                List<Ingredient> ingredients = ingredientDAO.getIngredientsByRecipeId(recipe.getRecipeId());
                RecipeDetailResponse response = new RecipeDetailResponse(recipe, ingredients);
                responses.add(response);
            }

            return responses;

        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }

    }


    public static void main(String[] args) { 

        // RecipeDAO recipeDAO = new RecipeDAO();
        // IngredientDAO ingredientDAO = new IngredientDAO();
        // RecipeService recipeService = new RecipeService(recipeDAO, ingredientDAO);


        // // create
        // boolean isCreated = recipeService.createRecipe(
        //     "Test Pasta",
        //     10,
        //     20,
        //     "Boil pasta, add sauce.",
        //     "test_pasta.jpg"
        // );
        // System.out.println("Create result: " + isCreated);

        // // delete
        // boolean isDeleted = recipeService.deleteRecipe(9); 
        // System.out.println("Delete result: " + isDeleted);

        // // update
        // boolean isUpdated = recipeService.updateRecipe(10,"Updated Pasta", 15,25,"Boil pasta, add new sauce.","updated_pasta.jpg");
        // System.out.println("Update result: " + isUpdated);

        // getById
        // int testRecipeId = 1;
        // RecipeDetailResponse response = recipeService.getRecipeById(testRecipeId);

        // if (response != null) {
        //     System.out.println("Recipe: " + response.getRecipe());
        //     System.out.println("Ingredients: ");
        //     for (Ingredient ingredient : response.getIngredients()) {
        //         System.out.println(ingredient);
        //     }
        // } else {
        //     System.out.println("No recipe found with ID: " + testRecipeId);
        // }

        // // getAll
        // System.out.println("\n=== All Recipes ===");
        // for (RecipeResponse response : recipeService.getAllRecipes()) {
        //     System.out.println("Recipe: " + response.getRecipe().getTitle());
        //     System.out.println("Ingredients: " + response.getIngredients());
        //     System.out.println("Image: " + response.getImagePath());
        //     System.out.println("------------");
        // }





    }
}

