package com.example.demo.Repository;

import com.example.demo.Model.Customer;
import com.example.demo.Model.Price;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PriceRepo {

    @Autowired
    JdbcTemplate template;

    public List <Price> fetchAll() {
        String sql = "SELECT * FROM item_categories, item_fees where foreign_categoryid = categoryid";
        RowMapper<Price> rowMapper = new BeanPropertyRowMapper<>(Price.class);
        return template.query(sql, rowMapper);
    }

    // categoryNumber: 1=Accessories 2=Season 3=Repair 4=Cancellation 5=Transfercost 6=Fuel 7= Others.
    public List <Price> fetchItemsFromCategoryNum(int categoryNumber) {
        String sql = "SELECT * FROM item_categories, item_fees where foreign_categoryid = categoryid and foreign_categoryid ="+categoryNumber+";";
        RowMapper<Price> rowMapper = new BeanPropertyRowMapper<>(Price.class);
        return template.query(sql, rowMapper);
    }

    public void addPrice(Price p){
        String sql = "INSERT INTO item_fees (item_name, item_price, foreign_categoryid) VALUES (?, ?, ?)";
        template.update(sql, p.getItemName(), p.getItemPrice(), p.getForeign_categoryID());
    }

    public Boolean deletePrice(int feeID){
        String sql = "DELETE FROM item_fees WHERE feeid = ?";
        return template.update(sql, feeID) > 0;
    }

    public Price findFeeID(int feeID){
        String sql = "select * from item_fees WHERE feeid = ?";
        RowMapper<Price> rowMapper = new BeanPropertyRowMapper<>(Price.class);
        Price p = template.queryForObject(sql, rowMapper, feeID);
        return p;
    }
    public void updateFeeInformation(int feeID, Price p){
        String sql = "UPDATE item_fees SET item_name = ?, item_price = ? WHERE feeid = ?";
        template.update(sql, p.getItemName(), p.getItemPrice(), p.getForeign_categoryID());
    }



}
