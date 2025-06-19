package g;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ComponentTester extends Application {
    
    @Override
    public void start(Stage primaryStage) throws IOException {
        // 可以通过程序参数指定要测试的FXML文件
        String fxmlFile = getParameters().getRaw().isEmpty() ? 
            "/g/RecipeDetailCard.fxml" : getParameters().getRaw().get(0);
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
        Scene scene = new Scene(loader.load());
        
        primaryStage.setTitle("Component Tester - " + fxmlFile);
        primaryStage.setScene(scene);
        primaryStage.show();
        
        // 打印Controller信息
        Object controller = loader.getController();
        System.out.println("Controller: " + controller.getClass().getName());
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
