package TicketBooking.Management;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ValidatorTest {
    /*
    @Test 
    public void testDuplicateAccount() {
        Validator test = new ValidatorImpl();
        assertTrue(test.duplicateAccountCheck("User123", "Customer"));
        assertFalse(test.duplicateAccountCheck("Tom", "Customer"));
    }
    */
    @Test
    public void testCheckCreditCard(){
        Validator test = new ValidatorImpl();
        assertTrue(test.creditCardCheck("Charles","40691"));
        assertFalse(test.creditCardCheck("charles","40691"));
        assertFalse(test.creditCardCheck("Charles","12334"));
    }


    // @Test
    // public void testLoginCheck()
    // {
    //     Validator test = new ValidatorImpl();

    //     assertTrue(test.loginCheck("src/main/java/TicketBooking/Storage/UserAccount.csv","User123","foobar123"));

    //     assertFalse(test.loginCheck("src/main/java/TicketBooking/Storage/UserAccount.csv","User456","foobar123"));

    // }

}
