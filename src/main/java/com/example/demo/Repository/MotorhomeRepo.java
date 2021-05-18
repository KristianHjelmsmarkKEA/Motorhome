package com.example.demo.Repository;

import com.example.demo.Model.Motorhome;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MotorhomeRepo {

    @Autowired
    JdbcTemplate template;

    public List<Motorhome> fetchAll() {
        String sql = "SELECT * FROM motorhomes";
        RowMapper<Motorhome> rowMapper = new BeanPropertyRowMapper<>(Motorhome.class);
        return template.query(sql, rowMapper);
    }

    //SQL Fikset tror jeg
    public Motorhome addMotorhome(Motorhome motorhome){
        String sql = "INSERT INTO motorhomes (VALUES (?, ?, ?, ?, ?, ?)";
        template.update(sql, motorhome.getBrandAndModel(), motorhome.getCapacity(), motorhome.getOdometer(), motorhome.getNumberPlate(), motorhome.getRentalPrice(), motorhome.isService());
        return null;
    }

    public Boolean deleteMotorhome(int motorhomeID){
        String sql = "DELETE FROM motorhomes WHERE motorhomeid = ?";
        return template.update(sql, motorhomeID) > 0;
    }

}
