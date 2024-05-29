package TicketBooking.Actions;


import TicketBooking.Management.Movie;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.lang.StringBuilder;

import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.ComboBox;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.control.TableView;
import javafx.collections.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.control.TableColumn.CellEditEvent;

import javafx.event.EventHandler;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.File;
import javafx.event.ActionEvent;

import javafx.util.converter.IntegerStringConverter;

import java.time.format.DateTimeFormatter;

public class MovieDBStage {

    @FXML
    private TableColumn<Movie, String> cCast;

    @FXML
    private TableColumn<Movie, String> cClassification;

    @FXML
    private TableColumn<Movie, String> cDirector;

    @FXML
    private TableColumn<Movie, ImageView> cImage;

    @FXML
    private TableColumn<Movie, String> cName;

    @FXML
    private TableColumn<Movie, String> cRelease;

    @FXML
    private TableColumn<Movie, String> cScreen;

    @FXML
    private TableColumn<Movie, String> cSynopsis;

    @FXML
    private TableColumn<Movie, Integer> cTime;

    @FXML
    private TableColumn<Movie, String> cUpcoming;

    @FXML
    private TableColumn<Movie, String> cCinema;

    @FXML
    private TableColumn<Movie, Integer> cSeats;

    @FXML
    private TableView<Movie> mTable;

    ObservableList<Movie> mList;

    @FXML
    private CheckBox mBronze;

    @FXML
    private TextArea mCast;

    @FXML
    private TextArea mDirector;

    @FXML
    private CheckBox mG;

    @FXML
    private CheckBox mGold;

    @FXML
    private TextArea mImage;

    @FXML
    private CheckBox mM;

    @FXML
    private CheckBox mMA;

    @FXML
    private TextArea mName;

    @FXML
    private CheckBox mPG;

    @FXML
    private CheckBox mR;

    @FXML
    private DatePicker mRelease;

    @FXML
    private TextArea mSeats;

    @FXML
    private CheckBox mSilver;

    @FXML
    private TextArea mSynopsis;

    @FXML
    private TextField mTime;

    @FXML
    private ComboBox<String> mUpcoming;

    @FXML
    private TextArea mCinema;

    @FXML
    private Button mAdd;

    @FXML
    private Button mEdit;

    @FXML
    private Button mDelete;

    private String selected;

