package TicketBooking.Actions;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class ManageStage {
    @FXML
    Button MovieManageButton, GiftCardManageButton, StaffManageButton, MovieReportsButton, cancellationLogReportButton, backButton;

    @FXML
    Label errMsg;

    public void initialize() throws IOException {
        errMsg.setText("");
        BufferedReader rdr = new BufferedReader(new InputStreamReader(new FileInputStream("src/main/java/TicketBooking/Storage/UserTemp.csv")));
        String[] info = rdr.readLine().split(",");
        if(!info[2].equals("Manager")){
            cancellationLogReportButton.setVisible(false);
            StaffManageButton.setVisible(false);
        }
    }

    public void switchToMovieManage(ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/MovieDB.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.println("ManageStage Class: switchToMovieManage() failed");
            //e.printStackTrace();
        }
    }

    public void switchToLoginStage(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/LoginStage.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
        } catch (IOException e) {
            System.out.println("ManageStage Class: switchToLoginStage() failed");
            //e.printStackTrace();
        }
    }

    public void switchToGiftCardManage(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/Giftcard.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
        } catch (IOException e) {
            System.out.println("ManageStage Class: switchToGiftCardStage() failed");
            e.printStackTrace();
        }
    }

    public void switchToStaffManage(ActionEvent event) {
        try {
            BufferedReader rdr = new BufferedReader(new InputStreamReader(new FileInputStream("src/main/java/TicketBooking/Storage/UserTemp.csv")));
            String[] info = rdr.readLine().split(",");
            if(!info[2].equals("Manager")){
                errMsg.setText("You are not the manager and cannot manage staff.");
                return;
            }
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/PersonOverview.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);

        } catch (IOException e) {
            System.out.println("ManageStage Class: switchToStaffManage() failed");
            e.printStackTrace();
        }
    }

    public void switchToGiftCardReportManage(ActionEvent event) {
        
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/ReportStage.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
        } catch (IOException e) {
            System.out.println("ManageStage Class: switchToReportManage() failed");
            //e.printStackTrace();
        }
    }

    public void switchToCancellationLogReport(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/CancellationLog.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
        } catch (IOException e) {
            System.out.println("ManageStage Class: switchToCancellationLogReport() failed");
            //e.printStackTrace();
        }
    }

    public void switchToUpcomingMovieReport(ActionEvent event) {
        //TODO:
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/UpcomingMoviesStage.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
        } catch (IOException e) {
            System.out.println("ManageStage Class: switchToCancellationLogReport() failed");
            //e.printStackTrace();
        }
    }


}
