package com.example.demo.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Motorhome {

    @Id
    //Fields/Attributes
    private int motorhomeID;
    private String brandAndModel;
    private int capacity;
    private int odometer;
    private String numberPlate;
    private double rentalPrice;
    private boolean inService;

    //Constructor
    public Motorhome() {}

    //Getters & Setters
    public int getMotorhomeID() {
        return motorhomeID;
    }

    public void setMotorhomeID(int motorhomeID) {
        this.motorhomeID = motorhomeID;
    }

    public String getBrandAndModel() {
        return brandAndModel;
    }

    public void setBrandAndModel(String brandAndModel) {
        this.brandAndModel = brandAndModel;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getOdometer() {
        return odometer;
    }

    public void setOdometer(int odometer) {
        this.odometer = odometer;
    }

    public boolean isInService() {
        return inService;
    }

    public void setInService(boolean inService) {
        this.inService = inService;
    }

    public String getNumberPlate() {
        return numberPlate;
    }

    public void setNumberPlate(String numberPlate) {
        this.numberPlate = numberPlate;
    }

    public double getRentalPrice() {
        return rentalPrice;
    }

    public void setRentalPrice(double rentalPrice) {
        this.rentalPrice = rentalPrice;
    }

    @Override
    public String toString() {
        return "Motorhome{"+ motorhomeID + brandAndModel + capacity + odometer + numberPlate + rentalPrice + inService + '}';
    }
}
