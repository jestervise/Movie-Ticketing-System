package MovieTicketingSystem.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ResourceBundle;


public class PaymentPageController extends AddOnPageController implements Initializable {
    String cardType;
    @FXML
    Label ticketPrice,movieText;
    @FXML
    ImageView visa,master;
    @FXML
    JFXTextField cardNumberTextField,cardHolderName,cvv;
    @FXML
    JFXComboBox<String> monthComboBox,yearComboBox;

    @FXML
    public void ChangeToAddOnScene(){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../layout/AddOnPage.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void populateYear(){
        for(int x=0;x<=15;x++) {
            int localDate=LocalDateTime.now().getYear()+x;
            yearComboBox.getItems().add(localDate+"");
        }
        for(int x=1;x<=12;x++) {
            Month monthString=Month.of(x);
            monthComboBox.getItems().add(monthString+"");
        }
    }

    @Override
    public void ChangeNextScene() {
        if(cardHolderName.getText().isEmpty() || cardNumberTextField.getText().isEmpty()){
            nextButton.setMouseTransparent(true);
        }
        else {
            try {
                nextButton.setMouseTransparent(true);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../layout/ConfirmationPage.fxml"));
                Parent root = loader.load();
                ConfirmationPageController confirmationPageController = loader.getController();
                confirmationPageController.SetPaymentMethod("Credit Card");

                Scene scene = new Scene(root);
                stage.setScene(scene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void SetListener(){
        cardNumberTextField.textProperty().addListener((v,oldValue,newValue)->{
            if(!newValue.isEmpty()) {
                    if(newValue.length()>=2) {
                        if ((newValue.trim().charAt(0) == '5' && newValue.trim().charAt(1) >= '1' && newValue.trim().charAt(1) <= '5')
                                || (newValue.trim().charAt(0) == '2' && newValue.trim().charAt(1) >= '2' && newValue.trim().charAt(1) <= '7')) {
                            master.setOpacity(1);
                            cardType="Master";
                        } else if (newValue.trim().charAt(0) == '4') {
                            visa.setOpacity(1);
                            cardType="Visa";
                        } else {
                            master.setOpacity(0.2);
                            visa.setOpacity(0.2);
                        }
                    }

            }else{
                master.setOpacity(0.2);
                visa.setOpacity(0.2);
            }
        }
                );
    }

    public void SetTicketPrice(String ticketPriceText) {
        ticketPrice.setText(ticketPriceText);
    }

    public void SetMovieText(String movieTextString) {
        movieText.setText(movieTextString);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        MoveStage();
        SetListener();
        populateYear();
    }


}
