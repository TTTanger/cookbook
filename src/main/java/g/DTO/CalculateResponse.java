package g.dto;

import java.util.List;

import g.model.Ingredient;

public class CalculateResponse {
    private List<Ingredient> ingredients; 

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }
}



