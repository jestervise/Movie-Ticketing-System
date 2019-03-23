package MovieTicketingSystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class SignUpScene extends Application {

    public static Stage registerStage;
    double xOffset;
    double yOffset;

    public SignUpScene(Stage primaryStage) throws Exception {
        start(primaryStage);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        registerStage= primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("layout/SignUpScene.fxml"));
        registerStage.setScene(new Scene(root, 615, 425));
    }

    }






