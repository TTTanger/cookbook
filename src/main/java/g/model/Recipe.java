package g.model;

import java.util.ArrayList;

public class Recipe {
    private int recipeId;
    private String title;
    private int prepTime;
    private int cookTime;
    private String instruction;
    private String imageAddr;
    private ArrayList<Ingredient> ingredients;

    public Recipe() {
    }

    public Recipe(int recipeId, String title, int prepTime, int cookTime, String instruction, String imageAddr, ArrayList<Ingredient> ingredients) {
        this.recipeId = recipeId;
        this.title = title;
        this.prepTime = prepTime;
        this.cookTime = cookTime;
        this.instruction = instruction;
        this.imageAddr = imageAddr;
        this.ingredients = ingredients;
    }

    public int getRecipeId() {
        return this.recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPrepTime() {
        return this.prepTime;
    }

    public void setPrepTime(int prepTime) {
        this.prepTime = prepTime;
    }

    public int getCookTime() {
        return this.cookTime;
    }

    public void setCookTime(int cookTime) {
        this.cookTime = cookTime;
    }

    public String getInstruction() {
        return this.instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public String getImageAddr() {
        return this.imageAddr;
    }

    public void setImageAddr(String imageAddr) {
        this.imageAddr = imageAddr;
    }

    public ArrayList<Ingredient> getIngredients() {
        return this.ingredients;
    }

    public void setIngredients(ArrayList<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }
    
    @Override
    public String toString() {
        return "Recipe{" +
                "recipeId=" + recipeId +
                ", title='" + title + '\'' +
                ", prepTime=" + prepTime +
                ", cookTime=" + cookTime +
                ", instruction='" + instruction + '\'' +
                ", imageAddr='" + imageAddr + '\'' +
                ", ingredients=" + ingredients +
                '}';
    }
    
}
