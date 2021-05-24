package com.example.demo.Model;

public class FinalContract extends Contract {

    private boolean finalizedContract;
    private double finalPrice;

    public FinalContract() { }

    public boolean isFinalizedContract() {
        return finalizedContract;
    }

    public void setFinalizedContract(boolean finalizedContract) {
        this.finalizedContract = finalizedContract;
    }

    public double getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(double finalPrice) {
        this.finalPrice = finalPrice;
    }
}
