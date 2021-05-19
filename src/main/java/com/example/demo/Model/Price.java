package com.example.demo.Model;

public class Price {

    //Fields/Attributes
    private int feeID;
    private String itemName;
    private double itemPrice;

    //Constructor
    public Price(){ }

    //Getters & Setters
    public int getFeeID() {return feeID;}

    public void setFeeID(int feeID) {this.feeID = feeID;}

    public String getItemName() {return itemName;}

    public void setItemName(String itemName) {this.itemName = itemName;}

    public double getItemPrice() {return itemPrice;}

    public void setItemPrice(double itemPrice) {this.itemPrice = itemPrice;}
}
