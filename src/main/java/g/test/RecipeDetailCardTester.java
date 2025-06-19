package g.test;

import java.io.IOException;
import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RecipeDetailCardTester extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        try {
            // 检查FXML文件是否存在
            URL fxmlUrl = getClass().getResource("/g/RecipeDetailCard.fxml");
            if (fxmlUrl == null) {
                showError("FXML文件未找到", "无法找到/g/RecipeDetailCard.fxml文件");
                return;
            }
            
            FXMLLoader loader = new FXMLLoader(fxmlUrl);
            Scene scene = new Scene(loader.load(), 600, 800);
            
            primaryStage.setTitle("Recipe Detail Card Tester");
            primaryStage.setScene(scene);
            primaryStage.show();
            
            // 获取Controller并进行测试
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
        // 如果FXML加载失败，显示一个简单的错误界面
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
