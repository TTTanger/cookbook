package g.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;

public class CategoryViewController implements Initializable {

    @FXML
    private VBox leftPane;
    @FXML
    private VBox rightPane;

    // 通过fx:id获取include的controller
    @FXML
    private CategoryListController categoryListController;
    @FXML
    private ListViewController listViewController;
    @FXML
    private RecipeDetailCardController recipeDetailCardController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // 监听左侧分类点击
        categoryListController.setOnItemSelected(category -> {
            // 加载该分类下的所有食谱
            listViewController.loadRecipesByCategory(category.getCategoryId());
        });

        // 监听中间食谱点击
        listViewController.setCallback(recipeId -> {
            recipeDetailCardController.loadRecipeData(recipeId);
        });
    }

    // 可选：创建分类按钮事件
    @FXML
    public void onCreateClicked() {
        // 打开创建分类窗口等
    }
}