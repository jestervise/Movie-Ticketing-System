package MovieTicketingSystem.controllers;

import MovieTicketingSystem.Main;
import com.jfoenix.controls.*;
import com.jfoenix.effects.JFXDepthManager;
import com.jfoenix.transitions.hamburger.HamburgerSlideCloseTransition;
import javafx.animation.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.effect.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Paint;
import javafx.scene.transform.Rotate;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import sun.audio.AudioPlayer;

import java.io.*;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class HomePageController implements Initializable{


    String [] youtubeLink = {"https://www.youtube.com/embed/i5qOzqD9Rms?rel=0&amp;controls=0&amp;showinfo=0&amp;start=2;autoplay=0",
            "https://www.youtube.com/embed/1FJD7jZqZEk?rel=0&amp;controls=0&amp;showinfo=0;autoplay=0",
            "https://www.youtube.com/embed/coOKvrsmQiI?rel=0&amp;control=0&amp;showinfo=0;autoplay=0",
            "https://www.youtube.com/embed/xZNBFcwd7zc?rel=0&amp;controls=0&amp;showinfo=0;autoplay=0"
    };

    @FXML
    private AnchorPane centerAnchorPane;
    @FXML
    ImageView incredibleImage;
    @FXML
    ImageView dragonImage;
    @FXML
    ImageView rampageImage;
    @FXML
    ImageView deadPool2Image;
    @FXML
    BorderPane basePane;
    @FXML
    private ImageView logo;
    @FXML
    private Label sloganText1;
    @FXML
    private Label sloganText2;
    @FXML
    private Pane leftPane;
    @FXML
    private Pane descriptionBox;
    @FXML
    private Label movieDescription;
    @FXML
    private JFXNodesList hamburgerNodeList;
    @FXML
    private JFXHamburger hamburgerNode;
    @FXML
    private ImageView chooseTimeButton;
    @FXML
    private AnchorPane showTimePane;
    @FXML
    private JFXComboBox<String> cinemaComboBox;
    @FXML
    private AnchorPane showTimeDisplay;
    @FXML
    private ImageView manageBookingButton;
    @FXML
    private JFXButton bookTicketButton;
    @FXML
    private Label bookingPromptText;
    @FXML
    private ScrollBar scrollBar;


    private AudioClip audio;
    int musicIsPlaying=1;
    private double xOffset;
    private double yOffset;
    //The main register stage from Main function
    private Stage stage ;

    int showTimePaneIsOpened=-1;
    boolean dateSentinel =false;
    boolean timeSentinel=false;

    public static Stage thisStage;

    boolean transitionIsPlaying=false;

    JFXDatePicker datePicker;
    JFXTimePicker timePicker;

    String choosenMovie="";
    String cinemaText="";

    String movieDescriptionText[]={
            //Incredibles 2
                "In “Incredibles 2,” Helen is called on to lead a campaign to bring Supers back, while Bob navigates the day-to-day heroics of “normal” " +
                "life at home with Violet, Dash and baby Jack-Jack—whose super powers are about to be discovered." +
                " Their mission is derailed, however, when a new villain emerges with a brilliant and dangerous plot that threatens everything." +
                " But the Parrs don’t shy away from a challenge, especially with Frozone by their side. That’s what makes this family so Incredible.",
            //Jurrasic World 2
                "It’s been three years since theme park and luxury resort Jurassic World was destroyed by dinosaurs out of containment.  " +
                "Isla Nublar now sits abandoned by humans while the surviving dinosaurs fend for themselves in the jungles.",
            //Rampage
                "Based on the classic 1980s video game featuring apes and monsters destroying cities.",
            //Dead pool 2
                "Wade Wilson is back as the violent and vulgar anti-superhero Deadpool. As he goes on with his life\n" +
                "fighting bad guys, he is suddenly visited by Cable who comes from the future. Cable is on a mission to kill\n" +
                "a boy with special fire based powers. Witnessing Cable&#39;s sinister intentions, Deadpool tries to protect\n" +
                "the boy which leads him to getting beaten to a pulp, but that does not stop Deadpool as he returns and\n" +
                "forms a team of superheroes called X-Force to go up against the super soldier."
    };

    JFXDepthManager depthManager;

    JFXRippler jurrasicripper;
    JFXRippler incredibleripper;
    JFXRippler rampageripper;
    JFXRippler deadpool2ripper;

    WebView youtubePlayer;

    JFXRippler lastClickedInfo;

    boolean iconMoved=false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FadeAnimation();
        depthManager = new JFXDepthManager();
        setImageRippersAndGlow();
        setImageHover();
        initializeWebPlayer();
        HamburgerNodeFormatting();
        PlayMusic();
        MoveStage();
        AddLabelToComboBox();
        initDatePicker();
        StoreTimeWhenImplicitLogout();
        ValidateCinemaInfo();
    }

    public void ValidateCinemaInfo(){
        if(timePicker.getValue().getHour()>2 && timePicker.getValue().getHour()<10){
            bookingPromptText.setText("The ticket is not available at this time");
            timeSentinel=false;
        }
        else {
            bookingPromptText.setText("");
            timeSentinel = true;
        }
        datePicker.valueProperty().addListener((v,oldValue,newValue)->{
            if(newValue.getDayOfYear()>LocalDate.now().getDayOfYear()+8){
                bookingPromptText.setText("The ticket on this day is not available yet");
                dateSentinel =false;
            }
            else if(newValue.getDayOfYear()<LocalDate.now().getDayOfYear()-8){
                bookingPromptText.setText("The ticket on this day is not available");
                dateSentinel =false;
            }
            else {
                bookingPromptText.setText("");
                dateSentinel =true;
            }
            if(dateSentinel ==true && timeSentinel==true && cinemaText!=""){
                bookTicketButton.setDisable(false);
            }
            else {
                bookTicketButton.setDisable(true);
            }
        });

        timePicker.valueProperty().addListener((v,oldValue,newValue)->{
            if(newValue.getHour()>2 && newValue.getHour()<10){
                bookingPromptText.setText("The ticket is not available at this time");
                timeSentinel=false;
            }
            else {
                bookingPromptText.setText("");
                timeSentinel=true;

            }

            if(dateSentinel ==true && timeSentinel==true){
                bookTicketButton.setDisable(false);
            }else{
                bookTicketButton.setDisable(true);
            }
        });
        bookTicketButton.setOnMouseClicked(e->ChangeToBookingScene(e));
    }

    public void ChangeToBookingScene(MouseEvent event){
        //Write the date and time into movie date time.txt file
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File("MovieDateTime.txt")));
            bufferedWriter.append(datePicker.getValue().toString() +"\t"+timePicker.getValue().getHour()+" "+timePicker.getValue().getMinute()+"\n");
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Parent root=null;
        try {
            FXMLLoader loader =new FXMLLoader(getClass().getResource("../layout/BookingPage.fxml"));
            root =  loader.load();
            BookingPageController bookingPageController = loader.getController();
            bookingPageController.SetMovieText(choosenMovie);
            bookingPageController.SetCinemaText(cinemaText);

        } catch (IOException e) {
            e.printStackTrace();
        }

        Scene scene = new Scene(root);
        Stage myStage=(Stage)((Node) event.getSource()).getScene().getWindow();



        //Fade animation
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(1000),basePane);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.play();
        fadeTransition.setOnFinished(e->myStage.setScene(scene));
    }

    public void StoreTimeWhenImplicitLogout(){
        Main.registerStage.setOnCloseRequest(e->{
            try {
                StoreLogOutTime();
            } catch (IOException e1) {
                e1.printStackTrace();
            }

        });
        stage.setOnCloseRequest(e->{
            try {
                StoreLogOutTime();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
    }

    public void FadeAnimation(){
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(500),basePane);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.play();
    }

    public void initDatePicker(){
        datePicker = new JFXDatePicker();
        datePicker.setPrefWidth(235);
        showTimeDisplay.getChildren().add(datePicker);
        AnchorPane.setTopAnchor(datePicker,150d);
        AnchorPane.setLeftAnchor(datePicker,115d);
        datePicker.getStylesheets().add("MovieTicketingSystem/stylesheets/datePickerStyle.css");
        timePicker = new JFXTimePicker(LocalTime.now().minusMinutes(LocalTime.now().getMinute()));
        timePicker.setPrefWidth(235);
        timePicker.setDefaultColor(Paint.valueOf("#59C7C6"));
        timePicker.getStylesheets().add("MovieTicketingSystem/stylesheets/timePickerStyle.css");
        showTimeDisplay.getChildren().add(timePicker);
        AnchorPane.setTopAnchor(timePicker,200d);
        AnchorPane.setLeftAnchor(timePicker,115d);


    }

    @FXML
    void ManageBookingButtonOnClicked() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../layout/ManageBookingScene.fxml"));
        Stage stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setResizable(false);
        Scene scene =new Scene(root,640,500);
        stage.setScene(scene);
        CheckStageClosed(stage);
        stage.show();
    }


    public void CheckStageClosed(Stage stage){
        Lighting monochrome = new Lighting();
        monochrome.setLight(new Light.Distant());
        monochrome.setContentInput(new GaussianBlur());

        Task <Void>newTask = new Task<Void>() {
            @Override
            protected Void call() {
                while(true)
                if(stage.isShowing()) {

                    basePane.setEffect(monochrome);
                }
                else{
                    basePane.setEffect(null);
                    break;

                }
                return null;
            }
        };
        //start Task
        new Thread(newTask).start();

    }

    public void HamburgerNodeFormatting(){
        hamburgerNodeList.getChildren().get(1).setTranslateY(10);
        HamburgerSlideCloseTransition xButton = new HamburgerSlideCloseTransition(hamburgerNode);
        // -1 is hamburger icon 1 is X icon
        xButton.setRate(-1);

        hamburgerNodeList.getChildren().get(1).setOnMouseEntered( e->{
            //Label textDescp = new Label("Promotions");
            /*textDescp.setStyle("-fx-text-fill:white");
            textDescp.setLayoutX(hamburgerNodeList.getChildren().get(1).getLayoutX()-10);
            textDescp.setLayoutX(hamburgerNodeList.getChildren().get(1).getLayoutY());
            centerAnchorPane.getChildren().add(textDescp);*/

        });

        hamburgerNode.setOnMouseClicked(e->{
            //If the rate is -1 * -1 will go back to hamburger icon
            xButton.setRate(xButton.getRate()*-1);
            xButton.play();
        });

        //Set node spacing
        hamburgerNodeList.setSpacing(10);
    }

    public void AddLabelToComboBox(){
        StringProperty oneUtama = new SimpleStringProperty("One Utama");
        StringProperty sunway = new SimpleStringProperty("Sunway Pyramid");
        StringProperty ioicity = new SimpleStringProperty("IOI City Mall");
        StringProperty pavillion = new SimpleStringProperty("Pavillion");
        StringProperty starling = new SimpleStringProperty("Starling Mall");
        StringProperty cittaMall = new SimpleStringProperty("Citta Mall");
        cinemaComboBox.getItems().addAll(oneUtama.getValue(),sunway.getValue(),ioicity.getValue(),pavillion.getValue(),starling.getValue(),cittaMall.getValue());
        cinemaComboBox.getSelectionModel().selectedItemProperty().addListener((v,oldValue,newValue)->{
            cinemaText = newValue;
        });
    }

    public void setImageOnMouseClicked(JFXRippler image){

        image.setOnMouseClicked(e->{
           SetGlow(image,e);
           //When finish translate go on play youtube video
            ShowChooseTimeButton();
           IconTranslateAnimation(image);
           disableSlogan();

        });

    }

    public void ShowChooseTimeButton(){
        if(iconMoved==false) {
            Image image = new Image("MovieTicketingSystem/rsc/manageBooking/button_side.png");
            chooseTimeButton.setImage(image);
            TranslateTransition translateTransition = new TranslateTransition(Duration.millis(1000), showTimePane);
            translateTransition.setByX(95);
            translateTransition.setCycleCount(1);
            chooseTimeButton.toFront();
            showTimePane.toFront();
            translateTransition.play();
            chooseTimeButton.setOnMouseClicked(e->{
                FlipAnimation(chooseTimeButton);
                SpawnChooseTimePanel();
            });


        }
    }

    @FXML
    void ScaleDownButton(MouseEvent event) {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(200),(Node)event.getSource());
        scaleTransition.setToX(1);
        scaleTransition.setToY(1);
        scaleTransition.play();
    }

    @FXML
    void ScaleUpButton(MouseEvent event) {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(200),(Node)event.getSource());
        scaleTransition.setToX(1.1);
        scaleTransition.setToY(1.1);
        scaleTransition.play();
    }

    @FXML
    void ChangePageTransition() {
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.millis(100));
        fadeTransition.setInterpolator(Interpolator.EASE_BOTH);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.setCycleCount(1);

    }


    public void SpawnChooseTimePanel(){
        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(1000), showTimePane);
        double transitionValue=405;

        if(showTimePaneIsOpened==-1) {
            transitionIsPlaying=true;
            translateTransition.setByX(transitionValue);
            translateTransition.setCycleCount(1);
            translateTransition.setInterpolator(Interpolator.EASE_BOTH);
            translateTransition.play();

        }else if(showTimePaneIsOpened==1){
            transitionIsPlaying=true;
            translateTransition.setByX(-transitionValue);
            translateTransition.setCycleCount(1);
            translateTransition.setInterpolator(Interpolator.EASE_BOTH);
            translateTransition.play();

        }
        showTimePaneIsOpened=showTimePaneIsOpened*-1;

        if(transitionIsPlaying==true){
            chooseTimeButton.setMouseTransparent(true);
        }

        translateTransition.setOnFinished(e->{
            transitionIsPlaying=false;
            if(transitionIsPlaying==false) {
                chooseTimeButton.setMouseTransparent(false);
            }
        });

    }



    public void FlipAnimation(ImageView chooseTimeButton){

        RotateTransition rotator = new RotateTransition(Duration.millis(200), chooseTimeButton);
        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(200), chooseTimeButton);
        if(showTimePaneIsOpened==-1) {
            rotator.setAxis(Rotate.Y_AXIS);
            rotator.setFromAngle(0);
            rotator.setToAngle(180);
            rotator.setInterpolator(Interpolator.EASE_BOTH);
            rotator.setCycleCount(1);
            rotator.play();
            translateTransition.setByX(-42);
            translateTransition.setCycleCount(1);
            translateTransition.play();
        }
        else{
            rotator.setAxis(Rotate.Y_AXIS);
            rotator.setFromAngle(0);
            rotator.setToAngle(360);
            rotator.setInterpolator(Interpolator.EASE_BOTH);
            rotator.setCycleCount(1);
            rotator.play();
            translateTransition.setByX(42);
            translateTransition.setCycleCount(1);
            translateTransition.play();
        }
    }

    public void initializeWebPlayer(){
        youtubePlayer = new WebView();

        // Youtube prefHeight: 300 prefWidth: 450, layout X 0 layout Y 100 visible false
        youtubePlayer.setPrefSize(450, 300);
        //youtubePlayer.setScaleY(5);
        youtubePlayer.setLayoutX(0);
        youtubePlayer.setLayoutY(100);
        leftPane.getChildren().add(youtubePlayer);
        youtubePlayer.setVisible(false);
        //youtubePlayer.setBlendMode(BlendMode.DARKEN);
    }
    public void playYoutubeVideo( JFXRippler image){
        youtubePlayer.setVisible(true);


        if(image.equals(incredibleripper)) {
            youtubePlayer.getEngine().load(youtubeLink[0]);
            movieDescription.setText(movieDescriptionText[0]);
            choosenMovie="Incredible 2";
            audio.stop();
        }
        else if(image.equals(jurrasicripper)){
            youtubePlayer.getEngine().load(youtubeLink[1]);
            movieDescription.setText(movieDescriptionText[1]);
            choosenMovie="Jurrasic World The Fallen Kingdom";
            audio.stop();
        }
        else if(image.equals((rampageripper))){
            youtubePlayer.getEngine().load(youtubeLink[2]);
            movieDescription.setText(movieDescriptionText[2]);
            choosenMovie="Rampage";
            audio.stop();
        }
        else if(image.equals((deadpool2ripper))){
            youtubePlayer.getEngine().load(youtubeLink[3]);
            movieDescription.setText(movieDescriptionText[3]);
            choosenMovie="Dead Pool 2";
            audio.stop();
        }



    }

    @FXML
    void CloseWindow(){
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
            try {
                StoreLogOutTime();

            } catch (IOException e1) {
                e1.printStackTrace();
            }
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

    public static void StoreLogOutTime() throws IOException {
        File file =new File("LogOutTime.txt");
        LocalDateTime timeNow = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedTimeNow= timeNow.format(formatter);
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        writer.append(formattedTimeNow);
        writer.close();

    }

    public void IconTranslateAnimation(JFXRippler image){
        if(iconMoved==false) {
            TranslateTransition translateTransition = new TranslateTransition();
            translateTransition.setNode(logo);
            translateTransition.setByX(58);
            translateTransition.setByY(-145);
            translateTransition.setCycleCount(1);
            translateTransition.play();

            ScaleTransition scaleTransition = new ScaleTransition();
            scaleTransition.setNode(logo);
            scaleTransition.setByX(-0.2);
            scaleTransition.setByY(-0.2);
            scaleTransition.setCycleCount(1);
            scaleTransition.play();
            translateTransition.setOnFinished(e->{playYoutubeVideo(image);
            TranslateDescriptionBox();
            });
            iconMoved=true;
        }
        else{
            TranslateDescriptionBox();
            playYoutubeVideo(image);
        }
    }

    //Set the Close function of the logout Button
    @FXML
    void LogOut() {

            thisStage=(Stage) basePane.getScene().getWindow();
            //GUI, Set up the prompt window
            Stage promptText =new Stage ();

            VBox root = new VBox();
            root.setAlignment(Pos.CENTER);
            root.setPrefHeight(400.0);
            root.setPrefWidth(600.0);
            root.setStyle("-fx-background-color: #242E44;");

            Label prompt = new Label("Are You Sure You Want To Log Out?");
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
                try {
                    Parent parent = FXMLLoader.load(getClass().getResource("../layout/MainPage.fxml"));
                    Scene scene2 = new Scene(parent,615,425);
                    thisStage.setScene(scene2);
                    StoreLogOutTime();
                    audio.stop();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            });

            promptText.setScene(scene);
            promptText.setResizable(false);
            promptText.initStyle(StageStyle.UNDECORATED);
            MoveStage(promptText,root);
            promptText.setOpacity(0.8);
            promptText.initModality(Modality.APPLICATION_MODAL);
            promptText.show();



    }

    //Allow the user to move the main stage freely
    private void MoveStage(){
        stage= Main.registerStage;
        //Set the position of the stage when they are pressed
        basePane.setOnMousePressed(e-> {

            xOffset = e.getSceneX();
            yOffset = e.getSceneY();
        });
        basePane.setOnMouseDragged(e-> {
            stage.setX(e.getScreenX() - xOffset);
            stage.setY(e.getScreenY() - yOffset);

        });
    }

    @FXML
    void ContactUsButtonClicked() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../layout/ContactPage.fxml"));
        Stage stage=new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        Scene scene = new Scene (root,640,480);
        stage.setScene(scene);
        stage.show();
        CheckStageClosed(stage);
    }

    //Allow user to move the prompt stage to move
    protected void MoveStage(Stage newStage,Pane scene){
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

    public void Testing(String x){
        x="yo";
    }

    @FXML
    void EnterFaqPage() throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("../layout/FaqPage.fxml"));
        Stage stage=new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        Scene scene = new Scene (root,640,480);
        stage.setScene(scene);
        stage.show();
        CheckStageClosed(stage);
    }

    public void PlayMusic(){

                int s = Integer.MAX_VALUE;
                try {
                    audio = new AudioClip(getClass().getResource("/MovieTicketingSystem/rsc/backgroundMusic.mp3").toExternalForm());
                }catch(NullPointerException e){
                    System.out.println("The audio file is not found");
                }

                audio.setVolume(1.0f);
                audio.setCycleCount(s);
                audio.play();


    }

    @FXML
    public void playMusicButton(){
        if(musicIsPlaying==1){
            audio.stop();
        }else{
            audio.play();
        }

        musicIsPlaying=musicIsPlaying*-1;
    }

    @FXML
    public void changeToPromotionPage(){
        Stage newStage = new Stage();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../layout/PromotionPage.fxml"));
            Scene scene = new Scene(root);
            newStage.setScene(scene);
            newStage.initStyle(StageStyle.UNDECORATED);
            newStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void TranslateDescriptionBox(){

        TranslateTransition translateTransition = new TranslateTransition();
        translateTransition.setNode(descriptionBox);
        translateTransition.setToY(-300);
        translateTransition.setCycleCount(1);
        translateTransition.play();
    }



    public void disableSlogan(){
        sloganText1.setVisible(false);
        sloganText2.setVisible(false);
    }

    public void SetGlow(JFXRippler image, MouseEvent e){
        //Set the image glow when clicked
        image.setEffect(new Glow(6.0));

        //If the image clicked is not equal to the image last clicked..
        if(e.getSource()!= lastClickedInfo && lastClickedInfo !=null){
            //Cancel the glow effect of last image
            lastClickedInfo.setEffect(new Glow(0));
        }

        //Store the last click image info into lastClickedInfo
        lastClickedInfo = (JFXRippler) e.getSource();
    }

    public void setImageRippersAndGlow(){
        //Jurrasic Park Rippler
        jurrasicripper=new JFXRippler(dragonImage);
        centerAnchorPane.getChildren().add(jurrasicripper);
        jurrasicripper.setLayoutX(76);
        jurrasicripper.setLayoutY(31);
        setImageOnMouseClicked(jurrasicripper);

        //Incredible 2 rippler
        incredibleripper=new JFXRippler(incredibleImage);
        centerAnchorPane.getChildren().add(incredibleripper);
        incredibleripper.setLayoutX(348);
        incredibleripper.setLayoutY(31);
        setImageOnMouseClicked(incredibleripper);

        //Rampage rippler
        rampageripper=new JFXRippler(rampageImage);
        centerAnchorPane.getChildren().add(rampageripper);
        rampageripper.setLayoutX(76);
        rampageripper.setLayoutY(387);
        setImageOnMouseClicked(rampageripper);

        //Dead pool 2 rippler
        deadpool2ripper=new JFXRippler(deadPool2Image);
        centerAnchorPane.getChildren().add(deadpool2ripper);
        deadpool2ripper.setLayoutX(348);
        deadpool2ripper.setLayoutY(387);
        setImageOnMouseClicked(deadpool2ripper);
    }


    public void setImageHover(){
        depthManager.setDepth(incredibleImage,1);
        depthManager.setDepth(dragonImage,1);
        depthManager.setDepth(rampageImage,1);
        depthManager.setDepth(deadPool2Image,1);

        dragonImage.setOnMouseEntered(e->increaseDepth(dragonImage));
        dragonImage.setOnMouseExited(e->decreaseDepth(dragonImage));

        incredibleImage.setOnMouseEntered(e->increaseDepth(incredibleImage));
        incredibleImage.setOnMouseExited(e->decreaseDepth(incredibleImage));

        rampageImage.setOnMouseEntered(e->increaseDepth(rampageImage));
        rampageImage.setOnMouseExited(e->decreaseDepth(rampageImage));

        deadPool2Image.setOnMouseEntered(e->increaseDepth(deadPool2Image));
        deadPool2Image.setOnMouseExited(e->decreaseDepth(deadPool2Image));
    }

    public void increaseDepth(ImageView movieImage){
        depthManager.setDepth(movieImage,3);
    }

    public void decreaseDepth(ImageView movieImage){
        depthManager.setDepth(movieImage,1);
    }

    @FXML
    public void LinkToAccountSetting() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../layout/AccountProfile.fxml"));
        Stage stage=new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        Scene scene = new Scene (root,640,480);

        stage.setScene(scene);
        stage.show();

        CheckStageClosed(stage);
    }


}
