package TicketBooking.Management;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

@TestMethodOrder(OrderAnnotation.class)
public class GiftcardTest {
   static Giftcard giftcard; 

   @BeforeAll
    public static void setUp() {
        giftcard = new Giftcard(3, "282689867167661GC", 80, true);

    }
    
    @Test
    @Order(1)
    public void testGetId() {
        assertEquals(3, giftcard.getId());
    }

    @Test
    @Order(2)
    public void testGetNumber() {
        assertEquals("28268986716761GC", giftcard.getNumber());
    }

    @Test
    @Order(3)
    public void testGetAmount() {
        assertEquals(80, giftcard.getAmount());
    
    }

    @Test
    @Order(4)
    public void testGetAvailable() {
        assertEquals(true, giftcard.getAvailable());
    }

    @Test
    @Order(1)
    public void testSetId() {
        giftcard.setId(1);
        assertEquals(1, giftcard.getId());
    }

    @Test
    @Order(2)
    public void testSetNumber() {
        giftcard.setNumber("282689867467f6GC");
        assertEquals("282689867467f6GC", giftcard.getNumber());
    }

    @Test
    @Order(3)
    public void testSetAmount() {
        giftcard.setAmount(10);
        assertEquals(10, giftcard.getAmount());
    }

    @Test
    @Order(4)
    public void testSetAvailable() {
        giftcard.setAvailable(false);
        assertEquals(false, giftcard.getAvailable());
    }

    @Test
    @Order(5)
    public void testToString() {
        assertEquals("1,282689867467f6GC,10,false", giftcard.getAvailable());
    }

    


}
