package com.example.demo.Controller;

import com.example.demo.Model.*;
import com.example.demo.Service.ContractService;
import com.example.demo.Service.MotorhomeService;
import com.example.demo.Service.PriceService;
import com.example.demo.Service.ContractDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ContractController {
    @Autowired
    MotorhomeService motorhomeService;
    @Autowired
    ContractService contractService;
    @Autowired
    PriceService priceService;
    @Autowired
    ContractDetailsService contractDetailsService;

    final int AVG_MAX_KM_PR_DAY = 400;

    @PostMapping("/chooseMotorhome")
    public String reservations(@ModelAttribute Contract contract, Model model) {
        List<Motorhome> availableMotorhomes = motorhomeService.fetchIntervalMotorhomes(contract.getStartDate(), contract.getEndDate());
        model.addAttribute("contractDate", contract);
        model.addAttribute("availableMotorhomes", motorhomeService.removeDuplicateBrands(availableMotorhomes));
        return "home/chooseMotorhome";
    }

    @GetMapping("extraSelection/{brandAndModel}")
    public String extraSelection(@PathVariable("brandAndModel") String brandAndModel,@ModelAttribute ContractDetails contractDetails, Contract contractDates, Model model) {
        List<Motorhome> allSortedMotorhomes = motorhomeService.fetchMotorhomesBrandAndModel(brandAndModel, contractDates.getStartDate(), contractDates.getEndDate());
        List<Price> accessories = priceService.fetchItemsFromCategoryNum(1);
        List<Price> transferCost = priceService.fetchItemsFromCategoryNum(5);
        List<Price> seasons = priceService.fetchItemsFromCategoryNum(2);
        System.out.println("Start Date= " + contractDates.getStartDate() + "End Date= " + contractDates.getEndDate());

        model.addAttribute("accessories", accessories);
        model.addAttribute("transferCost", transferCost);
        model.addAttribute("seasons", seasons);
        model.addAttribute("sortedMotorhomes", allSortedMotorhomes);
        model.addAttribute("contractDate", contractDates);
        model.addAttribute("contractDetails", new ContractDetails());
        return "home/extraSelection";
    }

    @PostMapping("/createContract")
    public String createContract(@RequestParam("amount") String amount, @RequestParam("foreign_feeID") String foreign_feeID,
                                @ModelAttribute("motorhomeID") Motorhome motorhome, Contract contractDates, Price season, Model model) {
        System.out.println("Amount of each item"+amount+"of the assosiated foreignkey"+foreign_feeID);
        System.out.println("Season price FeeID (4=1.6, 5=1.3, 6=1.0):"+season.getFeeID());
        contractDetailsService.generateOrderID();

        Price selectedSeason = priceService.findFeeID(season.getFeeID());
        Motorhome selectedMotorhome = motorhomeService.findMotorhome(motorhome.getMotorhomeID());

        ArrayList<ContractDetails> details = contractDetailsService.createContractDetails(amount, foreign_feeID);
        contractDetailsService.addListToContractDetails(details);
        int orderID = contractDetailsService.returnNewestOrderID();
        long daysBetween = ChronoUnit.DAYS.between(contractDates.getStartDate(),contractDates.getEndDate());
        double totalPrice = contractDetailsService.calculateTotalPrice(orderID, selectedMotorhome.getRentalPrice(), selectedSeason.getItemPrice());


        Contract finalContract = new Contract();

        finalContract.setStartDate(contractDates.getStartDate());
        finalContract.setEndDate(contractDates.getEndDate());
        finalContract.setStartOdometer(selectedMotorhome.getOdometer());
        finalContract.setEndOdometer(selectedMotorhome.getOdometer()+((int) daysBetween * AVG_MAX_KM_PR_DAY));
        finalContract.setTotalPrice(totalPrice);
        finalContract.setForeign_MotorhomeID(selectedMotorhome.getMotorhomeID());
        finalContract.setForeign_CustomerID(1);
        finalContract.setForeign_OrderID(orderID);

        System.out.println("CONTRACT DETAILS JUST CREATED:"+details);
        System.out.println("INITIAL CONTRACT JUST CREATED:"+finalContract);

        return "home/index";
    }

    @PostMapping("/addCustomerToContract")
    public String addCustomerToContract() {

        return "home/contractReciept";
    }


}
