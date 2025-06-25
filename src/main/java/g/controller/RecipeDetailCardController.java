package g.controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import g.dto.RecipeDetailResponse;
import g.model.Ingredient;
import g.model.Recipe;
import g.service.RecipeService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class RecipeDetailCardController implements Initializable {

    private final RecipeService recipeService;
    private RecipeDetailResponse recipeDetailResponse;
    private int recipeId;

    public RecipeDetailCardController() {
        // JavaFX requires a no-arg constructor
        this.recipeService = new RecipeService();
        this.recipeDetailResponse = null;
    }
    @FXML
    private UpdateViewController updateViewController;
    @FXML
    private Label recipeIdLabel;
    @FXML
    private Label title;
    @FXML
    private Label prepTime;
    @FXML
    private Label cookTime;
    @FXML
    private Label serve;
    @FXML
    private Label ingredients;
    @FXML
    private Label instructions;
    @FXML
    private Label imgAddr;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        // Initialize the controller
        System.out.println("RecipeDetailCardController initialized");
    }

    @FXML
    public void loadRecipeData(int recipeId) {

        this.recipeId = recipeId;
        RecipeDetailResponse recipeDetail = recipeService.getRecipeById(recipeId);

        Recipe recipe = recipeDetail.getRecipe();
        List<Ingredient> ingredients = recipeDetail.getIngredients();

        if (ingredients != null) {
            for (Ingredient ing : ingredients) {
                ing.setRecipeId(recipe.getRecipeId());
            }
        }
        this.recipeIdLabel.setText(String.valueOf(recipeId));
        this.title.setText(recipe.getTitle());
        this.prepTime.setText(String.valueOf(recipe.getPrepTime()));
        this.cookTime.setText(String.valueOf(recipe.getCookTime()));
        this.serve.setText(String.valueOf(recipe.getServe()));
        this.ingredients.setText(ingredients.toString());
        this.instructions.setText(recipe.getInstruction());
        this.imgAddr.setText(recipe.getImgAddr());
    }
    @FXML
    public void onRecipeDeleteClicked(ActionEvent event) {
        // 弹出确认对话框
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("确认删除");
        alert.setHeaderText(null);
        alert.setContentText("确定要删除该菜谱吗？");

        alert.showAndWait().ifPresent(result -> {
            if (result == ButtonType.OK) {

                recipeService.deleteRecipe(recipeId);
                System.out.println("Recipe with ID " + recipeId + " deleted successfully.");

                if (callback != null) {
                    callback.onRecipeDeleted(recipeId);
                }
            }
        });
    }

    @FXML
    public void onRecipeUpdateClicked(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/g/UpdateView.fxml"));
            Parent root = loader.load();
            this.updateViewController = loader.getController();

            // 设置数据
            RecipeDetailResponse recipeDetail = recipeService.getRecipeById(recipeId);
            updateViewController.setPreviousData(recipeDetail);
            updateViewController.setUpdateCallback(() -> {
            loadRecipeData(recipeId); // ✅ 局部刷新

            if (callback != null) {
                callback.onRecipeUpdated(recipeId); // ✅ 通知爷爷 HomeController 刷新列表
            }
        });

            Stage stage = new Stage();
            stage.setTitle("Update Recipe");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("无法加载 UpdateView.fxml 页面");
        }
    }

    @FXML
    public void onBackClicked(ActionEvent event) {
        if (callback != null) {
            callback.onBack();  
        }
    }

    @FXML
    public void onRecipeCategorizeClicked(ActionEvent event) {
        System.out.println("Recipe categorize button clicked for recipe ID: " + recipeId);
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/g/AddRecipeToCategory.fxml"));
            Parent root = loader.load();
            AddRecipeToCategoryController controller = loader.getController();
            controller.setRecipeId(recipeId);

            Stage stage = new Stage();
            stage.setTitle("Add Recipe to Category");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("无法加载 AddRecipeToCategory.fxml 页面");
        }
    }

    // Callback interface for actions
    public interface DetailCallback {

        void onRecipeDeleted(int recipeId);

        void onRecipeUpdated(int recipeId);

        void onBack();
    }

    private DetailCallback callback;

    public void setCallback(DetailCallback callback) {
        
        this.callback = callback;
    }

}
