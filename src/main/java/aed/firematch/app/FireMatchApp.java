package aed.firematch.app;

import aed.firematch.ui.controllers.LoginController;
import atlantafx.base.theme.NordDark;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.Objects;

public class FireMatchApp extends Application {

    private final LoginController loginController = new LoginController();

    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(loginController.getLoginRoot());
        Application.setUserAgentStylesheet(new NordDark().getUserAgentStylesheet());

        Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/heartbreak.png")));

        primaryStage.setScene(scene);
        primaryStage.getIcons().add(icon);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setTitle("FireMatch");
        primaryStage.show();
    }
}
