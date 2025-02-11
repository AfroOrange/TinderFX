package aed.firematch.ui.controllers;

import aed.firematch.ui.modelos.SharedData;
import aed.firematch.ui.modelos.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class MatchesController implements Initializable {

    @FXML
    private Button deleteButton;

    @FXML
    private ListView<String> matchesListView;

    @FXML
    private BorderPane matchesRoot;

    @FXML
    void onDeleteMatch(ActionEvent event) {
        String selected = matchesListView.getSelectionModel().getSelectedItem();
        SharedData.getInstance().getLikedUsuarios().remove(selected);
    }

    public MatchesController() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MatchesView.fxml"));
            loader.setController(this);
            loader.load();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        matchesListView.setItems(SharedData.getInstance().getLikedUsuarios());

        // bind the delete button to the selected item in the list view
        deleteButton.disableProperty().bind(matchesListView.getSelectionModel().selectedItemProperty().isNull());
    }

    public Button getDeleteButton() {
        return deleteButton;
    }

    public void setDeleteButton(Button deleteButton) {
        this.deleteButton = deleteButton;
    }

    public void setMatchesListView(ListView<String> matchesListView) {
        this.matchesListView = matchesListView;
    }

    public BorderPane getMatchesRoot() {
        return matchesRoot;
    }

    public void setMatchesRoot(BorderPane matchesRoot) {
        this.matchesRoot = matchesRoot;
    }
}

