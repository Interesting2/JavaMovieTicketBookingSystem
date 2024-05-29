package TicketBooking.Actions;

import TicketBooking.Management.CancellationLog;
import TicketBooking.Management.Movie;
import TicketBooking.Management.Transaction;
import TicketBooking.Management.User;
import javafx.collections.*;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Helper {
    /**
     * For MovieDetailStage, the method initialize can't get data from getter
     * Therefore a quickfix is use a temp file to store the movie data for initialize
     * @param movie
     * @throws IOException
     */


    public static void writeMovieNameToTemp(Movie movie) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/java/TicketBooking/Storage/MovieTemp.csv"));
        StringBuilder str = new StringBuilder();
        str.append(movie.getName());
        str.append(",");
        str.append(movie.getClassification().replaceAll(", ", "/"));
        str.append(",");
        str.append(movie.getScreenSize().replaceAll(", ", "/"));
        str.append(",");
        str.append(movie.getSynopsisPath());        // pass path instead
        str.append(",");
        str.append(movie.getDirector());
        str.append(",");
        str.append(movie.getCast().replaceAll(", ", "/"));
        str.append(",");
        str.append(movie.getUpcomingTime());
        str.append(",");
        str.append(movie.getReleaseDate());
        str.append(",");
        str.append(movie.getRunningTime());
        str.append(",");
        str.append(movie.getAvailableSeats());
        str.append(",");
        str.append(movie.getCinemaName());
        str.append("\n");
        //System.out.println(str);
        writer.write(str.toString());
        writer.close();
    }

    public static Movie readMovieNameFromTemp() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("src/main/java/TicketBooking/Storage/MovieTemp.csv"));
        String line = reader.readLine();
        String[] data = line.split(",");

        /*
Mature(M),Gold, Silver, Bronze,Inspired by the famous Disneyland theme park ride, Disney's "Jungle Cruise" is an adventure-filled, rollicking thrill-ride down the Amazon with wisecracking skipper Frank Wolff and intrepid researcher Dr. Lily Houghton. Lily travels from London, England to the Amazon jungle and enlists Frank’s questionable services to guide her downriver on La Quila—his ramshackle-but-charming boat. Lily is determined to uncover an ancient tree with unparalleled healing abilities—possessing the power to change the future of medicine. Thrust on this epic quest together, the unlikely duo encounters innumerable dangers and supernatural forces, all lurking in the deceptive beauty of the lush rainforest. But as the secrets of the lost tree unfold, the stakes reach even higher for Lily and Frank and their fate—and mankind’s—hangs in the balance. ,Jaume Collet-Serra,Dwayne Johnson, Emily Blunt, Edgar Ramirez, Jack Whitehall, Jesse Plemons, Paul Giamatti,13:00/17:00/20:00,12/07/2021,0
        public Movie(String name, String classification, String screenSize, String synopsis, String synopsisPath, String director,
                  String cast, String upcomingTime, String releaseDate, int runningTime, ImageView movieImage, String storage, int availableSeat) {
        */
        BufferedReader br = loadFile(data[3]); // get synopsis from synopsis path
        String synopsis = "";
        try{
            synopsis = br.readLine();
        }catch (NullPointerException e){
            System.out.println("Null synopsis");
        }
        String classification = data[1].replaceAll("/", ", ");
        String screenSize = data[2].replaceAll("/", ", ");
        String cast = data[5].replaceAll("/", ", ");
        Movie movie = new Movie(data[0], classification, screenSize, synopsis, data[3], data[4], 
                        cast, data[6], data[7], Integer.parseInt(data[8]), null, "",
                        Integer.parseInt(data[9]), data[10]);

        reader.close();
        return movie;
    }
    
    public static void writeUser(User user) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/java/TicketBooking/Storage/UserAccount.csv", true));
        StringBuilder str = new StringBuilder();
        str.append(user.getUserName());
        str.append(",");
        str.append(user.getPassword());
        str.append(",");
        str.append(user.getUserType());
        str.append(",");
        str.append(user.getCardHoldName());
        str.append(",");
        str.append(user.getCardNumber());
        str.append("\n");
        System.out.println("User added to UserAccount DB is: " + str);
        writer.write(str.toString());
        writer.close();
    }

    public static void writeUserToTemp(User user) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/java/TicketBooking/Storage/UserTemp.csv"));
        StringBuilder str = new StringBuilder();
        str.append(user.getUserName());
        str.append(",");
        str.append(user.getPassword());
        str.append(",");
        str.append(user.getUserType());
        str.append(",");
        str.append(user.getCardHoldName());
        str.append(",");
        str.append(user.getCardNumber());
        str.append("\n");
        System.out.println("User added to UserTemp DB is: " + str);
        writer.write(str.toString());
        writer.close();
    }

    public static User readUserFromTemp() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("src/main/java/TicketBooking/Storage/UserTemp.csv"));
        String line = reader.readLine();
        String[] data = line.split(",");
        User user = new User(data[0], data[1], data[2], data[3], data[4]);
        reader.close();
        System.out.println("Retrieve User: " + data[0]+","+ data[1]+","+ data[2]+","+ data[3]+","+ data[4]);
        return user;
    }

    // readUserFromCsv(String userName)
    // get user from UserAccount.csv based on userName
    // the file directory is src/main/java/TicketBooking/Storage/UserAccount.csv
    // the format of the file is: userName, password, userType, cardHoldName, cardNumber
    public static User readUserFromCsv(String userName) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("src/main/java/TicketBooking/Storage/UserAccount.csv"));
        String line = reader.readLine();
        while (line != null) {
            String[] data = line.split(",");
            if (data[0].equals(userName)) {
                User user = new User(data[0], data[1], data[2], data[3], data[4]);
                reader.close();
                System.out.println("Retrieve User: " + data[0]+","+ data[1]+","+ data[2]+","+ data[3]+","+ data[4]);
                return user;
            }
            line = reader.readLine();
        }
        reader.close();
        return null;
    }


    private static boolean checkCharInString(String target, String ch) {
         return true && target.contains(ch);
    }

    public static BufferedReader loadFile(String path) {

        try {
            BufferedReader b = new BufferedReader(new FileReader(path));

            //System.out.println("Read valid cards successfully");
            return b;

        } catch(IOException e) {
            System.out.println("Error when opening File");
            return null;
        }
    }



    public static void getDB(ObservableList<Movie> mList) {
        // retrieve from database
        
        try {
            BufferedReader br = loadFile("src/main/java/TicketBooking/Storage/UpcomingMovies.csv");
            
            String line = "";
            boolean skip = true;
            //System.out.println("Helper getDB()");
            while ( (line = br.readLine()) != null) {
                if (skip) skip = false;
                else {
                    String[] mDetails = line.split(",");
                    //System.out.println("mDetails size: " + mDetails.length);
                    String name = mDetails[0];

                    String tmpClass = mDetails[1];
                    String classification = "";
                    String[] tmpC = {tmpClass};
                    if (checkCharInString(tmpClass, "/")) {
                        tmpC = tmpClass.split("/");
                    }
                    StringBuilder sbClass = new StringBuilder();
                    for (int i = 0; i < tmpC.length; i++) {
                        sbClass.append(tmpC[i]);
                        if (i != tmpC.length - 1) {
                            sbClass.append(", ");
                        }
                    }
                    classification = sbClass.toString();
                
                    String tmpScreen = mDetails[2];

                    String screen = "";
                    String[] tmpS = {tmpScreen};
                    StringBuilder sbScreen = new StringBuilder();
                    if (checkCharInString(tmpScreen, "/")) {
                        tmpS = tmpScreen.split("/");
                    }
                    for (int i = 0; i < tmpS.length; i++) {
                        sbScreen.append(tmpS[i]);
                        if (i != tmpS.length - 1) {
                            sbScreen.append(", ");
                        }
                    }
                    screen = sbScreen.toString();
                    
                    String synopsis = "";
                    String synopsisPath = mDetails[3];

                    BufferedReader txt = loadFile(synopsisPath);
                    synopsis = txt.readLine();

                    String director = mDetails[4];
                    
                    String tmpCast = mDetails[5];
                    String cast;
                    String[] tmpCA = {tmpCast};
                    StringBuilder sbCast = new StringBuilder();
                    if (checkCharInString(tmpCast, "/")) {
                        tmpCA = tmpCast.split("/");
                    }
                    for (int i = 0; i < tmpCA.length; i++) {
                        sbCast.append(tmpCA[i]);
                        if (i != tmpCA.length - 1) {
                            sbCast.append(", ");
                        }
                    }
                    cast = sbCast.toString();
                    
                    String upcomingTime = mDetails[6];
                    String releaseTime = mDetails[7];
                    int runningTime = Integer.parseInt(mDetails[8]);

                    String imagePath = mDetails[9];
                    File f = new File(imagePath);
                    String url = f.toURI().toString();
                    //System.out.println(url);
                    Image tmpImage = new Image(url);
                    ImageView movieImage = new ImageView(tmpImage);

                    String storage = mDetails[9];
                    //System.out.println("Storage : " + storage);
                    int availableSeats = Integer.parseInt(mDetails[10]);
                    String cinemaName = mDetails[11];
                    Movie movie = new Movie(name, classification, screen, synopsis, synopsisPath, director, 
                                            cast, upcomingTime, releaseTime, runningTime, movieImage, storage, availableSeats, cinemaName);
                    mList.add(movie);
                }
            }

        } catch(IOException e) {
            System.out.println("Error when opening File in Helper stage");
        }
        
    }

    //getCancellationLog(ObservableList<CancellationLog> cList)
    // the method to get the cancellation log from the file
    // the file directory is src/main/java/TicketBooking/Management/CancellationLog.java
    public static void getCancellationLog(ObservableList<CancellationLog> cList) {
        try {
            BufferedReader br = loadFile("src/main/java/TicketBooking/Storage/CancellationLog.csv");
            String line = "";
            while ((line = br.readLine()) != null) {
                String[] cancellationDetails = line.split(",");
                String time = cancellationDetails[0];
                String userName = cancellationDetails[1];
                String reason = cancellationDetails[2];

                CancellationLog cancellationLog = new CancellationLog(time, userName, reason);
                cList.add(cancellationLog);
            }

        } catch(IOException e) {
            System.out.println("Error when opening File in Helper stage");
        }
    }

    // Record the reason of cancellation of the transaction to a csv file
    // the file directory is src/main/java/TicketBooking/Storage/CancellationLog.csv
    // the format is: time, userName ,reason
    public static void recordCancel(String userName,String reason) {
        // get current time in String
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        String time = dateFormat.format(date);

        String filePath = "src/main/java/TicketBooking/Storage/CancellationLog.csv";
        try {
            FileWriter fw = new FileWriter(filePath, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(time + "," + userName + "," + reason);
            bw.newLine();
            bw.close();
        } catch (IOException e) {
            System.out.println("Error when opening File in Helper stage");
        }
    }

    public static void extractUpcomingMovies() {
        String filePath = "src/main/java/TicketBooking/Storage/Movie.csv";
        String newFilePath = "src/main/java/TicketBooking/Storage/UpcomingMovies.csv";
        try {
            FileReader fr = new FileReader(filePath);
            BufferedReader br = new BufferedReader(fr);
            String line;
            List<String> list = new ArrayList<>();
            // skip the first line
            br.readLine();
            while((line = br.readLine()) != null) {
                String[] mDetails = line.split(",");
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm/dd/MM/yyyy");

                Date today = new Date();
                Date filmDate = simpleDateFormat.parse(mDetails[6]);

                if (filmDate.after(today))
                {
                    long diff = filmDate.getTime() - today.getTime();
                    if (TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS) < 7)
                    {
                        list.add(line);
                    }
                }

//                simpleDateFormat.parse(mDetails[6]).
//                if (simpleDateFormat.parse(mDetails[6]).after(simpleDateFormat.parse(getToday())) && simpleDateFormat.parse(mDetails[6]).before(simpleDateFormat.parse(getThisWeekSunday()))) {
//
//                }
            }

            FileWriter fw = new FileWriter(newFilePath);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("name,classification,screen,synopsis,director,cast,upcoming_time,release_date,running time,image,availableSeats,cinemaName");
            bw.newLine();
            for (String movie : list) {
                bw.write(movie);
                bw.newLine();
            }
            bw.close();
        } catch (IOException | ParseException e) {
            System.out.println("Error when opening File in Helper stage");
        }
    }

    // Read movie list from csv file
    // Read starts from the second line
    // the file directory is src/main/java/TicketBooking/Storage/Movie.csv
    // the format of Movie is: name, classification, screen, synopsis, director, cast, upcoming_time, release_date, running time, image,availableSeats,cinemaName
    // The upcoming_time is in the format of "HH:mm/dd/MM/yyyy"
    // extract the movie that its upcoming_time is between today and this week's Sunday into a new list
    // write the new list into src/main/java/TicketBooking/Storage/ThisWeekMovie.csv
    // The first line in the new file is the header
    public static void extractThisWeekMovie() {
        String filePath = "src/main/java/TicketBooking/Storage/Movie.csv";
        String newFilePath = "src/main/java/TicketBooking/Storage/ThisWeekMovie.csv";
        try {
            FileReader fr = new FileReader(filePath);
            BufferedReader br = new BufferedReader(fr);
            String line;
            List<String> list = new ArrayList<>();
            // skip the first line
            br.readLine();
            System.out.println("Today is: " + getToday());
            System.out.println("Sunday is" + getThisWeekSunday());
            while((line = br.readLine()) != null) {
                String[] mDetails = line.split(",");
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm/dd/MM/yyyy");
                if (simpleDateFormat.parse(mDetails[6]).after(simpleDateFormat.parse(getToday())) && simpleDateFormat.parse(mDetails[6]).before(simpleDateFormat.parse(getThisWeekSunday()))) {
                    list.add(line);
                }
            }

            FileWriter fw = new FileWriter(newFilePath);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("name,classification,screen,synopsis,director,cast,upcoming_time,release_date,running time,image,availableSeats,cinemaName");
            bw.newLine();
            for (String movie : list) {
                bw.write(movie);
                bw.newLine();
            }
            bw.close();
        } catch (IOException | ParseException e) {
            System.out.println("Error when opening File in Helper stage");
        }
    }

    // get today's date in the format of "HH:mm/dd/MM/yyyy"
    public static String getToday() {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm/dd/MM/yyyy");
        Date date = new Date();
        return dateFormat.format(date);
    }

    // get this week's Sunday's midnight in the format of "HH:mm/dd/MM/yyyy"
    // week starts from Monday
    public static String getThisWeekSunday() {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm/dd/MM/yyyy");
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, 7 - cal.get(Calendar.DAY_OF_WEEK) + 1);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        return dateFormat.format(cal.getTime());
    }

    // availableSeatUpdater(Movie movie, int ticketAmount)
    // update the available seats of the movie by subtracting the ticket amount
    // the file directory is src/main/java/TicketBooking/Storage/Movie.csv
    // the format of Movie is: name, classification, screen, sypnosis, director, cast, upcoming_time, release_date,running time, image,availableSeats,cinemaName
    public static void availableSeatUpdater(Movie movie, int ticketAmount) {
        String filePath = "src/main/java/TicketBooking/Storage/Movie.csv";
        try {
            FileReader fr = new FileReader(filePath);
            BufferedReader br = new BufferedReader(fr);
            String line;
            List<String> list = new ArrayList<>();
            // skip the first line
            br.readLine();
            while((line = br.readLine()) != null) {
                String[] mDetails = line.split(",");
                if (mDetails[0].equals(movie.getName()) && mDetails[6].equals(movie.getUpcomingTime()) && mDetails[11].equals(movie.getCinemaName())) {
                    int availableSeats = Integer.parseInt(mDetails[10]) - ticketAmount;
                    System.out.println("Available seats: " + availableSeats);
                    line = mDetails[0] + "," + mDetails[1] + "," + mDetails[2] + "," + mDetails[3] + "," + mDetails[4] + "," + mDetails[5] + "," + mDetails[6] + "," + mDetails[7] + "," + mDetails[8] + "," + mDetails[9] + "," + availableSeats + "," + mDetails[11];
                }
                list.add(line);
            }
            FileWriter fw = new FileWriter(filePath);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("name, classification, screen, sypnosis, director, cast, upcoming_time, release_date,running time, image,availableSeats,cinemaName");
            bw.newLine();
            for (String movie1 : list) {
                bw.write(movie1);
                bw.newLine();
            }
            bw.close();
        } catch (IOException e) {
            System.out.println("Error when opening File in Helper stage");
        }
    }

    // userCardInfoUpdater(User, user)
    // update the creditName and number of the user
    // the file directory is src/main/java/TicketBooking/Storage/UserAccount.csv
    // the format of User is : username, password, type, creditName, number
    public static void userCardInfoUpdater(User user) {
        String filePath = "src/main/java/TicketBooking/Storage/UserAccount.csv";
        try {
            FileReader fr = new FileReader(filePath);
            BufferedReader br = new BufferedReader(fr);
            String line;
            List<String> list = new ArrayList<>();
            // skip the first line
            br.readLine();
            while((line = br.readLine()) != null) {
                String[] userDetails = line.split(",");
                if (userDetails[0].equals(user.getUserName())) {
                    line = userDetails[0] + "," + userDetails[1] + "," + userDetails[2] + "," + user.getCardHoldName() + "," + user.getCardNumber();
                }
                list.add(line);
            }
            FileWriter fw = new FileWriter(filePath);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("username, password, type, creditName, number");
            bw.newLine();
            for (String user1 : list) {
                bw.write(user1);
                bw.newLine();
            }
            bw.close();
        } catch (IOException e) {
            System.out.println("Error when opening File in Helper stage");
        }
    }


    public static void recordBooking(User user, Movie movie, String tickets, int total_price, String payment_method, String transactionNumber)
    {
        // get current time in String
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date dateNow = new Date();
        String now = dateFormat.format(dateNow);

//        boolean firstLine = true;

        System.out.println("Record transaction");
        String filePath = "src/main/java/TicketBooking/Storage/BookingSessions.csv";
        try {
            FileWriter fw = new FileWriter(filePath, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(transactionNumber + "," +
                         user.getUserName() + "," +
                         movie.getName() + "," +
                         movie.getCinemaName() + "," +
                         movie.getUpcomingTime() + "," +
                         tickets + "," +
                         total_price + "," +
                         payment_method + "," +
                         now);
            bw.newLine();
            bw.close();
        } catch (IOException e) {
            System.out.println("Error when opening File in Helper stage");
        }

    }

    public static void getBookingDB(ObservableList<Transaction> mList)
    {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream("src/main/java/TicketBooking/Storage/BookingSessions.csv")));
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        try {
            String line;
            br.readLine();
            while ( (line = br.readLine()) != null) {
                String[] cardDetails = line.split(",");

                String transactionNumber = cardDetails[0];
                String userName = cardDetails[1];
                String movieName = cardDetails[2];
                String cinema = cardDetails[3];
                String sessionTime = cardDetails[4];
                String tickets = cardDetails[5];
                String totalPrice = cardDetails[6];
                String paymentMethod = cardDetails[7];

                String bookingDate = cardDetails[8];


                Transaction transaction = new Transaction(transactionNumber, userName,movieName,cinema, sessionTime,
                                                            tickets,totalPrice,paymentMethod,bookingDate);
                mList.add(transaction);



            }

        } catch(IOException e) {
            System.out.println("getBookingDB() Error when opening File");
        }
    }
}
