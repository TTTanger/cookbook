package g.service;

import java.util.ArrayList;
import java.util.List;

import g.dao.CategoryDAO;
import g.dao.CategoryRecipeDAO;
import g.dao.RecipeDAO;
import g.model.Category;
import g.model.Recipe;

public class CategoryService {
    private final CategoryRecipeDAO categoryRecipeDAO;
    private final RecipeDAO recipeDAO; 
    private final CategoryDAO categoryDAO;

    public CategoryService() {
        categoryDAO = new CategoryDAO();
        recipeDAO = new RecipeDAO();
        categoryRecipeDAO = new CategoryRecipeDAO();
    }

    public boolean addToCategory(int categoryId, int recipeId) {
        // Logic to add a recipe to a category
        // This would typically involve calling a method from recipeDAO
        boolean ifAdd = categoryRecipeDAO.addToCategory(categoryId, recipeId);
        if (ifAdd) {
            System.out.println("Recipe added to category.");
            return true;
        }
        else {
            System.out.println("Failed to add recipe to category.");
            return false;
        }
    }

    public boolean removeFromCategory(int categoryId, int recipeId) {
        boolean ifRemove = categoryRecipeDAO.removeFromCategory(categoryId, recipeId);
        if (ifRemove) {
            System.out.println("Recipe removed from category.");
            return true;
        } else {
            System.out.println("Failed to remove recipe from category.");
            return false;
        }
    }

    public boolean createCategory(String categoryName) {
        boolean ifCreate = categoryDAO.createCategory(categoryName);
        if (ifCreate) {
            System.out.println("Category '" + categoryName + "' created successfully.");
            return true;
        } else {
            System.out.println("Failed to create category '" + categoryName + "'.");
            return false;
        }
    }

    public boolean deleteCategory(String categoryName) {
        boolean ifDelete = categoryDAO.deleteCategory(categoryName);
        if (ifDelete) {
            System.out.println("Category '" + categoryName + "' deleted successfully.");
            return true;
        } else {
            System.out.println("Failed to delete category '" + categoryName + "'.");
            return false;
        }
    }

    public boolean updateCategory(int categoryId, String newCategoryName) {
        boolean ifUpdate = categoryDAO.updateCategory(categoryId, newCategoryName);
        if (ifUpdate) {
            System.out.println("Category updated successfully.");
            return true;
        } else {
            System.out.println("Failed to update category.");
            return false;
        }
    }

    public Category getCategoryById(int categoryId) {
        Category category = categoryDAO.getCategoryById(categoryId);
        if (category != null) {
            System.out.println("Category found: " + category.getCategoryName());
            return category;
        } else {
            System.out.println("Category not found.");
            return null;
        }
    }

    public Category getCategoryByName(String categoryName) {
        Category category = categoryDAO.getCategoryByName(categoryName);
        if (category != null) {
            System.out.println("Category found: " + category.getCategoryName());
            return category;
        } else {
            System.out.println("Category not found.");
            return null;
        }
    }

    public Category getCategoryWithRecipesByCategoryId(int categoryId) {
        List<Integer> recipeIds = categoryRecipeDAO.getRecipeIdsByCategoryId(categoryId);
        String categoryName = categoryDAO.getCategoryById(categoryId).getCategoryName(); 
        ArrayList<Recipe> recipes = new ArrayList<>();
        for (int recipeId : recipeIds) {
            Recipe recipe = recipeDAO.getRecipeById(recipeId);
            recipes.add(recipe);
        }
        Category categoryWithRecipes = new Category(categoryId, categoryName, recipes);
        System.out.println("Category: " + categoryWithRecipes);
        return categoryWithRecipes;
    }

    public Category getCategoryWithRecipesByCategoryName(String categoryName) {
        Category category = categoryDAO.getCategoryByName(categoryName);
        int categoryId = category.getCategoryId();
        List<Integer> recipeIds = categoryRecipeDAO.getRecipeIdsByCategoryId(categoryId);
        ArrayList<Recipe> recipes = new ArrayList<>();
        for (int recipeId : recipeIds) {
            Recipe recipe = recipeDAO.getRecipeById(recipeId);
            recipes.add(recipe);
        }
        Category categoryWithRecipes = new Category(categoryId, categoryName, recipes);
        System.out.println("Category ID: " + categoryWithRecipes.getCategoryId());
        return categoryWithRecipes;
    }

    public ArrayList<Category> getAllCategories() {
        ArrayList<Category> categories = categoryDAO.getAllCategories();
        return categories;
    }
}
