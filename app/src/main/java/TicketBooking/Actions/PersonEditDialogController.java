package TicketBooking.Actions;

import TicketBooking.Management.Staff;
import TicketBooking.Management.Validator;
import TicketBooking.Management.ValidatorImpl;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Dialog to edit details of a person.
 *
 * @author Marco Jakob
 */
public class PersonEditDialogController {

    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField usernameField;
    @FXML
    private TextField pwdField;

    @FXML
    private Label errMsg;


    private Stage dialogStage;
    private Staff person;
    private boolean okClicked = false;
    private boolean isEdit = false;


    @FXML
    private void initialize() {
    }


    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }


    public void setEdit(boolean val){
        this.isEdit = val;
    }
    public void setPerson(Staff person) {
        this.person = person;

        firstNameField.setText(person.getFirstName());
        lastNameField.setText(person.getLastName());
        usernameField.setText(person.getUsername());
        pwdField.setText(person.getPwd());
    }

    /**
     * Returns true if the user clicked OK, false otherwise.
     *
     * @return
     */
    public boolean isOkClicked() {
        return okClicked;
    }

    /**
     * Called when the user clicks ok.
     */
    @FXML
    private void handleOk() {
        if (isInputValid()) {
            person.setFirstName(firstNameField.getText());
            person.setLastName(lastNameField.getText());
            person.setUsername(usernameField.getText());
            person.setPwd(pwdField.getText());
            okClicked = true;
            dialogStage.close();
        }
    }

    /**
     * Called when the user clicks cancel.
     */
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    /**
     * Validates the user input in the text fields.
     *
     * @return true if the input is valid
     */
    private boolean isInputValid() {
        String errorMessage = "";
        Validator v = new ValidatorImpl();
        if(!this.isEdit){
            if(v.duplicateAccountCheck(usernameField.getText(),"Manager")){
                errorMessage+="The username was used, please have a new one.\n";
            }
        }else{
            if(!v.duplicateAccountCheck(usernameField.getText(),"Manager")){
                errorMessage+="Don't edit username, it's an identifier!\n";
                usernameField.setText(person.getUsername());
            }
        }

        if (firstNameField.getText() == null || firstNameField.getText().length() == 0) {
            errorMessage += "Invalid first name!\n";
        }
        if (lastNameField.getText() == null || lastNameField.getText().length() == 0) {
            errorMessage += "Invalid last name!\n";
        }


        if (pwdField.getText() == null || pwdField.getText().length() == 0) {
            errorMessage += "Invalid password!\n";
        }

        if(errorMessage.length() == 0)return true;
        else{
            errMsg.setText(errorMessage);
            return false;
        }
    }
}