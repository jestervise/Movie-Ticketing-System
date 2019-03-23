/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MovieTicketingSystem.controllers;


import MovieTicketingSystem.Main;
import MovieTicketingSystem.SignUpScene;
import com.jfoenix.controls.*;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

/**
 *
 * @author ChangMingJun
 */
public class MainPageController implements Initializable {
    //The position of the stage
    double xOffset=0;
    double yOffset=0;

    @FXML
    private JFXTextField userNameTextField;
    @FXML
    private JFXButton loginButton;
    @FXML
    private BorderPane mainPane;
    @FXML
    private JFXPasswordField passwordTextField;
    @FXML
    private ImageView promotionImages;
    @FXML
    private JFXTextArea promotionText;
    @FXML
    private Text registerText;
    @FXML
    private Text closeButton;
    @FXML
    private Label promptTextField;
    @FXML
    private JFXCheckBox rememberMeCheckBox;

    String adminUserName;

    //The main register stage from Main function
    private Stage stage ;
    int slideshowCount=0;

    @FXML
    private void UserNameValidation(){

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        PlayMusic();
        MoveStage();
        SetRememberMeText();
        userNameTextField.textProperty().addListener((v,oldValue,newValue)-> {
            if(newValue.contains(" ")){
                promptTextField.setText("Invalid format");
                loginButton.setDisable(true);
            }
            else{
                promptTextField.setText("");
                loginButton.setDisable(false);
            }
        } );
        FadeAnimation();
        SetPromotionsImagesSlider();
    }

