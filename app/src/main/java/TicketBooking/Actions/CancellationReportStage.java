package TicketBooking.Actions;

import TicketBooking.Management.CancellationLog;
import TicketBooking.Management.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;

public class CancellationReportStage {
    @FXML
    TableView<CancellationLog> reasonTableView;

    @FXML
    TableColumn<CancellationLog, String> time;

    @FXML
    TableColumn<CancellationLog, String> userName;

    @FXML
    TableColumn<CancellationLog, String> reason;

    @FXML
    Button backButton;

    ObservableList<CancellationLog> reasonList;

    private User user;

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void initialize() {
        getCancellationLog();
        time.setCellValueFactory(new PropertyValueFactory<>("time"));
        userName.setCellValueFactory(new PropertyValueFactory<>("userName"));
        reason.setCellValueFactory(new PropertyValueFactory<>("reason"));
        reasonTableView.setItems(reasonList);
    }

    private void getCancellationLog() {
        reasonList = FXCollections.observableArrayList();
        Helper.getCancellationLog(reasonList);
    }

    public void switchToManageStage(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/ManagerStage.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}