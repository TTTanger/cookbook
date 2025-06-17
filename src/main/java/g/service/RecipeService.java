package g.service;

import java.util.ArrayList;
import java.util.List;

import g.dao.ImageDAO;
import g.dao.IngredientDAO;
import g.dao.RecipeDAO;
import g.model.Ingredient;
import g.model.Recipe;
import g.model.RecipeResponse;

public class RecipeService {

    private RecipeDAO recipeDAO;
    private IngredientDAO ingredientDAO;
    private ImageDAO imageDAO;

    // Constructor: inject DAOs from outside
    public RecipeService(RecipeDAO recipeDAO, IngredientDAO ingredientDAO, ImageDAO imageDAO) {
        this.recipeDAO = recipeDAO;
        this.ingredientDAO = ingredientDAO;
        this.imageDAO = imageDAO;
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
    public RecipeResponse getRecipeById(int recipeId) {
        // Step 1: Get the recipe
        Recipe recipe = recipeDAO.getRecipeById(recipeId);

        // Step 2: Get related ingredients
        List<Ingredient> ingredients = ingredientDAO.getIngredientsByRecipeId(recipeId);

        // Step 3: Get image path (if stored separately)
        String imagePath = imageDAO.getImageByRecipeId(recipeId);

        // Step 4: Combine into response
        return new RecipeResponse(recipe, ingredients, imagePath);
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

        // RecipeDAO recipeDAO = new RecipeDAO();
        // IngredientDAO ingredientDAO = new IngredientDAO();
        // ImageDAO imageDAO = new ImageDAO();

        // RecipeService recipeService = new RecipeService(recipeDAO, ingredientDAO, imageDAO);

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

        // // getById
        // RecipeResponse result = recipeService.getRecipeById(10);
        // System.out.println("\n=== Get By ID ===");
        // System.out.println("ID: " + result.getRecipe().getRecipeId());
        // System.out.println("Title: " + result.getRecipe().getTitle());
        // System.out.println("Instruction: " + result.getRecipe().getInstruction());
        // System.out.println("Image Path: " + result.getImagePath());
        // System.out.println("Ingredients: " + result.getIngredients());

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

