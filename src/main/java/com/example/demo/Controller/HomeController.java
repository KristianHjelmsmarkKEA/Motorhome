package com.example.demo.Controller;

import com.example.demo.Model.*;
import com.example.demo.Service.ContractService;
import com.example.demo.Service.CustomerService;
import com.example.demo.Service.MotorhomeService;
import com.example.demo.Service.PriceService;
import com.example.demo.Service.ContractDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.temporal.ChronoUnit;

@Controller
public class HomeController {
    @Autowired
    CustomerService customerService;
    @Autowired
    MotorhomeService motorhomeService;
    @Autowired
    ContractService contractService;
    @Autowired
    PriceService priceService;
    @Autowired
    ContractDetailsService contractDetailsService;


    /** Homepage */
    @GetMapping("/")
    public String index(){
        return "home/index";
    }

    /*Author Gustav
    Tabel-oversigt over alle priser, med feeID, navn, kategori og pris
     */
    @GetMapping("/managePrices")
    public String managePrices(Model model) {
        model.addAttribute("prices", priceService.fetchAll());
        return "home/managePrices";
    }

    /*Author Kristian
    @PathVariable henter variablen "feeID" ud fra den valgte vare i tabellen,
    så vi får fat i den specifikke vares id, .findFeeID() henter alle item_fees information ud fra feeid,
    og mapper den med RowMapper<Price>, og tilføjer den til en collection.
    model.addAttribute binder collectionen til "prices"
    */
    @GetMapping("/updateItem/{feeID}")
    public String updateItemInformation(@PathVariable("feeID") int feeID, Model model){
        model.addAttribute("prices", priceService.findFeeID(feeID));
        return "home/updateItem";
    }

    /*Author Kristian
    Opdaterings page af vare-informationer.
    .updateFeeInformation henter FeeID'et for objektet, og sørger for, at det er den rette
    vare, som bliver opdateret. */
    @PostMapping("/updateItemInformation")
    public String updateItemInformation(@ModelAttribute Price price) {
        priceService.updateFeeInformation(price.getFeeID(), price);
        return "redirect:/managePrices";
    }

    /*Author Kristian
    Tilføjelse af ny vare i DB.
    .addPrice opdatere DB med indtastet information fra hjemmeside index/addItem.
    */
    @GetMapping("/addItem")
    public String addItem() { return "home/addItem"; }
    @PostMapping("/addItem")
    public String addItem(@ModelAttribute Price price) {
        priceService.addPrice(price);
        return "redirect:/managePrices";
    }

    /*Author Kristian
    Tabel-oversigt over alle kontrakter
     */
    @GetMapping ("/manageContracts")
    public String manageContracts(Model model) {
        model.addAttribute("contracts", contractService.fetchAll());
        return "home/manageContracts";
    }

    /*Author Gustav
    Burges til, at hente infromationer på en kontrakt, kunden og produkterne, så vi kan printe en specifik kontrakts kvittering,
    som man kan hente ud fra en tabel.
     */
    @GetMapping ("/receiptPage/{contractID}")
    public String receiptPage(@PathVariable("contractID") int contractID, Model model) {
        Contract contract = contractService.findContractByContractID(contractID);
        int daysBetween = (int)ChronoUnit.DAYS.between(contract.getStartDate(), contract.getEndDate());
        ContractDetails seasonDetail = contractDetailsService.fetchObjectCategoryFromOrderID(2, contract.getForeign_OrderID());

        model.addAttribute("seasonDetail", seasonDetail);
        model.addAttribute("prices", priceService.removeCategoryPrice(priceService.fetchAll(),2));
        model.addAttribute("selectedMotorhome", motorhomeService.findMotorhomeID(contract.getForeign_MotorhomeID()));
        model.addAttribute("chosenCustomer", customerService.findCustomerID(contract.getForeign_CustomerID()));
        model.addAttribute("mainContract", contract);
        model.addAttribute("details", contractDetailsService.fetchAllFromOrderID(contract.getForeign_OrderID()));
        model.addAttribute("daysBetween", daysBetween);
        model.addAttribute("motorhomeTotalPrice", (daysBetween * seasonDetail.getCalculatedPrice()));
        return "home/contractReceipt";
    }

