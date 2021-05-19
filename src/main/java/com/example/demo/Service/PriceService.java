package com.example.demo.Service;

import com.example.demo.Model.Price;
import com.example.demo.Repository.PriceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PriceService {

    @Autowired
    PriceRepo priceRepo;

    public List<Price> fetchAll() {return priceRepo.fetchAll();}

    public Price addPrice(Price price) {return priceRepo.addPrice(price);}

    public Boolean deletePrice(int feeID) {return priceRepo.deletePrice(feeID);}
}