package MovieTicketingSystem.controllers;

import javafx.animation.Interpolator;
import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class PromotionPageController implements Initializable {
    @FXML
    private ImageView closeButton;
    @FXML
    private Pane basePane;


    double xOffset,yOffset;
    Stage thisStage;


    @FXML
    void CloseWindow() {
        Stage stage =(Stage) basePane.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        MoveStage();
    }

    //Allow the user to move the main stage freely
    protected void MoveStage(){

        //Set the position of the stage when they are pressed
        basePane.setOnMousePressed(e-> {
            thisStage=(Stage) ((Node)e.getSource()).getScene().getWindow();
            xOffset = e.getSceneX();
            yOffset = e.getSceneY();
        });
        basePane.setOnMouseDragged(e-> {
            thisStage=(Stage) ((Node)e.getSource()).getScene().getWindow();
            thisStage.setX(e.getScreenX() - xOffset);
            thisStage.setY(e.getScreenY() - yOffset);

        });
    }

    @FXML
    public void ScaleUpButton(MouseEvent event){

        ((ImageView)event.getSource()).setOnMouseEntered(e-> {
            ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(200),((ImageView)event.getSource()));
            scaleTransition.setToX(1.2);
            scaleTransition.setToY(1.2);
            scaleTransition.setCycleCount(1);
            scaleTransition.setInterpolator(Interpolator.EASE_BOTH);
            scaleTransition.play();
        });
    }

    @FXML
    public void ScaleDownButton(MouseEvent event){

        ((ImageView)event.getSource()).setOnMouseExited(e-> {
            ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(200), ((ImageView)event.getSource()));
            scaleTransition.setToX(1);
            scaleTransition.setToY(1);
            scaleTransition.setCycleCount(1);
            scaleTransition.setInterpolator(Interpolator.EASE_BOTH);
            scaleTransition.play();
        });
    }




}
