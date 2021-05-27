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

    public List<Price> fetchAll() {
        return priceRepo.fetchAll();
    }

    public void addPrice(Price price) {
        priceRepo.addPrice(price);
    }

    public Boolean deletePrice(int feeID) {
        return priceRepo.deletePrice(feeID);
    }

    public Price findFeeID(int feeID) {
        return priceRepo.findFeeID(feeID);
    }

    public void updateFeeInformation(int feeID, Price p) {
        priceRepo.updateFeeInformation(feeID, p);
    }

    public List<Price> fetchItemsFromCategoryNum(int categoryNumber) {
        return priceRepo.fetchItemsFromCategoryNum(categoryNumber);
    }

    public List<Price> removeCategoryPrice(List<Price> listToRemove, int category) {
        return priceRepo.removeCategoryPrice(listToRemove, category);
    }

}