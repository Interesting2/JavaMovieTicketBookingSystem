package TicketBooking.Management;

import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(OrderAnnotation.class)
public class UserTest {
    static User user;

    @BeforeAll
    public static void setUp() {
        user = new User("JackLee", "12345678", "Customer", "JackLee", "40671");

    }
    
    @Test
    @Order(1)
    public void testGetUserName() {
        assertEquals("JackLee", user.getUserName());
    }

    
    @Test
    @Order(2)
    public void testSetUserName() {
        user.setUserName("Jacky");
        assertEquals("Jacky", user.getUserName());
    }

    @Test
    @Order(3)
    public void testGetPassword() {
        assertEquals("12345678", user.getPassword());
    }

    @Test
    @Order(4)
    public void testSetPassword() {
        user.setPassword("87654321");
        assertEquals("87654321", user.getPassword());
    }

    @Test
    @Order(5)
    public void testGetUserType() {
        assertEquals("Customer", user.getUserType());
    }

    @Test
    @Order(6)
    public void testSetUserType() {
        user.setUserType("Manager");
        assertEquals("Manager", user.getUserType());
    }

    @Test
    @Order(7)
    public void testGetCardHoldName() {
        assertEquals("JackLee", user.getCardHoldName());
    }

    @Test
    @Order(8)
    public void testSetCardHoldName() {
        user.setCardHoldName("Jacky");
        assertEquals("Jacky", user.getCardHoldName());
    }

    @Test
    @Order(9)
    public void testGetCardNumber() {
        assertEquals("40671", user.getCardNumber());
    }

    @Test
    @Order(10)
    public void testSetCardNumber() {
        user.setCardNumber("40672");
        assertEquals("40672", user.getCardNumber());

    }

    @Test
    @Order(10)
    public void testToString() {
        assertEquals("Jacky 87654321 Manager Jacky 40672", user.toString());
    }


}
