package TicketBooking.Actions;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class ReportStage {
    @FXML
    Button upcomingMoviesButton, customerBookingsButton, backButton;

    public void switchToUpcomingMoviesReport(ActionEvent event)
    {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/UpcomingMoviesStage.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
        } catch (IOException e) {
            System.out.println("ReportStage Class: switchToManagerStage() failed");
            //e.printStackTrace();
        }
    }


    public void switchToCustomerBookingsReport(ActionEvent event)
    {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/BookingsReportStage.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
        } catch (IOException e) {
            System.out.println("ReportStage Class: switchToManagerStage() failed");
            //e.printStackTrace();
        }
    }

    public void switchToManagerStage(ActionEvent event)
    {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/ManagerStage.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
        } catch (IOException e) {
            System.out.println("ReportStage Class: switchToManagerStage() failed");
            //e.printStackTrace();
        }
    }

}
