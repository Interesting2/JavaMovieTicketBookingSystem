package TicketBooking.Actions;

import TicketBooking.Management.Movie;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;

import java.io.IOException;

public class UpcomingMoviesStage {
    @FXML
    private TableColumn<Movie, String> cCast;

    @FXML
    private TableColumn<Movie, String> cClassification;

    @FXML
    private TableColumn<Movie, String> cDirector;

    @FXML
    private TableColumn<Movie, ImageView> cImage;

    @FXML
    private TableColumn<Movie, String> cName;

    @FXML
    private TableColumn<Movie, String> cRelease;

    @FXML
    private TableColumn<Movie, String> cScreen;

    @FXML
    private TableColumn<Movie, String> cSynopsis;

    @FXML
    private TableColumn<Movie, Integer> cTime;

    @FXML
    private TableColumn<Movie, String> cUpcoming;

    @FXML
    private TableColumn<Movie, String> cCinema;

    @FXML
    private TableColumn<Movie, Integer> cSeats;

    @FXML
    private TableView<Movie> mTable;

    ObservableList<Movie> mList;

    public void initialize() {
        cName.setCellValueFactory(new PropertyValueFactory<Movie, String>("name"));
        cName.setCellFactory(TextFieldTableCell.forTableColumn());


        cClassification.setCellValueFactory(new PropertyValueFactory<Movie, String>("classification"));
        cClassification.setCellFactory(TextFieldTableCell.forTableColumn());


        cScreen.setCellValueFactory(new PropertyValueFactory<Movie, String>("screenSize"));
        cScreen.setCellFactory(TextFieldTableCell.forTableColumn());


        cSynopsis.setCellValueFactory(new PropertyValueFactory<Movie, String>("synopsis"));
        cSynopsis.setCellFactory(TextFieldTableCell.forTableColumn());


        cDirector.setCellValueFactory(new PropertyValueFactory<Movie, String>("director"));
        cDirector.setCellFactory(TextFieldTableCell.forTableColumn());


        cCast.setCellValueFactory(new PropertyValueFactory<Movie, String>("cast"));
        cCast.setCellFactory(TextFieldTableCell.forTableColumn());


        cUpcoming.setCellValueFactory(new PropertyValueFactory<Movie, String>("upcomingTime"));
        cUpcoming.setCellFactory(TextFieldTableCell.forTableColumn());

        cRelease.setCellValueFactory(new PropertyValueFactory<Movie, String>("releaseDate"));
        cRelease.setCellFactory(TextFieldTableCell.forTableColumn());


        cTime.setCellValueFactory(new PropertyValueFactory<Movie, Integer>("runningTime"));
        cTime.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

        cSeats.setCellValueFactory(new PropertyValueFactory<Movie, Integer>("availableSeats"));
        cSeats.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));


        cCinema.setCellValueFactory(new PropertyValueFactory<Movie, String>("cinemaName"));
        cCinema.setCellFactory(TextFieldTableCell.forTableColumn());


        cImage.setCellValueFactory(new PropertyValueFactory<Movie, ImageView>("movieImage"));

        mList = FXCollections.observableArrayList();
        Helper.getDB(mList);
        mTable.setEditable(true);
        mTable.setItems(mList);

    }
    public void switchToReportPage(ActionEvent event)
    {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/ReportStage.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
        } catch (IOException e) {
            System.out.println("UpcomingMoviesStage Class: switchToReportManage() failed");
            //e.printStackTrace();
        }
    }

}