    private ObservableList<String> upcomingTimes = FXCollections.observableArrayList("10:00","12:00","14:00","16:00","18:00","20:00");

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
            System.out.println("Class MovieDB: switchToManageStage() failed");
        }
    }

    public BufferedReader loadFile(String path) {

        try {
            BufferedReader b = new BufferedReader(new FileReader(path));

            System.out.println("Read valid cards successfully");
            return b;

        } catch(IOException e) {
            System.out.println("Error when opening File");
            return null;
        }
    }


    public void initialize() {;

        //...

        mTable.setRowFactory( tv -> {
            TableRow<Movie> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() >= 1 && (! row.isEmpty()) ) {
                    Movie rowData = row.getItem();
                    System.out.println(rowData);
                    selected = rowData.toString();
                }
            });
            return row ;
        });
        cName.setCellValueFactory(new PropertyValueFactory<Movie, String>("name"));
        cName.setCellFactory(TextFieldTableCell.forTableColumn());
        cName.setOnEditCommit(new EventHandler<CellEditEvent<Movie, String>>() {
            public void handle(CellEditEvent<Movie, String> event) {
                Movie movie = event.getRowValue();
                movie.setName(event.getNewValue());
            }
        });


        cClassification.setCellValueFactory(new PropertyValueFactory<Movie, String>("classification"));
        cClassification.setCellFactory(TextFieldTableCell.forTableColumn());
        cClassification.setOnEditCommit(new EventHandler<CellEditEvent<Movie, String>>() {
            public void handle(CellEditEvent<Movie, String> event) {
                Movie movie = event.getRowValue();
                movie.setClassification(event.getNewValue());
            }
        });


        cScreen.setCellValueFactory(new PropertyValueFactory<Movie, String>("screenSize"));
        cScreen.setCellFactory(TextFieldTableCell.forTableColumn());
        cScreen.setOnEditCommit(new EventHandler<CellEditEvent<Movie, String>>() {
            public void handle(CellEditEvent<Movie, String> event) {
                Movie movie = event.getRowValue();
                movie.setScreenSize(event.getNewValue());
            }
        });



        cSynopsis.setCellValueFactory(new PropertyValueFactory<Movie, String>("synopsis"));
        cSynopsis.setCellFactory(TextFieldTableCell.forTableColumn());
        cSynopsis.setOnEditCommit(new EventHandler<CellEditEvent<Movie, String>>() {
            public void handle(CellEditEvent<Movie, String> event) {
                Movie movie = event.getRowValue();
                movie.setSynopsis(event.getNewValue());
            }
        });

        cDirector.setCellValueFactory(new PropertyValueFactory<Movie, String>("director"));
        cDirector.setCellFactory(TextFieldTableCell.forTableColumn());
        cDirector.setOnEditCommit(new EventHandler<CellEditEvent<Movie, String>>() {
            public void handle(CellEditEvent<Movie, String> event) {
                Movie movie = event.getRowValue();
                movie.setDirector(event.getNewValue());
            }
        });


        cCast.setCellValueFactory(new PropertyValueFactory<Movie, String>("cast"));
        cCast.setCellFactory(TextFieldTableCell.forTableColumn());
        cCast.setOnEditCommit(new EventHandler<CellEditEvent<Movie, String>>() {
            public void handle(CellEditEvent<Movie, String> event) {
                Movie movie = event.getRowValue();
                movie.setCast(event.getNewValue());
            }
        });

        cUpcoming.setCellValueFactory(new PropertyValueFactory<Movie, String>("upcomingTime"));
        cUpcoming.setCellFactory(TextFieldTableCell.forTableColumn());
        cUpcoming.setOnEditCommit(new EventHandler<CellEditEvent<Movie, String>>() {
            public void handle(CellEditEvent<Movie, String> event) {
                Movie movie = event.getRowValue();
                movie.setUpcomingTime(event.getNewValue());
            }
        });

        cRelease.setCellValueFactory(new PropertyValueFactory<Movie, String>("releaseDate"));
        cRelease.setCellFactory(TextFieldTableCell.forTableColumn());
        cRelease.setOnEditCommit(new EventHandler<CellEditEvent<Movie, String>>() {
            public void handle(CellEditEvent<Movie, String> event) {
                Movie movie = event.getRowValue();
                movie.setReleaseDate(event.getNewValue());
            }
        });

        cTime.setCellValueFactory(new PropertyValueFactory<Movie, Integer>("runningTime"));
        cTime.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        cTime.setOnEditCommit(new EventHandler<CellEditEvent<Movie, Integer>>() {

            public void handle(CellEditEvent<Movie, Integer> event) {
                Movie movie = event.getRowValue();
                movie.setRunningTime(event.getNewValue());
            }
        });

        cSeats.setCellValueFactory(new PropertyValueFactory<Movie, Integer>("availableSeats"));
        cSeats.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        cSeats.setOnEditCommit(new EventHandler<CellEditEvent<Movie, Integer>>() {

            public void handle(CellEditEvent<Movie, Integer> event) {
                Movie movie = event.getRowValue();
                movie.setAvailableSeats(event.getNewValue());
            }
        });

        cCinema.setCellValueFactory(new PropertyValueFactory<Movie, String>("cinemaName"));
        cCinema.setCellFactory(TextFieldTableCell.forTableColumn());
        cCinema.setOnEditCommit(new EventHandler<CellEditEvent<Movie, String>>() {
            public void handle(CellEditEvent<Movie, String> event) {
                Movie movie = event.getRowValue();
                movie.setCinemaName(event.getNewValue());
            }
        });

        cImage.setCellValueFactory(new PropertyValueFactory<Movie, ImageView>("movieImage"));

        mUpcoming.setItems(upcomingTimes);
        mList = FXCollections.observableArrayList();
        Helper.getDB(mList);
        mTable.setEditable(true);
        mTable.setItems(mList);

    }

    private Movie getInputField() {
        String name = mName.getText().replaceAll("\n", System.getProperty("line.separator"));
        System.out.println(name);

        StringBuilder classification= new StringBuilder();
        boolean tmpM = mM.isSelected();
        boolean tmpA = mMA.isSelected();
        boolean tmpG = mG.isSelected();
        boolean tmpPG = mPG.isSelected();
        boolean tmpR = mR.isSelected();

        if (tmpM) classification.append("Mature(M), ");
        if (tmpA) classification.append("MA15, ");
        if (tmpG) classification.append("General(G), ");
        if (tmpPG) classification.append("Parental Guidance(PG), ");
        if (tmpR) classification.append("R18+");
        if (classification.length() != 0 && classification.charAt(classification.length() - 1) == ' ') {
            classification.replace(classification.length() - 2, classification.length(), "");
        }
        String classString = classification.toString();
        System.out.println(classString);

        StringBuilder screen = new StringBuilder();

        boolean gold = mGold.isSelected();
        boolean silver = mSilver.isSelected();
        boolean bronze = mBronze.isSelected();
        if (gold) screen.append("Gold, ");
        if (silver) screen.append("Silver, ");
        if (bronze) screen.append("Bronze, ");
        if (screen.length() != 0 && screen.charAt(screen.length() - 1) == ' ') {
            screen.replace(screen.length() - 2, screen.length(), "");
        }
        String screenString = screen.toString();
        System.out.println(screenString);

        String synopsisPath = mSynopsis.getText().replaceAll("\n", System.getProperty("line.separator"));
        String synopsis = "";
        if (synopsis != "") {
            try {
                BufferedReader txt = loadFile(synopsisPath);
                synopsis = txt.readLine();
            } catch (IOException e) {
                System.out.println("Line 563 Read synopsis path error");
            }
            System.out.println(synopsis);
        }

        String director = mDirector.getText().replaceAll("\n", System.getProperty("line.separator"));
        System.out.println(director);

        String cast = mCast.getText().replaceAll("\n", System.getProperty("line.separator"));
        System.out.println(cast);

        String upcomingTime = "";
        if (mUpcoming.getValue() != null) {
            upcomingTime = mUpcoming.getValue().toString();
        }
        System.out.println(upcomingTime);

        String releaseTime = "";
        if (mRelease.getValue() != null) {
            releaseTime = mRelease.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        }
        System.out.println(releaseTime);

        int runningTime = -1;
        String rTime = mTime.getText().replaceAll("\n", System.getProperty("line.separator"));
        System.out.println("Here " + rTime.length());
        if (rTime.length() != 0) {
            runningTime = Integer.parseInt(rTime);
        }
        System.out.println(runningTime);

        int availableSeats = -1;
        String rSeats = mSeats.getText().replaceAll("\n", System.getProperty("line.separator"));
        System.out.println("Here " + rSeats.length());
        if (rSeats.length() != 0) {
            availableSeats = Integer.parseInt(rSeats);
        }
        System.out.println(availableSeats);



        String storage = mImage.getText().replaceAll("\n", System.getProperty("line.separator"));
        System.out.println(storage);

        String url = null;
        if (storage != "") {
            File f = new File(storage);
            url = f.toURI().toString();
            //System.out.println(url);
        }
        Image image = new Image(url);
        ImageView movieImage = new ImageView(image);

        String cinemaName = mCinema.getText().replaceAll("\n", System.getProperty("line.separator"));

        Movie movie = new Movie(name, classString, screenString, synopsis, synopsisPath, director,
                cast, upcomingTime, releaseTime, runningTime, movieImage, storage , availableSeats, cinemaName);

        return movie;
    }

    public void addMovie() {
        System.out.println("Add");
        Movie movie = getInputField();
        mList.add(movie);

        // write to file

        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/java/TicketBooking/Storage/ThisWeekMovie.csv",true));
            writer.write('\n' + movie.toString());
            writer.close();

        }catch(FileNotFoundException e){
            System.out.println("File not found!");
        }catch(IOException e){
            System.out.println("Error occurs while writing to the file.");
        }
        mTable.setItems(mList);


    }

    public BufferedReader loadCsvFile() {

        try {
            String path = "src/main/java/TicketBooking/Storage/ThisWeekMovie.csv";
            BufferedReader br = new BufferedReader(new FileReader(path));

            System.out.println("Read movies successfully");
            return br;

        } catch(IOException e) {
            System.out.println("Error when opening File");
            return null;
        }
    }

    private void writeData(String data) {
        try{
            System.out.println(data);
            BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/java/TicketBooking/Storage/ThisWeekMovie.csv",false));
            writer.write(data);
            writer.close();

        }catch(FileNotFoundException e){
            System.out.println("File not found!");
        }catch(IOException e){
            System.out.println("Error occurs while writing to the file.");
        }

    }

    public void editMovie() {

        Movie movie = mTable.getSelectionModel().getSelectedItem();

        System.out.println("Edit Button Pressed");
        BufferedReader reader = loadCsvFile();

        StringBuffer inputBuffer = new StringBuffer();
        String label = "name, classification, screen, sypnosis, director, cast, upcoming_time, release_date,running time, image,availableSeats,cinemaName";
        inputBuffer.append(label + "\n");
        String line;
        boolean skip = true;
        boolean done = true;
        try {
            while ((line = reader.readLine()) != null) {
                if (skip) skip = false;
                else {
                    String[] mvDetails = line.split(",");
                    if (selected.equals(line) && done) {
                        String target = movie.toString();
                        inputBuffer.append(target);
                        inputBuffer.append('\n');
                        done = false;
                    }else{
                        inputBuffer.append(line);
                        inputBuffer.append('\n');
                    }
                }
            }

        }catch(IOException e){
            System.out.println("Error occurs while reading the file.");
        }

        String data = inputBuffer.toString().trim();
        writeData(data);

    }

    public void deleteMovie() {
        Movie movie = mTable.getSelectionModel().getSelectedItem();

        System.out.println("delete button pressed");
        BufferedReader reader = loadCsvFile();

        StringBuffer inputBuffer = new StringBuffer();
        String label = "name, classification, screen, sypnosis, director, cast, upcoming_time, release_date,running time, image,availableSeats,cinemaName";
        inputBuffer.append(label + "\n");
        String line;
        boolean skip = true;
        boolean done = true;
        try {
            while ((line = reader.readLine()) != null) {
                if (skip) skip = false;
                else {
                    String[] mvDetails = line.split(",");
                    if (!(movie.toString().equals(line))) {
                        inputBuffer.append(line);
                        inputBuffer.append('\n');
                    }
                }
            }

        }catch(IOException e){
            System.out.println("Error occurs while reading the file.");
        }

        String data = inputBuffer.toString().trim();
        writeData(data);
    }
    public void refreshMovieDB(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/MovieDB.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.println("Class MovieDBStage: refreshMovieDB() failed");
        }
    }

}
