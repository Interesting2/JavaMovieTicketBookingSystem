package TicketBooking.Management;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MovieTest {
    // test unit for Movie class
    // its constructor is Movie (String name, String classification, String screenSize, String synopsis, String synopsisPath, String director,
    //                  String cast, String upcomingTime, String releaseDate, int runningTime, ImageView movieImage,
    //                  String storage, int availableSeat, String cinemaName)
    // its getter and setter is available
    
    @Test
    public void testGetName() {
        Movie movie = new Movie("name", "classification", "screenSize", "synopsis", "synopsisPath", "director", "cast", "upcomingTime", "releaseDate",
                10, null, "storage", 10, "cinemaName");
        assertEquals("name", movie.getName());
    }

    @Test
    public void testGetClassification() {
        Movie movie = new Movie("name", "classification", "screenSize", "synopsis", "synopsisPath", "director", "cast", "upcomingTime", "releaseDate",
                10, null, "storage", 10, "cinemaName");
        assertEquals("classification", movie.getClassification());
    }

    @Test
    public void testGetScreenSize() {
        Movie movie = new Movie("name", "classification", "screenSize", "synopsis", "synopsisPath", "director", "cast", "upcomingTime", "releaseDate",
                10, null, "storage", 10, "cinemaName");
        assertEquals("screenSize", movie.getScreenSize());
    }

    @Test
    public void testGetSynopsis() {
        Movie movie = new Movie("name", "classification", "screenSize", "synopsis", "synopsisPath", "director", "cast", "upcomingTime", "releaseDate",
                10, null, "storage", 10, "cinemaName");
        assertEquals("synopsis", movie.getSynopsis());
    }

    @Test
    public void testGetSynopsisPath() {
        Movie movie = new Movie("name", "classification", "screenSize", "synopsis", "synopsisPath", "director", "cast", "upcomingTime", "releaseDate",
                10, null, "storage", 10, "cinemaName");
        assertEquals("synopsisPath", movie.getSynopsisPath());
    }

    @Test
    public void testGetDirector() {
        Movie movie = new Movie("name", "classification", "screenSize", "synopsis", "synopsisPath", "director", "cast", "upcomingTime", "releaseDate",
                10, null, "storage", 10, "cinemaName");
        assertEquals("director", movie.getDirector());
    }

    @Test
    public void testGetCast() {
        Movie movie = new Movie("name", "classification", "screenSize", "synopsis", "synopsisPath", "director", "cast", "upcomingTime", "releaseDate",
                10, null, "storage", 10, "cinemaName");
        assertEquals("cast", movie.getCast());
    }

    @Test
    public void testGetUpcomingTime() {
        Movie movie = new Movie("name", "classification", "screenSize", "synopsis", "synopsisPath", "director", "cast", "upcomingTime", "releaseDate",
                10, null, "storage", 10, "cinemaName");
        assertEquals("upcomingTime", movie.getUpcomingTime());
    }

    @Test
    public void testGetReleaseDate() {
        Movie movie = new Movie("name", "classification", "screenSize", "synopsis", "synopsisPath", "director", "cast", "upcomingTime", "releaseDate",
                10, null, "storage", 10, "cinemaName");
        assertEquals("releaseDate", movie.getReleaseDate());
    }

    @Test
    public void testGetRunningTime() {
        Movie movie = new Movie("name", "classification", "screenSize", "synopsis", "synopsisPath", "director", "cast", "upcomingTime", "releaseDate",
                10, null, "storage", 10, "cinemaName");
        assertEquals(10, movie.getRunningTime());
    }

    @Test
    public void testGetStorage() {
        Movie movie = new Movie("name", "classification", "screenSize", "synopsis", "synopsisPath", "director", "cast", "upcomingTime", "releaseDate",
                10, null, "storage", 10, "cinemaName");
        assertEquals("storage", movie.getStorage());
    }

    @Test
    public void testGetCinemaName() {
        Movie movie = new Movie("name", "classification", "screenSize", "synopsis", "synopsisPath", "director", "cast", "upcomingTime", "releaseDate",
                10, null, "storage", 10, "cinemaName");
        assertEquals("cinemaName", movie.getCinemaName());
    }

    @Test
    public void testSetName() {
        Movie movie = new Movie("name", "classification", "screenSize", "synopsis", "synopsisPath", "director", "cast", "upcomingTime", "releaseDate",
                10, null, "storage", 10, "cinemaName");
        movie.setName("newName");
        assertEquals("newName", movie.getName());
    }

    @Test
    public void testSetClassification() {
        Movie movie = new Movie("name", "classification", "screenSize", "synopsis", "synopsisPath", "director", "cast", "upcomingTime", "releaseDate",
                10, null, "storage", 10, "cinemaName");
        movie.setClassification("newClassification");
        assertEquals("newClassification", movie.getClassification());
    }

    @Test
    public void testSetScreenSize() {
        Movie movie = new Movie("name", "classification", "screenSize", "synopsis", "synopsisPath", "director", "cast", "upcomingTime", "releaseDate",
                10, null, "storage", 10, "cinemaName");
        movie.setScreenSize("newScreenSize");
        assertEquals("newScreenSize", movie.getScreenSize());
    }

    @Test
    public void testSetSynopsis() {
        Movie movie = new Movie("name", "classification", "screenSize", "synopsis", "synopsisPath", "director", "cast", "upcomingTime", "releaseDate",
                10, null, "storage", 10, "cinemaName");
        movie.setSynopsis("newSynopsis");
        assertEquals("newSynopsis", movie.getSynopsis());
    }

    @Test
    public void testSetSynopsisPath() {
        Movie movie = new Movie("name", "classification", "screenSize", "synopsis", "synopsisPath", "director", "cast", "upcomingTime", "releaseDate",
                10, null, "storage", 10, "cinemaName");
        movie.setSynopsisPath("newSynopsisPath");
        assertEquals("newSynopsisPath", movie.getSynopsisPath());
    }

    @Test
    public void testSetDirector() {
        Movie movie = new Movie("name", "classification", "screenSize", "synopsis", "synopsisPath", "director", "cast", "upcomingTime", "releaseDate",
                10, null, "storage", 10, "cinemaName");
        movie.setDirector("newDirector");
        assertEquals("newDirector", movie.getDirector());
    }

    @Test
    public void testSetCast() {
        Movie movie = new Movie("name", "classification", "screenSize", "synopsis", "synopsisPath", "director", "cast", "upcomingTime", "releaseDate",
                10, null, "storage", 10, "cinemaName");
        movie.setCast("newCast");
        assertEquals("newCast", movie.getCast());
    }

    @Test
    public void testSetUpcomingTime() {
        Movie movie = new Movie("name", "classification", "screenSize", "synopsis", "synopsisPath", "director", "cast", "upcomingTime", "releaseDate",
                10, null, "storage", 10, "cinemaName");
        movie.setUpcomingTime("newUpcomingTime");
        assertEquals("newUpcomingTime", movie.getUpcomingTime());
    }

    @Test
    public void testSetReleaseDate() {
        Movie movie = new Movie("name", "classification", "screenSize", "synopsis", "synopsisPath", "director", "cast", "upcomingTime", "releaseDate",
                10, null, "storage", 10, "cinemaName");
        movie.setReleaseDate("newReleaseDate");
        assertEquals("newReleaseDate", movie.getReleaseDate());
    }

    @Test
    public void testSetStorage() {
        Movie movie = new Movie("name", "classification", "screenSize", "synopsis", "synopsisPath", "director", "cast", "upcomingTime", "releaseDate",
                10, null, "storage", 10, "cinemaName");
        movie.setStorage("newStorage");
        assertEquals("newStorage", movie.getStorage());
    }
}
