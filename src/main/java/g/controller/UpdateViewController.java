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
import g.dto.RecipeDetailResponse;
import g.model.Ingredient;
import g.model.Recipe;
import g.service.RecipeService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;

public class UpdateViewController {

    private final RecipeService recipeService;

    @FXML
    private RecipeDetailResponse previousData;

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
    private VBox ingredientContainer;
    @FXML
    private Button submitButton;
    @FXML
    private Button uploadButton;
    private final List<Integer> deletedPairIds = new ArrayList<>();
    private String uploadedImgPath = null;

    /** ImageView for previewing the uploaded image */
    @FXML
    private ImageView imgPreview;

    /** Label for image preview hint */
    @FXML
    private Label imgHint;

    public UpdateViewController() {
        this.recipeService = new RecipeService();
    }

    @FXML
    public void initialize() {
        System.out.println("UpdateViewController initialized");
        if (ingredientContainer.getChildren().isEmpty()) {
            addIngredient();
        }
        if (uploadedImgPath != null && !uploadedImgPath.isEmpty()) {
            imgPreview.setImage(new Image(new File(uploadedImgPath).toURI().toString()));
            imgHint.setVisible(false);
        } else {
            imgPreview.setImage(null);
            imgHint.setVisible(true);
        }
    }

    @FXML
    public void loadPreviousData(String title, int prepTime, int cookTime, int serve,
            List<Ingredient> ingredients, String instructions, String imgAddr) {
        ingredientContainer.getChildren().clear();

        this.titleField.setText(title);
        this.prepTimeField.setText(String.valueOf(prepTime));
        this.cookTimeField.setText(String.valueOf(cookTime));
        this.serveField.setText(String.valueOf(serve));

        if (ingredients == null || ingredients.isEmpty()) {
            addIngredient();
        } else {
            for (Ingredient ingredient : ingredients) {
                HBox entry = new HBox(10);
                entry.setUserData(ingredient.getPairId());
                System.out.println("Pair ID: " + ingredient.getPairId());

                TextField nameField = new TextField(ingredient.getIngredientName());
                nameField.setPromptText("Name");

                TextField quantityField = new TextField(String.valueOf(ingredient.getIngredientAmount()));
                quantityField.setPromptText("Amount");

                TextField unitField = new TextField(ingredient.getIngredientUnit());
                unitField.setPromptText("Unit");

                Button addButton = new Button("+");
                addButton.setOnAction(e -> addIngredient());

                Button removeBtn = new Button("-");
                removeBtn.setOnAction(e -> {
                    if (ingredientContainer.getChildren().size() > 1) {
                        Integer ingId = (Integer) entry.getUserData();
                        if (ingId != null) {
                            deletedPairIds.add(ingId);
                            System.out.println("Deleted ingredient ID: " + ingId);
                        }
                        ingredientContainer.getChildren().remove(entry);
                        updateRemoveButtons();
                    }
                });

                entry.getChildren().addAll(nameField, new Label(":"), quantityField, unitField, removeBtn, addButton);
                ingredientContainer.getChildren().add(entry);
            }
        }
        this.instructionField.setText(instructions);
        updateRemoveButtons();
    }

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
                Integer ingId = (Integer) entry.getUserData();
                if (ingId != null) {
                    deletedPairIds.add(ingId);
                    System.out.println("Deleted ingredient ID: " + ingId);
                }
                ingredientContainer.getChildren().remove(entry);
                updateRemoveButtons();
            }
        });

        entry.getChildren().addAll(nameField, new Label(":"), quantityField, unitField, removeBtn, addButton);
        ingredientContainer.getChildren().add(entry);

        updateRemoveButtons(); 
    }
    
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

    public void setPreviousData(RecipeDetailResponse previousData) {
        this.previousData = previousData;
        Recipe previousRecipe = previousData.getRecipe();
        System.out.println("Setting previous data for recipe: " + previousRecipe.getTitle());
        loadPreviousData(
                previousRecipe.getTitle(),
                previousRecipe.getPrepTime(),
                previousRecipe.getCookTime(),
                previousRecipe.getServe(),
                previousData.getIngredients(),
                previousRecipe.getInstruction(),
                previousRecipe.getImgAddr()
        );
        System.out.println("Previous data ingredients:" + previousData.getIngredients());
    }

    public void handleUpdateRecipe() {
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Confirm Update");
        confirmAlert.setHeaderText(null);
        confirmAlert.setContentText("Are you sure you want to save the changes to the recipe?");
        var result = confirmAlert.showAndWait();
        if (result.isEmpty() || result.get() != ButtonType.OK) {
            return; 
        }

        String title = titleField.getText();
        String prepTime = prepTimeField.getText();
        String cookTime = cookTimeField.getText();
        String serve = serveField.getText();
        String instruction = instructionField.getText();
        String imgAddr = uploadedImgPath != null ? uploadedImgPath : "https://i.imgur.com/3dVB5B9.jpg";

        // 校验主表单
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
            }
        }

        Recipe recipe = new Recipe();
        recipe.setRecipeId(previousData.getRecipe().getRecipeId());
        recipe.setTitle(title);
        recipe.setPrepTime(Integer.parseInt(prepTime));
        recipe.setCookTime(Integer.parseInt(cookTime));
        recipe.setServe(Integer.parseInt(serve));
        recipe.setInstruction(instruction);
        recipe.setImgAddr(imgAddr);

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
                        } else if (fieldCount == 1) {
                            quantityField = tf;
                        } else if (fieldCount == 2) {
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
                        ing.setRecipeId(recipe.getRecipeId());
                        ing.setIngredientName(name);
                        ing.setIngredientAmount(quantity);
                        ing.setIngredientUnit(unit);
                        Object pairId = hbox.getUserData();
                        if (pairId instanceof Integer) {
                            ing.setPairId((Integer) pairId);
                        }
                        ingredients.add(ing);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid quantity: " + quantityStr);
                    }
                }
            }
        }

        RecipeDetailRequest request = new RecipeDetailRequest(recipe, ingredients, deletedPairIds);
        boolean success = recipeService.updateRecipe(request);
        if (success) {
            Alert info = new Alert(Alert.AlertType.INFORMATION, "Recipe updated successfully!", ButtonType.OK);
            info.showAndWait();

            if (updateCallback != null) {
                updateCallback.onUpdateSuccess();
            }

            Stage stage = (Stage) submitButton.getScene().getWindow();
            stage.close();
        }
    }

    // Set callback
    public interface UpdateCallback {

        void onUpdateSuccess();
    }
    private UpdateCallback updateCallback;

    public void setUpdateCallback(UpdateCallback callback) {
        this.updateCallback = callback;
    }
}
