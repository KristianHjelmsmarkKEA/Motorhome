package com.example.demo.Repository;

import com.example.demo.Model.Contract;
import com.example.demo.Model.Motorhome;
import com.example.demo.Model.Price;
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

    public void addPrice(Price p){
        String sql = "INSERT INTO item_fees (item_name, item_price, foreign_categoryid) VALUES (?, ?, ?)";
        template.update(sql, p.getItemName(), p.getItemPrice(), p.getForeign_categoryID());
    }
    public void addContract(Contract contract){
        String sql = "INSERT INTO contracts (start_date, end_date, start_odometer, end_odometer, " +
                "total_price, foreign_motorhomeid, foreign_customerid, foreign_orderid) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        template.update(sql, contract.getStartDate(), contract.getEndDate(), contract.getStartOdometer(), contract.getEndOdometer(),
                contract.getTotalPrice(), contract.getForeign_MotorhomeID(), contract.getForeign_CustomerID(), contract.getForeign_OrderID());
    }

    public Boolean deleteContract(int contractID){
        String sql = "DELETE FROM contracts WHERE contractid = ?";
        return template.update(sql, contractID) > 0;
    }

    public List<Contract> fetchOngoingContracts() {
        String sql = "SELECT * FROM contracts where finalized_contract = 0 and cancelled_contract = 0";
        RowMapper<Contract> rowMapper = new BeanPropertyRowMapper<>(Contract.class);
        return template.query(sql, rowMapper);
    }

    public Contract findOngoingContractID(int contractID){
        String sql = "select * from contracts where contractID = ?";
        RowMapper<Contract> rowMapper = new BeanPropertyRowMapper<>(Contract.class);
        Contract c = template.queryForObject(sql, rowMapper, contractID);
        return c;
    }

    public void finalizeContractInformation(Contract c){
        String sql = "UPDATE contracts SET total_price = ?, finalized_contract = 1 WHERE contractid = ?";
        template.update(sql,c.getTotalPrice(), c.getContractID());
    }




}
