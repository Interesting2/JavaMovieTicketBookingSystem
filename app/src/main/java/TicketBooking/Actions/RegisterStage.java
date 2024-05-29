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

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class RegisterStage {
    @FXML
    Button registerButton, backButton;

    @FXML
    TextField usernameTextField;

    @FXML
    PasswordField passwordField, confirmTextField;

    @FXML
    Label errorLabel;

    /*
    For RegisterStage, it should accept some kind of parameter,
    i.e. staff/customer and write it into the csv file
    So we can use it for both customer register and staff register.
     */

    //Register
    public void switchToDefaultStage(ActionEvent event) {
        String username = "";
        String password = "";
        String confirmPassword = "";

        try {
            username = usernameTextField.getText().replaceAll("\\s", "");
            password = passwordField.getText().replaceAll("\\s", "");
            confirmPassword = confirmTextField.getText();
        } catch (RuntimeException e) {
            e.getStackTrace();
        }

        // just for testing
        System.out.println("Username length: " + username.length() + " " + "Password length: " + password.length());

        Validator validator = new ValidatorImpl();
        if (!username.equals("") && !password.equals("") && confirmPassword.equals(password)) {
            if (validator.duplicateAccountCheck(username,"Customer")) {
                // true = duplicate
                errorLabel.setText("This account already exists!");
            } else {
                User newUser = new User(username, password, "Customer","0", "0");
                try {
                    Helper.writeUser(newUser);
                    Helper.writeUserToTemp(newUser);
    
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/view/DefaultStage.fxml"));
                    Parent root = loader.load();
    
                    DefaultStage defaultStage = loader.getController();
                    defaultStage.setUser(newUser);
    
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.setX(80);

                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }else{
            errorLabel.setText("Password does not match");
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
            e.printStackTrace();
        }
    }

    public boolean addAccount(String username, String password){
        ValidatorImpl vi = new ValidatorImpl();
        if(vi.duplicateAccountCheck(username,"Customer")) return false;
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/java/TicketBooking/Storage/UserAccount.csv",true));
            writer.write("\n"+ username+","+password+",Customer,,");
            writer.close();
            return true;
        }catch(FileNotFoundException e){
            System.out.println("File not found!");
        }catch(IOException e){
            System.out.println("Error occurs while writing to the file.");
        }
        return false;
    }
}
