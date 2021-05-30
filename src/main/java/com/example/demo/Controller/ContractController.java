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

        model.addAttribute("accessories", accessories);
        model.addAttribute("customer", new Customer());
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

        contractDetailsService.generateOrderID();
        int orderID = contractDetailsService.returnNewestOrderID();

        Price selectedSeason = priceService.findFeeID(season.getFeeID());
        Motorhome selectedMotorhome = motorhomeService.findMotorhome(motorhome.getMotorhomeID());

        ArrayList<ContractDetails> details = contractDetailsService.createContractDetails(amount, foreign_feeID, orderID);
        ArrayList<ContractDetails> detailsWithSeason = new ArrayList<>(details);
        detailsWithSeason.add(new ContractDetails(1, (selectedMotorhome.getRentalPrice() * selectedSeason.getItemPrice()), selectedSeason.getFeeID(), orderID));
        contractDetailsService.addListToContractDetails(detailsWithSeason);

        long daysBetween = ChronoUnit.DAYS.between(contractDates.getStartDate(), contractDates.getEndDate());
        double motorhomeFullRentalPrice = (int) daysBetween * selectedMotorhome.getRentalPrice();
        double totalPrice = contractDetailsService.calculateTotalPrice(details, motorhomeFullRentalPrice, selectedSeason.getItemPrice());
        System.out.println("TOTAL PRICE CALCULATED: " + totalPrice);
        Contract initialContract = new Contract(contractDates.getStartDate(), contractDates.getEndDate(), selectedMotorhome.getOdometer(),
                selectedMotorhome.getOdometer()+((int) daysBetween * AVG_MAX_KM_PR_DAY),totalPrice,selectedMotorhome.getMotorhomeID(),0,orderID);
        model.addAttribute("prices", priceService.fetchAll());
        model.addAttribute("details", details);
        model.addAttribute("initialContract", initialContract);
        model.addAttribute("customers", new Customer());
        model.addAttribute("contract", new Contract());
        model.addAttribute("selectedMotorhome", selectedMotorhome);
        model.addAttribute("motorhomeTotalPrice", motorhomeFullRentalPrice*selectedSeason.getItemPrice());
        model.addAttribute("allCustomers", customerService.fetchAll());

        return "home/createContract";
    }

    @PostMapping("/newCustomerToContract")
    public String newCustomerToContractPost(@ModelAttribute Customer customer,  @ModelAttribute("initialContract") Contract initialContract, Model model) {
        Customer chosenCustomer;
        int newCustomerID;
        if (customer.getCustomerID() != 0) { //If customer is chosen
            chosenCustomer = customerService.findCustomerID(customer.getCustomerID());
            newCustomerID = customer.getCustomerID();
        } else { //else if customer is created
            chosenCustomer = customer;
            newCustomerID = customerService.addCustomerAddressZipcodeCountry(chosenCustomer);
        }
        initialContract.setForeign_CustomerID(newCustomerID);
        int contractID = contractService.addContract(initialContract);
        initialContract.setContractID(contractID);

        long daysBetween = ChronoUnit.DAYS.between(initialContract.getStartDate(),initialContract.getEndDate());
        ContractDetails seasonDetail = contractDetailsService.fetchObjectCategoryFromOrderID(2, initialContract.getForeign_OrderID());

        model.addAttribute("seasonDetail", seasonDetail);
        model.addAttribute("chosenCustomer", chosenCustomer);
        model.addAttribute("mainContract", initialContract);
        model.addAttribute("prices", priceService.removeCategoryPrice(priceService.fetchAll(),2));
        model.addAttribute("details", contractDetailsService.fetchAllFromOrderID(initialContract.getForeign_OrderID()));
        model.addAttribute("selectedMotorhome", motorhomeService.findMotorhome(initialContract.getForeign_MotorhomeID()));
        model.addAttribute("daysBetween", (int)daysBetween);
        model.addAttribute("motorhomeTotalPrice", ((int)daysBetween*seasonDetail.getCalculatedPrice()));

        return "home/contractReceipt";
    }

    @GetMapping("/closeContractTable")
    public String closeContractTable(Model model) {
        List<Contract> ongoingContractsList = contractService.fetchOngoingContracts();
        model.addAttribute("ongoingContracts", ongoingContractsList);

        return "home/closeContractTable";
    }

    /*Author Kristian
    Der oprettes en instans af contractFinalization, metodekaldet findOngoingContractID() bruger sql og rowMapper til,
    at oprette en collection af ongoing contracts, som vises i en tabel, hvor man kan vælge en specifik ongoingContract.
    @PathVariable henter variablen "contractID" ud fra den valgte ongoingContract i tabellen.
    selectedMotorhome variablen er en instans, som får værdien af autocamperens model og mærkes information.
    currentDetails variablen er en instans/collection af alle tidligere vare, som er tilknyttet til kontrakten.
    repairs og fuel er de nye tilføjede "vare/services", som brugeren kan vælge at tilføje på webapplikationen
    model.addAttribute bruges til, at binde en række collections og instanser til model attributes.
     */
    @GetMapping("/finalizeContract/{contractID}")
    public String finalizeContract(@PathVariable("contractID") int contractID, Model model ) {
        Contract contractFinalization = contractService.findContractByContractID(contractID);
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

    /*Author Kristian
    Denne metode vises som en kvittering i webapplikationen.
    @RequestParam(amount) og (foreign_feeID) bruges til at extracte form parametrene String amount og String foreign_feeID
    fra finalizeContractPage.html, details variablen opretter en ny instans med en collection af de tilføjede vare, og tilføjer
    dem efterfølgende med addListToContractDetails(), så man får en samlet collection af vare/pris-tilføjelser til kontrakten.
    Den endelige pris bliver fundet med finalizedTotalPrice variablen og setTotalPrice(finalizedTotalPrice) opdatere DB med ny endelig pris.
    saveContractInformation() metodekaldet opdatere DB boolean værdi fra 0 til 1, så kontrakten registreres som færdig
    og med ny endelig pris. */
    @PostMapping("/finalizeContractPage")
    public String finalizeContractPage(@ModelAttribute Contract contract, Model model, @RequestParam("amount") String amount,
                                       @RequestParam("foreign_feeID") String foreign_feeID) {
        Contract contractFinalization = contractService.findContractByContractID(contract.getContractID());
        List<ContractDetails> details = contractDetailsService.createContractDetails(amount, foreign_feeID, contractFinalization.getForeign_OrderID());
        contractDetailsService.addListToContractDetails(details);
        double finalizedTotalPrice = contractDetailsService.calculateTotalPriceFinalized(details, contractFinalization.getTotalPrice());
        Motorhome selectedMotorhome = motorhomeService.findMotorhome(contractFinalization.getForeign_MotorhomeID());
        Customer chosenCustomer = customerService.findCustomerID(contractFinalization.getForeign_CustomerID());
        long daysBetween = ChronoUnit.DAYS.between(contractFinalization.getStartDate(),contractFinalization.getEndDate());
        ContractDetails seasonDetail = contractDetailsService.fetchObjectCategoryFromOrderID(2, contractFinalization.getForeign_OrderID());

        contractFinalization.setTotalPrice(finalizedTotalPrice);
        selectedMotorhome.setOdometer(contract.getEndOdometer());
        motorhomeService.updateMotorhomeInformation(selectedMotorhome);
        contractFinalization.setEndOdometer(contract.getEndOdometer());
        contractFinalization.setStartOdometer(contract.getStartOdometer());


        contractService.saveContractInformation(contractFinalization, true);

        model.addAttribute("seasonDetail", seasonDetail);
        model.addAttribute("prices", priceService.removeCategoryPrice(priceService.fetchAll(),2));
        model.addAttribute("details",details);
        model.addAttribute("selectedMotorhome", selectedMotorhome);
        model.addAttribute("chosenCustomer", chosenCustomer);
        model.addAttribute("mainContract", contractFinalization);
        model.addAttribute("details", contractDetailsService.fetchAllFromOrderID(contractFinalization.getForeign_OrderID()));
        model.addAttribute("daysBetween", (int)daysBetween);
        model.addAttribute("motorhomeTotalPrice", ((int)daysBetween*seasonDetail.getCalculatedPrice()));

        return "home/contractReceipt";
    }

    @GetMapping("/cancelContract/{contractID}")
    public String cancelContract(@PathVariable("contractID") int contractID, @ModelAttribute Contract contract, Model model, @ModelAttribute ContractDetails contractDetails) {
        Contract contractFinalization = contractService.findContractByContractID(contractID);
        Motorhome selectedMotorhome = motorhomeService.findMotorhomeBrandAndModel(contractFinalization.getForeign_MotorhomeID());
        List<ContractDetails> currentDetails = contractDetailsService.fetchAllFromOrderID(contractFinalization.getForeign_OrderID());
        List<Price> cancelFees = priceService.fetchItemsFromCategoryNum(4);
        model.addAttribute("cancelFees", cancelFees);
        model.addAttribute("details", currentDetails);
        model.addAttribute("prices", priceService.fetchAll());
        model.addAttribute("contracts", contractFinalization);
        model.addAttribute("selectedMotorhome", selectedMotorhome);

        return "home/cancelContractPage";
    }

    @PostMapping("/cancelContractPage")
    public String cancelContractPage(@ModelAttribute Contract contract, Model model, Price selectedCancelFee,
                                     @ModelAttribute("foreign_MotorhomeID") Motorhome motorhome) {
        Contract contractFinalization = contractService.findContractByContractID(contract.getContractID());
        Price cancelFee = priceService.findFeeID(selectedCancelFee.getFeeID());
        double cancelTotalPrice = contractDetailsService.calculateTotalPriceCancelled(cancelFee.getItemPrice(), contractFinalization.getTotalPrice());
        ContractDetails cancelDetails = new ContractDetails(1, cancelTotalPrice, cancelFee.getFeeID(), contractFinalization.getForeign_OrderID());
        contractDetailsService.addContractDetails(cancelDetails);
        contractFinalization.setTotalPrice(cancelTotalPrice);
        contractService.saveContractInformation(contractFinalization, false);

        Motorhome selectedMotorhome = motorhomeService.findMotorhome(contractFinalization.getForeign_MotorhomeID());
        Customer chosenCustomer = customerService.findCustomerID(contractFinalization.getForeign_CustomerID());
        long daysBetween = ChronoUnit.DAYS.between(contractFinalization.getStartDate(), contractFinalization.getEndDate());

        ContractDetails seasonDetail = contractDetailsService.fetchObjectCategoryFromOrderID(2, contractFinalization.getForeign_OrderID());

        model.addAttribute("seasonDetail", seasonDetail);
        model.addAttribute("prices", priceService.removeCategoryPrice(priceService.fetchAll(),2));
        model.addAttribute("selectedMotorhome", selectedMotorhome);
        model.addAttribute("chosenCustomer", chosenCustomer);
        model.addAttribute("mainContract", contractFinalization);
        model.addAttribute("details", contractDetailsService.fetchAllFromOrderID(contractFinalization.getForeign_OrderID()));
        model.addAttribute("daysBetween", (int) daysBetween);
        model.addAttribute("motorhomeTotalPrice", ((int) daysBetween * seasonDetail.getCalculatedPrice()));

        return "home/contractReceipt";
    }
}
