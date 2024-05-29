package TicketBooking.Actions;

import TicketBooking.Management.Movie;
import TicketBooking.Management.Validator;
import TicketBooking.Management.ValidatorImpl;
import com.opencsv.*;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import TicketBooking.Management.User;
import TicketBooking.App;
import javafx.stage.WindowEvent;
import javafx.event.EventHandler;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import java.util.Timer;
import java.util.TimerTask;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class GiftCardPaymentStage implements javafx.fxml.Initializable {

    private User user;
    private Movie movie;
    private int ticketAmount;
    private int paymentAmount;
    private String ticketsString;

    private Timer tr;
    private int timing;
    private boolean notIdle;

    //user getter
    public User getUser() {
        return user;
    }

    //user setter
    public void setUser(User user) {
        this.user = user;
    }

    //movie getter
    public Movie getMovie() {
        return movie;
    }

    //movie setter
    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    //ticketAmount getter
    public int getTicketAmount() {
        return ticketAmount;
    }

    //ticketAmount setter
    public void setTicketAmount(int ticketAmount) {
        this.ticketAmount = ticketAmount;
    }

    //payment amount setter
    public void setPaymentAmount(int paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public void setTicketsString(String ticketsString) {
        this.ticketsString = ticketsString;
    }
    
    @FXML
    TextField giftCardTextField;

    @FXML
    Label errorLabel;

    @FXML
    Button confirmButton, cancelButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //TODO: 2 mins time out count down

        Stage stage = App.getStage();

        tr = new Timer() ;
        //schedule the repeating timer
        tr.scheduleAtFixedRate(new TimerTask(){
        //override run methid
            
            @Override
            public void run() {
                timing ++;
                if (!notIdle && timing == 10) {
                    System.out.println("You have been idle for 10 seconds");
                    tr.cancel();
                    System.out.println("Timer cancelled!");

                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            //update application thread
                            System.out.println("Running on application thread");
                            try {
                                Helper.recordCancel(getUser().getUserName(), "Cancelled at GiftCardPayStage due to Idle ");
                                FXMLLoader loader = new FXMLLoader();
                                loader.setLocation(getClass().getResource("/view/DefaultStage.fxml"));
                                Parent root = loader.load();

                                DefaultStage defaultStage = loader.getController();
                                defaultStage.setUser(getUser());

                                Scene scene = new Scene(root);
                                stage.setScene(scene);
                                stage.show();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    //Platform.setImplicitExit(false); 


                    timing = 0;
                } else {
                    if (notIdle) {
                        timing = 0;
                        notIdle = false;
                    } 
                }
                System.out.println(timing);
            }
        }, 0, 1000);

        
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                //Platform.exit();
                tr.cancel();
            }
        });
    }

    public void switchToReceipt(ActionEvent event) {
        String giftCardNum = null;
        try {
            giftCardNum = giftCardTextField.getText();
            Validator validator = new ValidatorImpl();

//            System.out.println("Payment required : " + paymentAmount);
//            System.out.println("Gift card balance : " + getGiftCardBalance(giftCardNum));
//            System.out.println(validator.checkGiftCardBalance(giftCardNum, paymentAmount));

            if (!validator.giftCardExists(giftCardNum))
            {
                errorLabel.setText("Gift card does not exists");
                Helper.recordCancel(getUser().getUserName(), "Invalid gift card");

            }
            else if (validator.giftCardUsed(giftCardNum))
            {
                errorLabel.setText("Gift card has already been redeemed");
                Helper.recordCancel(getUser().getUserName(), "Gift card already redeemed");
            }
            else if (!validator.checkGiftCardBalance(giftCardNum, paymentAmount))
            {
                String errorText = String.format("Gift card does not have enough balance. The balance is %.2f, The total price is %d",
                                                  getGiftCardBalance(giftCardNum),
                                                  paymentAmount);
                errorLabel.setText(errorText);
                Helper.recordCancel(getUser().getUserName(), "Gift card does not have enough balance");
            }
            else
            {
                System.out.println("Cancel timer before switching to Receipt");
                tr.cancel();

                errorLabel.setText("");
                Helper.availableSeatUpdater(getMovie(), getTicketAmount());
                String transactionNumber = Receipt.display("GiftCard",getUser(), "GiftCard");
                useGiftCard(giftCardNum);

                Helper.recordBooking(getUser(), getMovie(), ticketsString, paymentAmount, "Gift Card",transactionNumber);

                switchToDefaultStageAfterTransaction(event);
            }

        } catch (RuntimeException e) {
            e.getStackTrace();
        }
        System.out.println("Switch to receipt");
    }

    public void switchToDefaultStage(ActionEvent event) {
        System.out.println("Cancel timer before switching to credit card stage");
        tr.cancel();
        try {
            System.out.println("Switch to default stage");
            Helper.recordCancel(getUser().getUserName(), "Cancelled at GiftCardStage");
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/DefaultStage.fxml"));
            Parent root = loader.load();
            
            DefaultStage defaultStageController = loader.getController();
            defaultStageController.setUser(getUser());

            Stage stage =(Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // successful payment
    public void switchToDefaultStageAfterTransaction(ActionEvent event) {
        try {
            System.out.println("Switch to default stage");
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/DefaultStage.fxml"));
            Parent root = loader.load();

            DefaultStage defaultStageController = loader.getController();
            defaultStageController.setUser(getUser());

            Stage stage =(Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    void useGiftCard(String giftcardNumber)
    {
        String path = "src/main/java/TicketBooking/Storage/" + "Giftcard.csv";
        try {
            CSVParser csvParser = new CSVParserBuilder().withSeparator(',').build();

            CSVReader reader = new CSVReaderBuilder(new FileReader(path)).withCSVParser(csvParser).build();

            List<String[]> cardData = reader.readAll();


            boolean firstLine = true;

            for (int i = 0;i<cardData.size();i++)
            {
                if (firstLine)
                {
                    firstLine = false;
                    continue;
                }
                if (cardData.get(i)[1].equals(giftcardNumber))
                {
                    cardData.get(i)[3] = "false";
                }
            }

            CSVWriter writer = new CSVWriter(
                    new OutputStreamWriter(new FileOutputStream(path), StandardCharsets.UTF_8),
                    ',',
                    CSVWriter.NO_QUOTE_CHARACTER,
                    CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                    CSVWriter.DEFAULT_LINE_END
            );
            writer.writeAll(cardData);
            writer.flush();
            writer.close();

        } catch (FileNotFoundException e){

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    double getGiftCardBalance(String giftCardNumber)
    {
        String path = "src/main/java/TicketBooking/Storage/" + "Giftcard.csv";
        try {
            CSVParser csvParser = new CSVParserBuilder().withSeparator(',').build();

            CSVReader reader = new CSVReaderBuilder(new FileReader(path)).withCSVParser(csvParser).build();

            List<String[]> cardData = reader.readAll();


            boolean firstLine = true;

            for (int i = 0;i<cardData.size();i++)
            {
                if (firstLine)
                {
                    firstLine = false;
                    continue;
                }
                if (cardData.get(i)[1].equals(giftCardNumber))
                {
                    return Double.parseDouble(cardData.get(i)[2]);
                }
            }

        } catch (FileNotFoundException e){

        } catch (IOException e) {
            e.printStackTrace();
        }

        return 0;
    }

    @FXML
    public void keyPressed(KeyEvent event) {
        notIdle = true;
    }
    
    @FXML
    public void mouseClicked(MouseEvent event) {
        notIdle = true;
    }

    @FXML
    public void mouseMoved(MouseEvent event) {
        notIdle = true;
    }
}
