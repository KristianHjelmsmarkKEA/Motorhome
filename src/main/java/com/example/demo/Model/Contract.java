package com.example.demo.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;


@Entity
public class Contract {

    //Fields/Attributes
    @Id
    private int contractID;
    private LocalDate startDate;
    private LocalDate endDate;
    private double totalPrice;
    private boolean finalizedContract;
    private boolean cancelledContract;
    private int foreign_MotorhomeID;
    private int foreign_CustomerID;
    private int foreign_OrderID;

    //Constructor
    public Contract(){ }

    //Getters & Setters
    public int getContractID() {
        return contractID;
    }

    public void setContractID(int contractID) {
        this.contractID = contractID;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double finalPrice) {
        this.totalPrice = finalPrice;
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

    public int getForeign_MotorhomeID() {
        return foreign_MotorhomeID;
    }

    public void setForeign_MotorhomeID(int foreignMotorhomeID) {
        this.foreign_MotorhomeID = foreignMotorhomeID;
    }

    public int getForeign_CustomerID() {
        return foreign_CustomerID;
    }

    public void setForeign_CustomerID(int foreignCustomerID) {
        this.foreign_CustomerID = foreignCustomerID;
    }

    public int getForeign_OrderID() {
        return foreign_OrderID;
    }

    public void setForeign_OrderID(int foreignOrderID) {
        this.foreign_OrderID = foreignOrderID;
    }

}
