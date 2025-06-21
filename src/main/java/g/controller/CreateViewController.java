package g.controller;

import java.util.List;
import java.util.ArrayList;

import g.model.Recipe;
import g.service.RecipeService;
import g.DTO.RecipeDetailRequest;
import g.model.Ingredient;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.TextArea;
import javafx.scene.control.Button;

public class CreateViewController {
    private final RecipeService recipeService;
    
    @FXML private TextField titleField;
    @FXML private TextField prepTimeField;
    @FXML private TextField cookTimeField;
    @FXML private TextField serveField;
    @FXML private TextArea instructionField;
    @FXML private Button submitButton;
    @FXML private Button uploadButton;

    public CreateViewController() {
        // JavaFX requires a no-arg constructor
        this.recipeService = new RecipeService(); // 初始化服务层
    }
    @FXML
    public void initialize() {
        // 初始化方法，可以在这里设置默认值或进行其他初始化操作
        System.out.println("CreateViewController initialized");
    }

    // 这里可以添加更多方法来处理创建视图的逻辑
    @FXML
    public void handleCreateRecipe() {
        String title = titleField.getText();
        String prepTime = prepTimeField.getText();
        String cookTime = cookTimeField.getText();
        String serve = serveField.getText();
        String instruction = instructionField.getText();
        String imgAddr = "default_image.jpg"; 
        List<Ingredient> ingredients = new ArrayList<>(); 
        Ingredient testIngredient = new Ingredient();
        testIngredient.setIngredientName("Test Ingredient");
        testIngredient.setIngredientAmount(1);
        testIngredient.setUnit("unit");
        ingredients.add(testIngredient); 
        Recipe recipe = new Recipe();
        recipe.setTitle(title);
        recipe.setPrepTime(Integer.parseInt(prepTime));
        recipe.setCookTime(Integer.parseInt(cookTime));
        recipe.setServe(Integer.parseInt(serve));
        recipe.setInstruction(instruction);
        recipe.setImgAddr(imgAddr);
        
        System.out.println("Creating recipe with title: " + title);
        RecipeDetailRequest request = new RecipeDetailRequest(recipe, ingredients); 

        boolean success = recipeService.createRecipe(request);
        if (success) {
            System.out.println("Recipe created successfully!");
            if (onCreateSuccess != null) {
            onCreateSuccess.run();
        }

        // 关闭窗口
        ((Stage) submitButton.getScene().getWindow()).close();
        } else {
            System.out.println("Failed to create recipe.");
        }
    }

    @FXML
    public void uploadClicked() {
        // 处理上传图片的逻辑
        System.out.println("Upload button clicked");
        // 这里可以添加文件选择对话框等逻辑来选择图片文件
    }

    private Runnable onCreateSuccess;

    public void setOnCreateSuccess(Runnable callback) {
        this.onCreateSuccess = callback;
    }
}
