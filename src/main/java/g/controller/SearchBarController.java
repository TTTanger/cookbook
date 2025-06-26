package g.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class SearchBarController {

    @FXML
    private TextField searchField;

    /**
     * 初始化方法，可以设置按钮点击和回车事件
     */
    @FXML
    public void initialize() {
        System.out.println("Initializing SearchBarController");
    }

    /**
     * 执行搜索逻辑
     */
    @FXML
    public void performSearch(ActionEvent event) {
        String keyword = searchField.getText();
        if (callback != null) {
            callback.onSearch(keyword == null ? "" : keyword.trim());
        }
    }

    @FXML
    public void clearSearch(ActionEvent event) {
        searchField.clear();
    }

    // 回调接口
    public interface ActionCallback {

        void onSearch(String keyword);
    }

    private ActionCallback callback;

    public void setCallback(ActionCallback callback) {
        this.callback = callback;
    }

}
