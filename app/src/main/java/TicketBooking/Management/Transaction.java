package TicketBooking.Management;

public class Transaction {


    private String transactionNumber;
    private String user;
    private String movieName;
    private String cinema;
    private String sessionTime;
    private String tickets;
    private String totalPrice;
    private String paymentMethod;
    private String dateBooking;


    public Transaction(String transactionNumber, String user, String movieName, String cinema, String sessionTime,
                       String tickets, String totalPrice, String paymentMethod, String dateBooking) {
        this.transactionNumber = transactionNumber;
        this.user = user;
        this.movieName = movieName;
        this.cinema = cinema;
        this.sessionTime = sessionTime;
        this.tickets = tickets;
        this.totalPrice = totalPrice;
        this.paymentMethod = paymentMethod;
        this.dateBooking = dateBooking;
    }

    public String getTransactionNumber() {
        return transactionNumber;
    }

    public void setTransactionNumber(String transactionNumber) {
        this.transactionNumber = transactionNumber;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getCinema() {
        return cinema;
    }

    public void setCinema(String cinema) {
        this.cinema = cinema;
    }

    public String getSessionTime() {
        return sessionTime;
    }

    public void setSessionTime(String sessionTime) {
        this.sessionTime = sessionTime;
    }

    public String getTickets() {
        return tickets;
    }

    public void setTickets(String tickets) {
        this.tickets = tickets;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getDateBooking() {
        return dateBooking;
    }

    public void setDateBooking(String dateBooking) {
        this.dateBooking = dateBooking;
    }

}