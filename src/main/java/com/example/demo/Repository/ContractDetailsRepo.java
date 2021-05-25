package com.example.demo.Repository;

import com.example.demo.Model.ContractDetails;
import com.example.demo.Model.Customer;
import com.example.demo.Model.Motorhome;
import com.example.demo.Model.Price;
import com.example.demo.Service.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Repository
public class ContractDetailsRepo {

    @Autowired
    JdbcTemplate template;
    @Autowired
    PriceService priceService;


    public List<ContractDetails> fetchAll() {
        String sql = "SELECT * FROM contract_details";
        RowMapper<ContractDetails> rowMapper = new BeanPropertyRowMapper<>(ContractDetails.class);
        return template.query(sql, rowMapper);
    }

    public List<ContractDetails> fetchAllFromOrderID(int orderID) {
        String sql = "SELECT * FROM contract_details WHERE foreign_orderID= ?";
        RowMapper<ContractDetails> rowMapper = new BeanPropertyRowMapper<>(ContractDetails.class);
        return template.query(sql, rowMapper, orderID);
    }

    public Integer returnNewestOrderID() {
        String sql = "SELECT orderid FROM orders where orderid = (select max(orderid) from orders)";
        return template.queryForObject(sql, Integer.class);
    }


    public void generateOrderID(){
        String sql = "INSERT INTO orders (orderid) VALUES (null)";
        template.update(sql);
    }

    public void addContractDetails(ContractDetails cd){
        String sql = "INSERT INTO contract_details (amount, calculated_price, foreign_feeid, foreign_orderid) VALUES (?, ?, ?, ?)";
        template.update(sql, cd.getAmount(), cd.getCalculatedPrice(), cd.getForeign_feeID(), cd.getForeign_orderID());
    }


    public void addListToContractDetails (List<ContractDetails> allContractDetails) {
        for (ContractDetails contractDetails : allContractDetails) {
            addContractDetails(contractDetails);
        }
    }


    public ArrayList<ContractDetails> createContractDetails(String amount, String feeID) {
        int orderID = returnNewestOrderID();
        ArrayList<Integer> amountList = convertStringToIntArrayList(amount);
        ArrayList<Integer> feeIDList = convertStringToIntArrayList(feeID);
        ArrayList<Double> calculatedPrize = calculatedPriceOfFees(amountList, feeIDList);
        ArrayList<ContractDetails> contractDetails = new ArrayList<>();
        for (int i = 0; i < amountList.size(); i++) {
            contractDetails.add(new ContractDetails(amountList.get(i),calculatedPrize.get(i),feeIDList.get(i),orderID));
        }
        return contractDetails;
    }


    public ArrayList<Integer> convertStringToIntArrayList(String str) {
        String[] splitStr = str.split(",");
        ArrayList<Integer> listInt = new ArrayList<>();
        for (int i = 0; i < splitStr.length; i++) {
             listInt.add(Integer.parseInt(splitStr[i]));
        }
        return listInt;
    }

    public ArrayList<Double> calculatedPriceOfFees(ArrayList<Integer> amount, ArrayList<Integer> feeID) {
        ArrayList<Double> calculatedPrice = new ArrayList<>();
        List<Price> prices = priceService.fetchAll();

        for (Price price : prices) {
            for (int i = 0; i < feeID.size(); i++) {
                if (price.getFeeID() == feeID.get(i)) {
                    calculatedPrice.add(amount.get(i)*price.getItemPrice());
                }
            }

        }

        return calculatedPrice;
    }

    public double calculateTotalPrice(int orderID, double rentalPrice, double seasonModifier) {
        System.out.println("CALCULATION METHOD = orderID="+orderID+"rentalPrice="+rentalPrice+"seasonModifier="+seasonModifier);
        List<ContractDetails> contractDetailsList = fetchAllFromOrderID(orderID);
        double totalPrice = (rentalPrice*seasonModifier);
        for (ContractDetails contractDetails : contractDetailsList) {
            totalPrice += contractDetails.getCalculatedPrice();
        }
        return totalPrice;
    }

    public double calculateTotalPriceFinalized(int orderID, double estimatedPrice) {
        List<ContractDetails> contractDetailsList = fetchAllFromOrderID(orderID);
        double totalPrice = estimatedPrice;
        for (ContractDetails contractDetails : contractDetailsList) {
            totalPrice += contractDetails.getCalculatedPrice();
        }
        return totalPrice;
    }
}
