package g.service;

import java.util.ArrayList;
import java.util.List;

import g.dao.IngredientDAO;
import g.dao.RecipeDAO;
import g.dto.CalculateResponse;
import g.model.Ingredient;

public class CalculateService {

    private final IngredientDAO ingredientDAO;
    private final RecipeDAO recipeDAO;
        
    public CalculateService() {
        this.ingredientDAO = new IngredientDAO();
        this.recipeDAO = new RecipeDAO();
    }

    public CalculateResponse IngredientCalculate(int recipeId, int serve) {
       
        CalculateResponse response = new CalculateResponse();

        List<Ingredient> ingredients = ingredientDAO.getIngredientsByRecipeId(recipeId);

        int originalServe = recipeDAO.getRecipeById(recipeId).getServe();


        double scaleFactor = (double) serve / originalServe;


        List<Ingredient> scaledIngredients = new ArrayList<>();
        for (Ingredient ingredient : ingredients) {
            Ingredient result = new Ingredient();
            result.setPairId(ingredient.getPairId());
            result.setRecipeId(ingredient.getRecipeId());
            result.setIngredientName(ingredient.getIngredientName());
            result.setIngredientAmount((int) Math.ceil(ingredient.getIngredientAmount() * scaleFactor));
            result.setIngredientUnit(ingredient.getIngredientUnit());
            scaledIngredients.add(result);
        }

        response.setIngredients(scaledIngredients);

        return response;
    }
}
