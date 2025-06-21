package g.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import g.DTO.RecipeSummaryResponse;
import g.service.RecipeService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

public class ListViewController implements Initializable {

    private Consumer<RecipeSummaryResponse> onItemSelected;
    private RecipeService recipeService;
    @FXML
    private ListView<RecipeSummaryResponse> listView;

    public ListViewController() {
        this.recipeService = new RecipeService();
    }

    public List<RecipeSummaryResponse> fetchAllRecipeSummary() {
        System.out.println("Fetching recipe summary...");
        return recipeService.getAllRecipeSummary();
    }

    public void setOnItemSelected(Consumer<RecipeSummaryResponse> callback) {
        this.onItemSelected = callback;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("ListViewController 初始化！");
        System.out.println("ListView 元素是否为空：" + (listView == null));

        List<RecipeSummaryResponse> rawList = fetchAllRecipeSummary();
        ObservableList<RecipeSummaryResponse> observableList = FXCollections.observableArrayList(rawList);
        listView.setItems(observableList);

        listView.setOnMouseClicked(event -> {
        RecipeSummaryResponse selected = listView.getSelectionModel().getSelectedItem();
        System.out.println("点击事件触发，选中项: " + (selected != null ? selected.getTitle() : "null"));
        System.out.println("回调函数: " + (onItemSelected != null ? "已设置" : "null"));
        
        if (selected != null && onItemSelected != null) {
            System.out.println("选中：" + selected.getTitle());
            onItemSelected.accept(selected); 
        }
    });
    }
}
