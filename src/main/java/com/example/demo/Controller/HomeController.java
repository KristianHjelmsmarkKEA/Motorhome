package com.example.demo.Controller;

import com.example.demo.Model.*;
import com.example.demo.Service.ContractService;
import com.example.demo.Service.CustomerService;
import com.example.demo.Service.MotorhomeService;
import com.example.demo.Service.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    /** Homepage */
    @GetMapping("/")
    public String index(){
        return "home/index";
    }

    //Tabel-oversigt over alle priser, med feeID, navn, kategori og pris
    @GetMapping("/managePrices")
    public String managePrices(Model model) {
        List<Price> priceList = priceService.fetchAll();
        model.addAttribute("prices", priceList);
        return "home/managePrices";
    }

    /*Author
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

    /*Author
    Opdaterings page af vare-informationer.
    .updateFeeInformation henter FeeID'et for objektet, og sørger for, at det er den rette
    vare, som bliver opdateret. */
    @PostMapping("/updateItemInformation")
    public String updateItemInformation(@ModelAttribute Price price, Model model) {
        priceService.updateFeeInformation(price.getFeeID(), price);
        return "redirect:/managePrices";
    }

    /*Author
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

    //Tabel-oversigt over alle kontrakter
    @GetMapping ("/manageContracts")
    public String manageContracts(Model model) {
        List<Contract> contractList = contractService.fetchAll();
        model.addAttribute("contracts", contractList);
        return "home/manageContracts";
    }

    //Tabel-oversigt over alle autocamper
    @GetMapping ("/manageMotorhomes")
    public String manageMotorhomes(Model model) {
        List<Motorhome> motorhomeList = motorhomeService.fetchAll();
        model.addAttribute("motorhomes", motorhomeList);
        return "home/manageMotorhomes";
    }

    /*Author
    @PathVariable henter variablen "motorhomeID" ud fra den valgte autocamper i tabellen,
    så vi får fat i den specifikke autocampers id, .findMotorhome() henter alle autocamperens
    information ud fra motorhomeID, og mapper den med RowMapper<Motorhome>, og tilføjer den til en collection.
    model.addAttribute binder collectionen til "motorhomes" */
    @GetMapping("/updateMotorhome/{motorhomeID}")
    public String updateMotorhome(@PathVariable("motorhomeID") int motorhomeID, Model model) {
        model.addAttribute("motorhomes", motorhomeService.findMotorhome(motorhomeID));
        return "home/updateMotorhome";
    }

    /*Author
    Opdaterings page af autocamper-informationer.
    .updateMotorhomeInformation() henter MotorhomeID'et for objektet, og sørger for, at det er den rette
    autocampers information, som bliver opdateret. */
    @PostMapping("/updateMotorhomeInformation")
    public String updateMotorhomeInformation(@ModelAttribute Motorhome motorhome, Model model) {
        motorhomeService.updateMotorhomeInformation(motorhome.getMotorhomeID(), motorhome);
        return "redirect:/manageMotorhomes";
    }

    //Tabel-oversigt over alle kunder og deres informationer
    @GetMapping ("/manageCustomers")
    public String manageCustomers(Model model) {
        List<Customer> customerList = customerService.fetchAll();
        model.addAttribute("customers", customerList);
        return "home/manageCustomers";
    }

    /*Author
    @PathVariable henter variablen "customerID" ud fra den valgte autocamper i tabellen,
    så vi får fat i den specifikke kundes id, .findCustomerID() henter alle kundens
    information ud fra customerID, og mapper den med RowMapper<Customer>, og tilføjer den til en collection.
    model.addAttribute binder collectionen til "customers" */
    @GetMapping("/updateCustomer/{customerID}")
    public String update(@PathVariable("customerID") int customerID, Model model){
        model.addAttribute("customers", customerService.findCustomerID(customerID));
        return "home/updateCustomer";
    }

    /*Author
    Opdaterings page af kunde-informationer.
    .updateCustomerInformation() henter CustomerID'et for objektet, og sørger for, at det er den rette
    kundes information, som bliver opdateret. */
    @PostMapping("/updateCustomerInformation")
    public String updateCustomerInformation(@ModelAttribute Customer customer, Model model) {
        customerService.updateCustomerInformation(customer.getCustomerID(), customer);
        return "redirect:/";
    }
}
