package TicketBooking.Management;
import java.io.*;

public interface Validator {
    BufferedReader loadCsvFile(String csvFile);
    boolean loginCheck(String path, String username, String password);
    boolean creditCardCheck(String name, String cardNumber);
    boolean giftCardExists(String giftCardNumber);
    boolean checkGiftCardBalance(String giftCardNumber, double paymentAmount);
    boolean giftCardUsed(String giftCardNumber);
    boolean transactionIdCheck(int id);
    boolean duplicateAccountCheck(String username,String usertype);
    public boolean staffCheck(String path, String username, String password);

}