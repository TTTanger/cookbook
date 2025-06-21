package g.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class HomeController implements Initializable {

    @FXML
    private ListViewController listViewController;

    @FXML
    private RecipeDetailCardController recipeDetailCardController;

    @FXML
    private Button createButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listViewController.setOnItemSelected(recipeDetailCardController::renderRecipeData);
    }

    @FXML
    public void onCreateClicked() {
        System.out.println("Create button clicked");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/g/CreateView.fxml"));
            Parent root = loader.load();

            // 获取创建页面的 controller
            CreateViewController controller = loader.getController();

            // 设置回调函数（假设你已经把 ListViewController 作为成员变量 listViewController）
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
}
