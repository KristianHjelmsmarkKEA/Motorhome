package com.example.demo.Controller;

import org.springframework.web.bind.annotation.GetMapping;

public class HomeController {



    @GetMapping("/")
    public String index(){
        return "home/index";
    }





}
