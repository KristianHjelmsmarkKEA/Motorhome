package com.example.demo.Model;

public class OngoingContract extends Contract{

    private double currentPrice;

    public OngoingContract(){ }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }
}
