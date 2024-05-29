package TicketBooking.Actions;

import TicketBooking.App;
import TicketBooking.Management.Staff;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;


public class StaffOverviewController{
    @FXML
    private TableView<Staff> personTable;
    @FXML
    private TableColumn<Staff, String> firstNameColumn;
    @FXML
    private TableColumn<Staff, String> lastNameColumn;

    @FXML
    private Label firstNameLabel;
    @FXML
    private Label lastNameLabel;
    @FXML
    private Label pwdLabel;
    @FXML
    private Label usernameLabel;

    // Reference to the main application.
    private StaffDBStage mainPage;

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public StaffOverviewController() {
    }


    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param mainApp
     */
    public void setMainApp(StaffDBStage mainApp) {
        this.mainPage = mainApp;

        // Add observable list data to the table
        personTable.setItems(mainApp.getStaffData());
    }

    @FXML
    private void initialize() {
        // Initialize the person table with the two columns.
        firstNameColumn.setCellValueFactory(
        cellData -> cellData.getValue().firstNameProperty());
        lastNameColumn.setCellValueFactory(
                cellData -> cellData.getValue().lastNameProperty());
        // Listen for selection changes and show the person details when changed.

        personTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showPersonDetails(newValue));
        handleEditPerson();

}
    private void showPersonDetails(Staff person) {
        if (person != null) {
            // Fill the labels with info from the person object.
            firstNameLabel.setText(person.getFirstName());
            lastNameLabel.setText(person.getLastName());
            pwdLabel.setText(person.getPwd());
            usernameLabel.setText(person.getUsername());
        } else {
            // Person is null, remove all the text.
            firstNameLabel.setText("");
            lastNameLabel.setText("");
            pwdLabel.setText("");
            usernameLabel.setText("");
        }
    }
    @FXML
    private void handleDeletePerson() {
        int selectedIndex = personTable.getSelectionModel().getSelectedIndex();
        try {
            if (selectedIndex >= 0) {
                Staff person = personTable.getItems().get(selectedIndex);
                String lineToRemove = person.getUsername() + ","
                        + person.getPwd() + ","
                        + "Staff,"
                        + person.getFirstName() + ","
                        + person.getLastName();
                System.out.println(lineToRemove);
                BufferedReader reader = new BufferedReader(new FileReader("src/main/java/TicketBooking/Storage/UserAccount.csv"));
                StringBuffer inputBuffer = new StringBuffer();
                inputBuffer.append(reader.readLine() + "\n");
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.equals(lineToRemove)) continue;
                    inputBuffer.append(line + "\n");
                }
                reader.close();
                BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/java/TicketBooking/Storage/UserAccount.csv",false));
                writer.write(inputBuffer.toString().trim());
                writer.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }finally {
            personTable.getItems().remove(selectedIndex);
        }
    }
    @FXML
    private void handleNewPerson() {
        Staff tempPerson = new Staff("","","","");
        StaffDBStage d = new StaffDBStage(personTable);

        boolean okClicked = d.showPersonEditDialog(tempPerson,false);
        if (okClicked) {
            d.getStaffData().add(tempPerson);
            try{
                BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/java/TicketBooking/Storage/UserAccount.csv",true));
                writer.write("\n"+tempPerson.getUsername()+","+tempPerson.getPwd() +",Staff,"+tempPerson.getFirstName()+","+tempPerson.getLastName());
                writer.close();
            }catch(FileNotFoundException e){
                System.out.println("File not found!");
            }catch(IOException e){
                System.out.println("Error occurs while writing to the file.");
            }
;        }

    }

    /**
     * Called when the user clicks the edit button. Opens a dialog to edit
     * details for the selected person.
     */
    @FXML
    private void handleEditPerson() {
        Staff selectedPerson = personTable.getSelectionModel().getSelectedItem();

        StaffDBStage d = new StaffDBStage(personTable);
        if (selectedPerson != null) {
            String line = selectedPerson.toString();
            boolean okClicked = d.showPersonEditDialog(selectedPerson,true);
            if (okClicked) {
                try {
                    String lineToChange = selectedPerson.toString();
                    System.out.println(lineToChange);
                    BufferedReader reader = new BufferedReader(new FileReader("src/main/java/TicketBooking/Storage/UserAccount.csv"));
                    StringBuffer inputBuffer = new StringBuffer();
                    inputBuffer.append(reader.readLine() + "\n");
                    String currentline;
                    while ((currentline = reader.readLine()) != null) {
                        System.out.println(currentline);
                        if (currentline.equals(line)){
                            inputBuffer.append(lineToChange+"\n");
                            continue;
                        }
                        inputBuffer.append(currentline + "\n");
                    }
                    reader.close();
                    BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/java/TicketBooking/Storage/UserAccount.csv",false));
                    writer.write(inputBuffer.toString());
                    writer.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }catch (IOException e) {
                    e.printStackTrace();
                }
                this.initialize();
                showPersonDetails(selectedPerson);
            }

        }
    }

    @FXML
    public void goBack(ActionEvent event){
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/ManagerStage.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }
    @FXML
    public void showInstructionsPopup()
    {
        try {
            // Load the fxml file and create a new stage for the popup dialog.

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/InstructionPopup.fxml"));
            Parent page = loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit staff instructions");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(App.primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            InstructionPopupController controller = loader.getController();
            controller.setMessage("Instructions to modify staff: \n" +
                    "Add: Open a Pop up input box that requires username, password, first name, last name to create a staff account. Once information are entered, click Ok to insert an entry of staff.\n" +
                    "Edit:  select the required account to be edit on the LHS table. Click edit to open a pop up input box, enter the required data to be changed and click ok. \n" +
                    "Delete: select the required account to be remove from the LHS table. Click delete to remove the selected entry");

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();
//            return controller.isOkClicked();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}