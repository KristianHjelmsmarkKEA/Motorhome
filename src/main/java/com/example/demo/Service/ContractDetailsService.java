package com.example.demo.Service;

import com.example.demo.Model.ContractDetails;
import com.example.demo.Repository.ContractDetailsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ContractDetailsService {

    @Autowired
    ContractDetailsRepo contractDetailsRepo;

    public List<ContractDetails> fetchAll() {return contractDetailsRepo.fetchAll();}

    public List<ContractDetails> fetchAllFromOrderID(int orderID) { return contractDetailsRepo.fetchAllFromOrderID(orderID); }


    public ArrayList<ContractDetails> createContractDetails(String amount, String feeID) {
        return contractDetailsRepo.createContractDetails(amount, feeID);
    }

    public Integer returnNewestOrderID() {
        return contractDetailsRepo.returnNewestOrderID();
    }

    public void generateOrderID() {
        contractDetailsRepo.generateOrderID();
    }

    public void addContractDetails(ContractDetails cd) {
        contractDetailsRepo.addContractDetails(cd);
    }

    public void addListToContractDetails(List<ContractDetails> allContractDetails) {
        contractDetailsRepo.addListToContractDetails(allContractDetails);
    }

    public double calculateTotalPrice(int orderID, double rentalPrice, double seasonModifier) {
        return contractDetailsRepo.calculateTotalPrice(orderID, rentalPrice, seasonModifier);
    }

    public double calculateTotalPriceFinalized(int orderID, double totalPrice) {
        return contractDetailsRepo.calculateTotalPriceFinalized(orderID, totalPrice);
    }
}
