package aed.firematch.ui.controllers;

import aed.firematch.firebase.DBManager;
import aed.firematch.ui.modelos.Usuario;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.List;
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

    private DBManager dbManager = new DBManager();
    private String userEmail;
    private List<Usuario> usuarios;
    private ListProperty<Usuario> usuariosProperty = new SimpleListProperty<>(FXCollections.observableArrayList());

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
        rejectperson();
    }

    private void rejectperson() {
        if (usuarios != null && !usuarios.isEmpty()) {
            usuarios.remove(0);
            mostrarUsuarios(usuarios);
        }
    }

    @FXML
    void onSingarAction(ActionEvent event) {
        // Obtener el usuario actual
        Usuario usuario = usuarios.get(0);

        // Guardar el usuario en la lista de usuarios que le han gustado
        usuariosProperty.add(usuario);
        System.out.println("Le has dado like a " + usuario.getNombre());
        rejectperson();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialize the list of users
        usuarios = dbManager.obtenerUsuariosAleatorios(userEmail);
        mostrarUsuarios(usuarios);
    }

    private void mostrarUsuarios(List<Usuario> usuarios) {
        if (usuarios.isEmpty()) {
            // Mostrar mensaje de que no hay usuarios disponibles
            return;
        }

        // Mostrar el primer usuario de la lista
        Usuario usuario = usuarios.get(0);
        hediondaName.setText(usuario.getNombre());
        hediondaAge.setText(String.valueOf(usuario.getEdad()));
        hediondaDescription.getChildren().clear();
        hediondaDescription.getChildren().add(new Text(usuario.getDescripcion()));
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