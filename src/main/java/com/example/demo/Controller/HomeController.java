package com.example.demo.Controller;

import com.example.demo.Model.Contract;
import com.example.demo.Model.Customer;
import com.example.demo.Model.Motorhome;
import com.example.demo.Model.Price;
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
    MotorhomeService motorhomeService;
    ContractService contractService;
    PriceService priceService;

    /** Homepage */
    @GetMapping("/")
    public String index(){
        return "home/index";
    }

    //<editor-fold desc="Alle knapper på forsiden">
    @PostMapping("/enterDateAndLocation")
    public String enterDateAndLocation(Model model) {
        return "home/reservations";
    }

    @GetMapping("/managePrices")
    public String managePrices(Model model) {
        List<Price> priceList = priceService.fetchAll();
        model.addAttribute("prices", priceList);
        return "home/managePrices";
    }

    @GetMapping ("/manageMotorhomes")
    public String manageMotorhomes(Model model) {
        List<Motorhome> motorhomeList = motorhomeService.fetchAll();
        model.addAttribute("motorhomes", motorhomeList);
        return "home/manageMotorhomes";
    }


    @GetMapping ("/manageCustomers")
    public String manageCustomers(Model model) {
        List<Customer> customerList = customerService.fetchAll();
        model.addAttribute("customers", customerList);
        return "home/manageCustomers";
    }


    @GetMapping ("/manageContracts")
    public String manageContracts(Model model) {
        List<Contract> contractList = contractService.fetchAll();
        model.addAttribute("contracts", contractList);
        return "home/manageContracts";
    }
    //</editor-fold>


}
