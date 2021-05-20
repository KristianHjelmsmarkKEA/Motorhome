package com.example.demo.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ContractDetails {

    @Id
    //Fields & Attributes
    private int detailsID;
    private double calculatedPrice;
    private int foreign_feeID;
    private int foreign_orderID;

    //Constructor
    public ContractDetails() {}

    public int getDetailsID() {
        return detailsID;
    }

    public void setDetailsID(int detailsID) {
        this.detailsID = detailsID;
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
}
