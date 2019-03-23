package MovieTicketingSystem.controllers;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.Collection;
import java.util.ResourceBundle;
import java.util.stream.Collectors;


public class BookingDetailsController implements Initializable {
    @FXML
    Pane newBasePane;
    @FXML
    TableView table;

    double xOffset,yOffset;


    Stage thisStage;

    @FXML
    void CloseWindow(MouseEvent event){
        ((Stage)((Node)event.getSource()).getScene().getWindow()).close();
    }


    protected void MoveStage() {
        //Set the position of the stage when they are pressed
        newBasePane.setOnMousePressed(e-> {
            thisStage=(Stage) ((Node)e.getSource()).getScene().getWindow();
            xOffset = e.getSceneX();
            yOffset = e.getSceneY();
        });
        newBasePane.setOnMouseDragged(e-> {
            thisStage=(Stage) ((Node)e.getSource()).getScene().getWindow();
            thisStage.setX(e.getScreenX() - xOffset);
            thisStage.setY(e.getScreenY() - yOffset);

        });
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


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        MoveStage();
        SetUpTable();
    }
}
