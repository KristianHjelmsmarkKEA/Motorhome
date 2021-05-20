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

    //<editor-fold desc="Alle knapper på forsiden">
    @PostMapping("/enterDateAndLocation")
    public String enterDateAndLocation(Model model) {
        //code
        return "home/reservations";
    }

    @GetMapping("/managePrices")
    public String managePrices(Model model) {
        List<Price> priceList = priceService.fetchAll();
        model.addAttribute("prices", priceList);
        return "home/managePrices";
    }

    @PostMapping("/reservations")
    public String reservations(@ModelAttribute Contract contract, Model model) {
        List<Motorhome> availableMotorhomes = motorhomeService.fetchIntervalMotorhomes(contract.getStartDate(), contract.getEndDate());
        model.addAttribute("availableMotorhomes", availableMotorhomes);
        return "home/reservations";
    }


    @GetMapping ("/manageContracts")
    public String manageContracts(Model model) {
        List<Contract> contractList = contractService.fetchAll();
        model.addAttribute("contracts", contractList);
        return "home/manageContracts";
    }

    @GetMapping ("/manageMotorhomes")
    public String manageMotorhomes(Model model) {
        List<Motorhome> motorhomeList = motorhomeService.fetchAll();
        model.addAttribute("motorhomes", motorhomeList);
        return "home/manageMotorhomes";
    }

    @GetMapping("/updateMotorhome/{motorhomeID}")
    public String updateMotorhome(@PathVariable("motorhomeID") int motorhomeID, Model model) {
        model.addAttribute("motorhomes", motorhomeService.findMotorhome(motorhomeID));
        return "home/updateMotorhome";
    }

    @PostMapping("/updateMotorhomeInformation")
    public String updateMotorhomeInformation(@ModelAttribute Motorhome motorhome, Model model) {
        model.addAttribute("motorhomes", motorhome);
        motorhomeService.updateMotorhomeInformation(motorhome.getMotorhomeID(), motorhome);
        return "redirect:/";
    }

    //</editor-fold>
    @GetMapping ("/manageCustomers")
    public String manageCustomers(Model model) {
        List<Customer> customerList = customerService.fetchAll();
        model.addAttribute("customers", customerList);
        return "home/manageCustomers";
    }


    @GetMapping("/updateCustomer/{customerID}")
    public String update(@PathVariable("customerID") int customerID, Model model){
        model.addAttribute("customers", customerService.findCustomerID(customerID));
        return "home/updateCustomer";
    }

    @PostMapping("/updateCustomerInformation")
    public String updateCustomerInformation(@ModelAttribute Customer customer, Model model) {
        model.addAttribute("customers", customer);
        customerService.updateCustomerInformation(customer.getCustomerID(), customer);
        return "redirect:/";
    }

}
