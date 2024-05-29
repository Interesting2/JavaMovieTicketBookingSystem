package TicketBooking.Actions;

import TicketBooking.Management.Movie;
import TicketBooking.Management.User;
import TicketBooking.Management.Validator;
import TicketBooking.Management.ValidatorImpl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;

public class DefaultStage {

    @FXML
    Label userNameLabel;

    @FXML
    TableColumn<Movie, String> name;

    @FXML
    TableColumn<Movie, String> classification;

    @FXML
    TableColumn<Movie, String> screenSize;

    @FXML
    TableColumn<Movie, String> cinemaName;

    @FXML
    TableColumn<Movie, String> director;

    @FXML
    TableColumn<Movie, String> cast;

    @FXML
    TableColumn<Movie, String> upcomingTime;

    @FXML
    TableColumn<Movie, String> releaseDate;

    @FXML
    TableColumn<Movie, Integer> availableSeat;

    @FXML
    TableView<Movie> movieTableView;

    @FXML
    Button backButton, book;

    @FXML
    private Button unfilter;

    @FXML
    ComboBox<String> filterCinema, filterScreen;

    private ObservableList<String> observerCinema;

    private ObservableList<String> observerScreen;

    ObservableList<Movie> movieList;

    ObservableList<Movie> filteredList;

    private User user;

    String fCinema = "";

    String fScreen = "";

    public void setUser(User user){
        this.user = user;
    }

    public User getUser(){
        return user;
    }

    public String getUserName(){
        return user.getUserName();
    }

    private void setUpComboBox() {
        System.out.println("setUpComboBox()");
        Validator validator = new ValidatorImpl();  // read path

        //List<String> cinemaNames = new ArrayList<String>();
        observerCinema = FXCollections.observableArrayList(); 
        observerScreen = FXCollections.observableArrayList();
        filteredList = FXCollections.observableArrayList();

        observerScreen.addAll("Gold", "Silver", "Bronze");
        
        BufferedReader br = validator.loadCsvFile("src/main/java/TicketBooking/Storage/ThisWeekMovie.csv");
        boolean skip = true;
        String line = "";
        try {
            while ( (line = br.readLine()) != null) {
                if (skip) skip = false;
                else {
                    String[] details = line.split(",");
                    String name = details[11];
                    if (!observerCinema.contains(name)) {
                        observerCinema.add(name);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("DefaultStage class: setUpComboBox()");
        }
        
        filterCinema.setItems(observerCinema);
        filterScreen.setItems(observerScreen);

    }

    @FXML
    void resetFilteredList(ActionEvent event) {
        filteredList.clear();
        movieTableView.setItems(movieList);
    }

    @FXML
    void filterByCinemaName(ActionEvent event) {
        filteredList.clear();
        String value = filterCinema.getSelectionModel().getSelectedItem();
        System.out.println("filterByCinemaName(): Selected a cinema: " + value);

        fCinema = value;
        for (Movie movie : movieList) {
            if (movie.getCinemaName().equals(value)) {
                if (!(fScreen.equals(""))) {
                    if (movie.getScreenSize().equals(fScreen)) {
                        filteredList.add(movie); 
                    }
                } else {
                    filteredList.add(movie);
                }
            }
        }
        movieTableView.setItems(filteredList);
    }

    @FXML
    void filterByScreenSize(ActionEvent event) {
        filteredList.clear();
        String value = filterScreen.getSelectionModel().getSelectedItem();
        System.out.println("filterByScreenSize(): Selected a Screen Size: " + value);

        fScreen = value;
        for (Movie movie : movieList) {
            if (movie.getScreenSize().equals(value)) {
                if (!(fCinema.equals(""))) {
                    if (movie.getCinemaName().equals(fCinema)) {
                        filteredList.add(movie); 
                    }
                } else {
                    filteredList.add(movie);
                }   
            }
        }
        movieTableView.setItems(filteredList);
    }

    public void initialize() {
        
        Helper.extractThisWeekMovie();
        Helper.extractUpcomingMovies();

        User loggedInUser = null;
        try {
            loggedInUser = Helper.readUserFromTemp();
            if (loggedInUser.getUserType().equals("Customer")) {
                backButton.setText("Logout");
            } 
        } catch (IOException e) {
            e.printStackTrace();
        }

        // show movieList in tableView
        userNameLabel.setText(loggedInUser.getUserName());
        getMovieDataBase();
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        classification.setCellValueFactory(new PropertyValueFactory<>("classification"));
        screenSize.setCellValueFactory(new PropertyValueFactory<>("screenSize"));
        cinemaName.setCellValueFactory(new PropertyValueFactory<>("cinemaName"));
        director.setCellValueFactory(new PropertyValueFactory<>("director"));
        cast.setCellValueFactory(new PropertyValueFactory<>("cast"));
        upcomingTime.setCellValueFactory(new PropertyValueFactory<>("upcomingTime"));
        releaseDate.setCellValueFactory(new PropertyValueFactory<>("releaseDate"));
        availableSeat.setCellValueFactory(new PropertyValueFactory<>("availableSeats"));
        movieTableView.setItems(movieList);

        setUpComboBox();
    }

    // set a filter for movieList
    /*
    public void setFilter(String filter) {
        movieList.clear();
        for (Movie movie : getMovieDataBase()) {
            if (movie.getName().contains(filter)) {
                movieList.add(movie);
            }
        }
    }
    */


    private void getMovieDataBase() {
        movieList = FXCollections.observableArrayList();
        Helper.getDB(movieList);
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

    public void switchToMovieDetailStage(ActionEvent event) throws IOException {
        Movie selectedMovie = movieTableView.getSelectionModel().getSelectedItem();
        System.out.println("Selected movie name is : " + selectedMovie.getName());
        Helper.writeMovieNameToTemp(selectedMovie);
        if (selectedMovie != null) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/view/MovieDetailStage.fxml"));
                Parent root = loader.load();

                MovieDetailStage movieDetailStageController = loader.getController();
                movieDetailStageController.setMovie(selectedMovie);
                movieDetailStageController.setUser(getUser());

                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
