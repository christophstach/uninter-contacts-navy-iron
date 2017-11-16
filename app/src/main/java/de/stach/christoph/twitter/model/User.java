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

    @SerializedName("longitud")
    private double longitude;

    @SerializedName("latitud")
    private double latitude;

    public User(String firstName, String lastName, String telephone, double longitude, double latitude) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.telephone = telephone;
        this.longitude = longitude;
        this.latitude = latitude;
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

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", telephone='" + telephone + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                '}';
    }
}
