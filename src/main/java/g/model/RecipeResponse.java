package g.model;

import java.util.List;

public class RecipeResponse {

    private Recipe recipe;
    private List<Ingredient> ingredients;
    private String imagePath;

    public RecipeResponse(Recipe recipe, List<Ingredient> ingredients, String imagePath) {
        this.recipe = recipe;
        this.ingredients = ingredients;
        this.imagePath = imagePath;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public String getImagePath() {
        return imagePath;
    }


}
