package g.service;

import java.util.ArrayList;
import java.util.List;

import g.dao.CategoryDAO;
import g.dao.RecipeDAO;
import g.model.Category;
import g.model.Recipe;

public class CategoryService {
    private CategoryDAO categoryDAO;
    private RecipeDAO recipeDAO; 

    public CategoryService() {
        categoryDAO = new CategoryDAO();
        recipeDAO = new RecipeDAO();
    }

    public boolean addToCategory(String recipeTitle, String categoryName) {
        // Logic to add a recipe to a category
        // This would typically involve calling a method from recipeDAO
        boolean ifAdd = categoryDAO.addToCategory(recipeTitle, categoryName);
        if (ifAdd) {
            System.out.println("Recipe" + recipeTitle + " added to category '" + categoryName + "'.");
            return true;
        } else {
            System.out.println("Failed to add recipe" + recipeTitle + " to category '" + categoryName + "'.");
            return false;
        }
    }

    public boolean removeFromCategory(String recipeTitle, String categoryName) {
        // Logic to remove a recipe from favorites
        // This would typically involve calling a method from recipeDAO
        boolean ifRemove = categoryDAO.removeFromCategory(recipeTitle, categoryName);
        if (ifRemove) {
            System.out.println("Recipe" + recipeTitle + " removed from category '" + categoryName + "'.");
            return true;
        } else {
            System.out.println("Failed to remove recipe" + recipeTitle + " from category '" + categoryName + "'.");
            return false;
        }
    }

    public ArrayList<Category> getAllCategories() {
        if (categoryDAO == null) {
            categoryDAO = new CategoryDAO();
        }
        ArrayList<Category> categories = categoryDAO.getAllCategories();
        return categories;
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
        // Logic to delete a category
        // This would typically involve calling a method from categoryDAO
        boolean ifDelete = categoryDAO.deleteCategory(categoryName);
        if (ifDelete) {
            System.out.println("Category '" + categoryName + "' deleted successfully.");
            return true;
        } else {
            System.out.println("Failed to delete category '" + categoryName + "'.");
            return false;
        }
    }

    public ArrayList<Recipe> getRecipesByCategory(String categoryName) {
        // First, we would retrieve the recipes associated with the category
        List<String> recipeTitles = categoryDAO.getRecipesByCategory(categoryName);

        // Then, we would retrieve the actual recipes from the recipeDAO
        ArrayList<Recipe> recipes = new ArrayList<>();
        for (String title : recipeTitles) {
            Recipe recipe = recipeDAO.getRecipeByTitle(title);
            recipes.add(recipe);
        }
        // recipeDAO.getFavoritesByCategory(categoryName);
        System.out.println("Retrieving favorites for category: " + categoryName);
        return recipes;
    }

    public static void main(String[] args) {
        CategoryService categoryService = new CategoryService();
        categoryService.getAllCategories();
        categoryService.createCategory("Jiachangcai");
        categoryService.addToCategory("Hongshaorou", "Jiachangcai");
        categoryService.addToCategory("Jiaozi", "Jiachangcai");
        ArrayList<Recipe> recipes = categoryService.getRecipesByCategory("Jiachangcai");
        for (Recipe recipe : recipes) {
            System.out.println(recipe.getRecipeId() + recipe.getTitle());
        }
        // categoryService.removeFromCategory(1, "Desserts");
        // categoryService.deleteCategory("Desserts");
        categoryService.getAllCategories();
    }
}
