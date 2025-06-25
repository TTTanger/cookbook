package g.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import g.dao.IngredientDAO;
import g.dao.RecipeDAO;
import g.dto.RecipeDetailRequest;
import g.dto.RecipeDetailResponse;
import g.dto.RecipeSummaryResponse;
import g.model.Ingredient;
import g.model.Recipe;

public class RecipeService {

    private final RecipeDAO recipeDAO;
    private final IngredientDAO ingredientDAO;

    // Constructor to initialize DAOs
    public RecipeService() {
        this.recipeDAO = new RecipeDAO();
        this.ingredientDAO = new IngredientDAO();
    }

    public RecipeService(RecipeDAO recipeDAO, IngredientDAO ingredientDAO) {
        this.recipeDAO = recipeDAO;
        this.ingredientDAO = ingredientDAO;
    }

    public boolean createRecipe(RecipeDetailRequest request) {

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

        if (createRecipeResult == -1) {
            System.out.println("RecipeService failed to create recipe");
            return false;
        }

        for (Ingredient ingredient : ingredients) {
            boolean createIngredientResult = ingredientDAO.addIngredient(
                    createRecipeResult,
                    ingredient.getIngredientName(),
                    ingredient.getIngredientAmount(),
                    ingredient.getIngredientUnit()
            );
            if (!createIngredientResult) {
                System.out.println("RecipeService failed to insert ingredient: " + ingredient);
                return false;
            }
        }
        return true;
    }

    public boolean deleteRecipe(int recipeId) {
        boolean deleteIngredients = ingredientDAO.deleteIngredientsByRecipeId(recipeId);
        boolean deletedRecipe = recipeDAO.deleteRecipe(recipeId);
        if (!deleteIngredients) {
            return false;
        } else if (!deletedRecipe) {
            return false;
        }
        return true;
    }

    public boolean updateRecipe(RecipeDetailRequest request) {
        Recipe recipe = request.getRecipe();
        System.out.println("RecipeService: Updating recipe with id " + recipe.getRecipeId());
        List<Ingredient> ingredients = request.getIngredients();
        List<Integer> deleteList = request.getDeleteIds();
        for (int pairId : deleteList) {
            System.out.println("RecipeService: Deleting ingredient with pairId " + pairId);
            ingredientDAO.deleteIngredient(pairId);
        }
        
        for (Ingredient ingredient : ingredients) {
            if (ingredient.getPairId() == 0) {
                // 新增ingredient
                boolean insertIngredientResult = ingredientDAO.addIngredient(
                        recipe.getRecipeId(),
                        ingredient.getIngredientName(),
                        ingredient.getIngredientAmount(),
                        ingredient.getIngredientUnit());
                if (!insertIngredientResult) {
                    System.out.println("Failed to insert ingredient: " + ingredient);
                    return false;
                }
            } else {
                boolean updateIngredientResult = ingredientDAO.updateIngredient(
                        ingredient.getPairId(),
                        recipe.getRecipeId(),
                        ingredient.getIngredientName(),
                        ingredient.getIngredientAmount(),
                        ingredient.getIngredientUnit()
                );
                if (!updateIngredientResult) {
                    System.out.println("Failed to update ingredient: " + ingredient);
                    return false;
                }
            }
        }

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
        return true;
    }

    public RecipeDetailResponse getRecipeById(int recipeId) {
        Recipe recipe = recipeDAO.getRecipeById(recipeId);
        List<Ingredient> ingredients = ingredientDAO.getIngredientsByRecipeId(recipeId);
        RecipeDetailResponse response = new RecipeDetailResponse(recipe, ingredients);
        return response;
    }

    public List<RecipeSummaryResponse> getRecipeSummaryByTitle(String keyword) {
        List<RecipeSummaryResponse> responses = new ArrayList<>();
        try {
            List<Recipe> recipes = recipeDAO.getRecipeSummaryByTitle(keyword);
            for (Recipe recipe : recipes) {
                RecipeSummaryResponse response = new RecipeSummaryResponse(
                        recipe.getRecipeId(),
                        recipe.getTitle(),
                        recipe.getImgAddr()
                );
                responses.add(response);
            }
            return responses;
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public List<RecipeSummaryResponse> getAllRecipeSummary() {
        List<RecipeSummaryResponse> responses = new ArrayList<>();
        try {
            List<Recipe> recipes = recipeDAO.getAllRecipeSummary();
            for (Recipe recipe : recipes) {
                RecipeSummaryResponse response = new RecipeSummaryResponse(
                        recipe.getRecipeId(),
                        recipe.getTitle(),
                        recipe.getImgAddr()
                );
                responses.add(response);
            }
            return responses;
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}
