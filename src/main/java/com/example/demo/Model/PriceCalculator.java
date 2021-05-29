package com.example.demo.Model;

import com.example.demo.Service.PriceService;

import java.util.ArrayList;
import java.util.List;

public class PriceCalculator {

    PriceService priceService;

    public ArrayList<Double> calculatedPriceOfFees(ArrayList<Integer> amount, ArrayList<Integer> feeID) {
        ArrayList<Double> calculatedPrice = new ArrayList<>();
        List<Price> prices = priceService.fetchAll();

        for (Price price : prices) {
            for (int i = 0; i < feeID.size(); i++) {
                if (price.getFeeID() == feeID.get(i)) {
                    calculatedPrice.add(amount.get(i)*price.getItemPrice());
                }
            }

        }

        return calculatedPrice;
    }

    public double calculateTotalPrice(List<ContractDetails> contractDetailsList, double rentalPrice, double seasonModifier) {
        System.out.println("CALCULATION METHOD = rentalPrice="+rentalPrice+"seasonModifier="+seasonModifier);

        double totalPrice = (rentalPrice*seasonModifier);
        for (ContractDetails contractDetails : contractDetailsList) {
            totalPrice += contractDetails.getCalculatedPrice();
        }
        return totalPrice;
    }

    public double calculateTotalPriceFinalized(List<ContractDetails> fuelAndRepairDetails, double estimatedPrice) {
        double totalPrice = estimatedPrice;

        for (ContractDetails contractDetails : fuelAndRepairDetails) {
            totalPrice += contractDetails.getCalculatedPrice();
        }
        return totalPrice;

    }

    public double calculateTotalPriceCancelled(double priceModifier, double currentContractPrice) {

        double calculatedCancelFee = priceModifier*currentContractPrice;
        if (calculatedCancelFee < 200) {
            calculatedCancelFee = 200;
        }
        return calculatedCancelFee;
    }

}
