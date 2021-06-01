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

    /*Author Ludvig
    Mapper alle kontrakters informationer og tilføjer dem til en collection.
     */
    public List<Contract> fetchAll() {
        String sql = "SELECT * FROM contracts";
        RowMapper<Contract> rowMapper = new BeanPropertyRowMapper<>(Contract.class);
        return template.query(sql, rowMapper);
    }

    /*Author Gustav
    Tilføjer en hel ny kontrakt til databasen med alle information.
     */
    public int addContract(Contract contract){
        String sql = "INSERT INTO contracts (start_date, end_date, start_odometer, end_odometer, " +
                "total_price, foreign_motorhomeid, foreign_customerid, foreign_orderid) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        template.update(sql, contract.getStartDate(), contract.getEndDate(), contract.getStartOdometer(), contract.getEndOdometer(),
                contract.getTotalPrice(), contract.getForeign_MotorhomeID(), contract.getForeign_CustomerID(), contract.getForeign_OrderID());
        return returnNewContractID();
    }

    /*Author Gustav
    Mapper det nyeste oprettet contractID.
     */
    public int returnNewContractID(){
        String sql = "select * from contracts where contractid = (select max(contractid) from contracts);";
        RowMapper<Contract> rowMapper = new BeanPropertyRowMapper<>(Contract.class);
        Contract c = template.queryForObject(sql, rowMapper);
        return c.getContractID();
    }

    /*Author Kristian
    Mapper alle igangværende kontrakter, bruges når man skal vælge hvilken kontrakt, som man gerne vil færdiggøre eller annullere.
     */
    public List<Contract> fetchOngoingContracts() {
        String sql = "SELECT * FROM contracts where finalized_contract = 0 and cancelled_contract = 0";
        RowMapper<Contract> rowMapper = new BeanPropertyRowMapper<>(Contract.class);
        return template.query(sql, rowMapper);
    }

    /*Author Kristian
    Har contractID med som parameter, så man kan søge efter den specifikke contract ud fra contractID,
    derefter mappes c, med en collection af den specifikke kontrakts coullums indhold
     */
    public Contract findContractByContractID(int contractID){
        String sql = "select * from contracts where contractID = ?";
        RowMapper<Contract> rowMapper = new BeanPropertyRowMapper<>(Contract.class);
        Contract c = template.queryForObject(sql, rowMapper, contractID);
        return c;
    }

    /*Author Gustav
    Har en Contract c med som parameter, trueFinalFalseCancel er en boolean, der bruges til at sætte
    kontraktens finalized og cancelled's værdi til true/false i DB. */
    public void saveContractInformation(Contract c, boolean trueFinalFalseCancel) {
        boolean finalized = false, cancelled = false;
        if (trueFinalFalseCancel) {
            finalized = true;
        } else {
            cancelled = true;
        }
        String sql = "UPDATE contracts SET start_odometer = ?, end_odometer = ?, total_price = ?, " +
                "finalized_contract = ?, cancelled_contract = ? WHERE contractid = ?";
        template.update(sql, c.getStartOdometer(), c.getEndOdometer(), c.getTotalPrice(), finalized, cancelled, c.getContractID());
    }

}
