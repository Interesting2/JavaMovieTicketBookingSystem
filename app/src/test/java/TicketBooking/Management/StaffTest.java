package TicketBooking.Management;

import javafx.beans.property.SimpleStringProperty;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StaffTest {
    static Staff staff;

    @BeforeAll
    public static void setUp() {
        staff = new Staff("Winnie","123456","Popo","Xi");
    }

    @Test
    public void testGetName() {
        assertEquals("Winnie",staff.getUsername(), "UserName Correct");
    }

    @Test
    public void testGetPassword() {
        assertEquals("123456",staff.getPwd(), "Password Correct");
    }

    @Test
    public void testGetFirstName() {
        assertEquals("Popo",staff.getFirstName(), "FirstName Correct");
    }

    @Test
    public void testGetLastName() {
        assertEquals("Xi",staff.getLastName(), "LastName Correct");
    }
}
