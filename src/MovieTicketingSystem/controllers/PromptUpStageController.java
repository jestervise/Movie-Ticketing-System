package MovieTicketingSystem.controllers;

import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import static javafx.application.Platform.exit;

public class PromptUpStageController {
    @FXML
    private JFXButton yesButton;
    @FXML
    private JFXButton noButton;

    PromptUpStageController() throws IOException {
        yesButton.setOnAction(e->{
            exit();
        });
    }
}
