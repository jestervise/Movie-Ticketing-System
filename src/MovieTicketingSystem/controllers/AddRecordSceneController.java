package MovieTicketingSystem.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AddRecordSceneController extends AdminPageController{
    @FXML
    AnchorPane basePane;
    double xOffset,yOffset;
    @FXML
    JFXTextField addRecordButton;
    @FXML
    JFXButton addButton;

    Stage newStage;

    @FXML
    private void CloseWindow(){
        newStage.close();
    }

    protected void MoveStage(AnchorPane scene) {
        //Set the position of the stage when they are pressed

        scene.setOnMousePressed(e-> {

            xOffset = e.getSceneX();
            yOffset = e.getSceneY();
        });
        scene.setOnMouseDragged(e-> {
            newStage=(Stage)((Node)e.getSource()).getScene().getWindow();
            newStage.setX(e.getScreenX() - xOffset);
            newStage.setY(e.getScreenY() - yOffset);

        });
    }

    public void SetUpStage(Stage stage){
        newStage =stage;
    }
/*
    @FXML
    public void ChangeToChooseMovieScene(){

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../layout/BookingPage.fxml"));
        try {
            Parent root = loader.load();
            BookingPageController bookingPageController = loader.getController();
            bookingPageController.SetMovieText(addRecordButton.getText());
            bookingPageController.SetCinemaText("IOI City Mall");

        } catch (IOException e) {
            e.printStackTrace();
        }

        newStage.close();
    }
*/
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        MoveStage(basePane);
    }
}
