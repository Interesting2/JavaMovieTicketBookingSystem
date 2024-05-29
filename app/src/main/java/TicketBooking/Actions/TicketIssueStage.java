package TicketBooking.Actions;

import TicketBooking.Management.Movie;
import TicketBooking.Management.User;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import TicketBooking.App;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import java.util.Timer;
import java.util.TimerTask;

public class TicketIssueStage implements Initializable {

    private Movie movie;
    private User user;
    private Timer tr;
    private int timing;
    private boolean notIdle;

    //movie getter and setter
    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    //user getter and setter

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    int totalPrice = 0,
            childPrice = 0, studentPrice = 0, adultPrice = 0, seniorPrice = 0;
    int childQuantity = 0,
            studentQuantity = 0,
            adultQuantity = 0,
            seniorQuantity = 0;
    int ticketAmount = 0;


    @FXML
    ComboBox<String> childQuantityCombo,
            childSeatPositionCombo,
            studentQuantityCombo,
            studentSeatPositionCombo,
            adultQuantityCombo,
            adultSeatPositionCombo,
            seniorQuantityCombo,
            seniorSeatPositionCombo;

    @FXML
    Button creditCardButton, giftCardButton, cancelButton;

    @FXML
    Label errorLabel, totalPriceLabel, childPriceLabel, studentPriceLabel, adultPriceLabel, seniorPriceLabel;

    /**
     * From the movie booking stage,
     * it should pass the screenType to this stage
     */


    public int setDefaultPrice(String screenType) {
        switch (screenType) {
            case "Gold":
                return 30;
            case "Silver":
                return 20;
            default:
                return 10;
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        childQuantityCombo.getSelectionModel().select(childQuantity);
        childSeatPositionCombo.getSelectionModel().select(0);
        studentQuantityCombo.getSelectionModel().select(studentQuantity);
        studentSeatPositionCombo.getSelectionModel().select(0);
        adultQuantityCombo.getSelectionModel().select(adultQuantity);
        adultSeatPositionCombo.getSelectionModel().select(0);
        seniorQuantityCombo.getSelectionModel().select(seniorQuantity);
        seniorSeatPositionCombo.getSelectionModel().select(0);

        totalPriceLabel.setText("$" + totalPrice);
        childPriceLabel.setText("$" + childPrice);
        studentPriceLabel.setText("$" + studentPrice);
        adultPriceLabel.setText("$" + adultPrice);
        seniorPriceLabel.setText("$" + seniorPrice);
        //TODO: 2 mins time out count down

        Stage stage = App.getStage();

        tr = new Timer();
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
                                Helper.recordCancel(getUser().getUserName(), "Cancelled at TicketIssueStage due to Idle ");
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

    public void quantityProcess(ActionEvent event) {
        int defaultPrice = setDefaultPrice(getMovie().getScreenSize());

        int childQuantity = Integer.parseInt(childQuantityCombo.getValue()),
            studentQuantity = Integer.parseInt(studentQuantityCombo.getValue()),
            adultQuantity = Integer.parseInt(adultQuantityCombo.getValue()),
            seniorQuantity = Integer.parseInt(seniorQuantityCombo.getValue());
        int childPrice = childQuantity * (defaultPrice - 2),
            studentPrice = studentQuantity * (defaultPrice - 1),
            adultPrice = adultQuantity * defaultPrice,
            seniorPrice = seniorQuantity * (defaultPrice - 2);
        totalPrice = childPrice + studentPrice + adultPrice + seniorPrice;
        ticketAmount = childQuantity + studentQuantity + adultQuantity + seniorQuantity;

        /**
         * There should be a statement here to determine if it is oversold
         */

        totalPriceLabel.setText("$" + totalPrice);
        childPriceLabel.setText("$" + childPrice);
        studentPriceLabel.setText("$" + studentPrice);
        adultPriceLabel.setText("$" + adultPrice);
        seniorPriceLabel.setText("$" + seniorPrice);
        

    }

    public void switchToCreditCardPay(ActionEvent event) {
       
        if (totalPrice == 0) {
            errorLabel.setText("Please select a quantity");
        } else {
            if (ticketAmount > getMovie().getAvailableSeats()) {
                errorLabel.setText("Sorry, There are not enough seats");
            } else {
                try {
                    System.out.println("Cancel timer before switching to credit card stage");
                    tr.cancel();
                    tr.purge();

                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/view/CreditCardPaymentStage.fxml"));
                    Parent root = loader.load();

                    CreditCardPaymentStage creditCardPaymentStage = loader.getController();
                    creditCardPaymentStage.setUser(getUser());
                    creditCardPaymentStage.setMovie(getMovie());
                    creditCardPaymentStage.setTicketAmount(ticketAmount);
                    creditCardPaymentStage.setPaymentAmount(totalPrice);

                    String tickets = this.generateTicketString();
//                    System.out.println("ticket String:  " + tickets);

                    creditCardPaymentStage.setTicketsString(tickets);


                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void switchToGiftCardPay(ActionEvent event) {
        if (totalPrice == 0) {
            errorLabel.setText("Please select a quantity");
        } else {
            if (ticketAmount > getMovie().getAvailableSeats()) {
                errorLabel.setText("Sorry, There are not enough seats");
            } else {
                try {
                    tr.cancel();
                    tr.purge();
                    System.out.println("Cancel timer before switching to gift card stage");
                    
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/view/GiftCardPaymentStage.fxml"));
                    Parent root = loader.load();

                    GiftCardPaymentStage giftCardPayementStage = loader.getController();

                    giftCardPayementStage.setUser(getUser());
                    giftCardPayementStage.setMovie(getMovie());
                    giftCardPayementStage.setTicketAmount(ticketAmount);
                    giftCardPayementStage.setPaymentAmount(totalPrice);


                    String tickets = this.generateTicketString();

//                    System.out.println("ticket String:  " + tickets);
                    giftCardPayementStage.setTicketsString(tickets);

                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private String generateTicketString()
    {
        String tickets = "";

        if (Integer.parseInt(childQuantityCombo.getValue()) > 0)
        {
            System.out.println(childQuantity + " " + childQuantityCombo + " " + childSeatPositionCombo);
            tickets += childQuantityCombo.getValue() + " " + childSeatPositionCombo.getValue() + " child tickets ";
        }
        if (Integer.parseInt(childQuantityCombo.getValue()) > 0)
        {
            tickets += studentQuantityCombo.getValue() + " " + studentSeatPositionCombo.getValue() + " student tickets ";
        }
        if (Integer.parseInt(adultQuantityCombo.getValue()) > 0)
        {
            tickets += adultQuantityCombo.getValue() + " " + adultSeatPositionCombo.getValue() + " adult tickets ";
        }
        if (Integer.parseInt(seniorQuantityCombo.getValue()) > 0)
        {
            tickets += seniorQuantityCombo.getValue() + " " + seniorSeatPositionCombo.getValue() + " senior tickets ";
        }

        return tickets;
    }

    public void switchToDefaultStage(ActionEvent event) {
        try {
            tr.cancel();
            tr.purge();
            System.out.println("Cancel timer before switching to Default stage");
            Helper.recordCancel(getUser().getUserName(), "Cancel at TicketIssueStage");
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/DefaultStage.fxml"));
            Parent root = loader.load();

            DefaultStage defaultStage = loader.getController();
            defaultStage.setUser(getUser());

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
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
