package TicketBooking.Actions;

import TicketBooking.App;
import TicketBooking.Management.Giftcard;
import TicketBooking.Management.Validator;
import TicketBooking.Management.ValidatorImpl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
//import javafx.collections.FXCollections;
import javafx.collections.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.TableColumn.CellEditEvent;

import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;

import java.io.*;

import javafx.util.converter.IntegerStringConverter;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.BooleanStringConverter;

public class GiftCardStage {

    /* Btn */
    @FXML
    private Button addBtn;

    @FXML
    private Button deleteBtn;

    @FXML
    private Button editBtn;

    @FXML
    private Button backBtn;

    @FXML
    private Button refreshBtn;

    @FXML
    private Button instructionBtn;

    /* Text Field */
    @FXML
    private TextField inputAmount;

    @FXML
    private CheckBox inputAvailable;

    @FXML
    private TextField inputId;

    @FXML
    private TextField inputNumber;

    @FXML
    private Label errorLabel;

    /* Table Columns */
    @FXML
    private TableColumn<Giftcard, Double> gcAmount;

    @FXML
    private TableColumn<Giftcard, Boolean> gcAvailable;

    @FXML
    private TableColumn<Giftcard, Integer> gcId;

    @FXML
    private TableColumn<Giftcard, String> gcNumber;

    @FXML
    private TableView<Giftcard> gcTable;

    ObservableList<Giftcard> gcList;

