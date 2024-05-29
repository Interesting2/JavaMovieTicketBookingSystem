package TicketBooking.Actions;

import TicketBooking.Management.User;
import TicketBooking.Management.Validator;
import TicketBooking.Management.ValidatorImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginStage {
    @FXML
    Button loginButton, guestButton, registerButton;

    @FXML
    TextField usernameTextField;

    @FXML
    PasswordField passwordField;

    @FXML
    Label errorLabel;

    //Login
    public void switchToDefaultStage(ActionEvent event) {
        String username = "";
        String password = "";

        try{
            username = usernameTextField.getText();
            password = passwordField.getText();
        } catch (Exception e) {
            System.out.println("LoginStage: Error with getting username and password");
            e.printStackTrace();
        }

        System.out.println("Attempt to login with: " + username + " " + password);

        // TODO: Put all account info in same csv file, cuz the system will automatically determine the user type and jump to the appropriate page
        Validator validator = new ValidatorImpl();
        String path = "src/main/java/TicketBooking/Storage/UserAccount.csv";

        if (!username.equals("") && !password.equals("")) {
            // if it is a staff/manager
            if (validator.staffCheck(path, username, password)) {
                //User manage = new User("manage", "0", "Manage", "0", "0");
                try {
                    User manage = Helper.readUserFromCsv(username);
                    Helper.writeUserToTemp(manage);
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/view/ManagerStage.fxml"));
                    Parent root = loader.load();

                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    // validate username and user password
                    
                    
                    if (validator.loginCheck(path, username, password)) {
                        System.out.println("Correct Credentials: " + username + " " + password);
                        User loggedInUser = Helper.readUserFromCsv(username);
                        /**
                         * should be read user info from database,
                         * but we don't have one yet
                         */
                        Helper.writeUserToTemp(loggedInUser);
                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("/view/DefaultStage.fxml"));
                        Parent root = loader.load();
    
                        DefaultStage defaultStage = loader.getController();
                        defaultStage.setUser(loggedInUser);
    
                        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        Scene scene = new Scene(root);
                        stage.setScene(scene);
                        stage.setX(80);
                        //stage.show();
                    } else {
                        errorLabel.setText("Invalid Username or Password!"); 
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            errorLabel.setText("Please enter your username and password");
        }
    }


    public void switchToRegisterStage(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/RegisterStage.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void switchToGuestStage(ActionEvent event) {
        User guest = new User("guest", "0", "Guest", "0", "0");
        try {
            Helper.writeUserToTemp(guest);
            //TODO: pass guest to defaultPage
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/DefaultStage.fxml"));
            Parent root = loader.load();

            DefaultStage defaultStage = loader.getController();
            defaultStage.setUser(guest);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setX(80);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
}
