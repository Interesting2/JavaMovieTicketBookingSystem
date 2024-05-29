package TicketBooking.Actions;

import TicketBooking.App;
import TicketBooking.Management.Staff;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class StaffDBStage{
    private ObservableList<Staff> staffData = FXCollections.observableArrayList();

    public StaffDBStage(TableView<Staff> personTable){
        loadData("src/main/java/TicketBooking/Storage/UserAccount.csv", personTable);
    }

    private void loadData(String path, TableView<Staff> personTable){
        try{
            BufferedReader rdr = new BufferedReader(new InputStreamReader(new FileInputStream(path)));
            rdr.readLine(); //skip first line
            String line = null;
            while((line= rdr.readLine())!=null){
                String[] item = line.split(",");
                if(item[2].equals("Staff")){
                    staffData.add(new Staff(item[0],item[1],item[3],item[4])); //username,password,type,firstname,lastname
                }
            }
            personTable.setItems(staffData);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public ObservableList<Staff> getStaffData() {
        return staffData;
    }

    public boolean showPersonEditDialog(Staff person, boolean isEdit) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/PersonEditDialog.fxml"));
            Parent page = loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Person");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(App.primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            PersonEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setPerson(person);
            controller.setEdit(isEdit);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();
            return controller.isOkClicked();

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }



}