package com.example.demo.Controller;

import com.example.demo.Model.Customer;
import com.example.demo.Service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class HomeController {
    @Autowired
    CustomerService customerService;

    /** Homepage */
    @GetMapping("/")
    public String index(){
        return "home/index";
    }

    //<editor-fold desc="Alle knapper på forsiden">
    @PostMapping("/enterDateAndLocation")
    public String enterDateAndLocation(Model model) {
        //code
        return "home/reservations";
    }

    @GetMapping("/managePrices")
    public String managePrices(Model model) {
        //code ...
        return "home/managemotorhomes";
    }

    @GetMapping ("/manageMotorhomes")
    public String manageMotorhomes(Model model) {
        //code ...
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
        //Code ...
        return "home/manageContracts";
    }
    //</editor-fold>


}
