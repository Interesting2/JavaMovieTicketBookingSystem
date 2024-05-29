package TicketBooking.Management;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Model class for a Person.
 *
 * @author Marco Jakob
 */
public class Staff {

    private final StringProperty firstName;
    private final StringProperty lastName;
    private final StringProperty password;
    private final StringProperty username;

    /**
     * Constructor with some initial data.
     *
     * @param firstName
     * @param lastName
     */
    public Staff(String username, String password, String firstName, String lastName) {
        this.username = new SimpleStringProperty(username);
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.password = new SimpleStringProperty(password);
    }

    public String getFirstName() {
        return firstName.get();
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public StringProperty firstNameProperty() {
        return firstName;
    }

    public String getLastName() {
        return lastName.get();
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public StringProperty lastNameProperty() {
        return lastName;
    }

    public String getUsername() {
        return username.get();
    }

    public void setUsername(String username) {
        this.username.set(username);
    }

    public StringProperty usernameProperty() {
        return username;
    }

    public String getPwd() {
        return password.get();
    }

    public void setPwd(String pwd) {
        this.password.set(pwd);
    }

    public StringProperty pwdProperty() {
        return password;
    }

    public String toString(){
        return this.getUsername() + ","
                + this.getPwd() + ","
                + "Staff,"
                + this.getFirstName() + ","
                + this.getLastName();
    }

}