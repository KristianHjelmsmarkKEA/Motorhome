package com.example.demo.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ContractDetails {

    @Id
    //Fields & Attributes
    private int detailsID;
    private int amount;
    private double calculatedPrice;
    private int foreign_feeID;
    private int foreign_orderID;

    //Constructor
    public ContractDetails() {}

    public ContractDetails(int amount, double calculatedPrice, int foreign_feeID, int foreign_orderID) {
        this.amount = amount;
        this.calculatedPrice = calculatedPrice;
        this.foreign_feeID = foreign_feeID;
        this.foreign_orderID = foreign_orderID;
    }

    public int getDetailsID() {
        return detailsID;
    }

    public void setDetailsID(int detailsID) {
        this.detailsID = detailsID;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getCalculatedPrice() {
        return calculatedPrice;
    }

    public void setCalculatedPrice(double calculatedPrice) {
        this.calculatedPrice = calculatedPrice;
    }

    public int getForeign_feeID() {
        return foreign_feeID;
    }

    public void setForeign_feeID(int foreign_feeID) {
        this.foreign_feeID = foreign_feeID;
    }

    public int getForeign_orderID() {
        return foreign_orderID;
    }

    public void setForeign_orderID(int foreign_orderID) {
        this.foreign_orderID = foreign_orderID;
    }

    @Override
    public String toString() {
        return "ContractDetails{" +
                "detailsID=" + detailsID +
                ", amount=" + amount +
                ", calculatedPrice=" + calculatedPrice +
                ", foreign_feeID=" + foreign_feeID +
                ", foreign_orderID=" + foreign_orderID +
                '}';
    }
}
