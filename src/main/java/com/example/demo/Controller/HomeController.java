package com.example.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {
    @Autowired

@RequestMapping("home/index")
    public ModelAndView firstPage() {
        return new ModelAndView("home/index");
    }

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



    @GetMapping ("/manageMotorhomes")
    public String manageMotorhomes(Model model) {
        //code ...
        return "home/manageMotorhomes";
    }


    @GetMapping ("/manageCustomers")
    public String manageCustomers(Model model) {
        //Code ...
        return "home/manageCustomers";
    }


    @GetMapping ("/manageContracts")
    public String manageContracts(Model model) {
        //Code ...
        return "home/manageContracts";
    }
    //</editor-fold>


}
