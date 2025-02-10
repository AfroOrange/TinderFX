package aed.firematch.ui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.TextFlow;

import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class HediondaController implements Initializable {

    @FXML
    private Label hediondaAge;

    @FXML
    private TextFlow hediondaDescription;

    @FXML
    private Label hediondaName;

    @FXML
    private ImageView hediondaPfP;

    @FXML
    private BorderPane hediondasRoot;

    @FXML
    private Button onSingarAction;

    @FXML
    private Button singarButton;

    public HediondaController() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/HediondasView.fxml"));
            loader.setController(this);
            loader.load();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void onNextAction(ActionEvent event) {

    }

    @FXML
    void onSingarAction(ActionEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public Label getHediondaAge() {
        return hediondaAge;
    }

    public void setHediondaAge(Label hediondaAge) {
        this.hediondaAge = hediondaAge;
    }

    public TextFlow getHediondaDescription() {
        return hediondaDescription;
    }

    public void setHediondaDescription(TextFlow hediondaDescription) {
        this.hediondaDescription = hediondaDescription;
    }

    public Label getHediondaName() {
        return hediondaName;
    }

    public void setHediondaName(Label hediondaName) {
        this.hediondaName = hediondaName;
    }

    public ImageView getHediondaPfP() {
        return hediondaPfP;
    }

    public void setHediondaPfP(ImageView hediondaPfP) {
        this.hediondaPfP = hediondaPfP;
    }

    public BorderPane getHediondasRoot() {
        return hediondasRoot;
    }

    public void setHediondasRoot(BorderPane hediondasRoot) {
        this.hediondasRoot = hediondasRoot;
    }

    public Button getOnSingarAction() {
        return onSingarAction;
    }

    public void setOnSingarAction(Button onSingarAction) {
        this.onSingarAction = onSingarAction;
    }

    public Button getSingarButton() {
        return singarButton;
    }

    public void setSingarButton(Button singarButton) {
        this.singarButton = singarButton;
    }
}
