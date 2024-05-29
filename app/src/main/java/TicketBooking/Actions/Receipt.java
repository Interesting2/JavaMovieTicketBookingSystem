package TicketBooking.Actions;

import TicketBooking.Management.User;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class Receipt {
    // display receipt and return transaction number
    public static String display(String title, User user, String PaymentMethod) {

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);

        Label transactionNumber = new Label();
        Label userLabel = new Label();
        Label paymentMethod = new Label();

        String transactionNumberString = generateTransactionNumber();
        transactionNumber.setText("Transaction Number : "+ transactionNumberString);
        userLabel.setText("User : "+ user.getUserName());
        paymentMethod.setText("Payment Method : "+ PaymentMethod);

        Button closeButton = new Button("CLOSE");
        closeButton.setOnMouseClicked(event -> {
            stage.close();
        });

        VBox vBox = new VBox();
        vBox.getChildren().addAll(transactionNumber, userLabel, paymentMethod, closeButton);
        vBox.setAlignment(Pos.CENTER);
        Scene scene = new Scene(vBox, 300, 400);
        stage.setScene(scene);
        stage.setTitle(title);
        stage.showAndWait();

        return transactionNumberString;
    }

    public static String generateTransactionNumber() {
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern("yyyyMMddHHmmss");
        Date date = new Date();

        Random random = new Random();

        return sdf.format(date) + random.nextInt(4);
    }
}
