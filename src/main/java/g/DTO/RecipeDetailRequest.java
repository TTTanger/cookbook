package g.dto;

import java.util.List;

import g.model.Ingredient;
import g.model.Recipe;

public class RecipeDetailRequest {
    
    private Recipe recipe;
    private List<Ingredient> ingredients;

    public RecipeDetailRequest(Recipe recipe, List<Ingredient> ingredients) {
        this.recipe = recipe;
        this.ingredients = ingredients;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }
}
