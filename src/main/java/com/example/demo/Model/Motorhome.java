package com.example.demo.Model;

public class Motorhome {


    //Fields/Attributes
    private int motorhomeID;
    private String brandAndModel;
    private int capacity;
    private int odometer;
    private boolean service;
    private String plateNumber;
    private int rentalPrice;

    //Constructor
    public Motorhome(){ }

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

    public boolean isService() {
        return service;
    }

    public void setService(boolean service) {
        this.service = service;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public int getRentalPrice() {
        return rentalPrice;
    }

    public void setRentalPrice(int rentalPrice) {
        this.rentalPrice = rentalPrice;
    }
}
