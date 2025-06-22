package g.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import g.dto.RecipeSummaryResponse;
import g.service.RecipeService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

public class ListViewController implements Initializable {

    private final RecipeService recipeService;

    @FXML
    private ListView<RecipeSummaryResponse> listView;

    public ListViewController() {
        this.recipeService = new RecipeService();
    }

    public List<RecipeSummaryResponse> fetchAllRecipeSummary() {
        System.out.println("Fetching recipe summary...");
        return recipeService.getAllRecipeSummary();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("ListViewController 初始化！");
        System.out.println("ListView 元素是否为空：" + (listView == null));

        List<RecipeSummaryResponse> rawList = fetchAllRecipeSummary();
        ObservableList<RecipeSummaryResponse> observableList = FXCollections.observableArrayList(rawList);
        listView.setItems(observableList);

        listView.setCellFactory(lv -> new javafx.scene.control.ListCell<RecipeSummaryResponse>() {
            @Override
            protected void updateItem(RecipeSummaryResponse item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getTitle());  // 显示你想要的字段
                }
            }
        });

        listView.setOnMouseClicked(event -> {
            RecipeSummaryResponse selected = listView.getSelectionModel().getSelectedItem();
            if (selected != null && callback != null) {
                callback.onRecipeSelected(selected);
            }
        });

    }

    public void refreshList() {
        List<RecipeSummaryResponse> rawList = fetchAllRecipeSummary();
        ObservableList<RecipeSummaryResponse> observableList = FXCollections.observableArrayList(rawList);
        listView.setItems(observableList);
        System.out.println("ListView 已刷新！");
    }

    // Callback for item selection
    public interface RecipeSelectCallback {

        void onRecipeSelected(RecipeSummaryResponse item);
    }
    private RecipeSelectCallback callback;

    public void setOnItemSelected(RecipeSelectCallback callback) {
        this.callback = callback;
    }

}
