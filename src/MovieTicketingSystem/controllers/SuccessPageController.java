package MovieTicketingSystem.controllers;

import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SuccessPageController extends ConfirmationPageController implements Initializable {
    @FXML
    ImageView successArrow;
    @FXML
    AnchorPane basePane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        PlayArrowTransition();
    }

    public void PlayArrowTransition(){
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(200),basePane);
        fadeTransition.setCycleCount(1);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.setInterpolator(Interpolator.EASE_BOTH);
        fadeTransition.play();
        RotateTransition rotateTransition = new RotateTransition(Duration.millis(1000),successArrow);
        fadeTransition.setOnFinished(e->{

            rotateTransition.setCycleCount(1);
            rotateTransition.setFromAngle(0);
            rotateTransition.setToAngle(180);
            rotateTransition.setInterpolator(Interpolator.EASE_BOTH);
            rotateTransition.play();
        });
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(200),successArrow);
        scaleTransition.setCycleCount(4);
        scaleTransition.setToX(1.2);
        scaleTransition.setToY(1.2);
        scaleTransition.setInterpolator(Interpolator.EASE_BOTH);
        scaleTransition.setAutoReverse(true);

        rotateTransition.setOnFinished(e->scaleTransition.play());
        scaleTransition.setOnFinished(e->NextPage(e));
    }

    public void NextPage(ActionEvent event) {
        ChangeNextScene(event);
    }


    public void ChangeNextScene(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../layout/HomePage.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            ((Stage)(((ScaleTransition)event.getSource()).getNode()).getScene().getWindow()).setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
