// package g.service;

// import java.util.ArrayList;
// import java.util.List;

// import g.model.Category;
// import g.model.Recipe;
// import g.model.RecipeResponse;

// public class SearchService {
//     private RecipeService recipeService;
//     private CategoryService catogoryService;


//     public SearchService(RecipeService recipeService, CategoryService catogoryService) {
//         this.recipeService = recipeService;
//         this.catogoryService = catogoryService;
//     }

//     public List<RecipeResponse> searchRecipes(String title, String categoryName) {
//         if (title != null && !title.trim().isEmpty()) {
//             return recipeService.getRecipesByTitle(title);
//         } 
//         else if (categoryName != null && !categoryName.trim().isEmpty()) {
//             Category category = catogoryService.getCategoryWithRecipesByCategoryName(categoryName);
//             List<Recipe> recipes = category.getRecipes();
//             List<RecipeResponse> responses = new ArrayList<>();
//             for (Recipe recipe : recipes) {
//                 int recipeId = recipe.getRecipeId();
//                 RecipeResponse recipesWithInfos = recipeService.getRecipeById(recipeId);
//                 responses.add(recipesWithInfos);
//             }
//             return responses;
//         } 
//         else {
//             return new ArrayList<>();
//         }
//     }
// }