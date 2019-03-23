package MovieTicketingSystem.controllers;

import MovieTicketingSystem.Main;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.time.temporal.Temporal;
import java.util.ResourceBundle;

import static MovieTicketingSystem.Main.registerStage;

public class SignUpSceneController implements Initializable {
    private Stage stage ;
    @FXML
    Pane mainPane;

    @FXML
    JFXTextField userNameTextField;
    @FXML
    Label   userNamePromptTextField;
    @FXML
    JFXTextField emailTextField;
    @FXML
    Label   emailPromptTextField;
    @FXML
    JFXPasswordField passwordTextField;
    @FXML
    Label   passwordPromptTextField;
    @FXML
    JFXButton registerButton;
    @FXML
    ImageView promotionImages;

    //The position of the stage
    double xOffset=0;
    double yOffset=0;

    Parent root;

    {
        try {
            root = FXMLLoader.load(getClass().getResource("../layout/MainPage.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    ImageView backButton;

    @FXML
    Text closeButton;

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
            Platform.exit();
        });

        promptText.setScene(scene);
        promptText.setResizable(false);
        promptText.initStyle(StageStyle.UNDECORATED);
        MoveStage(promptText,root);
        promptText.setOpacity(0.8);
        promptText.initModality(Modality.APPLICATION_MODAL);
        promptText.show();


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
    public void ScaleUpBackButton(){
        ScaleTransition scaleTransition = new ScaleTransition();
        scaleTransition.setDuration(Duration.millis(200));
        scaleTransition.setNode(backButton);
        scaleTransition.setToX(1.5);
        scaleTransition.setToY(1.5);
        scaleTransition.setCycleCount(1);
        scaleTransition.setAutoReverse(true);
        scaleTransition.play();
        /*closeButton.setScaleX(1.5);
        closeButton.setScaleY(1.5);
    */
    }

    @FXML
    public void ScaleDownBackButton(){
        ScaleTransition scaleTransition = new ScaleTransition();
        scaleTransition.setDuration(Duration.millis(200));
        scaleTransition.setNode(backButton);
        scaleTransition.setToY(1);
        scaleTransition.setToX(1);
        scaleTransition.setCycleCount(1);
        scaleTransition.setAutoReverse(true);
        scaleTransition.play();
        /*closeButton.setScaleX(1);
        closeButton.setScaleY(1);
        */
    }


    //Allow the user to move the main stage freely
    private void MoveStage() {
        //Set the position of the stage when they are pressed
        mainPane.setOnMousePressed(e -> {

            xOffset = e.getSceneX();
            yOffset = e.getSceneY();
        });
        mainPane.setOnMouseDragged(e -> {
            registerStage.setX(e.getScreenX() - xOffset);
            registerStage.setY(e.getScreenY() - yOffset);

        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        MoveStage();
        UserNameListener();
        PasswordListener();
        SetPromotionsImagesSlider();
        registerButton.setDisable(true);
    }


    public void UserNameListener() {
        userNameTextField.textProperty().addListener((v, oldValue, newValue) -> {
            if (CheckUserNameCharacterSequence(newValue)) {
                userNamePromptTextField.setText("Invalid format");
                RegisterButtonDisable();
            }
            else if(!(newValue.length() >= 6) && newValue.length()!=0) {
                userNamePromptTextField.setText("The user name should be more than 6 letters");
                RegisterButtonDisable();
            } else {
                userNamePromptTextField.setText("");
                RegisterButtonEnable();
            }
        });
    }

    public void PasswordListener() {
        passwordTextField.textProperty().addListener((v, oldValue, newValue) -> {
            if(!(newValue.length() >= 6) && newValue.length()!=0) {
                passwordPromptTextField.setText("The password should be more than 6 letters");
                RegisterButtonDisable();
            } else {
                passwordPromptTextField.setText("");
                RegisterButtonEnable();
            }
        });
    }

    public void SaveUserData(){
        File file = new File("UserData.txt");

        try {
            FileWriter out = new FileWriter(file,true);
            BufferedWriter fp = new BufferedWriter(out);
            fp.append(userNameTextField.getText()+" "+passwordTextField.getText()+" " +"normal"+"\n");
            userNameTextField.clear();
            passwordTextField.clear();
            emailTextField.clear();
            fp.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void RegisterButtonDisable(){registerButton.setDisable(true);}

    public void RegisterButtonEnable(){registerButton.setDisable(false);}

    @FXML
    public void ValidateUser(ActionEvent event){
        if(userNameTextField.getText().isEmpty() || passwordTextField.getText().isEmpty()
                || emailTextField.getText().isEmpty()) {
            event.consume();
            registerButton.setDisable(true);
        }else{

        if(!emailTextField.getText().contains("@") || !emailTextField.getText().contains(".com")){
            emailPromptTextField.setText("Invalid email address");
        }else{
            emailPromptTextField.setText("");
            SaveUserData();
            FadeAnimation();
        }

        }
    }

    void FadeAnimation(){
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(500),mainPane);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.play();
        fadeTransition.setOnFinished(e-> registerStage.setScene(new Scene(root, 615, 425)));

    }

    public boolean CheckUserNameCharacterSequence(String newValue){
         if(newValue.contains(" ") || newValue.contains(",") || newValue.contains("/")
         || newValue.contains(".") || newValue.contains("!") || newValue.contains("@")
         || newValue.contains("#") || newValue.contains("$") || newValue.contains("%")
         || newValue.contains("^") || newValue.contains("&") || newValue.contains("*")
         || newValue.contains("(") || newValue.contains(")") || newValue.contains("-")
         || newValue.contains("_") || newValue.contains("+") || newValue.contains("=")
         || newValue.contains("\\") || newValue.contains("~") || newValue.contains("<")
         || newValue.contains(">") || newValue.contains("?") || newValue.contains("`")
         || newValue.contains(":") || newValue.contains(";") || newValue.contains("[")
         || newValue.contains("]") || newValue.contains("{") || newValue.contains("}")
         || newValue.contains("|") || newValue.contains("'"))
         {return true;}
         else{return false;}
    }

    @FXML
    public void BackToMainPage() throws IOException {

        registerStage.setScene(new Scene(root, 615, 425));

    }

    public void SetPromotionsImagesSlider() {
        File[] files = {new File("MovieTicketingSystem/rsc/ticket.png"),
                new File("MovieTicketingSystem/rsc/promotion.png"), new File("MovieTicketingSystem/rsc/popCorn.png")};
        Image[] promotionImage = {new Image(String.valueOf(files[0])), new Image(String.valueOf(files[1])),
                new Image(String.valueOf(files[2]))};

        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                int x = 0;
                while (true) {
                    promotionImages.setImage(promotionImage[x]);

                    if (x == 2) {
                        x = 0;
                    } else {
                        x++;
                    }
                    Thread.sleep(2500);
                }
            }
        };
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }
}
