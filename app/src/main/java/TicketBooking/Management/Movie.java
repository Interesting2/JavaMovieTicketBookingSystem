package TicketBooking.Management;

import javafx.scene.image.ImageView;

public class Movie {
   private String name;
   private String classification;
   private String screenSize;
   private String synopsis;
   private String synopsisPath;
   private String director;
   private String cast;
   private String upcomingTime;
   private String releaseDate;
   private int runningTime;
   private ImageView movieImage;
   private String storage;
   private int availableSeats;
   private String cinemaName;

   public Movie(String name, String classification, String screenSize, String synopsis, String synopsisPath, String director,
                  String cast, String upcomingTime, String releaseDate, int runningTime, ImageView movieImage, 
                  String storage, int availableSeat, String cinemaName) {
      this.name = name;
      this.classification = classification;
      this.screenSize = screenSize;
      this.synopsis = synopsis;
      this.synopsisPath = synopsisPath;
      this.director = director;
      this.cast = cast;
      this.upcomingTime = upcomingTime;
      this.releaseDate = releaseDate;
      this.runningTime = runningTime;
      this.movieImage = movieImage;
      this.storage = storage;
      this.availableSeats = availableSeat;
      this.cinemaName = cinemaName;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getClassification() {
      return classification;
   }

   public void setClassification(String classification) {
      this.classification = classification;
   }

   public String getScreenSize() {
      return screenSize;
   }

   public void setScreenSize(String screenSize) {
      this.screenSize = screenSize;
   }

   public String getSynopsis() {
      return synopsis;
   }

   public void setSynopsis(String synopsis) {
      this.synopsis = synopsis;
   }
   
   public String getSynopsisPath() {
      return synopsisPath;
   }

   public void setSynopsisPath(String synopsisPath) {
      this.synopsisPath = synopsisPath;
   }

   public String getDirector() {
      return director;
   }

   public void setDirector(String director) {
      this.director = director;
   }

   public String getCast() {
      return cast;
   }

   public void setCast(String cast) {
      this.cast = cast;
   }

   public String getUpcomingTime() {
      return upcomingTime;
   }

   public void setUpcomingTime(String upcomingTime) {
      this.upcomingTime = upcomingTime;
   }

   public String getReleaseDate() {
      return releaseDate;
   }

   public void setReleaseDate(String releaseDate) {
      this.releaseDate = releaseDate;
   }

   public void setRunningTime(int runningTime) {
      this.runningTime = runningTime;
   }
   
   public int getRunningTime() {
      return this.runningTime;
   }

   public void setMovieImage(ImageView movieImage) {
      this.movieImage = movieImage;
   }

   public ImageView getMovieImage() {
      this.movieImage.setFitWidth(100);
      this.movieImage.setPreserveRatio(true);
      this.movieImage.setSmooth(true);
      this.movieImage.setCache(true);
      return this.movieImage;
   }

   public void setStorage(String storage) {
      this.storage = storage;
   }

   public String getStorage() {
      return this.storage;
   }

   public int getAvailableSeats() {
      return this.availableSeats;
   }

   public void setAvailableSeats(int availableSeat) {
      this.availableSeats = availableSeat;
   }

   public void setCinemaName(String cinemaName) {
      this.cinemaName = cinemaName;
   }

   public String getCinemaName() {
      return this.cinemaName;
   }

   @Override
   public String toString() {
      String output = this.name + "," + this.classification.replaceAll(", ", "/") +
                     "," + this.screenSize.replaceAll(", ", "/") + "," + 
                     this.synopsisPath + "," + this.director + "," +
                     this.cast.replaceAll(", ", "/") + "," + this.upcomingTime + "," + 
                     this.releaseDate + "," + Integer.toString(this.runningTime) + "," + 
                     this.storage + "," + Integer.toString(this.availableSeats) + "," + this.cinemaName;
      return output;
   }

}
