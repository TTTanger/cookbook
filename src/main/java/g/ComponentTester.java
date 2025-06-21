package g;

import java.io.IOException;
import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ComponentTester extends Application {

    private String fxmlFileName = "/g/Home.fxml"; // 默认FXML路径
    private String windowTitle = "Component Tester";
    private double windowWidth = 600;
    private double windowHeight = 800;

    @Override
    public void start(Stage primaryStage) {
        // 只处理第一个参数：FXML文件名（无路径无后缀）
        if (!getParameters().getRaw().isEmpty()) {
            String inputFile = getParameters().getRaw().get(0);
            // 自动拼接路径和后缀
            if (!inputFile.startsWith("/")) {
                if (!inputFile.endsWith(".fxml")) {
                    fxmlFileName = "/g/" + inputFile + ".fxml";
                } else {
                    fxmlFileName = "/g/" + inputFile;
                }
            } else {
                fxmlFileName = inputFile;
            }

            // 设置窗口标题为文件名 + Tester
            String fileName = fxmlFileName.substring(fxmlFileName.lastIndexOf("/") + 1);
            windowTitle = fileName.replace(".fxml", "") + " Tester";
        }

        try {
            URL fxmlUrl = getClass().getResource(fxmlFileName);
            if (fxmlUrl == null) {
                showError("FXML文件未找到", "无法找到文件: " + fxmlFileName);
                return;
            }

            System.out.println("正在加载FXML文件: " + fxmlFileName);

            FXMLLoader loader = new FXMLLoader(fxmlUrl);
            Scene scene = new Scene(loader.load(), windowWidth, windowHeight);

            primaryStage.setTitle(windowTitle);
            primaryStage.setScene(scene);
            primaryStage.show();

            Object controller = loader.getController();
            System.out.println("Controller loaded: " + (controller != null ? controller.getClass().getName() : "null"));

            // 如果是ListViewController，设置测试回调
            if (controller != null && controller.getClass().getSimpleName().equals("ListViewController")) {
                try {
                    controller.getClass().getMethod("setOnItemSelected", java.util.function.Consumer.class)
                            .invoke(controller, (java.util.function.Consumer<Object>) selectedItem -> {
                                System.out.println("测试回调 - 选中项目: " + selectedItem);
                            });
                    System.out.println("已为ListViewController设置测试回调");
                } catch (Exception e) {
                    System.out.println("无法设置ListViewController回调: " + e.getMessage());
                }
            }

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
        VBox root = new VBox(10);
        root.setStyle("-fx-padding: 20;");
        root.getChildren().addAll(
                new Label("错误: " + title),
                new Label("详情: " + message),
                new Label(""),
                new Label("使用方法:"),
                new Label("java g.GenericTester [FXML文件名]"),
                new Label("示例: java g.GenericTester ListView")
        );

        Scene scene = new Scene(root, 500, 300);
        errorStage.setTitle("测试错误");
        errorStage.setScene(scene);
        errorStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
