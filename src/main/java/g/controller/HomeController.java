package g.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import g.service.RecipeService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class HomeController implements Initializable {

    private RecipeService recipeService;

    @FXML
    private ListViewController listViewController;

    @FXML
    private RecipeDetailCardController recipeDetailCardController;

    @FXML
    private UpdateViewController updateViewController;

    @FXML
    private SearchBarController searchBarController;

    @FXML private VBox emptyPane;
    @FXML private Parent recipeDetailCard;

    public HomeController() {
        this.recipeService = new RecipeService();
    }

    // Initiate
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listViewController.setCallback(recipeId -> {
            recipeDetailCardController.loadRecipeData(recipeId);
            showDetailPane();
        });
        recipeDetailCardController.setCallback(new RecipeDetailCardController.DetailCallback() {
            @Override
            public void onRecipeDeleted(int recipeId) {
                listViewController.refreshList();
                showEmptyPane();
            }

            @Override
            public void onRecipeUpdated(int recipeId) {
                listViewController.refreshListAndRetainSelection(recipeId); 
            }

            @Override
            public void onBack() {
                System.out.println("Back button clicked, returning to list view.");
                showEmptyPane();
            }
        });
        searchBarController.setCallback(new SearchBarController.ActionCallback() {
            @Override
            public void onSearch(String query) {
                listViewController.search(query);
            }
        });
        showEmptyPane(); // 默认显示空白面板
    }

    @FXML
    public void onCreateClicked() {
        System.out.println("Create button clicked");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/g/CreateView.fxml"));
            Parent root = loader.load();
            CreateViewController controller = loader.getController();

            // Callback to refresh the list view after creation
            controller.setOnCreateSuccess(() -> {
                System.out.println("CreateView 创建成功，回调刷新 ListView！");
                listViewController.refreshList();
            });

            Stage stage = new Stage();
            stage.setTitle("Create Recipe");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("无法加载 CreateView.fxml 页面");
        }
    }

    // 当没有选中任何item时调用
    private void onNoRecipeSelected() {
        recipeDetailCardController.showEmptyMessage();
    }

    public void refreshList() {
        listViewController.refreshList();
    }
    // 显示空面板
    public void showEmptyPane() {
        // 设置空面板可见
        emptyPane.setVisible(true);
        // 设置空面板管理
        emptyPane.setManaged(true);
        // 设置菜谱详情卡片不可见
        recipeDetailCard.setVisible(false);
        // 设置菜谱详情卡片不管理
        recipeDetailCard.setManaged(false);
    }

    private void showDetailPane() {
        emptyPane.setVisible(false);
        emptyPane.setManaged(false);
        recipeDetailCard.setVisible(true);
        recipeDetailCard.setManaged(true);
    }

}
