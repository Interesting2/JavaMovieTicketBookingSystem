package TicketBooking.Actions;

import TicketBooking.App;
import TicketBooking.Management.Movie;
import TicketBooking.Management.User;
import TicketBooking.Management.ValidatorImpl;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class CreditCardPaymentStage {
    private User user;
    private Movie movie;
    private int ticketAmount;
    private String ticketsString;
    private int paymentAmount;


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

    //ticketAmount setter
    public void setTicketsString(String ticketsString) {
        this.ticketsString = ticketsString;
    }


    @FXML
    TextField cardHolderTextField, cardNumFieldUnmasked;

    @FXML
    PasswordField cardNumFieldMasked;

    @FXML
    Button confirmButton, cancelButton;

    @FXML
    Label errorLabel;

    @FXML
    CheckBox rememberCard;

    /*
     * If you want the password to unmask after untick the checkbox
     * then use this method
     *
     public void toggleVisible(ActionEvent event) {
        if (cardNumFieldMasked.isVisible()) {
            cardNumFieldMasked.setVisible(false);
            cardNumFieldUnmasked.setVisible(true);
        } else {
            cardNumFieldMasked.setVisible(true);
            cardNumFieldUnmasked.setVisible(false);
        }
    }
     */

    public void initialize() {
        User loggedInUser = null;
        try {
            loggedInUser = Helper.readUserFromTemp();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!loggedInUser.getCardHoldName().equals("0")) {
            cardNumFieldUnmasked.setVisible(false);
            cardNumFieldMasked.setVisible(true);

            cardHolderTextField.setText(loggedInUser.getCardHoldName());
            cardNumFieldMasked.setText(loggedInUser.getCardNumber());
            rememberCard.setSelected(true);
        } else {
            cardNumFieldUnmasked.setVisible(true);
            cardNumFieldMasked.setVisible(false);
        }

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
                    tr.purge();
                    System.out.println("Timer cancelled!");

                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            //update application thread
                            System.out.println("Running on application thread");
                            try {
                                Helper.recordCancel(getUser().getUserName(), "Cancelled at CreditCardPayStage due to Idle ");
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
                tr.purge();
            }
        });
    }

    public String getCardNumber() {
        if (cardNumFieldMasked.isVisible()) {
            return cardNumFieldMasked.getText();
        } else {
            return cardNumFieldUnmasked.getText();
        }
    }

    public void rememberCard() {
        if (rememberCard.isSelected()) {
            getUser().setCardHoldName(cardHolderTextField.getText());
            getUser().setCardNumber(getCardNumber());
        } else {
            getUser().setCardHoldName("0");
            getUser().setCardNumber("0");
        }
    }

    public void switchToReceipt(ActionEvent event) throws IOException {
        /**
         * read from account database for remembered card
         */
        String cardHolder = null;
        String cardNum = null;
        System.out.println(cardHolder +", "+ cardNum);
        try {
            cardHolder = cardHolderTextField.getText();
            cardNum = getCardNumber();
        } catch (RuntimeException e) {
            e.getStackTrace();
        }
        System.out.println(cardHolder +", "+ cardNum);

        if (cardHolder != null && cardNum != null) {
            ValidatorImpl validator = new ValidatorImpl();
            if (validator.creditCardCheck(cardHolder, cardNum)) {
                tr.cancel();
                System.out.println("Cancel timer before switching to Reicpt");
                
                Helper.availableSeatUpdater(getMovie(), getTicketAmount());
                rememberCard();
                Helper.writeUserToTemp(getUser());
                Helper.userCardInfoUpdater(getUser());
                String transactionNumber = Receipt.display("CreditCard",getUser(), "CreditCard");

                Helper.recordBooking(getUser(), getMovie(), ticketsString, paymentAmount, "Credit Card",transactionNumber);

                switchToDefaultStageAfterTransaction(event);
            } else {
                errorLabel.setText("Invalid Credit Card");
                Helper.recordCancel(getUser().getUserName(), "Invalid Credit Card");
            }
        } else {
            errorLabel.setText("Please enter your credit card information");
        }
    }

    // This is for cancel
    public void switchToDefaultStage(ActionEvent event) {
        System.out.println("Cancel timer before switching to credit card stage");
        tr.cancel();
        tr.purge(); 
        try {
            Helper.recordCancel(getUser().getUserName(), "Cancelled at CreditCard Stage");
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/DefaultStage.fxml"));
            Parent root = loader.load();

            DefaultStage defaultStage = loader.getController();
            defaultStage.setUser(getUser());

            Stage stage =(Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // This is for successful payment
    public void switchToDefaultStageAfterTransaction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/DefaultStage.fxml"));
            Parent root = loader.load();

            DefaultStage defaultStage = loader.getController();
            defaultStage.setUser(getUser());

            Stage stage =(Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
