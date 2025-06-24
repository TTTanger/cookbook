package g.controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import g.dto.RecipeDetailResponse;
import g.dto.RecipeSummaryResponse;
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
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class RecipeDetailCardController implements Initializable {

    private final RecipeService recipeService;

    public RecipeDetailCardController() {
        // JavaFX requires a no-arg constructor
        this.recipeService = new RecipeService();
    }
    @FXML
    private UpdateViewController updateViewController;
    @FXML
    private Button recipeUpdateButton;
    @FXML
    private Button recipeDeleteButton;
    @FXML
    private Button recipeCategorizeButton;
    @FXML
    private Label recipeId;
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
    public void loadRecipeData(int recipeId, String title, int prepTime, int cookTime, int serve,
            List<Ingredient> ingredients, String instructions,
            String imgAddr) {
        this.recipeId.setText(String.valueOf(recipeId));
        this.title.setText(title);
        this.prepTime.setText(String.valueOf(prepTime));
        this.cookTime.setText(String.valueOf(cookTime));
        this.serve.setText(String.valueOf(serve));
        this.ingredients.setText(ingredients.toString());
        this.instructions.setText(instructions);
        this.imgAddr.setText(imgAddr);
    }

    @FXML
    public void renderRecipeData(RecipeSummaryResponse recipeResponse) {
        int recipeId = recipeResponse.getRecipeId();
        RecipeDetailResponse recipeDetail = recipeService.getRecipeById(recipeId);
        
        Recipe recipe = recipeDetail.getRecipe();
        List<Ingredient> ingredients = recipeDetail.getIngredients();
        loadRecipeData(
                recipe.getRecipeId(),
                recipe.getTitle(),
                recipe.getPrepTime(),
                recipe.getCookTime(),
                recipe.getServe(),
                ingredients,
                recipe.getInstruction(),
                recipe.getImgAddr()
        );
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
                int id = Integer.parseInt(recipeId.getText());
                recipeService.deleteRecipe(id);
                System.out.println("Recipe with ID " + id + " deleted successfully.");

                if (callback != null) {
                    callback.onRecipeDeleted(id); 
                }
            }
        });
    }

    @FXML
    public void onRecipeUpdateClicked(ActionEvent event) {
        System.out.println("Recipe update button clicked");
        int id = Integer.parseInt(recipeId.getText());
        System.out.println("Recipe ID to update: " + id);
        
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/g/UpdateView.fxml"));
            Parent root = loader.load();
            updateViewController = loader.getController();
            // 设置 UpdateViewController 的 previousData
            RecipeDetailResponse recipeDetail = recipeService.getRecipeById(id);
            updateViewController.setPreviousData(recipeDetail);

            Stage stage = new Stage();
            stage.setTitle("Create Recipe");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("无法加载 UpdateView.fxml 页面");
        }

        

        if (callback != null) {
            callback.onRecipeUpdate(id);  
        }
    }

    @FXML
    public void onBackClicked(ActionEvent event) {
        if (callback != null) {
            callback.onBack();  // 通知父组件
        }
    }

    @FXML
    public void onRecipeCategorizeClicked(ActionEvent event) {
        System.out.println("Recipe categorize button clicked for recipe ID: " + recipeId.getText());
    }

    

    // Callback interface for actions
    public interface ActionCallback {

        void onRecipeDeleted(int recipeId);

        void onRecipeUpdate(int recipeId);

        void onBack();
    }

    private ActionCallback callback;

    public void setCallback(ActionCallback callback) {
        this.callback = callback;
    }

}
