package g.service;

import java.util.ArrayList;
import java.util.List;

import g.dao.IngredientDAO;
import g.model.Ingredient;

public class CalculateService {
    private final IngredientDAO ingredientDAO;

    public CalculateService() {
        this.ingredientDAO = new IngredientDAO();
    }

    public List<Ingredient> calculateServing(int serve, int recipeId) {
        List<Ingredient> ingredients = this.ingredientDAO.getIngredientsByRecipeId(recipeId);
        List<Ingredient> calculatedIngredients = new ArrayList<>();
        for (Ingredient ingredient : ingredients) {
            int ingredientValue = ingredient.getIngredientValue();
            int calculatedValue = ingredientValue * serve;
            Ingredient calculatedIngredient = new Ingredient(calculatedValue, ingredient.getIngredientName());
            calculatedIngredients.add(calculatedIngredient);
        }
        return calculatedIngredients;
    }

    public static void main(String[] args) {
        CalculateService calculateService = new CalculateService();
        List<Ingredient> calculatedIngredients = calculateService.calculateServing(2, 1);
        for (Ingredient ingredient : calculatedIngredients) {
            System.out.println(ingredient.getIngredientName() + ": " + ingredient.getIngredientValue());
        }
    }
}
