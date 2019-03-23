package MovieTicketingSystem.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.transitions.JFXFillTransition;
import javafx.animation.Interpolator;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;


import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.util.Collection;
import java.util.ResourceBundle;
import java.util.stream.Collectors;


public class AccountProfileController implements Initializable {

    @FXML
    private AnchorPane basePane;
    @FXML
    private ImageView closeButton;
    @FXML
    private ImageView redeemCodeButton;
    @FXML
    private JFXButton manageBookingButton;
    @FXML
    private ImageView editButton;
    @FXML
    private JFXTextField nameTextField;
    @FXML
    private TableView <TableData> table;
    @FXML
    private AnchorPane redeemCodePane;
    @FXML
    private JFXTextField redeemCodeTextField;

    int editedClicked=-1;

    double xOffset,yOffset;
    Stage newStage;
    int isPressed=-1;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FillTransition();
        initButtonScaleOnHover();
        MoveStage(basePane);
        ButtonChangeColor(redeemCodeButton);
        try {
            SetUpTableData();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void SetUpTableData() throws IOException {
        Collection<TableData> list = Files.readAllLines(new File("MovieDetails.txt").toPath())
                .stream()
                .map(line -> {
                    String[] details = line.split("\t");
                    TableData cd = new TableData();
                    cd.setBookingTime(details[0]);
                    cd.setShowingHour(details[6]);
                    cd.setShowingMinutes(details[7]);
                    cd.setMovie(details[4]);
                    return cd;
                })
                .collect(Collectors.toList());

        ObservableList<TableData> details = FXCollections.observableArrayList(list);

        TableColumn<TableData, String> col1 = new TableColumn<>("Booking Time");
        TableColumn<TableData, String> col2 = new TableColumn<>("Showing Time");
        TableColumn<TableData, String> col3 = new TableColumn<>();
        TableColumn<TableData, String> col4 = new TableColumn<>("Movie");

        table.getColumns().addAll(col1, col2, col3, col4);

        col1.setCellValueFactory(data -> data.getValue().bookingTimeProperty());
        col2.setCellValueFactory(data -> data.getValue().showingHourProperty());
        col3.setCellValueFactory(data -> data.getValue().showingMinutesProperty());
        col4.setCellValueFactory(data -> data.getValue().movieProperty());

        table.setItems(details);

    }

    public void ButtonChangeColor(ImageView button){
        Image onPressedImage= new Image("MovieTicketingSystem/rsc/button_next_blue.png");
        Image onReleasedImage= new Image("MovieTicketingSystem/rsc/button_next.png");
        button.setOnMouseClicked(e->{
            if(isPressed==-1) {
                button.setImage(onPressedImage);
                SetPushTransition();

            }else{
                button.setImage(onReleasedImage);
                SetPullTransition();
            }
            isPressed=isPressed*-1;
        });
    }

    public void SetPushTransition(){
        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(350),redeemCodePane);
        translateTransition.setInterpolator(Interpolator.EASE_BOTH);
        translateTransition.setCycleCount(1);
        translateTransition.setToX(230);
        translateTransition.play();
    }

    public void SetPullTransition(){
        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(350),redeemCodePane);
        translateTransition.setInterpolator(Interpolator.EASE_BOTH);
        translateTransition.setCycleCount(1);
        translateTransition.setToX(0);
        translateTransition.play();
    }

    void SetTableData(){
        String [] buffers = new String[40];
        String [] buffer= new String [40];
        try {
            BufferedReader in = new BufferedReader(new FileReader(new File("MovieDetails.txt")));
            for(int x= 0;(buffers[x]=in.readLine())!=null;x++){
                buffer = buffers[x].split("\t");
                for(String y:buffer) {
                    System.out.println(y);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void ShowPrompt(){
        redeemCodeTextField.clear();
        redeemCodeTextField.setText("The code is not correct");
    }

    @FXML
    void goToManageBookingPage(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../layout/ManageBookingScene.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            ((Stage)((Node)event.getSource()).getScene().getWindow()).setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void ChangeName(){
        if(editedClicked==-1)
        nameTextField.setEditable(true);
        else
        nameTextField.setEditable(false);

        editedClicked=editedClicked*-1;
    }


    //Allow user to move the prompt stage to move
    private void MoveStage(Pane scene){
        //Set the position of the stage when they are pressed

        scene.setOnMousePressed(e-> {
            newStage= (Stage) ((Node)e.getSource()).getScene().getWindow();
            xOffset = e.getSceneX();
            yOffset = e.getSceneY();
        });
        scene.setOnMouseDragged(e-> {
            newStage= (Stage) ((Node)e.getSource()).getScene().getWindow();
            newStage.setX(e.getScreenX() - xOffset);
            newStage.setY(e.getScreenY() - yOffset);

        });
    }

    public void initButtonScaleOnHover(){
        ScaleUpButton(redeemCodeButton);
        ScaleDownButton(redeemCodeButton);

        ScaleUpButton(closeButton);
        ScaleDownButton(closeButton);

        ScaleUpButton(redeemCodeButton);
        ScaleDownButton(redeemCodeButton);

        ScaleUpButton(manageBookingButton);
        ScaleDownButton(manageBookingButton);

        ScaleUpButton(editButton);
        ScaleDownButton(editButton);
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


    void ScaleUpButton(Node button){
        button.setOnMouseEntered(e->{
            ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(200),button);
            scaleTransition.setToX(1.2);
            scaleTransition.setToY(1.2);
            scaleTransition.setInterpolator(Interpolator.EASE_BOTH);
            scaleTransition.setCycleCount(1);
            scaleTransition.play();
        });

    }
    void ScaleDownButton(Node button){
        button.setOnMouseExited(e->{
            ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(200),button);
            scaleTransition.setToX(1);
            scaleTransition.setToY(1);
            scaleTransition.setInterpolator(Interpolator.EASE_BOTH);
            scaleTransition.setCycleCount(1);
            scaleTransition.play();

        });
    }


    @FXML
    void CloseWindow(MouseEvent event) {
        Stage stage = (Stage)(((Node)event.getSource()).getScene().getWindow());
        stage.close();
    }











}
