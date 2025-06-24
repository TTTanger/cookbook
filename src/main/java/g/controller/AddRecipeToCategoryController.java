package g.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import g.dto.CategoryResponse;
import g.service.CategoryService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AddRecipeToCategoryController implements Initializable {

    @FXML
    private VBox categoryCheckBoxContainer;

    private final CategoryService categoryService = new CategoryService();

    private int recipeId = -1; // 当前操作的食谱ID

    // 初始化时动态加载所有分类
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<CategoryResponse> categories = categoryService.getAllCategories();
        for (CategoryResponse category : categories) {
            CheckBox checkBox = new CheckBox(category.getCategoryName());
            checkBox.setUserData(category.getCategoryId());
            categoryCheckBoxContainer.getChildren().add(checkBox);
        }
    }

    // 设置当前操作的食谱ID（外部打开窗口时调用）
    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
        System.out.println("设置当前操作的食谱ID: " + recipeId);
    }

    @FXML
    private void onConfirm() {
        List<Integer> selectedCategoryIds = categoryCheckBoxContainer.getChildren().stream()
                .filter(node -> node instanceof CheckBox cb && cb.isSelected())
                .map(node -> (Integer) ((CheckBox) node).getUserData())
                .collect(Collectors.toList());

        if (recipeId == -1) {
            showAlert("错误", "未指定食谱ID！");
            return;
        }
        if (selectedCategoryIds.isEmpty()) {
            showAlert("提示", "请至少选择一个分类！");
            return;
        }

        boolean success = categoryService.addRecipeToCategory(selectedCategoryIds, recipeId);
        if (success) {
            showAlert("成功", "已成功添加到分类！");
            closeWindow();
        } else {
            showAlert("失败", "添加到分类失败！");
        }
    }

    @FXML
    private void onCancel() {
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) categoryCheckBoxContainer.getScene().getWindow();
        stage.close();
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}