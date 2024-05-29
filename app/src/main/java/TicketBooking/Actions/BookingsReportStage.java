package TicketBooking.Actions;

import TicketBooking.Management.Transaction;
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
import javafx.stage.Stage;

import java.io.IOException;

public class BookingsReportStage {

    @FXML
    private TableColumn<Transaction, String> cTransactionNumber;

    @FXML
    private TableColumn<Transaction, String> cUser;

    @FXML
    private TableColumn<Transaction, String> cMovie;

    @FXML
    private TableColumn<Transaction, String> cCinema;

    @FXML
    private TableColumn<Transaction, String> cSession;

    @FXML
    private TableColumn<Transaction, String> cTickets;
    @FXML
    private TableColumn<Transaction, String> cTotalPrice;

    @FXML
    private TableColumn<Transaction, String> cPaymentMethod;

    @FXML
    private TableColumn<Transaction, String> cDate;

    @FXML
    private TableView<Transaction> mTable;

    ObservableList<Transaction> mList;

    public void initialize() {
        cTransactionNumber.setCellValueFactory(new PropertyValueFactory<Transaction, String>("transactionNumber"));
        cTransactionNumber.setCellFactory(TextFieldTableCell.forTableColumn());


        cUser.setCellValueFactory(new PropertyValueFactory<Transaction, String>("user"));
        cUser.setCellFactory(TextFieldTableCell.forTableColumn());


        cMovie.setCellValueFactory(new PropertyValueFactory<Transaction, String>("movieName"));
        cMovie.setCellFactory(TextFieldTableCell.forTableColumn());


        cCinema.setCellValueFactory(new PropertyValueFactory<Transaction, String>("cinema"));
        cCinema.setCellFactory(TextFieldTableCell.forTableColumn());


        cSession.setCellValueFactory(new PropertyValueFactory<Transaction, String>("sessionTime"));
        cSession.setCellFactory(TextFieldTableCell.forTableColumn());


        cTickets.setCellValueFactory(new PropertyValueFactory<Transaction, String>("tickets"));
        cTickets.setCellFactory(TextFieldTableCell.forTableColumn());


        cTotalPrice.setCellValueFactory(new PropertyValueFactory<Transaction, String>("totalPrice"));
        cTotalPrice.setCellFactory(TextFieldTableCell.forTableColumn());


        cPaymentMethod.setCellValueFactory(new PropertyValueFactory<Transaction, String>("paymentMethod"));
        cPaymentMethod.setCellFactory(TextFieldTableCell.forTableColumn());

        cDate.setCellValueFactory(new PropertyValueFactory<Transaction, String>("dateBooking"));
        cDate.setCellFactory(TextFieldTableCell.forTableColumn());


        mList = FXCollections.observableArrayList();
        Helper.getBookingDB(mList);
        mTable.setEditable(false);
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
