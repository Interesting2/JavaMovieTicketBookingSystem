package TicketBooking.Management;

public class User {
    private String userName;
    private String password;
    private String userType;
    private String cardHoldName;
    private String cardNumber;

    public User(String userName, String password, String userType, String cardHoldName, String cardNumber) {
        this.userName = userName;
        this.password = password;
        this.userType = userType;
        this.cardHoldName = cardHoldName;
        this.cardNumber = cardNumber;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getUserType() {
        return userType;
    }

    public String getCardHoldName() {
        return cardHoldName;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public void setCardHoldName(String cardHoldName) {
        this.cardHoldName = cardHoldName;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    @Override
    public String toString() {
        return this.userName + " " + this.password + " " + this.userType + " " + this.cardHoldName + " " + this.cardNumber;
    }
}
