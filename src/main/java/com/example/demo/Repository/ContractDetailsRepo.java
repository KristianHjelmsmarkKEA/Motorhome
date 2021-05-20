package com.example.demo.Repository;

import com.example.demo.Model.ContractDetails;
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
}
