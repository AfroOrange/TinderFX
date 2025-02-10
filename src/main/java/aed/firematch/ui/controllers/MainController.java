package aed.firematch.ui.controllers;

import aed.firematch.firebase.DBManager;
import aed.firematch.ipinfo.IPinfoAPI;
import aed.firematch.ui.modelos.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

public class MainController implements Initializable {

    // Model
    private DBManager dbManager = new DBManager();
    private final String userEmail;

    // View
    @FXML
    private BorderPane mainRoot;

    @FXML
    private Label ageLabel;

    @FXML
    private Label cityLabel;

    @FXML
    private TextFlow descriptionArea;

    @FXML
    private Label genderLabel;

    @FXML
    private Label nameLabel;

    @FXML
    private GridPane userInformationPane;

    @FXML
    private Button startButton;

    public MainController(String userEmail) {
        this.userEmail = userEmail;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainView.fxml"));
            loader.setController(this);
            loader.load();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // initialize user information
        try {
            cityLabel.setText(IPinfoAPI.getLocation());

            // Fetch user information from Firestore
            Usuario usuario = dbManager.getUserByEmail(userEmail);

            if (usuario != null) {
                nameLabel.setText(usuario.getNombre());
                ageLabel.setText(String.valueOf(usuario.getEdad()));
                genderLabel.setText(usuario.getGenero().name());
                descriptionArea.getChildren().add(new Text(usuario.getDescripcion()));
            }
        } catch (IOException | ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void onStartAction(ActionEvent event) {
        HediondaController hediondaController = new HediondaController(userEmail);
        mainRoot.setCenter(hediondaController.getHediondasRoot());
    }

    @FXML
    void onCheckMatches(ActionEvent event) {

    }

    public BorderPane getRoot() {
        return mainRoot;
    }

    public DBManager getDbManager() {
        return dbManager;
    }

    public void setDbManager(DBManager dbManager) {
        this.dbManager = dbManager;
    }

    public BorderPane getMainRoot() {
        return mainRoot;
    }

    public void setMainRoot(BorderPane mainRoot) {
        this.mainRoot = mainRoot;
    }

    public Label getAgeLabel() {
        return ageLabel;
    }

    public void setAgeLabel(Label ageLabel) {
        this.ageLabel = ageLabel;
    }

    public Label getCityLabel() {
        return cityLabel;
    }

    public void setCityLabel(Label cityLabel) {
        this.cityLabel = cityLabel;
    }

    public TextFlow getDescriptionArea() {
        return descriptionArea;
    }

    public void setDescriptionArea(TextFlow descriptionArea) {
        this.descriptionArea = descriptionArea;
    }

    public Label getGenderLabel() {
        return genderLabel;
    }

    public void setGenderLabel(Label genderLabel) {
        this.genderLabel = genderLabel;
    }

    public Label getNameLabel() {
        return nameLabel;
    }

    public void setNameLabel(Label nameLabel) {
        this.nameLabel = nameLabel;
    }

    public GridPane getUserInformationPane() {
        return userInformationPane;
    }

    public void setUserInformationPane(GridPane userInformationPane) {
        this.userInformationPane = userInformationPane;
    }
}