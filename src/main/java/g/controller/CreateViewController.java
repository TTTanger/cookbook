package g.controller;

import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;

import g.dto.RecipeDetailRequest;
import g.model.Ingredient;
import g.model.Recipe;
import g.service.RecipeService;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;

/**
 * Controller for the create recipe view. Handles the creation of new recipes and their ingredients.
 *
 * @author Junzhe Luo
 */
public class CreateViewController {

    /** Service for recipe operations */
    private final RecipeService recipeService;
    /** TextField for recipe title */
    @FXML
    private TextField titleField;
    /** TextField for preparation time */
    @FXML
    private TextField prepTimeField;
    /** TextField for cooking time */
    @FXML
    private TextField cookTimeField;
    /** TextField for servings */
    @FXML
    private TextField serveField;
    /** TextArea for instructions */
    @FXML
    private TextArea instructionField;
    /** VBox for dynamic ingredient entries */
    @FXML
    private VBox ingredientContainer;
    /** Button for submitting the recipe */
    @FXML
    private Button submitButton;
    /** Button for uploading an image */
    @FXML
    private Button uploadButton;
    /** Path of the uploaded image */
    private String uploadedImgPath = null;
    /** Callback function for successful creation */
    private Runnable onCreateSuccess;
    /** ImageView for previewing the uploaded image */
    @FXML
    private ImageView imgPreview;
    /** Label for image preview hint */
    @FXML
    private Label imgHint;

    /**
     * Constructor initializes the recipe service.
     */
    public CreateViewController() {
        this.recipeService = new RecipeService();
    }

    /**
     * Initializes the controller, adds the first ingredient row, and updates remove buttons.
     */
    @FXML
    public void initialize() {
        addIngredient();
        updateRemoveButtons();
        System.out.println("CreateViewController initialized");
        if (uploadedImgPath != null && !uploadedImgPath.isEmpty()) {
            imgPreview.setImage(new Image(new File(uploadedImgPath).toURI().toString()));
            imgHint.setVisible(false);
        } else {
            imgPreview.setImage(null);
            imgHint.setVisible(true);
        }
    }

    /**
     * Handles the creation of a new recipe. Validates input and submits the recipe.
     */
    @FXML
    public void handleCreateRecipe() {
        String title = titleField.getText();
        String prepTime = prepTimeField.getText();
        String cookTime = cookTimeField.getText();
        String serve = serveField.getText();
        String instruction = instructionField.getText();
        String imgAddr = uploadedImgPath != null ? uploadedImgPath : "default_image.jpg";
        List<Ingredient> ingredients = new ArrayList<>();

        // Validate main form
        if (title == null || title.trim().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Title cannot be empty").showAndWait();
            return;
        }
        if (instruction == null || instruction.trim().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Instruction cannot be empty").showAndWait();
            return;
        }
        int serveInt, prepInt, cookInt;
        try {
            serveInt = Integer.parseInt(serve);
            if (serveInt <= 0) throw new Exception();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Servings must be a positive integer").showAndWait();
            return;
        }
        try {
            prepInt = Integer.parseInt(prepTime);
            if (prepInt < 0) throw new Exception();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Prep Time must be a non-negative integer").showAndWait();
            return;
        }
        try {
            cookInt = Integer.parseInt(cookTime);
            if (cookInt < 0) throw new Exception();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Cook Time must be a non-negative integer").showAndWait();
            return;
        }

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
                        } else if (fieldCount == 1) {
                            quantityField = tf;
                        } else if (fieldCount == 2) {
                            unitField = tf;
                        }
                        fieldCount++;
                    }
                }
                if (nameField == null || nameField.getText().trim().isEmpty()) {
                    new Alert(Alert.AlertType.ERROR, "Ingredient Name cannot be empty").showAndWait();
                    return;
                }
                if (unitField == null || unitField.getText().trim().isEmpty()) {
                    new Alert(Alert.AlertType.ERROR, "Ingredient Unit cannot be empty").showAndWait();
                    return;
                }
                int quantity;
                try {
                    quantity = Integer.parseInt(quantityField.getText());
                    if (quantity <= 0) throw new Exception();
                } catch (Exception e) {
                    new Alert(Alert.AlertType.ERROR, "Ingredient Amount must be a positive integer").showAndWait();
                    return;
                }
                Ingredient ing = new Ingredient();
                ing.setIngredientName(nameField.getText());
                ing.setIngredientAmount(quantity);
                ing.setIngredientUnit(unitField.getText());
                ingredients.add(ing);
            }
        }

        Recipe recipe = new Recipe();
        recipe.setTitle(title);
        recipe.setPrepTime(prepInt);
        recipe.setCookTime(cookInt);
        recipe.setServe(serveInt);
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

    /**
     * Handles the upload button click event. Allows the user to select and upload an image.
     */
    @FXML
    public void uploadClicked() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );
        File file = fileChooser.showOpenDialog(uploadButton.getScene().getWindow());
        if (file != null) {
            try {
                String ext = file.getName().substring(file.getName().lastIndexOf('.'));
                String newName = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + ext;
                File dest = new File("imgs", newName);
                Files.copy(file.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
                uploadedImgPath = "imgs/" + newName;
                // Preview the image
                imgPreview.setImage(new Image(dest.toURI().toString()));
                imgHint.setVisible(false);
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Image uploaded successfully!", ButtonType.OK);
                alert.showAndWait();
            } catch (IOException e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR, "Image upload failed!", ButtonType.OK);
                alert.showAndWait();
            }
        }
    }

    /**
     * Adds a new ingredient row to the ingredient container.
     */
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
        updateRemoveButtons(); // Update remove button state after adding
    }

    /**
     * Updates the state of remove buttons for all ingredient rows. Only enabled if more than one row exists.
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

    /**
     * Sets the callback to be executed after successful recipe creation.
     * @param callback The callback to set
     */
    public void setOnCreateSuccess(Runnable callback) {
        this.onCreateSuccess = callback;
    }
}
