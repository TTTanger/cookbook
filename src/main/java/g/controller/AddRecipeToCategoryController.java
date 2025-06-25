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
    private List<CategoryResponse> originalCategories = List.of(); // 记录初始已选分类

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // 分类加载延后到 setRecipeId
    }

    // 设置当前操作的食谱ID（外部打开窗口时调用）
    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
        System.out.println("设置当前操作的食谱ID: " + recipeId);

        // 1. 查询所有分类
        List<CategoryResponse> categories = categoryService.getAllCategories();
        // 2. 查询该食谱已属于哪些分类
        originalCategories = categoryService.getCategoriesByRecipeId(recipeId);

        // 提取已选分类的id集合
        List<Integer> originalCategoryIds = originalCategories.stream()
                .map(CategoryResponse::getCategoryId)
                .collect(Collectors.toList());

        categoryCheckBoxContainer.getChildren().clear();
        for (CategoryResponse category : categories) {
            CheckBox checkBox = new CheckBox(category.getCategoryName());
            checkBox.setUserData(category.getCategoryId());
            // 如果已属于该分类，则勾选
            if (originalCategoryIds.contains(category.getCategoryId())) {
                checkBox.setSelected(true);
            }
            categoryCheckBoxContainer.getChildren().add(checkBox);
        }
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

        List<Integer> originalCategoryIds = originalCategories.stream()
                .map(CategoryResponse::getCategoryId)
                .collect(Collectors.toList());

        // 判断是否有变化
        boolean changed = !(selectedCategoryIds.size() == originalCategoryIds.size()
                && selectedCategoryIds.containsAll(originalCategoryIds)
                && originalCategoryIds.containsAll(selectedCategoryIds));

        if (!changed) {
            // 没有变化，直接关闭窗口
            closeWindow();
            return;
        }

        // 有变化才更新
        boolean updateSuccess = categoryService.updateRecipeToCategory(selectedCategoryIds, recipeId);
        if (!updateSuccess) {
            showAlert("失败", "更新分类失败！");
            return;
        }

        showAlert("成功", "分类已更新！");
        closeWindow();
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