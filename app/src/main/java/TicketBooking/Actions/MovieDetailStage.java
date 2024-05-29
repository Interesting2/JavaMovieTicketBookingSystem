package TicketBooking.Actions;

import TicketBooking.Management.Movie;
import TicketBooking.Management.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MovieDetailStage implements Initializable {
    private Movie movie;
    private User user;

    public Movie getMovie() {
        return this.movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @FXML
    private Button backButton;

    @FXML
    private Button bookButton;

    @FXML
    private Label mCast;

    @FXML
    private Label mDirector;

    @FXML
    private Label mRating;

    @FXML
    private Label mReleaseDate;

    @FXML
    private Label mRunningTime;

    @FXML
    private Label mSize;

    @FXML
    private Label mSynopsis;

    @FXML
    private Label mTitle;

    @FXML
    private Label mUpcomingTime;

    @FXML
    private Label movieDetail;

    @FXML
    private Label movieName;

    @FXML
    private AnchorPane pane;

    @FXML
    private ImageView poster;

    @FXML
    private Pane descriptionPane;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Movie selectedMovie = null;
        try {
            selectedMovie = Helper.readMovieNameFromTemp();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        String name = selectedMovie.getName().replaceAll(" ", "");
        String path = "/images/" + name + ".jpg";
        Image img;
        if (getClass().getResourceAsStream(path) == null) {
            img = new Image(getClass().getResourceAsStream("/images/image-not-found.png"));
        } else {
            img = new Image(getClass().getResourceAsStream(path));
        }

        poster.setImage(img);
        mTitle.setText(selectedMovie.getName());
        //mTitle.setStyle("-fx-font-weight: bold; -fx-font-size: 30pt");

        mRating.setText(selectedMovie.getClassification());
        mSize.setText(selectedMovie.getScreenSize());
        mSynopsis.setText(selectedMovie.getSynopsis());
        mDirector.setText(selectedMovie.getDirector());
        mCast.setText(selectedMovie.getCast());
        mReleaseDate.setText(selectedMovie.getReleaseDate());
        mUpcomingTime.setText(selectedMovie.getUpcomingTime());
        mRunningTime.setText(Integer.toString(selectedMovie.getRunningTime()));
    }


    public void switchToTicketIssueStage(ActionEvent event) {
        String screenType = ((Button) event.getSource()).getText();
        /**
         * pass the screenType to TicketIssueStage
         */
        System.out.println("Booking in Progress");
        System.out.println("Username is : " + getUser().getUserName());
        System.out.println("Usertype is : " + getUser().getUserType());
        if (getUser().getUserType().equals("Customer")) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/view/TicketIssueStage.fxml"));
                Parent root = loader.load();

                TicketIssueStage ticketIssueStage = loader.getController();
                ticketIssueStage.setMovie(getMovie());
                ticketIssueStage.setUser(getUser());

                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Booking without logging in ");
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/view/RegistrationAlert.fxml"));
                Parent root = loader.load();

                RegistrationAlertStage registrationAlertStageController = loader.getController();
                //registrationAlertStageController.setUser(user);

                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void switchToDefaultStage(ActionEvent event) {
        try {
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

}
