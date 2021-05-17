package com.example.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class HomeController {
    @Autowired



    /** Homepage */
    @GetMapping("/")
    public String index(){
        return "home/index";
    }

    //<editor-fold desc="Alle knapper på forsiden">
    @PostMapping("/enterDateAndLocation")
    public String enterDateAndLocation(Model model) {
        //code
        return "home/enterDateAndLocation";
    }



    @GetMapping ("/manageMotorhomes")
    public String manageMotorhomes(Model model) {
        //code ...
        return "home/manageMotorhomes";
    }


    @GetMapping ("/manageCustomers")
    public String manageCustomers(Model model) {
        //Code ...
        return "home/manageMotorhomes";
    }


    @GetMapping ("/manageContracts")
    public String manageContracts(Model model) {
        //Code ...
        return "home/manageMotorhomes";
    }
    //</editor-fold>


}
