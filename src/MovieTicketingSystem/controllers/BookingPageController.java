package MovieTicketingSystem.controllers;

import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.ScaleTransition;
import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.ObservableList;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicReference;

public class BookingPageController extends Application implements Initializable {
    @FXML
    private GridPane showOrderPane;
    @FXML
    private AnchorPane basePane;
    @FXML
    private VBox bottomPane;
    @FXML
    private Label yourOrderText;
    @FXML
    private GridPane bottomGrid;
    @FXML
    private Line line;
    @FXML
    private GridPane chooseSeatGrid;
    @FXML
    private ImageView backButton;
    @FXML
    protected SplitPane splitPane;
    @FXML
    private ImageView seat;
    @FXML
    private Label testLabel;
    @FXML
    private Label seatText;
    @FXML
    protected ImageView nextButton;
    @FXML
    private Label movieText;
    @FXML
    private Label movieText2;
    @FXML
    private Label cinemaText;


    protected Stage stage;
    int seatSelectedIndex=-1;

    double xOffset,yOffset;

    int selectedSeatCount=0;

    int customerCount=0;
    String[] customers;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FadeAnimation();
        bottomLineAutoResize();
        MoveStage();
        SelectSeatColorChange();
        NextPage();

    }

    public void SetMovieText(String movieTextString){
        movieText.setText(movieTextString);
        movieText2.setText(movieTextString);
    }

    public void SetCinemaText(String cinemaTextString){
        cinemaText.setText(cinemaTextString);
    }

    public void NextPage(){

        nextButton.setOnMouseClicked(e->ChangeNextScene());
    }

    public void ChangeNextScene(){
        try {
            /* File file = new File("CustomerCount.txt");
            BufferedWriter bufferedWriter= new BufferedWriter(new FileWriter(file));
            bufferedWriter.write(customerCount);
            System.out.println(customerCount);*/
            //WriteCustomerCount();
            FXMLLoader loader =new FXMLLoader(getClass().getResource("../layout/SelectTicketPage.fxml"));
            Parent root = loader.load();
            SelectTicketPageController selectTicketPageController = loader.getController();
            selectTicketPageController.SetMovieText(movieText.getText());
            selectTicketPageController.SetCinemaText(cinemaText.getText());
            selectTicketPageController.SetSeatText(seatText.getText());
            selectTicketPageController.SetCustomerNumber(SetCustomerCount());
            Scene scene = new Scene(root);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public void FadeAnimation(){
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(500),basePane);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.play();
    }

    @FXML
    public void ScaleUpButton(){
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(200),nextButton);
        scaleTransition.setInterpolator(Interpolator.EASE_BOTH);
        scaleTransition.setToX(1.2);
        scaleTransition.setToY(1.2);
        scaleTransition.setCycleCount(1);
        scaleTransition.play();
    }

    public int SetCustomerCount()  {
        customers=seatText.getText().trim().split(" ");
        customerCount= customers.length;
        return customerCount;
    }

    @FXML
    public void ScaleDownButton(){
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(200),nextButton);
        scaleTransition.setInterpolator(Interpolator.EASE_BOTH);
        scaleTransition.setToX(1);
        scaleTransition.setToY(1);
        scaleTransition.setCycleCount(1);
        scaleTransition.play();
    }

    @FXML
    void BackToHome(MouseEvent event) {
        Parent root=null;
        try {
            root = FXMLLoader.load(getClass().getResource("../layout/HomePage.fxml"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        Scene scene = new Scene(root);
        Stage myStage=(Stage)((Node) event.getSource()).getScene().getWindow();
        myStage.setScene(scene);
    }

    public void MoveStage(){

        //Set the position of the stage when they are pressed
        splitPane.setOnMousePressed(e-> {
            stage = (Stage) ((Node)e.getSource()).getScene().getWindow();
            xOffset = e.getSceneX();
            yOffset = e.getSceneY();
        });
        splitPane.setOnMouseDragged(e-> {
            stage = (Stage) ((Node)e.getSource()).getScene().getWindow();
            stage.setX(e.getScreenX() - xOffset);
            stage.setY(e.getScreenY() - yOffset);

        });
    }


    public void SelectSeatColorChange(){
        ObservableList<Node> list =chooseSeatGrid.getChildren();
        Image seatSelected = new Image("MovieTicketingSystem/rsc/bookingPage/seat_selected.png");
        Image seatAvailable = new Image("MovieTicketingSystem/rsc/bookingPage/seat_available.png");
        for(Node listItem: list){
            if(listItem.getClass()==testLabel.getClass()){
                listItem.setMouseTransparent(true);
            }else if (listItem.getClass()==seat.getClass()){
                ((ImageView)listItem).imageProperty().addListener((v,oldValue,newValue)->{

                });
                listItem.setOnMouseClicked(e->{

                    if((((ImageView)listItem).getImage())!=seatSelected) {

                        ((ImageView) listItem).setImage(seatSelected);

                        BottomGridPaneUpdateText(GridPane.getRowIndex(listItem),GridPane.getColumnIndex(listItem));
                    }
                    else if((((ImageView)listItem).getImage())!=seatAvailable){
                        ((ImageView) listItem).setImage(seatAvailable);
                        BottomGridPaneUpdateText(GridPane.getRowIndex(listItem),GridPane.getColumnIndex(listItem));
                    }

                }

                );

                ((ImageView)listItem).imageProperty().addListener((v,oldValue,newValue)->{
                    if(newValue==seatAvailable){
                        selectedSeatCount--;
                    }else if(newValue==seatSelected){
                        selectedSeatCount++;
                    }
                    if(selectedSeatCount==0)
                        nextButton.setDisable(true);
                    else
                        nextButton.setDisable(false);
                });

            }
        }

    }

    public void BottomGridPaneUpdateText(int rowIndex,int columnIndex){
        char rowCharacter;


        switch(rowIndex){
            case 4:
                rowCharacter='A';
                break;
            case  5:
                rowCharacter='B';
                break;
            case 6:
                rowCharacter='C';
                break;
            case  7:
                rowCharacter='D';
                break;
            case 8:
                rowCharacter='E';
                break;
            case  9:
                rowCharacter='F';
                break;
            case 10:
                rowCharacter='G';
                break;
            case  11:
                rowCharacter='H';
                break;
            case 12:
                rowCharacter='J';
                break;
            case  13:
                rowCharacter='K';
                break;
             default:
                 rowCharacter='Z';
        }

        //Set column index
        //If choose twin seat
        if(rowCharacter=='J' || rowCharacter=='K'){
                //Row J
                if(rowCharacter=='J') {
                    CheckTwinSeatRowJ(columnIndex, 3, 4, 2, rowCharacter);
                    CheckTwinSeatRowJ(columnIndex, 9, 10, 6, rowCharacter);
                    CheckTwinSeatRowJ(columnIndex, 11, 12, 6, rowCharacter);
                    CheckTwinSeatRowJ(columnIndex, 13, 14, 6, rowCharacter);
                    CheckTwinSeatRowJ(columnIndex, 15, 16, 6, rowCharacter);
                    CheckTwinSeatRowJ(columnIndex, 21, 22, 10, rowCharacter);
                }
                else if(rowCharacter=='K') {
                    //Row K
                    CheckTwinSeatRowJ(columnIndex, 9, 10, 8, rowCharacter);
                    CheckTwinSeatRowJ(columnIndex, 11, 12, 8, rowCharacter);
                    CheckTwinSeatRowJ(columnIndex, 13, 14, 8, rowCharacter);
                    CheckTwinSeatRowJ(columnIndex, 15, 16, 8, rowCharacter);
                }


        }
        else if(rowCharacter=='A'){
            String seatTextString =String.valueOf(rowCharacter) +(columnIndex-6);
            BottomGridClearText(seatTextString);
        }
        else{
            if (columnIndex ==4 || columnIndex==3){
                String seatTextString =String.valueOf(rowCharacter) +(columnIndex-2);
                BottomGridClearText(seatTextString);
            }
            else if(columnIndex>=7 && columnIndex<=18){
                String seatTextString =String.valueOf(rowCharacter) +(columnIndex-4);
                BottomGridClearText(seatTextString);
            }
            else if(columnIndex==21 || columnIndex==22){
                String seatTextString =String.valueOf(rowCharacter) +(columnIndex-6);
                BottomGridClearText(seatTextString);

            }
        }





    }

    public void BottomGridClearText(String seatTextString){
        if (seatText.getText().contains(seatTextString)) {
            String y= seatText.getText().replaceAll(seatTextString +" ","");
            seatText.setText(y);
        }else{
            seatText.setText(seatText.getText() +seatTextString +" ");
        }

    }

    public void CheckTwinSeatRowJ(int columnIndex, int firstSeat, int secondSeat, int minusIndex,char rowCharacter){
        if(columnIndex==firstSeat || columnIndex==secondSeat){
            //The minus index makes the column no and the shown num the same
            int columnNum = columnIndex - minusIndex;
            if (columnIndex == firstSeat) {
                for (int x = 0; x < 2; x++) {
                    String seatTextString =String.valueOf(rowCharacter) +columnNum;
                    BottomGridClearText(seatTextString);

                    columnNum = columnNum + 1;
                }

            }
            else if(columnIndex==secondSeat){
                for (int x = 0; x < 2; x++) {
                    String seatTextString =String.valueOf(rowCharacter) +columnNum;
                    BottomGridClearText(seatTextString);

                    columnNum = columnNum - 1;
                }
            }
        }
    }




    public void bottomLineAutoResize(){
        DoubleProperty doubleProperty = new SimpleDoubleProperty(30);
        line.endXProperty().bind(showOrderPane.layoutXProperty().add(showOrderPane.widthProperty()).subtract(doubleProperty));
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../layout/BookingPage.fxml"));
        Scene testScene = new Scene(root);
        primaryStage.setScene(testScene);
        primaryStage.show();
    }

    public static void main(String [] args){
        launch();
    }
}
