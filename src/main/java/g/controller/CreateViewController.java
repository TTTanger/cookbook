package g.controller;

import java.util.ArrayList;
import java.util.List;

import g.dto.RecipeDetailRequest;
import g.model.Ingredient;
import g.model.Recipe;
import g.service.RecipeService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CreateViewController {

    private final RecipeService recipeService;

    @FXML
    private TextField titleField;
    @FXML
    private TextField prepTimeField;
    @FXML
    private TextField cookTimeField;
    @FXML
    private TextField serveField;
    @FXML
    private TextArea instructionField;
    @FXML
    private VBox ingredientContainer;  // Dynamic container for ingredient entries
    @FXML
    private Button submitButton;
    @FXML
    private Button uploadButton;

    public CreateViewController() {
        this.recipeService = new RecipeService();
    }

    @FXML
    public void initialize() {
        addIngredient(); 
        updateRemoveButtons(); 
        System.out.println("CreateViewController initialized");
    }

    @FXML
    public void handleCreateRecipe() {
        String title = titleField.getText();
        String prepTime = prepTimeField.getText();
        String cookTime = cookTimeField.getText();
        String serve = serveField.getText();
        String instruction = instructionField.getText();
        String imgAddr = "default_image.jpg";
        List<Ingredient> ingredients = new ArrayList<>();

        for (var node : ingredientContainer.getChildren()) {
            if (node instanceof HBox hbox) {
                List<javafx.scene.Node> fields = hbox.getChildren();

                TextField nameField = null;
                TextField quantityField = null;
                TextField unitField = null;

                int fieldCount = 0;
                for (javafx.scene.Node child : fields) {
                    if (child instanceof TextField tf) {
                        if (fieldCount == 0) {
                            nameField = tf; 
                        }else if (fieldCount == 1) {
                            quantityField = tf; 
                        }else if (fieldCount == 2) {
                            unitField = tf;
                        }
                        fieldCount++;
                    }
                }

                if (nameField != null && quantityField != null && unitField != null) {
                    String name = nameField.getText();
                    String quantityStr = quantityField.getText();
                    String unit = unitField.getText();

                    try {
                        int quantity = Integer.parseInt(quantityStr);
                        Ingredient ing = new Ingredient();
                        ing.setIngredientName(name);
                        ing.setIngredientAmount(quantity);
                        ing.setIngredientUnit(unit);
                        ingredients.add(ing);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid quantity: " + quantityStr);
                    }
                }
            }
        }

        Recipe recipe = new Recipe();
        recipe.setTitle(title);
        recipe.setPrepTime(Integer.parseInt(prepTime));
        recipe.setCookTime(Integer.parseInt(cookTime));
        recipe.setServe(Integer.parseInt(serve));
        recipe.setInstruction(instruction);
        recipe.setImgAddr(imgAddr);

        System.out.println("Creating recipe with title: " + title);
        RecipeDetailRequest request = new RecipeDetailRequest();

        request.setRecipe(recipe);
        request.setIngredients(ingredients);

        boolean success = recipeService.createRecipe(request);
        if (success) {
            System.out.println("Recipe created successfully!");
            if (onCreateSuccess != null) {
                onCreateSuccess.run();
            }

            ((Stage) submitButton.getScene().getWindow()).close();
        } else {
            System.out.println("Failed to create recipe.");
        }
    }

    @FXML
    public void uploadClicked() {
        System.out.println("Upload button clicked");
    }

    @FXML
    private void addIngredient() {
        HBox entry = new HBox(10);

        TextField nameField = new TextField();
        nameField.setPromptText("Name");

        TextField quantityField = new TextField();
        quantityField.setPromptText("Amount");

        TextField unitField = new TextField();
        unitField.setPromptText("Unit");

        Button addButton = new Button("+");
        addButton.setOnAction(e -> addIngredient());

        Button removeBtn = new Button("-");
        removeBtn.setOnAction(e -> {
            if (ingredientContainer.getChildren().size() > 1) {
                ingredientContainer.getChildren().remove(entry);
                updateRemoveButtons(); 
            }
        });

        entry.getChildren().addAll(nameField, new Label(":"), quantityField, unitField, removeBtn, addButton);
        ingredientContainer.getChildren().add(entry);

        updateRemoveButtons(); // 每次添加后更新减号按钮状态
    }

    /**
     * 更新所有ingredient行的减号按钮状态：只有多于一行时才可用，否则禁用
     */
    private void updateRemoveButtons() {
        int count = ingredientContainer.getChildren().size();
        for (var node : ingredientContainer.getChildren()) {
            if (node instanceof HBox hbox) {
                for (var child : hbox.getChildren()) {
                    if (child instanceof Button btn && "-".equals(btn.getText())) {
                        btn.setDisable(count <= 1);
                    }
                }
            }
        }
    }

    // Callback function
    private Runnable onCreateSuccess;

    public void setOnCreateSuccess(Runnable callback) {
        this.onCreateSuccess = callback;
    }
}
