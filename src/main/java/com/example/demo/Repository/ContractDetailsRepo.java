package com.example.demo.Repository;

import com.example.demo.Model.ContractDetails;
import com.example.demo.Model.Price;
import com.example.demo.Service.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ContractDetailsRepo {

    @Autowired
    JdbcTemplate template;
    @Autowired
    PriceService priceService;


    /*
    Henter alle informationer i contract_details, og mapper det med RowMapper.
     */
    public List<ContractDetails> fetchAll() {
        String sql = "SELECT * FROM contract_details";
        RowMapper<ContractDetails> rowMapper = new BeanPropertyRowMapper<>(ContractDetails.class);
        return template.query(sql, rowMapper);
    }

    /*
    Henter al information om en specifik contract_details ud fra et orderID.
    Man får derved alle tilhørende/tilvalgte produkter på en kontrakt
     */
    public List<ContractDetails> fetchAllFromOrderID(int orderID) {
        String sql = "SELECT * FROM contract_details WHERE foreign_orderID= ?";
        RowMapper<ContractDetails> rowMapper = new BeanPropertyRowMapper<>(ContractDetails.class);
        return template.query(sql, rowMapper, orderID);
    }
    /*
    Henter produkter i en katagori ud fra orderID og categoryID
    Bruges i fetchObjectCategoryFromOrderID();
     */
    public List<ContractDetails> fetchCategoryFromOrderID(int category, int orderID) {
        String sql = "SELECT detailsid, amount, calculated_price, foreign_feeid, foreign_orderid " +
                "FROM motorhome.contract_details INNER JOIN motorhome.item_fees ON contract_details.foreign_feeid = item_fees.feeid " +
                "WHERE foreign_orderID= ? AND foreign_categoryid = ?;";
        RowMapper<ContractDetails> rowMapper = new BeanPropertyRowMapper<>(ContractDetails.class);
        return template.query(sql, rowMapper, orderID, category);
    }

    /*
    Finder det nyeste oprettet orderID
     */
    public Integer returnNewestOrderID() {
        String sql = "SELECT orderid FROM orders where orderid = (select max(orderid) from orders)";
        return template.queryForObject(sql, Integer.class);
    }


    /*
    Genererer et nyt orderID i databasen.
     */
    public void generateOrderID(){
        String sql = "INSERT INTO orders (orderid) VALUES (null)";
        template.update(sql);
    }

    /*
    Tilføjer en kontrakts produkt-informationer til database.
     */
    public void addContractDetails(ContractDetails cd){
        String sql = "INSERT INTO contract_details (amount, calculated_price, foreign_feeid, foreign_orderid) VALUES (?, ?, ?, ?)";
        template.update(sql, cd.getAmount(), cd.getCalculatedPrice(), cd.getForeign_feeID(), cd.getForeign_orderID());
    }

    /*
    Henter et specifikt katagori-produkt, bruges til at finde hvilken season der er valgt til kontrakten.
    */
    public ContractDetails fetchObjectCategoryFromOrderID(int category, int orderID) {
        List<ContractDetails> seasonDetailList = fetchCategoryFromOrderID(category, orderID);
        ContractDetails seasonDetail = new ContractDetails();
        for (int i = 0; i<seasonDetailList.size(); i++)
            seasonDetail = seasonDetailList.get(i);
        return seasonDetail;
    }

    /*
    Tilføjer tilføjet produkter på en kontrakt, til den samlede liste, brugte når en kontrakt oprettes
    og når man skal tilføje endelige gebyrer ved færdiggørelse af en kontrakt.
     */
    public void addListToContractDetails (List<ContractDetails> allContractDetails) {
        for (ContractDetails contractDetails : allContractDetails) {
            addContractDetails(contractDetails);
        }
    }

    /*
    Opretter en ny contractDetails, som indeholder produkter på en kontrakt, med antal, feeId, og pris.
     */
    public ArrayList<ContractDetails> createContractDetails(String amount, String feeID, int orderID) {
        ArrayList<Integer> amountList = convertStringToIntArrayList(amount);
        ArrayList<Integer> feeIDList = convertStringToIntArrayList(feeID);
        ArrayList<Double> calculatedPrize = calculatedPriceOfFees(amountList, feeIDList);
        ArrayList<ContractDetails> contractDetails = new ArrayList<>();
        for (int i = 0; i < amountList.size(); i++) {
            contractDetails.add(new ContractDetails(amountList.get(i),calculatedPrize.get(i),feeIDList.get(i),orderID));
        }
        return contractDetails;
    }


    /*
    konvertere en String til en IntArrayList bruges til at oprette en ny contractDetails collection.
    Årsagen til det er Strings er, at vi får parametrene amount og feeID fra @RequestParam.
     */
    public ArrayList<Integer> convertStringToIntArrayList(String str) {
        String[] splitStr = str.split(",");
        ArrayList<Integer> listInt = new ArrayList<>();
        for (int i = 0; i < splitStr.length; i++) {
             listInt.add(Integer.parseInt(splitStr[i]));
        }
        return listInt;
    }

    /*
    Finder prisen for hvert produkt, udfra hvor mange (amount) og hver feeID's pris per amount.
     */
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

    /*
    Bruges til at udregne den esitmeret totalPrise ved kontrakt oprettelse, udfra tilføjet produkter, autocamperens
    pris og hvilken season den udlejes i.
     */
    public double calculateTotalPrice(List<ContractDetails> contractDetailsList, double rentalPrice, double seasonModifier) {
        double totalPrice = rentalPrice * seasonModifier;
        for (ContractDetails contractDetails : contractDetailsList) {
            totalPrice += contractDetails.getCalculatedPrice();
        }
        return totalPrice;
    }

    /*
    Udregner den totale pris for hele udlejningen, bruges når vi færdiggører en kontrakt med afsluttende informationer,
    og hvis der er tilføjet flere produkter eller gebyrer.
     */

    public double calculateTotalPriceFinalized(List<ContractDetails> fuelAndRepairDetails, double estimatedPrice) {
        double totalPrice = estimatedPrice;
        for (ContractDetails contractDetails : fuelAndRepairDetails) {
            totalPrice += contractDetails.getCalculatedPrice();
        }
        return totalPrice;
    }

    /*
    Udregner den totale pris for kontrakten, hvis det er kontrakten bliver annulleret.
     */
    public double calculateTotalPriceCancelled(double priceModifier, double currentContractPrice) {
        double calculatedCancelFee = priceModifier * currentContractPrice;
        final double cancelTotalPriceLimit = 200;
        if (calculatedCancelFee < cancelTotalPriceLimit) {
            calculatedCancelFee = cancelTotalPriceLimit;
        }
        return calculatedCancelFee;
    }
}
