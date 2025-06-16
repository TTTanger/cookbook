package g.service;

import java.util.ArrayList;
import java.util.List;

import g.model.RecipeResponse;

public class SearchService {
    private RecipeService recipeService;
    private CategoryService catogoryService;


    public SearchService(RecipeService recipeService, CategoryService catogoryService) {
        this.recipeService = recipeService;
        this.catogoryService = catogoryService;
    }

    public List<RecipeResponse> searchRecipes(String title, String categoryName) {
        if (title != null && !title.trim().isEmpty()) {
            return recipeService.getRecipesByTitle(title);
        } else if (categoryName != null && !categoryName.trim().isEmpty()) {
            List<Integer> recipeIds = CategoryService.getRecipesByCategory(categoryName);
            List<RecipeResponse> responses = new ArrayList<>();
            for (Integer recipeId : recipeIds) {
                responses.add(recipeService.getRecipeById(recipeId));
            }
            return responses;
        } else {
            return new ArrayList<>();
        }
    }
}
