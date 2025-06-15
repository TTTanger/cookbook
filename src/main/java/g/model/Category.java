package g.model;

import java.util.List;

public class Category {
    private String categoryName;
    private int categoryId;
    private List<Integer> recipeIds;

    public Category(String categoryName, int categoryId, List<Integer> recipeIds) {
        this.categoryName = categoryName;
        this.categoryId = categoryId;
        this.recipeIds = recipeIds;
    }

    public String getCategoryName() {
        return this.categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getCategoryId() {
        return this.categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public List<Integer> getRecipeIds() {
        return this.recipeIds;
    }

    public void setRecipeIds(List<Integer> recipeIds) {
        this.recipeIds = recipeIds;
    }

    @Override
    public String toString() {
        return "Category{" +
                "categoryName='" + categoryName + '\'' +
                '}';
    }
}
