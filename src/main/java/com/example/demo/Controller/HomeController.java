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
    @GetMapping("/managePrices")
    public String managePrices(Model model) {
        List<Price> priceList = priceService.fetchAll();
        model.addAttribute("prices", priceList);
        return "home/managePrices";
    }

    @GetMapping("/updateItem/{feeID}")
    public String updateItemInformation(@PathVariable("feeID") int feeID, Model model){
        model.addAttribute("prices", priceService.findFeeID(feeID));
        return "home/updateItem";
    }

    @PostMapping("/updateItemInformation")
    public String updateItemInformation(@ModelAttribute Price price, Model model) {
        model.addAttribute("prices", price);
        priceService.updateFeeInformation(price.getFeeID(), price);
        return "redirect:/managePrices";
    }

    @GetMapping("/addItem")
    public String addItem() { return "home/addItem"; }
    @PostMapping("/addItem")
    public String addItem(@ModelAttribute Price price) {
        priceService.addPrice(price);
        return "redirect:/managePrices";
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
        System.out.println(motorhomeList);
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
        return "redirect:/manageMotorhomes";
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

    @PostMapping("/chooseMotorhome")
    public String reservations(@ModelAttribute Contract contract, Model model) {
        List<Motorhome> availableMotorhomes = motorhomeService.fetchIntervalMotorhomes(contract.getStartDate(), contract.getEndDate());
        model.addAttribute("contractDate", contract);
        model.addAttribute("availableMotorhomes", motorhomeService.removeDuplicateBrands(availableMotorhomes));

        return "home/chooseMotorhome";
    }

    @PostMapping("/extraSelection/{brandAndModel}")
    public String extraSelection(@PathVariable("brandAndModel") String brandAndModel, @ModelAttribute Contract contract, Model model) {
        List<Motorhome> allSortedMotorhomes = motorhomeService.fetchMotorhomesBrandAndModel(brandAndModel, contract.getStartDate(), contract.getEndDate());
        List<Price> accessories = priceService.fetchItemsFromCategoryNum(1);
        model.addAttribute("accessories", accessories);
        model.addAttribute("sortedMotorhomes", allSortedMotorhomes);

        return "home/extraSelection";
    }

}
