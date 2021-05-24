package com.example.demo.Repository;

import com.example.demo.Model.Contract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ContractRepo {

    @Autowired
    JdbcTemplate template;


    public List<Contract> fetchAll() {
        String sql = "SELECT * FROM contracts";
        RowMapper<Contract> rowMapper = new BeanPropertyRowMapper<>(Contract.class);
        return template.query(sql, rowMapper);
    }

    //SQL STRING SKAL ÆNDRES
    public Contract addContract(Contract contract){
        String sql = "INSERT INTO contracts (VALUES (?, ?, ?, ?, ?, ?)";
        template.update(sql, contract.getStartDate());
        return null;
    }

    public Boolean deleteContract(int contractID){
        String sql = "DELETE FROM contracts WHERE contractid = ?";
        return template.update(sql, contractID) > 0;
    }
}
