package MovieTicketingSystem.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ConfirmationPageController extends AddOnPageController{
    @FXML
    Label ticketPrice,movieText,movieText2,cinemaText,seatText,paymentMethod,totalAmount;

    @Override
    public void ChangeNextScene() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../layout/SuccessPage.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
        ReadFile();
    }

    public void ReadFile(){
        ArrayList<String> buffers = new ArrayList();
        try {
            BufferedReader in = new BufferedReader(new FileReader(new File("MovieDetails.txt")));

            String why ="";
            while(why!=null){
                buffers.add((why=in.readLine()));
            }
            buffers.trimToSize();
            String [] buffer =buffers.get(buffers.size()-2).split("\t");
            cinemaText.setText(buffer[1]);
            seatText.setText(buffer[2]);
            movieText.setText(buffer[4]);
            movieText2.setText(buffer[4]);
            ticketPrice.setText(buffer[5]);
            String [] x=buffer[5].split(" ");
            totalAmount.setText("RM "+(Double.parseDouble(x[1])+2.0)+"");
            in.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void SetPaymentMethod(String paymentMethodText){
        paymentMethod.setText(paymentMethodText);
    }
}
