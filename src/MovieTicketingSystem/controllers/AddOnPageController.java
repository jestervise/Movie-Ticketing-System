package MovieTicketingSystem.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AddOnPageController extends SelectTicketPageController implements Initializable {

    @FXML
    protected ImageView selectTicketIcon;
    @FXML
    protected Label selectTicketText;
    @FXML
    private ImageView btn1Up,btn2Up,btn3Up,btn4Up,btn1Down,btn2Down,btn3Down,btn4Down;
    @FXML
    private JFXTextField textField1,textField2,textField3,textField4;
    @FXML
    private Label subTotal1,subTotal2,subTotal3,subTotal4;
    @FXML
    Label rmText1,rmText2,rmText3,rmText4;
    @FXML
    Label customerCountText;
    @FXML
    private Label movieText;
    @FXML
    private Label cinemaText;
    @FXML
    private Label seatText;


    int customerNumber=0;
    int customerSelected=0;

    double [] addOnPriceList={7.00,6.00,7.00,9.00};

    @FXML
    public void BackToSelectTicketPage(){
        try {

            FXMLLoader loader =new FXMLLoader(getClass().getResource("../layout/SelectTicketPage.fxml"));
            Parent root = loader.load();
            SelectTicketPageController selectTicketPageController = loader.getController();
            selectTicketPageController.SetSeatText(seatText.getText());
            selectTicketPageController.SetCinemaText(cinemaText.getText());
            selectTicketPageController.SetMovieText(movieText.getText());
            selectTicketPageController.SetCustomerNumber(SetCustomerCount());
            selectTicketPageController.SetTicketPrice(0);
            selectTicketPageController.SetCustomerSelected(customerSelected);
            selectTicketPageController.setCustomerSelectedText();

            Scene scene = new Scene(root);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void ChangeNextScene() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../layout/PaymentPage.fxml"));
            Parent root = loader.load();
            WriteMovieDetails();
            PaymentPageController paymentPageController = loader.getController();
            paymentPageController.SetTicketPrice(ticketPrice.getText());
            paymentPageController.SetMovieText(movieText.getText());
            Scene scene = new Scene(root);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void WriteMovieDetails(){
        try {
            BufferedWriter fp = new BufferedWriter(new FileWriter(new File("MovieDetails.txt"),true));
            BufferedReader in = new BufferedReader(new FileReader(new File("MovieDateTime.txt")));
            ArrayList<String> buffers = new ArrayList();
            String why ="";
            while(why!=null){
                buffers.add((why=in.readLine()));
            }
            buffers.trimToSize();
            String [] dateTime =buffers.get(buffers.size()-2).split("\t");
            fp.append(LocalDate.now()+"\t"+cinemaText.getText()+"\t"+seatText.getText()+"\t"
                    +SetCustomerCount()+"\t"+movieText.getText()+"\t"+ticketPrice.getText()+"\t"+dateTime[0]+
                    "\t"+dateTime[1]+"\n");
            fp.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void decreaseCountSetText(MouseEvent event, ImageView button, JFXTextField textField, Label subTotal, double price, Label rmText,double addOnPrice){

        if(event.getSource()==button)
            if(textField.getText().compareTo("1")==0){
                rmText.setVisible(false);
                textField.setText(String.valueOf(Integer.parseInt(textField.getText()) - 1));
                subTotal.setText("");

                totalPrice= totalPrice-addOnPrice;
                ticketPrice.setText("RM "+totalPrice);

            }
            else if(textField.getText().compareTo("0")!=0){
                rmText.setVisible(true);
                textField.setText(String.valueOf(Integer.parseInt(textField.getText()) - 1));
                String subTotalText= String.valueOf(Double.valueOf(textField.getText())*price);
                subTotal.setText(subTotalText);
                totalPrice= totalPrice-addOnPrice;
                ticketPrice.setText("RM "+totalPrice);

            }

    }

    void increaseCountSetText(MouseEvent event, ImageView button,JFXTextField textField,Label subTotal,double price,Label rmText,double addOnPrice){
        if(event.getSource()==button)
            if(Integer.parseInt(textField.getText())<10) {
                rmText.setVisible(true);
                textField.setText(String.valueOf(Integer.parseInt(textField.getText()) + 1));
                String subTotalText= String.valueOf(Double.valueOf(textField.getText())*price);
                subTotal.setText(subTotalText);
                totalPrice= totalPrice+addOnPrice;
                ticketPrice.setText("RM "+totalPrice);

            }
    }

    @FXML
    void DecreaseCount(MouseEvent event) {
       decreaseCountSetText(event,btn1Down,textField1,subTotal1,7.00d,rmText1,addOnPriceList[0]);
       decreaseCountSetText(event,btn2Down,textField2,subTotal2,6.00d,rmText2,addOnPriceList[1]);
       decreaseCountSetText(event,btn3Down,textField3,subTotal3,7.00d,rmText3,addOnPriceList[2]);
       decreaseCountSetText(event,btn4Down,textField4,subTotal4,9.00d,rmText4,addOnPriceList[3]);

    }

    @FXML
    void IncreaseCount(MouseEvent event) {
        increaseCountSetText(event,btn1Up,textField1,subTotal1,7.00d,rmText1,addOnPriceList[0]);
        increaseCountSetText(event,btn2Up,textField2,subTotal2,6.00d,rmText2,addOnPriceList[1]);
        increaseCountSetText(event,btn3Up,textField3,subTotal3,7.00d,rmText3,addOnPriceList[2]);
        increaseCountSetText(event,btn4Up,textField4,subTotal4,9.00d,rmText4,addOnPriceList[3]);
    }

    @Override
    void SetTicketPrice(double totalPriceDouble){
        this.totalPrice = totalPriceDouble;
        ticketPrice.setText("RM "+totalPriceDouble);
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        NextPage();
        MoveStage();

    }
}
