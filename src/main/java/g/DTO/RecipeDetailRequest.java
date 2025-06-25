package g.dto;

import java.util.List;

import g.model.Ingredient;
import g.model.Recipe;

public class RecipeDetailRequest {
    
    private Recipe recipe;
    private List<Ingredient> ingredients;
    private List<Integer> deleteIds;

    public RecipeDetailRequest() {
        
    }

    public RecipeDetailRequest(Recipe recipe, List<Ingredient> ingredients,List<Integer> deleteIds) {
        this.recipe = recipe;
        this.ingredients = ingredients;
        this.deleteIds = deleteIds;
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

    public List<Integer> getDeleteIds() {
        return deleteIds;
    }

    public void setDeleteIds(List<Integer> deleteIds) {
        this.deleteIds = deleteIds;
    }
}
