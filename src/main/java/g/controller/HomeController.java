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

/**
 * Controller for the home view. Handles the main navigation and recipe management.
 *
 * @author Junzhe Luo
 */
public class HomeController implements Initializable {

    /** Service for recipe operations */
    private RecipeService recipeService;

    /** Controller for the recipe list view */
    @FXML
    private ListViewController listViewController;
    /** Controller for the recipe detail card view */
    @FXML
    private RecipeDetailCardController recipeDetailCardController;
    /** Controller for the update view */
    @FXML
    private UpdateViewController updateViewController;
    /** Controller for the search bar */
    @FXML
    private SearchBarController searchBarController;
    /** VBox for the empty pane (placeholder) */
    @FXML private VBox emptyPane;
    /** Parent node for the recipe detail card */
    @FXML private Parent recipeDetailCard;

    /**
     * Constructor initializes the recipe service.
     */
    public HomeController() {
        this.recipeService = new RecipeService();
    }

    /**
     * Initializes the controller, sets up callbacks and default view.
     * @param location The location used to resolve relative paths for the root object, or null if unknown.
     * @param resources The resources used to localize the root object, or null if not localized.
     */
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
        showEmptyPane(); // Show empty pane by default
    }

    /**
     * Handles the create recipe button click event. Opens the create recipe dialog.
     */
    @FXML
    public void onCreateClicked() {
        System.out.println("Create button clicked");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/g/CreateView.fxml"));
            Parent root = loader.load();
            CreateViewController controller = loader.getController();
            // Callback to refresh the list view after creation
            controller.setOnCreateSuccess(() -> {
                System.out.println("CreateView created successfully, refreshing ListView!");
                listViewController.refreshList();
            });
            Stage stage = new Stage();
            stage.setTitle("Create Recipe");
            stage.setScene(new Scene(root, 1000, 600));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed to load CreateView.fxml page");
        }
    }

    /**
     * Called when no recipe is selected. Shows the empty message in the detail card.
     */
    private void onNoRecipeSelected() {
        recipeDetailCardController.showEmptyMessage();
    }

    /**
     * Refreshes the recipe list.
     */
    public void refreshList() {
        listViewController.refreshList();
    }

    /**
     * Shows the empty pane and hides the recipe detail card.
     */
    public void showEmptyPane() {
        emptyPane.setVisible(true);
        emptyPane.setManaged(true);
        recipeDetailCard.setVisible(false);
        recipeDetailCard.setManaged(false);
    }

    /**
     * Shows the recipe detail card and hides the empty pane.
     */
    private void showDetailPane() {
        emptyPane.setVisible(false);
        emptyPane.setManaged(false);
        recipeDetailCard.setVisible(true);
        recipeDetailCard.setManaged(true);
    }
}
