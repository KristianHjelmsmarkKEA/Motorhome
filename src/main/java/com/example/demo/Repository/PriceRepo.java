package com.example.demo.Repository;

import com.example.demo.Model.Price;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PriceRepo {

    @Autowired
    JdbcTemplate template;

    /*
    Mapper alle infromationer i vores produkt-katagorier og produkters informationer.
     */
    public List <Price> fetchAll() {
        String sql = "SELECT * FROM item_categories, item_fees where foreign_categoryid = categoryid";
        RowMapper<Price> rowMapper = new BeanPropertyRowMapper<>(Price.class);
        return template.query(sql, rowMapper);
    }

    /*
    Mapper alle produkter i en specifik produkt-katagori
    categoryNumber: 1=Accessories 2=Season 3=Repair 4=Cancellation 5=Transfercost 6=Fuel 7= Others.
     */
    public List <Price> fetchItemsFromCategoryNum(int categoryNumber) {
        String sql = "SELECT * FROM item_categories, item_fees where foreign_categoryid = categoryid and foreign_categoryid ="+categoryNumber+";";
        RowMapper<Price> rowMapper = new BeanPropertyRowMapper<>(Price.class);
        return template.query(sql, rowMapper);
    }

    /*
    Tilføjer et nyt produkt til vores database.
     */
    public void addPrice(Price p){
        String sql = "INSERT INTO item_fees (item_name, item_price, foreign_categoryid) VALUES (?, ?, ?)";
        template.update(sql, p.getItemName(), p.getItemPrice(), p.getForeign_categoryID());
    }

    /*
    Mappper et specifikt produkts informationer, heri: navn, pris og foreign_category.
     */
    public Price findFeeID(int feeID){
        String sql = "select * from item_fees WHERE feeid = ?";
        RowMapper<Price> rowMapper = new BeanPropertyRowMapper<>(Price.class);
        Price p = template.queryForObject(sql, rowMapper, feeID);
        return p;
    }

    /*
    Opdatere et produkts informationer i databasen, hvor instansen p indeholder informationerne.
     */
    public void updateFeeInformation(int feeID, Price p){
        String sql = "UPDATE item_fees SET item_name = ?, item_price = ?, foreign_categoryid = ?  WHERE feeid = ?";
        template.update(sql, p.getItemName(), p.getItemPrice(), p.getForeign_categoryID(), feeID);
    }

    /*
    Fjerne er en specifik katagori fra en liste, bruges til at fjerne season katagorien.
     */
    public List<Price> removeCategoryPrice(List<Price> listToRemove, int category) {
        List<Price> newPriceList = new ArrayList<>();
        for (Price price : listToRemove) {
            if (price.getCategoryID() != category) {
                newPriceList.add(price);
            }
        }
        return newPriceList;
    }

}
