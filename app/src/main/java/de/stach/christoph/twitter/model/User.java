package de.stach.christoph.twitter.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by christoph on 07.11.17.
 * <p>
 * User model
 */
public class User {
    @SerializedName("nombre")
    private String firstName;

    @SerializedName("apellido")
    private String lastName;

    @SerializedName("telefono")
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

    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", telephone='" + telephone + '\'' +
                '}';
    }
}
