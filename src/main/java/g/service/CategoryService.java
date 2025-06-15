package g.service;

import java.util.ArrayList;

import g.dao.CategoryDAO;
import g.model.Category;

public class CategoryService {
    private CategoryDAO categoryDAO;
    // private RecipeDAO recipeDAO; 

    public boolean addToCategory(int recipeId, String categoryName) {
        // Logic to add a recipe to a category
        // This would typically involve calling a method from recipeDAO
        // recipeDAO.addRecipeToFavorites(recipeId);
        if (categoryDAO == null) {
            categoryDAO = new CategoryDAO();
        }
        boolean ifAdd = categoryDAO.addToCategory(recipeId, categoryName);
        if (ifAdd) {
            System.out.println("Recipe with ID " + recipeId + " added to category '" + categoryName + "'.");
            return true;
        } else {
            System.out.println("Failed to add recipe with ID " + recipeId + " to category '" + categoryName + "'.");
            return false;
        }
    }

    public boolean removeFromFavorite(int recipeId, String categoryName) {
        // Logic to remove a recipe from favorites
        // This would typically involve calling a method from recipeDAO
        // recipeDAO.removeRecipeFromFavorites(recipeId);
        if (categoryDAO == null) {
            categoryDAO = new CategoryDAO();
        }
        boolean ifRemove = categoryDAO.removeFromCategory(recipeId, categoryName);
        if (ifRemove) {
            System.out.println("Recipe with ID " + recipeId + " removed from category '" + categoryName + "'.");
            return true;
        } else {
            System.out.println("Failed to remove recipe with ID " + recipeId + " from category '" + categoryName + "'.");
            return false;
        }
    }

    public ArrayList<Category> getAllCategories() {
        if (categoryDAO == null) {
            categoryDAO = new CategoryDAO();
        }
        // Logic to retrieve all categories
        // This would typically involve calling a method from categoryDAO
        ArrayList<Category> categories = categoryDAO.getAllCategories();
        return categories;
    }

    public boolean createCategory(String categoryName) {
        if (categoryDAO == null) {
            categoryDAO = new CategoryDAO();
        }
        // Logic to create a new category
        // This would typically involve calling a method from categoryDAO
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
        if (categoryDAO == null) {
            categoryDAO = new CategoryDAO();
        }
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

    public void getRecipesByCategory(String categoryName) {
        if (categoryDAO == null) {
            categoryDAO = new CategoryDAO();
        }
        // First, we would retrieve the recipes associated with the category
        
        // recipeDAO.getFavoritesByCategory(categoryName);
        System.out.println("Retrieving favorites for category: " + categoryName);
    }
}
