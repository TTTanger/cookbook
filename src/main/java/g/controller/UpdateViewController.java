package g.controller;

import java.util.ArrayList;
import java.util.List;

import g.dto.RecipeDetailRequest;
import g.dto.RecipeDetailResponse;
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

    public UpdateViewController() {
        this.recipeService = new RecipeService();
    }

    @FXML
    public void initialize() {
        System.out.println("UpdateViewController initialized");
        // 只在没有已有数据时添加空 ingredient 条目
        if (ingredientContainer.getChildren().isEmpty()) {
            addIngredient();
        }
    }

    @FXML
    public void loadPreviousData(String title, int prepTime, int cookTime, int serve,
            List<Ingredient> ingredients, String instructions, String imgAddr) {
        // 先清空容器，避免重复添加
        ingredientContainer.getChildren().clear();

        this.titleField.setText(title);
        this.prepTimeField.setText(String.valueOf(prepTime));
        this.cookTimeField.setText(String.valueOf(cookTime));
        this.serveField.setText(String.valueOf(serve));

        if (ingredients == null || ingredients.isEmpty()) {
            addIngredient(); // 没有已有数据时才添加空行
        } else {
            for (Ingredient ingredient : ingredients) {
                HBox entry = new HBox(10);
                TextField nameField = new TextField(ingredient.getIngredientName());
                nameField.setPromptText("Name");

                TextField quantityField = new TextField(String.valueOf(ingredient.getIngredientAmount()));
                quantityField.setPromptText("Amount");

                TextField unitField = new TextField(ingredient.getUnit());
                unitField.setPromptText("Unit");

                Button addButton = new Button("+");
                addButton.setOnAction(e -> addIngredient());

                Button removeBtn = new Button("-");
                removeBtn.setOnAction(e -> ingredientContainer.getChildren().remove(entry));

                entry.getChildren().addAll(nameField, new Label(":"), quantityField, unitField, removeBtn, addButton);
                ingredientContainer.getChildren().add(entry);
            }
        }
        this.instructionField.setText(instructions);
        updateRemoveButtons();
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
                updateRemoveButtons(); // 每次删除后更新减号按钮状态
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
    }

    public void handleUpdateRecipe() {
        String title = titleField.getText();
        String prepTime = prepTimeField.getText();
        String cookTime = cookTimeField.getText();
        String serve = serveField.getText();
        String instruction = instructionField.getText();
        String imgAddr = "https://i.imgur.com/3dVB5B9.jpg"; 
        Recipe recipe = new Recipe();
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
                        ing.setUnit(unit);
                        ingredients.add(ing);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid quantity: " + quantityStr);
                    }
                }
            }
        }
        RecipeDetailRequest request = new RecipeDetailRequest(recipe, ingredients);
        boolean success = recipeService.updateRecipe(request);
        if (success) {
            System.out.println("Recipe updated successfully!");
            // Optionally, you can close the window or reset the form
        } else {
            System.out.println("UpdateView Failed to update recipe!");
        }
    }
}
