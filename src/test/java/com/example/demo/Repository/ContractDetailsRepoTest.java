package com.example.demo.Repository;

import com.example.demo.Model.ContractDetails;
import com.example.demo.Model.Price;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ContractDetailsRepoTest {

    @Test //Intet at Teste
    void fetchAll() {
    }

    @Test //Intet at Teste
    void fetchAllFromOrderID() {
    }

    @Test //Intet at Teste
    void fetchCategoryFromOrderID() {
    }

    @Test //For Loop test
    void fetchObjectCategoryFromOrderID() {
        //Test Dummy
        ContractDetails testSeasonDetail = new ContractDetails(2,0.12,4,1);
        List<ContractDetails> seasonDetailList = new ArrayList<>();
        seasonDetailList.add(testSeasonDetail);

        //Testing...
        ContractDetails seasonDetail = new ContractDetails();
        for (int i = 0; i<seasonDetailList.size(); i++)
            seasonDetail = seasonDetailList.get(i);
        assertEquals(testSeasonDetail, seasonDetail);
    }

    @Test //Intet at Teste
    void returnNewestOrderID() {
    }

    @Test //Intet at Teste
    void generateOrderID() {
    }

    @Test //Intet at Teste
    void addContractDetails() {
    }

    @Test //Intet at Teste
    void addListToContractDetails() {
    }

    @Test //Intet at Teste
    void createContractDetails() {
    }

    @Test
    void convertStringToIntArrayList() {
        //Test Dummies
        int iOne = 2;
        int iTwo = 3;
        int iThree = 9;
        int iFour = 4;

        //Actual Test
        String testString = (iOne+","+iTwo+","+iThree+","+iFour);
        String[] splitStr = testString.split(",");
        ArrayList<Integer> listInt = new ArrayList<>();
        for (int i = 0; i < splitStr.length; i++) {
            listInt.add(Integer.parseInt(splitStr[i]));
        }
        //Assert Size+Length and for loop.
        assertEquals(listInt.size(), splitStr.length);
        assertEquals(listInt.get(0), iOne);
        assertEquals(listInt.get(1), iTwo);
        assertEquals(listInt.get(2), iThree);
        assertEquals(listInt.get(3), iFour);
    }

    @Test
    void calculatedPriceOfFees() {
        //Test Dummies
        int testFeeIDOne = 1;
        int testFeeIDTwo = 2;
        int testAmountOne = 4;
        int testAmountTwo = 3;
        double testItemPriceOne = 15.5;
        double testItemPriceTwo = 12.0;
        Price testPriceOne = new Price(testFeeIDOne, "chair", testItemPriceOne, 1, 1, "ACCESSORIES");
        Price testPriceTwo = new Price(testFeeIDTwo, "table", testItemPriceTwo, 1, 1, "ACCESSORIES");

        //List Dummies
        List<Price> testPrices = new ArrayList<>();
        testPrices.add(testPriceOne);
        testPrices.add(testPriceTwo);

        List<Integer> testFeeID = new ArrayList<>();
        testFeeID.add(testFeeIDOne);
        testFeeID.add(testFeeIDTwo);

        List<Integer> testAmount = new ArrayList<>();
        testAmount.add(testAmountOne);
        testAmount.add(testAmountTwo);

        //Return Dummy
        ArrayList<Double> testCalculatedPrice = new ArrayList<>();
        testCalculatedPrice.add(testItemPriceOne*testAmountOne);
        testCalculatedPrice.add(testItemPriceTwo*testAmountTwo);

        ArrayList<Double> calculatedPrice = new ArrayList<>();
        for (Price price : testPrices) {
            for (int i = 0; i < testFeeID.size(); i++) {
                System.out.println("TEST AMOUNT" +testAmount.get(i) + "TEST PRICE itemPrice" + price.getItemPrice());
                if (price.getFeeID() == testFeeID.get(i)) {
                    calculatedPrice.add(testAmount.get(i)*price.getItemPrice());
                }
            }
        }
        assertEquals(calculatedPrice, testCalculatedPrice);
    }


    @Test
    void calculateTotalPrice() {
        double testCalcPriceOne = 50.2;
        double testCalcPriceTwo = 24.5;
        ContractDetails testDetailOne = new ContractDetails(2,testCalcPriceOne,6,2);
        ContractDetails testDetailTwo = new ContractDetails(4,testCalcPriceTwo,23,2);
        List<ContractDetails> contractDetailsList = new ArrayList<>();
        contractDetailsList.add(testDetailOne);
        contractDetailsList.add(testDetailTwo);
        double seasonModifier = 1.3;
        double rentalPrice = 546.50;

        double testTotalCalculated = testCalcPriceOne+testCalcPriceTwo+(seasonModifier*rentalPrice);

        double totalPrice = rentalPrice * seasonModifier;
        for (ContractDetails contractDetails : contractDetailsList) {
            totalPrice += contractDetails.getCalculatedPrice();
        }
        assertEquals(totalPrice, testTotalCalculated);
    }

    @Test
    void calculateTotalPriceFinalized() {
        double testCalcPriceOne = 233.2;
        double testCalcPriceTwo = 125.5;
        double testEstimatedPrice = 50.0;
        ContractDetails testDetailOne = new ContractDetails(2, testCalcPriceOne,6,2);
        ContractDetails testDetailTwo = new ContractDetails(4, testCalcPriceTwo,23,2);
        List<ContractDetails> fuelAndRepairDetails = new ArrayList<>();
        fuelAndRepairDetails.add(testDetailOne);
        fuelAndRepairDetails.add(testDetailTwo);

        double testTotalPrice = testCalcPriceOne+testCalcPriceTwo+testEstimatedPrice;
        double totalPrice = testEstimatedPrice;

        for (ContractDetails contractDetails : fuelAndRepairDetails) {
            totalPrice += contractDetails.getCalculatedPrice();
        }

        assertEquals(totalPrice, testTotalPrice);
    }

    @Test
    void calculateTotalPriceCancelled() {
        double testPriceModifier = 0.2;
        double testCurrentContractPriceHigh = 1452.2;
        double testCurrentContractPriceLow = 625.5;
        double cancelMinPriceLimit = 200;

        //Test for when price is higher than 1000 (200 = 0.2*1000)
        double calculatedCancelFeeHigh = testPriceModifier * testCurrentContractPriceHigh;
        assertFalse(calculatedCancelFeeHigh < cancelMinPriceLimit);
        if (calculatedCancelFeeHigh < cancelMinPriceLimit) {
            calculatedCancelFeeHigh = cancelMinPriceLimit;
        }

        //Test for when price is higher than 1000 (200 = 0.2*1000)
        double calculatedCancelFeeLow = testPriceModifier * testCurrentContractPriceLow;
        assertTrue(calculatedCancelFeeLow < cancelMinPriceLimit);
        if (calculatedCancelFeeLow < cancelMinPriceLimit) {
            calculatedCancelFeeLow = cancelMinPriceLimit;
        }

        assertEquals(calculatedCancelFeeHigh, testPriceModifier*testCurrentContractPriceHigh);
        assertEquals(calculatedCancelFeeLow, cancelMinPriceLimit);
    }
}