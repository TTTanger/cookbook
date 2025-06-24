package g.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import g.dto.CategoryResponse;
import g.service.CategoryService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

public class CategoryListController implements Initializable {

    private final CategoryService categoryService;

    @FXML
    private ListView<CategoryResponse> listView;

    public CategoryListController() {
        this.categoryService = new CategoryService();
    }

    public List<CategoryResponse> fetchAllCategories() {
        System.out.println("Fetching all categories...");
        return categoryService.getAllCategories();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("CategoryViewController 初始化！");
        System.out.println("ListView 元素是否为空：" + (listView == null));

        List<CategoryResponse> rawList = fetchAllCategories();
        ObservableList<CategoryResponse> observableList = FXCollections.observableArrayList(rawList);
        listView.setItems(observableList);

        listView.setCellFactory(lv -> new javafx.scene.control.ListCell<CategoryResponse>() {
            @Override
            protected void updateItem(CategoryResponse item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getCategoryName()); 
                }
            }
        });

        listView.setOnMouseClicked(event -> {
            CategoryResponse selected = listView.getSelectionModel().getSelectedItem();
            if (selected != null && callback != null) {
                callback.onCategorySelected(selected);
            }
        });
    }

    public void refreshList() {
        List<CategoryResponse> rawList = fetchAllCategories();
        ObservableList<CategoryResponse> observableList = FXCollections.observableArrayList(rawList);
        listView.setItems(observableList);
        System.out.println("Category ListView 已刷新！");
    }

    // Callback for item selection
    public interface CategorySelectCallback {
        void onCategorySelected(CategoryResponse item);
    }
    private CategorySelectCallback callback;

    public void setOnItemSelected(CategorySelectCallback callback) {
        this.callback = callback;
    }
}