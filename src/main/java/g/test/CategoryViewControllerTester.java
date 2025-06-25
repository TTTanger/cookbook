package g.test;

import java.io.IOException;
import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CategoryViewControllerTester extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            URL fxmlUrl = getClass().getResource("/g/CategoryView.fxml");
            if (fxmlUrl == null) {
                showError("FXML文件未找到", "无法找到/g/CategoryView.fxml文件");
                return;
            }
            
            FXMLLoader loader = new FXMLLoader(fxmlUrl);
            Scene scene = new Scene(loader.load(), 600, 400);
            scene.getStylesheets().add(getClass().getResource("/g/app.css").toExternalForm());
            
            primaryStage.setTitle("Category View Tester");
            primaryStage.setScene(scene);
            primaryStage.show();

            Object controller = loader.getController();
            System.out.println("Controller loaded: " + controller);
            
        } catch (IOException e) {
            System.err.println("FXML加载失败: " + e.getMessage());
            e.printStackTrace();
            showError("FXML加载失败", e.getMessage());
        } catch (Exception e) {
            System.err.println("应用启动失败: " + e.getMessage());
            e.printStackTrace();
            showError("应用启动失败", e.getMessage());
        }
    }
    
    private void showError(String title, String message) {
        Stage errorStage = new Stage();
        VBox root = new VBox();
        root.getChildren().addAll(
            new Label("错误: " + title),
            new Label("详情: " + message)
        );
        
        Scene scene = new Scene(root, 400, 200);
        errorStage.setTitle("测试错误");
        errorStage.setScene(scene);
        errorStage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}

