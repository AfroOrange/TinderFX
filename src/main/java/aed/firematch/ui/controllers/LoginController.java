package aed.firematch.ui.controllers;

import aed.firematch.firebase.DBManager;
import aed.firematch.ui.modelos.Genero;
import aed.firematch.ui.modelos.Usuario;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    // Model
    private final DBManager dbManager = new DBManager();
    private final StringProperty email = new SimpleStringProperty();
    private final StringProperty password = new SimpleStringProperty();

    // View
    @FXML
    private Button cerrarButton;

    @FXML
    private TextField emailTextField;

    @FXML
    private Button loginButton;

    @FXML
    private GridPane loginRoot;

    @FXML
    private PasswordField passwdTextField;

    @FXML
    void onCerrarButton(ActionEvent event) {
        Stage stage = (Stage) cerrarButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    void onLoginAction(ActionEvent event) {
        if (dbManager.login(email.getValue(), password.getValue())) {
            // Cerrar la ventana de login
            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.close();

            // Abrir la ventana principal
            MainController mainController = new MainController(email.getValue());
            Stage mainStage = new Stage();
            Scene scene = new Scene(mainController.getRoot());
            mainStage.setScene(scene);
            mainStage.setTitle("FireMatch");
            mainStage.show();
        } else {
            // Mostrar diálogo de error
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error de inicio de sesión");
            alert.setHeaderText("Credenciales incorrectas");
            alert.setContentText("El usuario o la contraseña son incorrectos. Por favor, inténtelo de nuevo.");
            alert.showAndWait();
        }
    }

    public LoginController() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/LoginView.fxml"));
            loader.setController(this);
            loader.load();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Primer usuario
        Usuario usuario1 = new Usuario();
        usuario1.setId(1);
        usuario1.setEmail("jonathan@gmail.com");
        usuario1.setNombre("Jonathan");
        usuario1.setApellidos("Gutiérrez");
        usuario1.setEdad(25);
        usuario1.setPassword("jonathan123");
        usuario1.setDescripcion("Amo la programación");
        usuario1.setGenero(Genero.NO_BINARIO);
        usuario1.setNickname("jonathan_gr");
        usuario1.getCaracteristicas().add("Me gusta el furbo");
        usuario1.getGustos().add(Genero.FEMENINO);
        usuario1.getGustos().add(Genero.NO_BINARIO);
        dbManager.crearUsuario(usuario1);

        // Segundo usuario
        Usuario usuario2 = new Usuario();
        usuario2.setId(2);
        usuario2.setEmail("maria@gmail.com");
        usuario2.setNombre("Maria");
        usuario2.setApellidos("Lopez");
        usuario2.setEdad(18);
        usuario2.setPassword("maria123");
        usuario2.setDescripcion("Me encanta la música");
        usuario2.setGenero(Genero.OTRO);
        usuario2.setNickname("maria_l");
        usuario2.getCaracteristicas().add("Me gusta el piano");
        usuario2.getGustos().add(Genero.MASCULINO);
        usuario2.getGustos().add(Genero.OTRO);
        dbManager.crearUsuario(usuario2);

        email.bindBidirectional(emailTextField.textProperty());
        password.bindBidirectional(passwdTextField.textProperty());
    }

    public GridPane getLoginRoot() {
        return loginRoot;
    }
}