package com.example.demo.Repository;

import com.example.demo.Model.CancelContract;
import com.example.demo.Model.Contract;
import com.example.demo.Model.FinalContract;
import com.example.demo.Model.OngoingContract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
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

    public List<FinalContract> fetchAllFinalContracts() {
        String sql = "SELECT * FROM contracts WHERE finalized_contract = true";
        RowMapper<FinalContract> rowMapper = new BeanPropertyRowMapper<>(FinalContract.class);
        return template.query(sql, rowMapper);
    }

    public List<CancelContract> fetchAllCancelledContracts() {
        String sql = "SELECT * FROM contracts WHERE cancelled_contract = true";
        RowMapper<CancelContract> rowMapper = new BeanPropertyRowMapper<>(CancelContract.class);
        return template.query(sql, rowMapper);
    }

    public List<OngoingContract> fetchAllOngoingContracts() {
        String sql = "SELECT * FROM contracts WHERE finalized_contract = false and cancelled_contract = false";
        RowMapper<OngoingContract> rowMapper = new BeanPropertyRowMapper<>(OngoingContract.class);
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
