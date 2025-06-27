package g.controller;

import java.io.File;
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
import javafx.scene.layout.VBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Controller for the recipe detail card view. Handles the display and interaction of recipe details.
 *
 * @author Junzhe Luo
 */
public class RecipeDetailCardController implements Initializable {

    /** Service for recipe operations */
    private final RecipeService recipeService;
    /** Service for ingredient calculation */
    private final CalculateService calculateService;
    /** The current recipe detail response */
    private RecipeDetailResponse recipeDetailResponse;
    /** The current recipe ID */
    private int recipeId;

    /** Update view controller */
    @FXML
    private UpdateViewController updateViewController;
    /** Label for the recipe title */
    @FXML
    private Label title;
    /** Label for preparation time */
    @FXML
    private Label prepTime;
    /** Label for cooking time */
    @FXML
    private Label cookTime;
    /** TextArea for ingredients (not used in table) */
    @FXML
    private TextArea ingredients;
    /** Label for instructions */
    @FXML
    private TextArea instructions;
    /** Spinner for servings */
    @FXML
    private Spinner<Integer> serveSpinner;
    /** TableView for ingredients */
    @FXML
    private TableView<Ingredient> ingredientsTable;
    /** TableColumn for ingredient name */
    @FXML
    private TableColumn<Ingredient, String> ingredientNameCol;
    /** TableColumn for ingredient amount */
    @FXML
    private TableColumn<Ingredient, Integer> ingredientAmountCol;
    /** TableColumn for ingredient unit */
    @FXML
    private TableColumn<Ingredient, String> ingredientUnitCol;
    /** Label for empty recipe message */
    @FXML
    private Label emptyRecipeLabel;
    /** VBox for detail content */
    @FXML
    private VBox detailContentBox;
    /** ImageView for recipe image */
    @FXML
    private ImageView imgView;

    /**
     * Constructor initializes the recipe and calculation services.
     */
    public RecipeDetailCardController() {
        this.recipeService = new RecipeService();
        this.calculateService = new CalculateService();
        this.recipeDetailResponse = null;
    }

    /**
     * Initializes the controller and sets up the TableView and Spinner.
     * @param location The location used to resolve relative paths for the root object, or null if unknown.
     * @param resources The resources used to localize the root object, or null if not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (serveSpinner.getValueFactory() == null) {
            serveSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1));
        }
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
        ingredientsTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        ingredientsTable.setEditable(false);
        ingredientsTable.getColumns().clear();
        ingredientsTable.getColumns().addAll(ingredientNameCol, ingredientAmountCol, ingredientUnitCol);
    }

    /**
     * Loads recipe data by recipe ID and updates the detail view.
     * @param recipeId The recipe ID
     */
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
        // Display image
        if (recipe.getImgAddr() != null && !recipe.getImgAddr().isEmpty()) {
            try {
                Image img;
                if (recipe.getImgAddr().startsWith("http")) {
                    img = new Image(recipe.getImgAddr(), true);
                } else {
                    img = new Image(new File(recipe.getImgAddr()).toURI().toString(), true);
                }
                imgView.setImage(img);
            } catch (Exception e) {
                imgView.setImage(null);
            }
        } else {
            imgView.setImage(null);
        }
        // Show detail content, hide empty message
        if (emptyRecipeLabel != null) {
            emptyRecipeLabel.setVisible(false);
            emptyRecipeLabel.setManaged(false);
        }
        if (detailContentBox != null) {
            detailContentBox.setVisible(true);
            detailContentBox.setManaged(true);
        }
    }

    /**
     * Handles the delete recipe button click event.
     * @param event The action event
     */
    @FXML
    public void onRecipeDeleteClicked(ActionEvent event) {
        // Show confirmation dialog
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Delete");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete this recipe?");
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

    /**
     * Handles the update recipe button click event.
     * @param event The action event
     */
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
            stage.setScene(new Scene(root, 1000, 600));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed to load UpdateView.fxml page");
        }
    }

    /**
     * Handles the back button click event.
     * @param event The action event
     */
    @FXML
    public void onBackClicked(ActionEvent event) {
        showEmptyMessage(); // Clear current content and show "Please Select a Recipe"
        if (callback != null) {
            callback.onBack();
        }
    }

    /**
     * Handles the categorize recipe button click event.
     * @param event The action event
     */
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
            System.err.println("Failed to load AddRecipeToCategory.fxml page");
        }
    }

    /**
     * Callback interface for detail actions.
     */
    public interface DetailCallback {
        void onRecipeDeleted(int recipeId);
        void onRecipeUpdated(int recipeId);
        void onBack();
    }
    private DetailCallback callback;

    /**
     * Sets the callback for detail actions.
     * @param callback The callback to set
     */
    public void setCallback(DetailCallback callback) {
        this.callback = callback;
    }

    /**
     * Updates the ingredients display based on servings.
     * @param recipeId The recipe ID
     * @param serve The number of servings
     */
    private void updateIngredientsDisplay(int recipeId, int serve) {
        CalculateResponse scaledIngredients = calculateService.IngredientCalculate(recipeId, serve);
        List<Ingredient> ingredientsList = scaledIngredients.getIngredients();
        if (ingredientsList != null && !ingredientsList.isEmpty()) {
            ingredientsTable.setItems(FXCollections.observableArrayList(ingredientsList));
        } else {
            ingredientsTable.setItems(FXCollections.observableArrayList());
        }
    }

    /**
     * Shows the empty message and hides the detail content.
     */
    public void showEmptyMessage() {
        if (emptyRecipeLabel != null) {
            emptyRecipeLabel.setVisible(true);
            emptyRecipeLabel.setManaged(true);
        }
        if (detailContentBox != null) {
            detailContentBox.setVisible(false);
            detailContentBox.setManaged(false);
        }
        this.title.setText("");
        this.prepTime.setText("");
        this.cookTime.setText("");
        this.serveSpinner.getValueFactory().setValue(1);
        this.ingredientsTable.setItems(FXCollections.observableArrayList());
        this.instructions.setText("Please Select a Recipe");
        imgView.setImage(null);
    }
}
