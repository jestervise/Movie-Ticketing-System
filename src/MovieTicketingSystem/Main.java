package MovieTicketingSystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    public static Stage registerStage;
    public static  Parent root;

    @Override
    public void start(Stage primaryStage) throws Exception{
        registerStage= primaryStage;
        root = FXMLLoader.load(getClass().getResource("layout/MainPage.fxml"));
        registerStage.setScene(new Scene(root, 615, 425));
        registerStage.setResizable(false);
        registerStage.initStyle(StageStyle.UNDECORATED);
        registerStage.setOpacity(1);
        registerStage.show();
    }





    public static void main(String[] args) {
        launch(args);
    }
}