    public void SetRememberMeText(){
        File file = new File("RememberMeText.txt");
        FileReader in = null;
        try {
            in = new FileReader(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedReader fp = new BufferedReader(in);
        String buffer;

        try {

            while ((buffer=fp.readLine())!=null){
                userNameTextField.setText(buffer);
            }
            fp.close();
        }catch (IOException e){
            e.printStackTrace();
        }


    }


    public void FadeAnimation(){
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(1000),mainPane);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.play();

    }

    @FXML
    public void RegisterTextHover(){
        registerText.setStyle("-fx-underline: true");
    }

    @FXML
    public void RegisterTextLeave(){
        registerText.setStyle("-fx-underline: false");
    }

    @FXML
    public void ScaleUpCloseButton(){
        ScaleTransition scaleTransition = new ScaleTransition();
        scaleTransition.setDuration(Duration.millis(200));
        scaleTransition.setNode(closeButton);
        scaleTransition.setToX(1.5);
        scaleTransition.setToY(1.5);
        scaleTransition.setCycleCount(1);
        scaleTransition.setAutoReverse(true);
        scaleTransition.play();
        /*closeButton.setScaleX(1.5);
        closeButton.setScaleY(1.5);
    */
    }

    public void SetPromotionsImagesSlider(){
        File [] files = {new File("MovieTicketingSystem/rsc/ticket.png"),
                new File("MovieTicketingSystem/rsc/promotion.png"),new File("MovieTicketingSystem/rsc/popCorn.png")};
        Image [] promotionImage = {new Image(String.valueOf(files[0])),new Image(String.valueOf(files[1])),
                new Image(String.valueOf(files[2]))};
        String promotionNoText[] ={"You can buy ticket online !","Various event and promotions!","You can buy various food and beverages in dasKino!"};


        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                int x =0;
                while (true) {
                    Thread.sleep(2500);
                    promotionImages.setImage(promotionImage[x]);
                    promotionText.setText(promotionNoText[x]);
                    if(x==2){
                        x=0;
                    }else{
                        x++;
                    }
                }
            }
        };

        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }

    @FXML
    public void ScaleDownCloseButton(){
        ScaleTransition scaleTransition = new ScaleTransition();
        scaleTransition.setDuration(Duration.millis(200));
        scaleTransition.setNode(closeButton);
        scaleTransition.setToY(1);
        scaleTransition.setToX(1);
        scaleTransition.setCycleCount(1);
        scaleTransition.setAutoReverse(true);
        scaleTransition.play();
        /*closeButton.setScaleX(1);
        closeButton.setScaleY(1);
        */
    }

    @FXML
    public void ScaleDownLoginButton(){
        ScaleTransition scaleTransition = new ScaleTransition();
        scaleTransition.setDuration(Duration.millis(200));
        scaleTransition.setNode(loginButton);
        scaleTransition.setToY(1);
        scaleTransition.setToX(1);
        scaleTransition.setCycleCount(1);
        scaleTransition.setAutoReverse(true);
        scaleTransition.play();
    }

    @FXML
    public void ScaleUpLoginButton(){
        ScaleTransition scaleTransition = new ScaleTransition();
        scaleTransition.setDuration(Duration.millis(200));
        scaleTransition.setNode(loginButton);
        scaleTransition.setToX(1.1);
        scaleTransition.setToY(1.1);
        scaleTransition.setCycleCount(1);
        scaleTransition.setAutoReverse(true);
        scaleTransition.play();
    }

    void WriteUserName(){

        File file = new File("RememberMeText.txt");
        if(rememberMeCheckBox.isSelected()) {
            try {
                FileWriter out = new FileWriter(file);
                BufferedWriter fp = new BufferedWriter(out);
                adminUserName=userNameTextField.getText();
                fp.write(userNameTextField.getText());
                fp.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    // Play pop sound when the scene is loaded
    private void PlayMusic(){
        Media media = new Media("http://soundbible.com/grab.php?id=2067&type=mp3");
        MediaPlayer player = new MediaPlayer(media);
        player.play();
        ButtonScalingAnimation();
    }


    private void ButtonScalingAnimation(){
        ScaleTransition scaleTransition = new ScaleTransition();
        scaleTransition.setDuration(Duration.millis(500));
        scaleTransition.setNode(loginButton);
        scaleTransition.setByY(0.1);
        scaleTransition.setByX(0.1);
        scaleTransition.setCycleCount(4);
        scaleTransition.setAutoReverse(true);
        scaleTransition.play();
    }


    //Allow the user to move the main stage freely
    private void MoveStage(){
        stage= Main.registerStage;
        //Set the position of the stage when they are pressed
        mainPane.setOnMousePressed(e-> {

                xOffset = e.getSceneX();
                yOffset = e.getSceneY();
                });
        mainPane.setOnMouseDragged(e-> {
                stage.setX(e.getScreenX() - xOffset);
                stage.setY(e.getScreenY() - yOffset);

        });
    }

    //Allow user to move the prompt stage to move
    private void MoveStage(Stage newStage,Pane scene){
        //Set the position of the stage when they are pressed

        scene.setOnMousePressed(e-> {

            xOffset = e.getSceneX();
            yOffset = e.getSceneY();
        });
        scene.setOnMouseDragged(e-> {
            newStage.setX(e.getScreenX() - xOffset);
            newStage.setY(e.getScreenY() - yOffset);

        });
    }

        @FXML
        private void RegisterButtonClicked() throws IOException {
            try {
                SignUpScene scene=new SignUpScene(stage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @FXML
        private void RegisterButtonPressed(){
            registerText.setFill(Paint.valueOf("#59C7C6"));
        }

        @FXML
        private void RegisterButtonReleased(){
        registerText.setFill(Paint.valueOf("#FFFFFF"));
        }
        @FXML
        //Set the Close function of the "X" Button
        public void CloseWindowButtons() throws IOException{

            //GUI, Set up the prompt window
            Stage promptText =new Stage ();

            VBox root = new VBox();
            root.setAlignment(Pos.CENTER);
            root.setPrefHeight(400.0);
            root.setPrefWidth(600.0);
            root.setStyle("-fx-background-color: #242E44;");

            Label prompt = new Label("Are You Sure You Want To Exit?");
            prompt.setStyle("-fx-font-weight: bold; -fx-font-size: 20;-fx-text-fill:WHITE");
            prompt.setPadding(new Insets(0,0,20,0));

            HBox bottomButtons = new HBox();
            bottomButtons.setSpacing(20.0);
            bottomButtons.setAlignment(Pos.CENTER);
            JFXButton yesButton = new JFXButton("YES");
            yesButton.setPrefHeight(51.0);
            yesButton.setPrefWidth(94.0);
            yesButton.setStyle("-fx-background-color: #59C7C6;-fx-text-fill:WHITE");

            Region region = new Region();
            region.setPrefHeight(96.0);
            region.setPrefWidth(50.0);

            JFXButton noButton = new JFXButton("NO");
            noButton.setPrefHeight(51.0);
            noButton.setPrefWidth(94.0);
            noButton.setStyle("-fx-background-color: #59C7C6;-fx-text-fill:WHITE");

            bottomButtons.getChildren().addAll(yesButton,region,noButton);

            root.getChildren().addAll(prompt,bottomButtons);

            Scene scene= new Scene(root,425,200);

            //No button close the prompt stage
            noButton.setOnAction(e-> promptText.close());

            //YesButton to close the prompt stage and main stage
            yesButton.setOnAction(e->{
                promptText.close();
                stage.close();
            });

            promptText.setScene(scene);
            promptText.setResizable(false);
            promptText.initStyle(StageStyle.UNDECORATED);
            MoveStage(promptText,root);
            promptText.setOpacity(0.8);
            promptText.initModality(Modality.APPLICATION_MODAL);
            promptText.show();


        }

    @FXML
    public void ValidateUser() throws IOException {
        boolean showText=true;
        String dataBase[]=null;
        try {
            dataBase=scanUserFile();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        //Validate if both user field and password field is not empty
        if((!userNameTextField.getText().isEmpty()) && (!passwordTextField.getText().isEmpty())){

            boolean userNameSentinel;
            boolean passwordSentinel;
            boolean adminSentinel;
            try {
            for(int x=0;x<dataBase.length;x++) {
                            String y[]=dataBase[x].split(" ");
                            if (y[0].compareTo(userNameTextField.getText().trim())==0)
                                userNameSentinel = true;
                            else
                                userNameSentinel = false;


                            if(y[1].compareTo(passwordTextField.getText().trim())==0)
                                passwordSentinel=true;
                            else
                                passwordSentinel=false;

                            if(y[2].compareTo("admin")==0)
                                adminSentinel=true;
                            else
                                adminSentinel=false;

                if(passwordSentinel && userNameSentinel && !adminSentinel) {
                    WriteUserName();
                    ChangeToHomePage();
                    showText=false;
                    break;
                }else if(passwordSentinel && userNameSentinel){
                    WriteUserName();
                    ChangeToAdminPage();
                    showText=false;
                    break;
                }

                }}catch (NullPointerException e2){

            }

            userNameTextField.clear();
            passwordTextField.clear();
            if(showText==true) {
                promptTextField.setText("User name or password incorrect, please try again!");
            }
            }

            else if((!userNameTextField.getText().isEmpty()) || (!passwordTextField.getText().isEmpty())){
            userNameTextField.clear();
            passwordTextField.clear();
            promptTextField.setText("Please do not leave the field empty");
        }





    }


    public void ChangeToAdminPage(){

        FadeTransition fadeTransition = new FadeTransition(Duration.millis(500),mainPane);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.play();
        fadeTransition.setOnFinished(e->{
            Parent root = null;
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../layout/AdminPage.fxml"));
                root = loader.load();
                AdminPageController adminPageController = loader.getController();
                adminPageController.SetAdminName(adminUserName);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            Scene scene =new Scene(root,1200,700);
            Stage stage = (Stage)mainPane.getScene().getWindow();
            stage.setScene(scene);

        });
    }

    public void ChangeToHomePage(){
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(500),mainPane);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.play();
        fadeTransition.setOnFinished(e->{
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("../layout/HomePage.fxml"));
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            Scene scene =new Scene(root,1200,700);
            Stage stage = (Stage)mainPane.getScene().getWindow();
            stage.setScene(scene);

        });
    }


    //Scan user database(txt file)
    public String[] scanUserFile() throws URISyntaxException {
        FileReader in;

        //Create table
        String[] table = new String[40];
        File file = new File("UserData.txt");
        try {
            //Read file
            in = new FileReader(file);
            BufferedReader fp = new BufferedReader(in);
            String buffer;
            //read the file and store all the text to buffer
            for(int x=0;(buffer=fp.readLine())!=null;x++) {
               table[x]=buffer;
            }


            fp.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error reading file");
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return table;


    }

}
