package TicketBooking.Management;

public class Giftcard {
   private int id;
   private String number;
   private double amount;
   private boolean available; 

    public Giftcard(int id, String number, double amount, boolean available) {
        this.id = id;
        this.number = number;  
        this.amount = amount;
        this.available = available;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public int getId() {
        return this.id;
    }
    
    public String getNumber() {
        return this.number;
    }

    public double getAmount() {
        return this.amount;
    }

    public boolean getAvailable() {
        return this.available;
    }

    @Override
    public String toString() {
        String output = Integer.toString(this.getId()) + "," + 
                        this.getNumber() + "," + Double.toString(this.getAmount()) + 
                        "," + Boolean.toString(this.getAvailable());
        return output;
    }
}
