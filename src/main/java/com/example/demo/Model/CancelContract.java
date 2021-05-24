package com.example.demo.Model;

public class CancelContract extends Contract {

    private boolean cancelledContract;
    private double cancelledPrice;

    public CancelContract() { }

    public boolean isCancelledContract() {
        return cancelledContract;
    }

    public void setCancelledContract(boolean cancelledContract) {
        this.cancelledContract = cancelledContract;
    }

    public double getCancelledPrice() {
        return cancelledPrice;
    }

    public void setCancelledPrice(double cancelledPrice) {
        this.cancelledPrice = cancelledPrice;
    }
}
