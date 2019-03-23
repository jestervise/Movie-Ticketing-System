package MovieTicketingSystem.controllers;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SelectTicketPageController extends BookingPageController implements Initializable{
    @FXML
    protected ImageView selectSeatIcon;
    @FXML
    protected Label selectSeatText;
    @FXML
    protected Label customerCountText;
    @FXML
    private Label movieText;
    @FXML
    private Label cinemaText;
    @FXML
    private Label seatText;
    @FXML
    protected Label ticketPrice;
    @FXML
    private Label price1,price2,price3,price4,price5,price6,price7,price8;

    double totalPrice=0.00;

    protected int customerNumber=0;
    protected int customerSelected=0;


    public void SetTotalPrice(double totalPrice){
        this.totalPrice=totalPrice;
    }

    public void setCustomerCountText() {
        customerCountText.setText(customerSelected+"/"+customerNumber);
    }

    @Override
    public void SetCinemaText(String cinemaTextString) {
        super.SetCinemaText(cinemaTextString);
    }

    @Override
    public void SetMovieText(String movieTextString) {
        super.SetMovieText(movieTextString);
    }

    public void SetSeatText(String seatTextString){
        seatText.setText(seatTextString);
    }

    @FXML
    public void ChangeToSelectSeatScene(){
        try {
            FXMLLoader loader =new FXMLLoader(getClass().getResource("../layout/BookingPage.fxml"));
            Parent root = loader.load();
            BookingPageController bookingPageController = loader.getController();
            bookingPageController.SetMovieText(movieText.getText());
            bookingPageController.SetCinemaText(cinemaText.getText());
            Scene scene = new Scene(root);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void SetCustomerNumber(int customerNumber)  {
       this.customerNumber=customerNumber;
       customerCountText.setText("0/"+customerNumber);
    }



    @FXML
    public void ChangeNumber(MouseEvent e){
        Label thisLabel = ((Label) e.getSource());
        int integerLabelText;
        //left click
        if(e.getButton()==MouseButton.PRIMARY) {
                if (Integer.parseInt(thisLabel.getText()) < customerNumber) {
                    //when the customer count is not full, increase the total customer count
                    if (customerSelected != customerNumber) {
                        integerLabelText = Integer.parseInt(thisLabel.getText()) + 1;
                        thisLabel.setText(integerLabelText + "");
                        customerSelected++;
                        customerCountText.setText(customerSelected + "/" + customerNumber);
                        NextButtonIsDisabled();
                        //else when full set next button enable
                        if (customerSelected==customerNumber){
                            nextButton.setDisable(false);
                        }
                    }


                }
                //When the customer selected is more than customer count, set the customer selected to 0
                else {
                    thisLabel.setText("0");
                    SetCustomerSelectedText();
                    NextButtonIsDisabled();
                }
            }
        //right click
            else {
                //if the number is less than 1 then set the number to 0
                if (Integer.parseInt(thisLabel.getText()) < 1) {
                    thisLabel.setText("0");
                    SetCustomerSelectedText();
                    NextButtonIsDisabled();
                }
                //else decrement the number and show it
                else {
                    integerLabelText = Integer.parseInt(thisLabel.getText()) - 1;
                    thisLabel.setText(integerLabelText + "");
                    SetCustomerSelectedText();
                    NextButtonIsDisabled();
                }
            }

    }

    public void SetCustomerSelectedText(){
        if (customerSelected!=0) {
            customerSelected--;
            customerCountText.setText(customerSelected + "/" + customerNumber);
        }
        else{
            customerSelected=0;
        }
    }

    public void setCustomerSelectedText(){
        customerCountText.setText(customerSelected + "/" + customerNumber);

    }

    public void SetCustomerSelected(int customerSelected){
        this.customerSelected= customerSelected;
    }

    public void TicketListener(){
        double [] ticketPrices ={8.00,13.00,8.00,6.00,18.00,13.00,13.00,12.00};
        ArrayList<Label> prices = new ArrayList();
        prices.add(price1);
        prices.add(price2);
        prices.add(price3);
        prices.add(price4);
        prices.add(price5);
        prices.add(price6);
        prices.add(price7);
        prices.add(price8);

        for (int x=0;x<prices.size();x++){
            int finalX = x;
            prices.get(x).textProperty().addListener((v, oldValue, newValue)->{
                if(oldValue!=null) {
                    if(Double.parseDouble(oldValue)==customerNumber &&Double.parseDouble(newValue)==0){
                        totalPrice = totalPrice-(ticketPrices[finalX]*customerNumber);
                        ticketPrice.setText("RM " + totalPrice);
                        customerSelected=customerSelected-customerNumber;
                        customerCountText.setText(customerSelected + "/" + customerNumber);
                    }
                    else if(Integer.parseInt(oldValue)<Integer.parseInt(newValue)) {
                        totalPrice = ticketPrices[finalX] + totalPrice;
                        ticketPrice.setText("RM " + totalPrice);
                    }else{
                        totalPrice =totalPrice-ticketPrices[finalX];
                        ticketPrice.setText("RM " + totalPrice);
                    }
                }
            });
        }

    }

    protected void NextButtonIsDisabled(){
        nextButton.setDisable(true);
    }

    @Override
    public void ChangeNextScene() {
        try {
            FXMLLoader loader =new FXMLLoader(getClass().getResource("../layout/AddOnPage.fxml"));
            Parent root = loader.load();
            AddOnPageController addOnPageController = loader.getController();
            addOnPageController.SetSeatText(seatText.getText());
            addOnPageController.SetCinemaText(cinemaText.getText());
            addOnPageController.SetMovieText(movieText.getText());
            addOnPageController.SetCustomerNumber(SetCustomerCount());
            addOnPageController.SetTicketPrice(totalPrice);
            addOnPageController.SetCustomerSelected(customerSelected);
            addOnPageController.setCustomerSelectedText();
            Scene scene = new Scene(root);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void SetTicketPrice(double totalPriceDouble){
        this.totalPrice = totalPriceDouble;
        ticketPrice.setText("RM "+totalPriceDouble);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        MoveStage();
        NextPage();
        TicketListener();
        NextButtonIsDisabled();
    }
}
