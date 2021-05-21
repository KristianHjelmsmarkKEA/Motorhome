package com.example.demo.Repository;

import com.example.demo.Model.ContractDetails;
import com.example.demo.Model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ContractDetailsRepo {

    @Autowired
    JdbcTemplate template;


    public List<ContractDetails> fetchAll() {
        String sql = "SELECT * FROM contract_details";
        RowMapper<ContractDetails> rowMapper = new BeanPropertyRowMapper<>(ContractDetails.class);
        return template.query(sql, rowMapper);
    }

    public Integer generateAndReturnNewOrderID() {
        generateOrderID();
        String sql = "SELECT orderid FROM orders where orderid = (select max(orderid) from orders)";
        return template.queryForObject(sql, Integer.class);
    }

    public void generateOrderID(){
        String sql = "INSERT INTO orders (orderid) VALUES (null)";
        template.update(sql);
    }
}