    public void switchToManageStage(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/ManagerStage.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.println("Class GiftCardStage: switchToManageStage() failed");
        }
    }

    public void refreshGiftCardStage(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/Giftcard.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.println("Class GiftCardStage: refreshGiftCardStage() failed");
        }
    }

    public BufferedReader loadCsvFile() {

        try {
            String path = "src/main/java/TicketBooking/Storage/Giftcard.csv";
            BufferedReader br = new BufferedReader(new FileReader(path));

            System.out.println("Read valid cards successfully");
            return br;

        } catch(IOException e) {
            System.out.println("Error when opening File");
            return null;
        }
    }
    private void getDB() {
        gcList = FXCollections.observableArrayList();
        // retrieve from database
        try {
            BufferedReader br = loadCsvFile();
            
            String line = "";
            boolean skip = true;
            while ( (line = br.readLine()) != null) {
                if (skip) skip = false;
                else {
                    String[] gcDetails = line.split(",");
                    int id = Integer.parseInt(gcDetails[0]);
                    String number = gcDetails[1];
                    double amount = Double.parseDouble(gcDetails[2]);
                    boolean available = Boolean.valueOf(gcDetails[3]);
                    Giftcard giftcard = new Giftcard(id, number, amount, available);
                    gcList.add(giftcard);
                }
            }

        } catch(IOException e) {
            System.out.println("Error when opening File");
        }
    }

    public void initialize() {
        errorLabel.setStyle("-fx-font-color: #8B0000");
        gcId.setCellValueFactory(new PropertyValueFactory<Giftcard, Integer>("id"));
        gcId.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

        gcNumber.setCellValueFactory(new PropertyValueFactory<Giftcard, String>("number"));
        gcNumber.setCellFactory(TextFieldTableCell.forTableColumn());
        gcNumber.setOnEditCommit(new EventHandler<CellEditEvent<Giftcard, String>>() {

            public void handle(CellEditEvent<Giftcard, String> event) {
                Giftcard card = event.getRowValue();
                card.setNumber(event.getNewValue());
            }
        });

        gcAmount.setCellValueFactory(new PropertyValueFactory<Giftcard, Double>("amount"));
        gcAmount.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        gcAmount.setOnEditCommit(new EventHandler<CellEditEvent<Giftcard, Double>>() {

            public void handle(CellEditEvent<Giftcard, Double> event) {
                Giftcard card = event.getRowValue();
                card.setAmount(event.getNewValue());
            }
        });

        gcAvailable.setCellValueFactory(new PropertyValueFactory<Giftcard, Boolean>("available"));
        gcAvailable.setCellFactory(TextFieldTableCell.forTableColumn(new BooleanStringConverter()));
        gcAvailable.setOnEditCommit(new EventHandler<CellEditEvent<Giftcard, Boolean>>() {

            public void handle(CellEditEvent<Giftcard, Boolean> event) {
                Giftcard card = event.getRowValue();
                card.setAvailable(event.getNewValue());
            }
        });

        gcId.setEditable(true);
        gcNumber.setEditable(true);
        gcAmount.setEditable(true);
        gcAvailable.setEditable(true);
        
        getDB();
        gcTable.setItems(gcList);
        gcTable.setEditable(true);

        System.out.println("Hello");
    }

    private Giftcard getInputField() {
        
        int id = Integer.parseInt(inputId.getText());

        String number = inputNumber.getText();

        double amount = Double.parseDouble(inputAmount.getText());

        boolean available = inputAvailable.isSelected();
        if (errorChecking(id, number, amount)) {
            return null;
        }
        Giftcard giftcard = new Giftcard(id, number, amount, available);
        return giftcard;
    }

    boolean errorChecking(int id, String number, double amount) {
        Validator validator = new ValidatorImpl();

        if (checkIDExists(id)) {
            errorLabel.setText("ID already exists");
            return true;
        } else errorLabel.setText("");
        
        if (number.length() != 16) {
            errorLabel.setText("Card number must be 16 characters");
            return true;
        } else errorLabel.setText("");

        if (validator.giftCardExists(number)) {
            // if duplicate
            errorLabel.setText("Card already exists");
            return true;
        } else errorLabel.setText("");

        if (amount < 10) {
            errorLabel.setText("Amount must be larger or equal to 10");
            return true;
        } else errorLabel.setText("");
        return false;
    }

    boolean checkIDExists(int id) {
        BufferedReader reader = loadCsvFile();
        String line = "";
        boolean skip = true;
        try {
            while ( (line = reader.readLine()) != null) {
                if (skip) skip = false;
                else {
                    String[] details = line.split(",");
                    if (id == Integer.parseInt(details[0])) {
                        System.out.println(Integer.toString(id) + " " + Integer.parseInt(details[0]));
                        return true;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("checkIDExists() failed to read");
        }
        return false;

    }

    @FXML
    void addGiftCard(ActionEvent event) {
        // get input from TextField
        System.out.println("Add Button Pressed");
        Giftcard giftcard = getInputField();
        if (giftcard == null) return;
        
        gcList.add(giftcard);

        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/java/TicketBooking/Storage/Giftcard.csv",true));
            writer.write('\n' + giftcard.toString());
            writer.close();

        }catch(FileNotFoundException e){
            System.out.println("File not found!");
        }catch(IOException e){
            System.out.println("Error occurs while writing to the file.");
        }
        gcTable.setItems(gcList);
        errorLabel.setText("Card id: " + giftcard.getId() + " added successfully");
    }

    private void writeData(String data) {
        try{
            System.out.println(data);
            BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/java/TicketBooking/Storage/Giftcard.csv",false));
            writer.write(data);
            writer.close();

        }catch(FileNotFoundException e){
            System.out.println("File not found!");
        }catch(IOException e){
            System.out.println("Error occurs while writing to the file.");
        }
        
    }

    @FXML
    void deleteGiftCard(ActionEvent event) {
        System.out.println("Delete Button Pressed");

        Giftcard giftcard = gcTable.getSelectionModel().getSelectedItem();
        gcTable.getItems().remove(giftcard);

        BufferedReader reader = loadCsvFile();

        StringBuffer inputBuffer = new StringBuffer();
        String label = ",Gift Card Number,amount,available";
        inputBuffer.append(label + "\n");
        String line;
        boolean skip = true;
        try {
            while ((line = reader.readLine()) != null) {
                if (skip) skip = false;
                else {
                    String[] gcDetails = line.split(",");
                    int id = Integer.parseInt(gcDetails[0]);
                    if (giftcard.getId() != id) {
                        inputBuffer.append(line);
                        inputBuffer.append('\n');
                    } 

                }
            }   
            errorLabel.setText("Card id: " + giftcard.getId() + " removed successfully");

        }catch(IOException e){
            System.out.println("Error occurs while reading the file.");
        }

        String data = inputBuffer.toString().trim();
        writeData(data);
        

    }

    @FXML
    void editGiftCard(ActionEvent event) {
        Validator validator = new ValidatorImpl();
        System.out.println("Edit Button Pressed");
        
        Giftcard giftcard = gcTable.getSelectionModel().getSelectedItem();
        if (giftcard.getAmount() < 10) {
            errorLabel.setText("Amount must be larger or equal to 10");
            return;
        }
        if (giftcard.getNumber().length() != 16) {
            errorLabel.setText("Card number must be 16 characters"); 
            return;
        }
        if (validator.giftCardExists(giftcard.getNumber())) {
            // if duplicate
            errorLabel.setText("Card already exists");
            return;
        }

        //System.out.println(giftcard.getNumber());

        //gcList.add(giftcard);
        BufferedReader reader = loadCsvFile();

        StringBuffer inputBuffer = new StringBuffer();
        String label = ",Gift Card Number,amount,available";
        inputBuffer.append(label + "\n");
        String line;
        boolean skip = true;
        try {
            while ((line = reader.readLine()) != null) {
                if (skip) skip = false;
                else {
                    String[] gcDetails = line.split(",");
                    int id = Integer.parseInt(gcDetails[0]);
                    if (giftcard.getId() == id) {
                        String target = giftcard.toString();
                        inputBuffer.append(target);
                        inputBuffer.append('\n'); 
                    } else {
                        inputBuffer.append(line);
                        inputBuffer.append('\n');
                    }
                }
            }   
            errorLabel.setText("Card id: " + giftcard.getId() + " edited successfully");

        }catch(IOException e){
            System.out.println("Error occurs while reading the file.");
        }

        String data = inputBuffer.toString().trim();
        writeData(data);

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
            dialogStage.setTitle("Edit gift card instructions");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(App.primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            InstructionPopupController controller = loader.getController();
            controller.setMessage("Instructions to modify gift cards: \n " +
                    "Add: Enter the gift card id, number, amount in the corresponding text fields, and whether the Giftcard is available to use. \n Ticking the check box means if it available, otherwise it the user is not available to use.\n" +
                    "Edit: Select one row, double click on a particular column to edit that cell. Once you are ready to change, press on enter to commit the changes. \n Edit multiple rows with the previous instructions if you like. After all changes, click on the Edit button to commit all changes.\n" +
                    "Refresh: This button refreshes the current window.\n" +
                    "Delete: Click on a particular row that you want to delete. Once you are ready to delete, click on the Delete button to remove the entire row"
                    );

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();
//            return controller.isOkClicked();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}


