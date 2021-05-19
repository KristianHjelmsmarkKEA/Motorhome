package com.example.demo.Repository;

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
        String sql = "SELECT * FROM item_fees";
        RowMapper<Price> rowMapper = new BeanPropertyRowMapper<>(Price.class);
        return template.query(sql, rowMapper);
    }

    public Price addPrice(Price price){
        String sql = "INSERT INTO item_fees (VALUES (?, ?)";
        template.update(sql, price.getItemName(), price.getItemPrice());
        return null;
    }

    public Boolean deletePrice(int feeID){
        String sql = "DELETE FROM item_fees WHERE feeid = ?";
        return template.update(sql, feeID) > 0;
    }
}
