package de.stach.christoph.twitter.model;

/**
 * Created by christoph on 07.11.17.
 * <p>
 * User model
 */
public class User {
    private String firstName;
    private String lastName;
    private String telephone;

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

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
}
