package g.controller;

import java.util.ArrayList;

import g.DTO.RecipeDetailResponse;

public class ListViewController {
    private final ArrayList<RecipeDetailResponse> recipeList = new ArrayList<>();

    public ArrayList<RecipeResponse> getRecipeList() {
        return recipeList;
    }

    public void setRecipeList(ArrayList<RecipeResponse> recipeList) {
        
    }

    public void sendRecipeListRequest(){
        
    }
}
