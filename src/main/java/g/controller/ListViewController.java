package g.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import g.dto.RecipeSummaryResponse;
import g.service.CategoryService;
import g.service.RecipeService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

/**
 * Controller for the recipe list view. Handles the display and selection of recipes.
 *
 * @author Junzhe Luo
 */
public class ListViewController implements Initializable {

    /** Service for recipe operations */
    private final RecipeService recipeService;
    /** Service for category operations */
    private final CategoryService categoryService;

    /** ListView for displaying recipes */
    @FXML
    private ListView<RecipeSummaryResponse> listView;

    /**
     * Constructor initializes the recipe and category services.
     */
    public ListViewController() {
        this.recipeService = new RecipeService();
        this.categoryService = new CategoryService();
    }

    /**
     * Fetches all recipe summaries from the service.
     * @return List of RecipeSummaryResponse objects
     */
    public List<RecipeSummaryResponse> fetchAllRecipeSummary() {
        System.out.println("Fetching recipe summary...");
        return recipeService.getAllRecipeSummary();
    }

    /**
     * Initializes the controller and sets up the ListView.
     * @param location The location used to resolve relative paths for the root object, or null if unknown.
     * @param resources The resources used to localize the root object, or null if not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("ListViewController initialized!");
        System.out.println("ListView element is null: " + (listView == null));

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
                    setText(item.getTitle());
                }
            }
        });

        listView.setOnMouseClicked(event -> {
            RecipeSummaryResponse selected = listView.getSelectionModel().getSelectedItem();
            if (selected != null && callback != null) {
                callback.onRecipeSelected(selected.getRecipeId());
            }
        });
    }

    /**
     * Loads recipes by category ID and displays them in the ListView.
     * @param categoryId The category ID
     */
    public void loadRecipesByCategory(int categoryId) {
        System.out.println("Loading recipes for category ID: " + categoryId);
        List<RecipeSummaryResponse> rawList = categoryService.getRecipeSummaryByCategoryId(categoryId);
        ObservableList<RecipeSummaryResponse> observableList = FXCollections.observableArrayList(rawList);
        listView.setItems(observableList);
        System.out.println("ListView loaded recipes for the category!");
    }

    /**
     * Refreshes the recipe list with all recipes.
     */
    public void refreshList() {
        List<RecipeSummaryResponse> rawList = fetchAllRecipeSummary();
        ObservableList<RecipeSummaryResponse> observableList = FXCollections.observableArrayList(rawList);
        listView.setItems(observableList);
        System.out.println("ListView refreshed!");
    }

    /**
     * Refreshes the recipe list for a specific category.
     * @param categoryId The category ID
     */
    public void refreshListInCategory(int categoryId) {
        System.out.println("Refreshing list in category ID: " + categoryId);
        List<RecipeSummaryResponse> rawList = categoryService.getRecipeSummaryByCategoryId(categoryId);
        ObservableList<RecipeSummaryResponse> observableList = FXCollections.observableArrayList(rawList);
        listView.setItems(observableList);
        System.out.println("ListView refreshed in category!");
    }

    /**
     * Refreshes the recipe list and retains the selection of a specific recipe.
     * @param recipeIdToKeepSelected The recipe ID to keep selected
     */
    public void refreshListAndRetainSelection(int recipeIdToKeepSelected) {
        refreshList();
        // Find and reselect the previously selected recipe
        for (RecipeSummaryResponse item : listView.getItems()) {
            if (item.getRecipeId() == recipeIdToKeepSelected) {
                listView.getSelectionModel().select(item);
                break;
            }
        }
    }

    /**
     * Searches recipes by keyword and updates the ListView.
     * @param keyword The search keyword
     */
    public void search(String keyword) {
        List<RecipeSummaryResponse> rawList = fetchAllRecipeSummary();
        List<RecipeSummaryResponse> filteredList;
        if (keyword == null || keyword.isBlank()) {
            filteredList = rawList;
        } else {
            filteredList = rawList.stream()
                    .filter(item -> item.getTitle() != null && item.getTitle().toLowerCase().contains(keyword.toLowerCase()))
                    .toList();
        }
        ObservableList<RecipeSummaryResponse> observableList = FXCollections.observableArrayList(filteredList);
        listView.setItems(observableList);
        System.out.println("ListView filtered and refreshed by keyword!");
    }

    /**
     * Searches recipes in a specific category by keyword and updates the ListView.
     * @param categoryId The category ID
     * @param keyword The search keyword
     */
    public void searchInCategory(int categoryId, String keyword) {
        List<RecipeSummaryResponse> rawList = categoryService.getRecipeSummaryByCategoryId(categoryId);
        List<RecipeSummaryResponse> filteredList;
        if (keyword == null || keyword.isBlank()) {
            filteredList = rawList;
        } else {
            filteredList = rawList.stream()
                    .filter(item -> item.getTitle() != null && item.getTitle().toLowerCase().contains(keyword.toLowerCase()))
                    .toList();
        }
        ObservableList<RecipeSummaryResponse> observableList = FXCollections.observableArrayList(filteredList);
        listView.setItems(observableList);
        System.out.println("ListView filtered and refreshed by category and keyword!");
    }

    /**
     * Clears the recipe list in the ListView.
     */
    public void clearList() {
        listView.getItems().clear();
    }

    /**
     * Callback interface for recipe item selection.
     */
    public interface ActionCallback {
        void onRecipeSelected(int recipeId);
    }
    private ActionCallback callback;

    /**
     * Sets the callback for recipe selection.
     * @param callback The callback to set
     */
    public void setCallback(ActionCallback callback) {
        this.callback = callback;
    }

    /**
     * Sets the visibility and managed state of the ListView.
     * @param visible true to show the ListView, false to hide it
     */
    public void setListViewVisible(boolean visible) {
        listView.setVisible(visible);
        listView.setManaged(visible);
    }
}
