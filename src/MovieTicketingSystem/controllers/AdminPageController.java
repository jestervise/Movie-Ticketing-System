package MovieTicketingSystem.controllers;

import MovieTicketingSystem.Main;
import com.jfoenix.controls.JFXButton;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.ScaleTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.Collection;
import java.util.ResourceBundle;
import java.util.stream.Collectors;


public class AdminPageController implements Initializable {
    @FXML
    AnchorPane basePane;
    Stage stage;
    double xOffset,yOffset;
    @FXML
    Label adminName;
    @FXML
    TableView<TableData> table;
    @FXML
    Pane addButton;

    @FXML
    public void MousePressedTransition(MouseEvent event){
        ((AnchorPane)event.getSource()).setEffect(null);
        for( Node x:((AnchorPane)event.getSource()).getChildren()){
            x.setEffect(null);
        }
        ((AnchorPane)event.getSource()).setEffect(new InnerShadow());

        for( Node x:((AnchorPane)event.getSource()).getChildren()){
            x.setEffect(new Glow());
        }
    }

    public void SetAdminName(String name){
        adminName.setText(name);
    }

    public void SetUpTable(){
        Collection<TableData> list = null;
        try {
            list = Files.readAllLines(new File("MovieDetails.txt").toPath())
                    .stream()
                    .map(line -> {
                        String[] details = line.split("\t");
                        TableData cd = new TableData();
                        cd.setBookingTime(details[0]);
                        cd.setShowingHour(details[6]);
                        cd.setShowingMinutes(details[7]);
                        cd.setMovie(details[4]);
                        cd.setCinema(details[1]);
                        cd.setPrice(details[5]);
                        cd.setSeat(details[2]);
                        return cd;
                    })
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }

        ObservableList<TableData> details = FXCollections.observableArrayList(list);

        TableColumn<TableData, String> col1 = new TableColumn<>("Booking Time");
        TableColumn<TableData, String> col2 = new TableColumn<>("Showing Time");
        TableColumn<TableData, String> col3 = new TableColumn<>();
        TableColumn<TableData, String> col4 = new TableColumn<>("Movie");
        TableColumn<TableData, String> col5 = new TableColumn<>("Cinema");
        TableColumn<TableData, String> col6 = new TableColumn<>("Price");
        TableColumn<TableData, String> col7 = new TableColumn<>("Seat");

        table.getColumns().addAll(col1, col2, col3, col4,col5,col6,col7);

        col1.setCellValueFactory(data -> data.getValue().bookingTimeProperty());
        col2.setCellValueFactory(data -> data.getValue().showingHourProperty());
        col3.setCellValueFactory(data -> data.getValue().showingMinutesProperty());
        col4.setCellValueFactory(data -> data.getValue().movieProperty());
        col5.setCellValueFactory(data -> data.getValue().cinemaProperty());
        col6.setCellValueFactory(data -> data.getValue().priceProperty());
        col7.setCellValueFactory(data -> data.getValue().seatProperty());

        table.setItems(details);
    }

    @FXML
    void AddRecord(){
        Stage newStage = new Stage();
        newStage.initStyle(StageStyle.UNDECORATED);
        newStage.initModality(Modality.APPLICATION_MODAL);

        try {

            FXMLLoader loader =new FXMLLoader(getClass().getResource("../layout/AddRecordScene.fxml"));
            Parent root = loader.load();
            AddRecordSceneController addRecordSceneController = loader.getController();
            Scene scene = new Scene(root);
            newStage.setScene(scene);
            newStage.show();
            addRecordSceneController.SetUpStage(newStage);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @FXML
    public void MouseReleasedTransition(MouseEvent event){
        ((AnchorPane)event.getSource()).setEffect(null);
        for( Node x:((AnchorPane)event.getSource()).getChildren()){
            x.setEffect(new InnerShadow());
        }
        ((AnchorPane)event.getSource()).setEffect(new DropShadow());

        for( Node x:((AnchorPane)event.getSource()).getChildren()){
            x.setEffect(null);
        }
    }

    public void FadeAnimation(){
        //Fade animation
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(500),basePane);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.play();
    }

    //Allow the user to move the main stage freely
    protected void MoveStage(){
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
    private void ScaleUpButton(MouseEvent event){
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(200), (Node)event.getSource());
        scaleTransition.setToX(1.7);
        scaleTransition.setToY(1.7);
        scaleTransition.setCycleCount(1);
        scaleTransition.setInterpolator(Interpolator.EASE_BOTH);
        scaleTransition.play();
    }

    @FXML
    private void ScaleDownButton(MouseEvent event){
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(200), (Node)event.getSource());
        scaleTransition.setToX(1.5);
        scaleTransition.setToY(1.5);
        scaleTransition.setCycleCount(1);
        scaleTransition.setInterpolator(Interpolator.EASE_BOTH);
        scaleTransition.play();
    }

    @FXML
    private void CloseWindow(){
        Stage promptText =new Stage ();

        VBox root = new VBox();
        root.setAlignment(Pos.CENTER);
        root.setPrefHeight(400.0);
        root.setPrefWidth(600.0);
        root.setStyle("-fx-background-color: #242E44;");

        Label prompt = new Label("Are You Sure You Want To Logout?");
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
            Parent root2=null;
            try {
                root2 = FXMLLoader.load(getClass().getResource("../layout/MainPage.fxml"));
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            Scene scene2 = new Scene(root2,615,425);
            stage.setScene(scene2);
            ((Stage)((Node)e.getSource()).getScene().getWindow()).close();
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

    @FXML
    public void Exit() throws IOException {
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
            stage.close();
            promptText.close();
        });

        promptText.setScene(scene);
        promptText.setResizable(false);
        promptText.initStyle(StageStyle.UNDECORATED);
        MoveStage(promptText,root);
        promptText.setOpacity(0.8);
        promptText.initModality(Modality.APPLICATION_MODAL);
        promptText.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FadeAnimation();
        SetUpTable();
        MoveStage();
    }
}
