package g.model;

import java.util.List;

public class Category {
    private int categoryId;
    private String categoryName;
    private List<Recipe> recipes;

    public Category(int categoryId, String categoryName, List<Recipe> recipes) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.recipes = recipes;
    }

    public int getCategoryId() {
        return this.categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return this.categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public List<Recipe> getRecipes() {
        return this.recipes;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }
}
