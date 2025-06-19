package g.service;

import java.util.ArrayList;
import java.util.List;

import g.dao.IngredientDAO;
import g.dao.RecipeDAO;
import g.model.Ingredient;
import g.model.Recipe;
import g.DTO.RecipeDetailResponse;

public class RecipeService {

    private RecipeDAO recipeDAO;
    private IngredientDAO ingredientDAO;

    // Constructor: inject DAOs from outside
    public RecipeService(RecipeDAO recipeDAO, IngredientDAO ingredientDAO) {
        this.recipeDAO = recipeDAO;
        this.ingredientDAO = ingredientDAO;
    }

    // Create a new recipe
    public boolean createRecipe(String title, int prepTime, int cookTime, String instruction, String imgAddr) {
        // Validate input
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Recipe name cannot be empty.");
        }

        // Check for duplicate
        if (recipeDAO.existsByTitle(title)) {
            return false;
        }

        // Create recipe
        boolean isCreated = recipeDAO.createRecipe(title, prepTime, cookTime, instruction, imgAddr);

        // If your recipe table already stores the image, no need to call imageDAO here
        return isCreated;
    }

    // Delete a recipe by ID
    public boolean deleteRecipe(int recipeId) {
        // Step 1: Delete ingredients related to the recipe
        boolean ingredientsDeleted = ingredientDAO.deleteIngredientsByRecipeId(recipeId);
        
        // Step 2: Delete the recipe itself
        boolean isDeleted = recipeDAO.deleteRecipe(recipeId);

        if (!ingredientsDeleted) {
            return false; // If ingredients deletion fails, return false
        }else if (!isDeleted) {
            return false; // If recipe deletion fails, return false
        }

        return isDeleted;
    }

    // Update an existing recipe
    public boolean updateRecipe(int recipeId, String title, int prepTime, int cookTime, String instruction, String imgAddr) {
        // Step 1: Validate input
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Recipe name cannot be empty.");
        }

        // Step 2: Call DAO to update the recipe
        boolean isUpdated = recipeDAO.updateRecipe(recipeId, title, prepTime, cookTime, instruction, imgAddr);

        // Step 3: Return whether update succeeded
        return isUpdated;
    }

    // Get a recipe by ID
    public RecipeDetailResponse getRecipeById(int recipeId) {
   
        Recipe recipe = recipeDAO.getRecipeById(recipeId);

        List<Ingredient> ingredients = ingredientDAO.getIngredientsByRecipeId(recipeId);

        RecipeDetailResponse response = new RecipeDetailResponse(recipe, ingredients);

        return response;
    }

    // Get recipes by title
    public List<RecipeResponse> getRecipesByTitle(String title) {
        List<Recipe> recipes = recipeDAO.getRecipesByTitle(title);
        List<RecipeResponse> responses = new ArrayList<>();
        for (Recipe recipe : recipes) {
            int recipeId = recipe.getRecipeId();
            List<Ingredient> ingredients = ingredientDAO.getIngredientsByRecipeId(recipeId);
            String imagePath = imageDAO.getImageByRecipeId(recipeId);
            responses.add(new RecipeResponse(recipe, ingredients, imagePath));
        }
        return responses;
    }

    // Get all recipes
    public List<RecipeResponse> getAllRecipes() {
        List<Recipe> recipes = recipeDAO.getAllRecipes();
        List<RecipeResponse> responses = new ArrayList<>();
        for (Recipe recipe : recipes) {
            int recipeId = recipe.getRecipeId();
            List<Ingredient> ingredients = ingredientDAO.getIngredientsByRecipeId(recipeId);
            String imagePath = imageDAO.getImageByRecipeId(recipeId);
            responses.add(new RecipeResponse(recipe, ingredients, imagePath));
        }
        return responses;
    }



    public static void main(String[] args) { 

        RecipeDAO recipeDAO = new RecipeDAO();
        IngredientDAO ingredientDAO = new IngredientDAO();
        RecipeService recipeService = new RecipeService(recipeDAO, ingredientDAO);


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
        int testRecipeId = 1;
        RecipeDetailResponse response = recipeService.getRecipeById(testRecipeId);

        if (response != null) {
            System.out.println("Recipe: " + response.getRecipe());
            System.out.println("Ingredients: ");
            for (Ingredient ingredient : response.getIngredients()) {
                System.out.println(ingredient);
            }
        } else {
            System.out.println("No recipe found with ID: " + testRecipeId);
        }

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

