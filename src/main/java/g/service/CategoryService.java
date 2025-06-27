package g.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import g.dao.CategoryDAO;
import g.dao.CategoryRecipeDAO;
import g.dao.RecipeDAO;
import g.dto.CategoryResponse;
import g.dto.RecipeSummaryResponse;
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

    // public boolean addRecipeToCategory(List<Integer> categoryIds, int recipeId) {
    //     boolean ifAdd = categoryRecipeDAO.addToCategory(categoryIds, recipeId);
    //     if (ifAdd) {
    //         System.out.println("Recipe added to all categories.");
    //         return true;
    //     } else {
    //         System.out.println("Failed to add recipe to categories.");
    //         return false;
    //     }
    // }

    public boolean updateRecipeToCategory(List<Integer> categoryIds, int recipeId) {
        // 先查一下当前有哪些分类
        List<Integer> currentCategoryIds = categoryRecipeDAO.getCategoryIdsByRecipeId(recipeId);

        // 如果当前本来就没有分类且也不需要添加，直接返回true
        if ((currentCategoryIds == null || currentCategoryIds.isEmpty()) && (categoryIds == null || categoryIds.isEmpty())) {
            System.out.println("No categories to update for recipe " + recipeId);
            return true;
        }

        // 只有当前有分类时才清空
        boolean ifClear = true;
        if (currentCategoryIds != null && !currentCategoryIds.isEmpty()) {
            ifClear = categoryRecipeDAO.clearCategoriesForRecipe(recipeId);
            if (!ifClear) {
                System.out.println("Failed to clear old category relations.");
                return false;
            }
        }

        // 需要添加新分类时才添加
        if (categoryIds != null && !categoryIds.isEmpty()) {
            boolean ifAdd = categoryRecipeDAO.addToCategory(categoryIds, recipeId);
            if (ifAdd) {
                System.out.println("Recipe added to all categories.");
                return true;
            } else {
                System.out.println("Failed to add recipe to categories.");
                return false;
            }
        }

        // 如果只是清空分类，也算成功
        return ifClear;
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

    public boolean deleteCategory(int categoryId) {

        boolean ifClearRelations = categoryRecipeDAO.removeAllRecipesFromCategory(categoryId);
        if (!ifClearRelations) {
            System.out.println("Failed to clear recipe relations for category '" + categoryId + "'.");
            return false;
        }

        boolean ifDelete = categoryDAO.deleteCategory(categoryId);
        if (ifDelete) {
            System.out.println("Category '" + categoryId + "' deleted successfully.");
            return true;
        } else {
            System.out.println("Failed to delete category '" + categoryId + "'.");
            return false;
        }
    }

    public boolean updateCategory(int categoryId, String CategoryName) {

        boolean ifUpdate = categoryDAO.updateCategory(categoryId, CategoryName);
        if (ifUpdate) {
            System.out.println("Category updated successfully.");
            return true;
        } else {
            System.out.println("Failed to update category.");
            return false;
        }
    }

    public List<CategoryResponse> getAllCategories() {

        List<CategoryResponse> responses = new ArrayList<>();

        try {

            List<Category> categoryList = categoryDAO.getAllCategories();

            if (categoryList != null) {
                for (Category category : categoryList) {
                    CategoryResponse response = new CategoryResponse(
                        category.getCategoryId(),
                        category.getCategoryName()
                    );
                    responses.add(response);
                }
            }

            return responses;

        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
   
    public List<RecipeSummaryResponse> getRecipeSummaryByCategoryId(int categoryId) {

        List<RecipeSummaryResponse> responses = new ArrayList<>();

        try {

            List<Integer> recipeIds = categoryRecipeDAO.getRecipeIdsByCategoryId(categoryId);

            for (int recipeId : recipeIds) {
                Recipe recipe = recipeDAO.getRecipeSummaryById(recipeId);

                if (recipe != null) {
                    RecipeSummaryResponse response = new RecipeSummaryResponse(
                        recipe.getRecipeId(),
                        recipe.getTitle(),
                        recipe.getImgAddr()
                    );
                    responses.add(response);
                }
            }

            return responses;

        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public List<CategoryResponse> getCategoriesByRecipeId(int recipeId) {

        List<CategoryResponse> responses = new ArrayList<>();

        try {

            List<Integer> categoryIds = categoryRecipeDAO.getCategoryIdsByRecipeId(recipeId);

            List<Category> categories = categoryDAO.getCategoriesByIds(categoryIds);

            for (Category category : categories) {
                responses.add(new CategoryResponse(
                category.getCategoryId(),
                category.getCategoryName()
                ));
            }

            return responses;

        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

}


