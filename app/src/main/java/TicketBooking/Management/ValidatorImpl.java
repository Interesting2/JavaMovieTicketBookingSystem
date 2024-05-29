package TicketBooking.Management;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.*;
import java.util.List;

public class ValidatorImpl implements Validator{
    public ValidatorImpl() {}

    public BufferedReader loadCsvFile(String path) {

        try {

            BufferedReader br = new BufferedReader(new FileReader(path));

            System.out.println("Read " + path + " successfully");
            return br;

        } catch(IOException e) {
            System.out.println("loadCsvFile(), Error when opening File");
            return null;
        }
    }

    public boolean loginCheck(String path, String username, String password) {
        BufferedReader buffer = loadCsvFile(path);
        String line;
        try{
            buffer.readLine();
            while ((line = buffer.readLine()) != null) {
                String[] accountDetails = line.split(",");
                String in_username  = accountDetails[0];
                String in_password = accountDetails[1];
                //String name  = accountDetails[2];
                //int number =  Integer.parseInt(accountDetails[3]);
                if(username.equals(in_username) && password.equals(in_password)){
                    return true;

                }
            }
        } catch(IOException e) {
            System.out.println("Error when opening File");
            return false;
        }
        return false;
    }
    public boolean staffCheck(String path, String username, String password) {
        BufferedReader buffer = loadCsvFile(path);
        String line;
        boolean skip = true;
        try{
            while ((line = buffer.readLine()) != null) {
                if (skip) skip = false;
                else {
                    String[] accountDetails = line.split(",");
                    String in_username  = accountDetails[0];
                    String in_password = accountDetails[1];
                    String userType = accountDetails[2];
                    //String name  = accountDetails[2];
                    //int number =  Integer.parseInt(accountDetails[3]);
                    if(username.equals(in_username) && password.equals(in_password)){
                        return userType.equals("Manager") || userType.equals("Staff");
                    }
                }
            }
        } catch(IOException e) {
            System.out.println("Error when opening File");
            return false;
        }
        return false;
    }


    @Override
    public boolean giftCardExists(String giftCardNumber) {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream("src/main/java/TicketBooking/Storage/Giftcard.csv")));
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        try {
            String line;
            br.readLine();
            while ( (line = br.readLine()) != null) {
                String[] cardDetails = line.split(",");

                String cardNumber = cardDetails[1];
                if (giftCardNumber.equals(cardNumber))
                {
                    return true;   // duplciate card
                }

            }
            return false;

        } catch(IOException e) {
            System.out.println("giftcardcheck() Error when opening File");
            return false;
        }
    }


    // return true if balance if sufficient, false otherwise
    @Override
    public boolean checkGiftCardBalance(String giftcardNumber, double paymentAmount)
    {
        String path = "src/main/java/TicketBooking/Storage/" + "Giftcard.csv";
        try {
            CSVParser csvParser = new CSVParserBuilder().withSeparator(',').build();

            CSVReader reader = new CSVReaderBuilder(new FileReader(path)).withCSVParser(csvParser).build();

            List<String[]> cardData = reader.readAll();


            boolean firstLine = true;

            for (int i = 0;i<cardData.size();i++)
            {
                if (firstLine)
                {
                    firstLine = false;
                    continue;
                }
                if (cardData.get(i)[1].equals(giftcardNumber))
                {
                    double cardBalance = Double.parseDouble(cardData.get(i)[2]);
                    if (cardBalance >= paymentAmount)
                    {
                        return true;
                    }
                    else
                    {
                        return false;
                    }
                }
            }

        } catch (FileNotFoundException e){

        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean giftCardUsed(String giftCardNumber) {
        String path = "src/main/java/TicketBooking/Storage/" + "Giftcard.csv";
        try {
            CSVParser csvParser = new CSVParserBuilder().withSeparator(',').build();

            CSVReader reader = new CSVReaderBuilder(new FileReader(path)).withCSVParser(csvParser).build();

            List<String[]> cardData = reader.readAll();


            boolean firstLine = true;

            for (int i = 0;i<cardData.size();i++)
            {
                if (firstLine)
                {
                    firstLine = false;
                    continue;
                }
                if (cardData.get(i)[1].equals(giftCardNumber))
                {
                    if (cardData.get(i)[3].equals("false"))
                    {
                        return true;
                    }
                    else
                    {
                        return false;
                    }
                }
            }

        } catch (FileNotFoundException e){

        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }


    @Override
    public boolean transactionIdCheck(int id) {
        return true;
    }
    
    @Override
    public boolean creditCardCheck(String name, String cardNumber){
        if(name==null||cardNumber==null)return false;
        String rawData = null;
        try{
            rawData = Files.readString(Path.of("src/main/java/TicketBooking/Storage/credit_cards.json"));
        }catch(IOException e){
            System.out.println("Config file DNE!");
        }
        JSONArray array = (JSONArray) JSONValue.parse(rawData);
        for(Object j: array){
            JSONObject personalInfo = (JSONObject) j;
            if(personalInfo.get("name").equals(name)){
                if(personalInfo.get("number").equals(cardNumber)){
                    return true;
                }
            }
        }
        return false;
    }

    public boolean duplicateAccountCheck(String username,String userType) {

        try {
            BufferedReader br = loadCsvFile("src/main/java/TicketBooking/Storage/UserAccount.csv");

            String line;
            br.readLine();
            while ( (line = br.readLine()) != null) {
                String[] userDetails = line.split(",");
                if(userDetails[3].equals(userType)||userDetails[0].equals(username)){
                    System.out.println(line);
                    return true;
                }
            }
        } catch(IOException e) {
            System.out.println("Error when opening File");
            return false;
        }
        return false;
    }
}