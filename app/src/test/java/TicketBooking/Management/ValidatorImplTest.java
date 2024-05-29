package TicketBooking.Management;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;


public class ValidatorImplTest {

    Validator validator;
    String path = "src/main/java/TicketBooking/Storage/UserAccount.csv";

    @BeforeEach
    public void setUp() {
        validator = new ValidatorImpl();
        //validator.loadCsvFile(path);
    }

    @Test
    public void testLogin() {
        assertFalse(validator.loginCheck(path,"admin", "admin"));
    }

    @Test
    public void testLogin2() {
        assertTrue(validator.loginCheck(path,"MasterP", "123456"));
    }

    @Test
    public void testStaffCheck() {
        assertTrue(validator.staffCheck(path,"Jackson", "jackson"));
    }

    @Test
    public void testStaffCheck2() {
        assertFalse(validator.staffCheck(path,"MasterP", "123456"));
    }

    @Test
    public void testGiftCardExists() {
        assertFalse(validator.giftCardExists("19890604"));
    }

    @Test
    public void testGiftCardExists2() {
        assertTrue(validator.giftCardExists("8333899840308193"));
    }

    @Test
    public void testCheckGiftCardBalance() {
        assertTrue(validator.checkGiftCardBalance("42457030736754",66));
    }

    @Test
    public void testCheckGiftCardBalance2() {
        assertFalse(validator.checkGiftCardBalance("42457030736754",71));
    }

    @Test
    public void testGiftCardUsed() {
        assertFalse(validator.giftCardUsed("42457030736754"));
    }

    @Test
    public void testGiftCardUsed2() {
        assertTrue(validator.giftCardUsed("8333899840308193"));
    }

    @Test
    public void testCreditCardCheck() {
        assertTrue(validator.creditCardCheck("Ruth", "55134"));
    }

    @Test
    public void testCreditCardCheck2() {
        assertFalse(validator.creditCardCheck("Ruth", "5513"));
    }

    @Test
    public void testCreditCardCheck3() {
        assertFalse(validator.creditCardCheck("ruth", "55134"));
    }

    @Test
    public void testDuplicateAccountCheck() {
        assertTrue(validator.duplicateAccountCheck("Jackson", "Staff"));
    }

    @Test
    public void testDuplicateAccountCheck2() {
        assertTrue(validator.duplicateAccountCheck("Jackson", "Customer"));
    }

    @Test
    public void testDuplicateAccountCheck3() {
        assertFalse(validator.duplicateAccountCheck("Popo", "Customer"));
    }
}
