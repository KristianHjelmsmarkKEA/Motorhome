package com.example.demo.Controller;

import com.example.demo.Model.*;
import com.example.demo.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    @Autowired
    CustomerService customerService;

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
        double motorhomeFullRentalPrice = (int) daysBetween * selectedMotorhome.getRentalPrice();
        double totalPrice = contractDetailsService.calculateTotalPrice(orderID, motorhomeFullRentalPrice, selectedSeason.getItemPrice());


        Contract initialContract = new Contract(contractDates.getStartDate(), contractDates.getEndDate(), selectedMotorhome.getOdometer(),
                selectedMotorhome.getOdometer()+((int) daysBetween * AVG_MAX_KM_PR_DAY),totalPrice,selectedMotorhome.getMotorhomeID(),0,orderID);
        model.addAttribute("prices", priceService.fetchAll());
        model.addAttribute("details", details);
        model.addAttribute("initialContract", initialContract);
        model.addAttribute("customers", new Customer());
        model.addAttribute("selectedMotorhome", selectedMotorhome);
        model.addAttribute("motorhomeTotalPrice", motorhomeFullRentalPrice*selectedSeason.getItemPrice());
        System.out.println("CONTRACT DETAILS JUST CREATED:"+details);
        System.out.println("INITIAL CONTRACT JUST CREATED:"+initialContract);

        return "home/createContract";
    }

    @GetMapping("/createContract")
    public String addCustomerToContract(Contract intialContract, Customer customer, Model model) {

        return "home/contractReciept";
    }

    @GetMapping("/finalizeContractTable")
    public String finalizeContractTable(Model model) {
        List<Contract> ongoingContractsList = contractService.fetchOngoingContracts();
        model.addAttribute("ongoingContracts", ongoingContractsList);

        return "home/finalizeContractTable";
    }

    @GetMapping("/finalizeContract/{contractID}")
    public String finalizeContract(@PathVariable("contractID") int contractID, @ModelAttribute Contract contract,
                                   Model model, @ModelAttribute ContractDetails contractDetails  ) {

        Contract contractFinalization = contractService.findOngoingContractID(contractID);
        Motorhome selectedMotorhome = motorhomeService.findMotorhomeBrandAndModel(contractFinalization.getForeign_MotorhomeID());
        List<ContractDetails> currentDetails = contractDetailsService.fetchAllFromOrderID(contractFinalization.getForeign_OrderID());
        List<Price> repairs = priceService.fetchItemsFromCategoryNum(3);
        List<Price> fuel = priceService.fetchItemsFromCategoryNum(6);


        model.addAttribute("repair", repairs);
        model.addAttribute("fuel", fuel);
        model.addAttribute("details", currentDetails);
        model.addAttribute("prices", priceService.fetchAll());
        model.addAttribute("contracts", contractFinalization);
        model.addAttribute("selectedMotorhome", selectedMotorhome);


        return "home/finalizeContractPage";
    }

    @PostMapping("/finalizeContractPage")
    public String finalizeContractPage(@ModelAttribute Contract contract, Model model, @RequestParam("amount") String amount, @RequestParam("foreign_feeID") String foreign_feeID,
                                       @ModelAttribute("foreign_MotorhomeID") Motorhome motorhome) {

        Contract contractFinalization = contractService.findOngoingContractID(contract.getContractID());
        System.out.println(contractFinalization);


        List<ContractDetails> details = contractDetailsService.fetchAllFromOrderID(contractFinalization.getForeign_OrderID());
        contractDetailsService.addListToContractDetails(details);
        model.addAttribute("details", details);
        System.out.println(details);


        double finalizedTotalPrice = contractDetailsService.calculateTotalPriceFinalized(contractFinalization.getForeign_OrderID(), contractFinalization.getTotalPrice());

        System.out.println(contractDetailsService.calculateTotalPriceFinalized(contractFinalization.getForeign_OrderID(), finalizedTotalPrice));

        System.out.println(contract.getContractID());

        model.addAttribute("contracts", contractFinalization);
        model.addAttribute("motorhome", motorhome);

        contractFinalization.setTotalPrice(finalizedTotalPrice);
        System.out.println(contractFinalization);
        contractService.finalizeContractInformation(contractFinalization);

        return "redirect:/finalizeContractTable";
    }


}
