package MovieTicketingSystem.controllers;

import javafx.animation.Interpolator;
import javafx.animation.ScaleTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;


import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.util.Collection;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ManageBookingSceneController implements Initializable {
    @FXML
    private ImageView closeButton;
    @FXML
    private AnchorPane basePane;
    @FXML
    private Label logoutTimeDisplay;
    @FXML
    private TableView<TableData> upComingMovie;

    Stage newStage;


    double xOffset,yOffset;
    Stage thisStage;


    @FXML
    void CloseWindow() {
        Stage stage =(Stage) basePane.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ScaleUpButton(closeButton);
        ScaleDownButton(closeButton);
        try {
            SetUpTableData(upComingMovie);
            ReadDateTime();
        } catch (IOException e) {
            e.printStackTrace();
        }
        MoveStage();

    }

    public void SetUpTableData(TableView<TableData> table) throws IOException {
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

    //Allow the user to move the main stage freely
    protected void MoveStage(){

        //Set the position of the stage when they are pressed
        basePane.setOnMousePressed(e-> {
            thisStage=(Stage) ((Node)e.getSource()).getScene().getWindow();
            xOffset = e.getSceneX();
            yOffset = e.getSceneY();
        });
        basePane.setOnMouseDragged(e-> {
            thisStage=(Stage) ((Node)e.getSource()).getScene().getWindow();
            thisStage.setX(e.getScreenX() - xOffset);
            thisStage.setY(e.getScreenY() - yOffset);

        });
    }

    @FXML
    public void PopUpDetails(){
        newStage = new Stage();
        newStage.initStyle(StageStyle.TRANSPARENT);
        newStage.initModality(Modality.APPLICATION_MODAL);
        try {
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("../layout/BookingDetails.fxml")));
            scene.setFill(Color.TRANSPARENT);
            newStage.setScene(scene);
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void ReadDateTime()throws IOException{
        File file =new File("LogOutTime.txt");
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String nowTime;
        while((nowTime=reader.readLine())!=null){
            logoutTimeDisplay.setText(" "+nowTime);
        }
    }

    public void ScaleUpButton(Node button){

        button.setOnMouseEntered(e-> {
            ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(200), button);
            scaleTransition.setToX(1.2);
            scaleTransition.setToY(1.2);
            scaleTransition.setCycleCount(1);
            scaleTransition.setInterpolator(Interpolator.EASE_BOTH);
            scaleTransition.play();
        });
    }

    public void ScaleDownButton(Node button){

        button.setOnMouseExited(e-> {
            ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(200), button);
            scaleTransition.setToX(1);
            scaleTransition.setToY(1);
            scaleTransition.setCycleCount(1);
            scaleTransition.setInterpolator(Interpolator.EASE_BOTH);
            scaleTransition.play();
        });
    }


}
