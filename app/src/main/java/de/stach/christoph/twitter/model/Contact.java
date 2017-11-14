package de.stach.christoph.twitter.model;

import java.io.Serializable;

/**
 * Created by christoph on 26.09.17.
 * <p>
 * A model for contacts
 */

public class Contact implements Serializable {
    private String firstName;
    private String lastName;
    private String telephoneNumber;
    private String userTelephoneNubmer;
    private String latitude;
    private String longitude;

    public Contact() {
    }

    public Contact(String firstName, String lastName, String telephoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.telephoneNumber = telephoneNumber;
    }

    public Contact(String firstName, String lastName, String telephoneNumber, String latitude, String longitude) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.telephoneNumber = telephoneNumber;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", telephoneNumber='" + telephoneNumber + '\'' +
                '}';
    }
}
