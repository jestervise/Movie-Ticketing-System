package MovieTicketingSystem.controllers;

import com.jfoenix.transitions.JFXFillTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class ContactPageController implements Initializable {
    @FXML
    AnchorPane basePane;

    @FXML
    void CloseWindow(MouseEvent event) {
        Stage stage = (Stage)(((Node)event.getSource()).getScene().getWindow());
        stage.close();
    }

    public void FillTransition(){
        //Play the fill transition when stage is loaded
        JFXFillTransition transition = new JFXFillTransition();
        transition.setDuration(Duration.millis(1000));
        transition.setFromValue(Color.valueOf("#4286f4"));
        transition.setToValue(Color.valueOf("#4059A9"));
        transition.setRegion(basePane);
        transition.play();
        JFXFillTransition transition2 = new JFXFillTransition(Duration.millis(1000),basePane,Color.valueOf("#4059A9"),Color.valueOf("#242E44"));
        transition.setOnFinished(e->transition2.play());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FillTransition();
    }
}
