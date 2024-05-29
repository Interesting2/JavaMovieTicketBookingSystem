package TicketBooking.Management;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransactionTest {
    // test unit for Transaction class
    // its constructor is Transaction(String transactionNumber, String user, String movieName, String cinema, String sessionTime,
    //                       String tickets, String totalPrice, String paymentMethod, String dateBooking)
    // its getter and setter is available

    // test case 1:
    // test the constructor and getter and setter for Transaction class
    // expected output:
    // transactionNumber: T00001
    // user: user1
    // movieName: movie1
    // cinema: cinema1
    // sessionTime: session1
    // tickets: 1
    // totalPrice: 100
    // paymentMethod: cash
    // dateBooking: 2019-01-01
    @Test
    public void testTransactionConstructor(){
        Transaction transaction = new Transaction("T00001", "user1", "movie1", "cinema1", "session1", "1", "100", "cash", "2019-01-01");
        assertEquals("T00001", transaction.getTransactionNumber());
        assertEquals("user1", transaction.getUser());
        assertEquals("movie1", transaction.getMovieName());
        assertEquals("cinema1", transaction.getCinema());
        assertEquals("session1", transaction.getSessionTime());
        assertEquals("1", transaction.getTickets());
        assertEquals("100", transaction.getTotalPrice());
        assertEquals("cash", transaction.getPaymentMethod());
        assertEquals("2019-01-01", transaction.getDateBooking());
    }

    // test case 2:
    // test the getter and setter for Transaction class
    // expected output:
    // transactionNumber: T00002
    // user: user2
    // movieName: movie2
    // cinema: cinema2
    // sessionTime: session2
    // tickets: 2
    // totalPrice: 200
    // paymentMethod: cash
    // dateBooking: 2019-01-02
    @Test
    public void testTransactionGetterAndSetter(){
        Transaction transaction = new Transaction("T00002", "user2", "movie2", "cinema2", "session2", "2", "200", "cash", "2019-01-02");
        transaction.setTransactionNumber("T00002");
        transaction.setUser("user2");
        transaction.setMovieName("movie2");
        transaction.setCinema("cinema2");
        transaction.setSessionTime("session2");
        transaction.setTickets("2");
        transaction.setTotalPrice("200");
        transaction.setPaymentMethod("cash");
        transaction.setDateBooking("2019-01-02");
        assertEquals("T00002", transaction.getTransactionNumber());
        assertEquals("user2", transaction.getUser());
        assertEquals("movie2", transaction.getMovieName());
        assertEquals("cinema2", transaction.getCinema());
        assertEquals("session2", transaction.getSessionTime());
        assertEquals("2", transaction.getTickets());
        assertEquals("200", transaction.getTotalPrice());
        assertEquals("cash", transaction.getPaymentMethod());
        assertEquals("2019-01-02", transaction.getDateBooking());
    }
}
