package g.controller;

import java.net.URL;
import java.util.ResourceBundle;

import g.service.CategoryService;
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

    public CategoryViewController() {
        CategoryService categoryService = new CategoryService();
    }
    

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
        javafx.scene.control.TextInputDialog dialog = new javafx.scene.control.TextInputDialog();
        dialog.setTitle("创建新分类");
        dialog.setHeaderText(null);
        dialog.setContentText("请输入分类名称：");

        dialog.showAndWait().ifPresent(name -> {
            if (name != null && !name.trim().isEmpty()) {
                boolean success = new CategoryService().createCategory(name.trim());
                if (success) {
                    javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION, "分类创建成功！");
                    alert.showAndWait();
                    // 可选：刷新分类列表
                    categoryListController.refreshList();
                } else {
                    javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR, "分类创建失败！");
                    alert.showAndWait();
                }
            } else {
                javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.WARNING, "分类名称不能为空！");
                alert.showAndWait();
            }
        });
    }

}