package g.controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import g.dto.CalculateResponse;
import g.dto.RecipeDetailResponse;
import g.model.Ingredient;
import g.model.Recipe;
import g.service.CalculateService;
import g.service.RecipeService;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class RecipeDetailCardController implements Initializable {

    private final RecipeService recipeService;
    private final CalculateService calculateService;
    private RecipeDetailResponse recipeDetailResponse;
    private int recipeId;

    public RecipeDetailCardController() {
        // JavaFX requires a no-arg constructor
        this.recipeService = new RecipeService();
        this.calculateService = new CalculateService();
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
    private TextArea ingredients; 
    @FXML
    private Label instructions;
    @FXML
    private Label imgAddr;
    @FXML
    private Spinner<Integer> serveSpinner;

    @FXML
    private TableView<Ingredient> ingredientsTable;
    @FXML
    private TableColumn<Ingredient, String> ingredientNameCol;
    @FXML
    private TableColumn<Ingredient, Integer> ingredientAmountCol;
    @FXML
    private TableColumn<Ingredient, String> ingredientUnitCol;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        // Initialize the controller
        System.out.println("RecipeDetailCardController initialized");
        if (ingredientNameCol != null) {
            ingredientNameCol.setCellValueFactory(new PropertyValueFactory<>("ingredientName"));
        }
        if (ingredientAmountCol != null) {
            ingredientAmountCol.setCellValueFactory(new PropertyValueFactory<>("ingredientAmount"));
        }
        if (ingredientUnitCol != null) {
            ingredientUnitCol.setCellValueFactory(new PropertyValueFactory<>("ingredientUnit"));
        }
    }

    @FXML
    public void loadRecipeData(int recipeId) {

        this.recipeId = recipeId;
        RecipeDetailResponse recipeDetail = recipeService.getRecipeById(recipeId);

        Recipe recipe = recipeDetail.getRecipe();
        List<Ingredient> ingredientsList = recipeDetail.getIngredients();

        if (ingredientsList != null) {
            for (Ingredient ing : ingredientsList) {
                ing.setRecipeId(recipe.getRecipeId());
            }
        }
        this.recipeIdLabel.setText(String.valueOf(recipeId));
        this.title.setText(recipe.getTitle());
        this.prepTime.setText(String.valueOf(recipe.getPrepTime()));
        this.cookTime.setText(String.valueOf(recipe.getCookTime()));

        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, recipe.getServe());
        serveSpinner.setValueFactory(valueFactory);
        serveSpinner.setEditable(true);

        int initialServings = serveSpinner.getValue();
        System.out.println("Initial servings: " + initialServings);

        updateIngredientsDisplay(recipeId, initialServings);

        serveSpinner.valueProperty().addListener((obs, oldValue, newValue) -> {
            System.out.println("Servings changed from " + oldValue + " to " + newValue);
            updateIngredientsDisplay(recipeId, newValue);
        });

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

            RecipeDetailResponse recipeDetail = recipeService.getRecipeById(recipeId);
            updateViewController.setPreviousData(recipeDetail);
            updateViewController.setUpdateCallback(() -> {
            loadRecipeData(recipeId); 

            if (callback != null) {
                callback.onRecipeUpdated(recipeId); 
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
        showEmptyMessage(); // 清空当前内容，显示“Please Select a Recipe”
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

    private void updateIngredientsDisplay(int recipeId, int serve) {
        CalculateResponse scaledIngredients = calculateService.IngredientCalculate(recipeId, serve);
        List<Ingredient> ingredientsList = scaledIngredients.getIngredients();
        if (ingredientsList != null && !ingredientsList.isEmpty()) {
            ingredientsTable.setItems(FXCollections.observableArrayList(ingredientsList));
        } else {
            ingredientsTable.setItems(FXCollections.observableArrayList());
        }
    }
    public void showEmptyMessage() {
        this.recipeIdLabel.setText("");
        this.title.setText("");
        this.prepTime.setText("");
        this.cookTime.setText("");
        this.serveSpinner.getValueFactory().setValue(1);
        this.ingredientsTable.setItems(FXCollections.observableArrayList());
        this.instructions.setText("Please Select a Recipe");
        this.imgAddr.setText("");
    }
}