    /*Author Frederik
    Tabel-oversigt over alle autocamper
     */
    @GetMapping ("/manageMotorhomes")
    public String manageMotorhomes(Model model) {
        model.addAttribute("motorhomes", motorhomeService.fetchAll());
        return "home/manageMotorhomes";
    }

    /*Author Kristian
    @PathVariable henter variablen "motorhomeID" ud fra den valgte autocamper i tabellen,
    så vi får fat i den specifikke autocampers id, .findMotorhome() henter alle autocamperens
    information ud fra motorhomeID, og mapper den med RowMapper<Motorhome>, og tilføjer den til en collection.
    model.addAttribute binder collectionen til "motorhomes" */
    @GetMapping("/updateMotorhome/{motorhomeID}")
    public String updateMotorhome(@PathVariable("motorhomeID") int motorhomeID, Model model) {
        model.addAttribute("motorhomes", motorhomeService.findMotorhomeID(motorhomeID));
        return "home/updateMotorhome";
    }

    /*Author Kristian
    Opdaterings page af autocamper-informationer.
    .updateMotorhomeInformation() henter MotorhomeID'et for objektet, og sørger for, at det er den rette
    autocampers information, som bliver opdateret. */
    @PostMapping("/updateMotorhomeInformation")
    public String updateMotorhomeInformation(@ModelAttribute Motorhome motorhome) {
        motorhomeService.updateMotorhome(motorhome);
        return "redirect:/manageMotorhomes";
    }

    /*Author Kristian
    Tilføjelse af ny autocamper i DB.
    .addMotorhome() opdatere DB med indtastet information fra hjemmeside index/addMotorhome.
    */
    @GetMapping("/addMotorhome")
    public String addMotorhome() { return "home/addMotorhome"; }
    @PostMapping("/addMotorhome")
    public String addMotorhome(@ModelAttribute Motorhome motorhome) {
        motorhomeService.addMotorhome(motorhome);
        return "redirect:/manageMotorhomes";
    }

    /*Author Gustav
    Tabel-oversigt over alle kunder og deres informationer
     */
    @GetMapping ("/manageCustomers")
    public String manageCustomers(Model model) {
        model.addAttribute("customers", customerService.fetchAll());
        return "home/manageCustomers";
    }

    /*Author Kristian
    @PathVariable henter variablen "customerID" ud fra den valgte autocamper i tabellen,
    så vi får fat i den specifikke kundes id, .findCustomerID() henter alle kundens
    information ud fra customerID, og mapper den med RowMapper<Customer>, og tilføjer den til en collection.
    model.addAttribute binder collectionen til "customers" */
    @GetMapping("/updateCustomer/{customerID}")
    public String updateCustomer(@PathVariable("customerID") int customerID, Model model){
        model.addAttribute("customers", customerService.findCustomerID(customerID));
        return "home/updateCustomer";
    }

    /*Author Kristian
    Opdaterings page af kunde-informationer.
    .updateCustomerInformation() henter CustomerID'et for objektet, og sørger for, at det er den rette
    kundes information, som bliver opdateret. */
    @PostMapping("/updateCustomerInformation")
    public String updateCustomerInformation(@ModelAttribute Customer customer) {
        customerService.updateCustomer(customer);
        return "redirect:/";
    }

    /*Author Kristian
    Tilføjelse af ny vare i DB.
    .addPrice opdatere DB med indtastet information fra hjemmeside index/addItem.
    */
    @GetMapping("/addCustomer")
    public String addCustomer() { return "home/addCustomer"; }
    @PostMapping("/addCustomer")
    public String addCustomer(@ModelAttribute Customer customer) {
        customerService.addCustomerAddressZipcodeCountry(customer);
        return "redirect:/manageCustomers";
    }
}
