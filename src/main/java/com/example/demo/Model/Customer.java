package com.example.demo.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
public class Customer {

    @Id
    //Fields & Attributes
    private int customerID;
    private String firstName;
    private String lastName;
    private int phoneNumber;
    private String email;
    private String driverLicense;
    private LocalDate driverSinceDate;
    private int foreign_AddressID;

    private int addressID;
    private String address;
    private int foreign_ZipcodeID;

    private int zipcodeID;
    private int zipcode;
    private String city;
    private int foreign_countryID;

    private int countryID;
    private String country;

    //Constructor
    public Customer() {}

    //Getters & Setters
    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
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

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDriverLicense() {
        return driverLicense;
    }

    public void setDriverLicense(String driverLicense) {
        this.driverLicense = driverLicense;
    }

    public LocalDate getDriverSinceDate() {
        return driverSinceDate;
    }

    public void setDriverSinceDate(LocalDate driverSinceDate) {
        this.driverSinceDate = driverSinceDate;
    }

    public int getForeign_AddressID() {
        return foreign_AddressID;
    }

    public void setForeign_AddressID(int foreign_AddressID) {
        this.foreign_AddressID = foreign_AddressID;
    }

    public int getAddressID() {
        return addressID;
    }

    public void setAddressID(int addressID) {
        this.addressID = addressID;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getForeign_ZipcodeID() {
        return foreign_ZipcodeID;
    }

    public void setForeign_ZipcodeID(int foreign_ZipcodeID) {
        this.foreign_ZipcodeID = foreign_ZipcodeID;
    }

    public int getZipcodeID() {
        return zipcodeID;
    }

    public void setZipcodeID(int zipcodeID) {
        this.zipcodeID = zipcodeID;
    }

    public int getZipcode() {
        return zipcode;
    }

    public void setZipcode(int zipcode) {
        this.zipcode = zipcode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getForeign_countryID() {
        return foreign_countryID;
    }

    public void setForeign_countryID(int foreign_countryID) {
        this.foreign_countryID = foreign_countryID;
    }

    public int getCountryID() {
        return countryID;
    }

    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
