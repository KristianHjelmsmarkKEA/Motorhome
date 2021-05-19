package com.example.demo.Model;

import java.util.Date;

public class Contract {

    //Fields/Attributes
    private int contractID;
    private Date startDate;
    private Date endDate;
    private boolean finalizedContract;
    private boolean cancelledContract;
    private int foreignMotorhomeID;
    private int foreignCustomerID;
    private int foreignOrderID;

    //Constructor
    public Contract(){ }

    //Getters & Setters
    public int getContractID() {
        return contractID;
    }

    public void setContractID(int contractID) {
        this.contractID = contractID;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public boolean isFinalizedContract() {
        return finalizedContract;
    }

    public void setFinalizedContract(boolean finalizedContract) {
        this.finalizedContract = finalizedContract;
    }

    public boolean isCancelledContract() {
        return cancelledContract;
    }

    public void setCancelledContract(boolean cancelledContract) {
        this.cancelledContract = cancelledContract;
    }

    public int getForeignMotorhomeID() {
        return foreignMotorhomeID;
    }

    public void setForeignMotorhomeID(int foreignMotorhomeID) {
        this.foreignMotorhomeID = foreignMotorhomeID;
    }

    public int getForeignCustomerID() {
        return foreignCustomerID;
    }

    public void setForeignCustomerID(int foreignCustomerID) {
        this.foreignCustomerID = foreignCustomerID;
    }

    public int getForeignOrderID() {
        return foreignOrderID;
    }

    public void setForeignOrderID(int foreignOrderID) {
        this.foreignOrderID = foreignOrderID;
    }
}
