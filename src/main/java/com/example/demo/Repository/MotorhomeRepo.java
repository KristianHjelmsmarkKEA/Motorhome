package com.example.demo.Repository;

import com.example.demo.Model.Contract;
import com.example.demo.Model.Motorhome;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
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

    public List<Motorhome> fetchAllInService() {
        String sql = "SELECT * FROM motorhomes WHERE in_service = 1";
        RowMapper<Motorhome> rowMapper = new BeanPropertyRowMapper<>(Motorhome.class);
        return template.query(sql, rowMapper);
    }

    //SQL Fikset tror jeg
    public void addMotorhome(Motorhome motorhome){
        String sql = "INSERT INTO motorhomes (brand_and_model, capacity, odometer, number_plate, rental_price, in_service) VALUES (?, ?, ?, ?, ?, 1)";
        template.update(sql, motorhome.getBrandAndModel(), motorhome.getCapacity(), motorhome.getOdometer(), motorhome.getNumberPlate(), motorhome.getRentalPrice());
    }

    public Motorhome findMotorhomeID(int motorhomeID){
        String sql = "select * from motorhomes where motorhomeid = ?";
        RowMapper<Motorhome> rowMapper = new BeanPropertyRowMapper<>(Motorhome.class);
        Motorhome m = template.queryForObject(sql, rowMapper, motorhomeID);
        return m;
    }

    public void updateMotorhomeInformation(Motorhome m) {
        String sql = "UPDATE motorhomes SET odometer = ?, rental_price = ?, in_service =? where motorhomeid = ?";
        template.update(sql, m.getOdometer(), m.getRentalPrice(), m.isInService(), m.getMotorhomeID());
    }

    public List<Integer> unavailableMotorhomes(LocalDate startDate, LocalDate endDate) {
        String sql = "SELECT foreign_motorhomeid from contracts where" +
                "(start_date BETWEEN '"+startDate+"' AND '"+endDate+"') OR " +
                "(end_date BETWEEN '"+startDate+"' AND '"+endDate+"') OR " +
                "('"+startDate+"' between start_date AND end_date) OR " +
                "('"+endDate+"' between start_date AND end_date);";
        try { //Try Catch, til hvis den ikke kan finde nogen foreign_motorhomeid I brug af andre kontrakter.
            RowMapper<Contract> rowMapper = new BeanPropertyRowMapper<>(Contract.class);
            List<Contract> listContracts = template.query(sql, rowMapper);
            ArrayList<Integer> listInts = new ArrayList<>();
            for (Contract contract : listContracts) {
                listInts.add(contract.getForeign_MotorhomeID());
            }
            return listInts;
        } catch (EmptyResultDataAccessException e) {
            System.out.println("Exception caught!!! " + e + ", RETURNING null instead :)");
            return null;
        }
    }

    public List<Motorhome> fetchIntervalMotorhomes(LocalDate startDate, LocalDate endDate) {

        List<Motorhome> allMotorhomesInService = fetchAllInService();
        List<Motorhome> unAvailableMotorhomes = new ArrayList<>();

        if (unavailableMotorhomes(startDate, endDate) == null) {
            return allMotorhomesInService;
        }

        List<Integer> unavailableMH = unavailableMotorhomes(startDate, endDate);

        for (Motorhome motorhome : allMotorhomesInService) {
            for (int i = 0; i < unavailableMH.size(); i++) {
                if (motorhome.getMotorhomeID() == unavailableMH.get(i)) {
                    if (!unAvailableMotorhomes.contains(motorhome)) { //Adds Motorhome Object to unAvailableMotorhomes
                        unAvailableMotorhomes.add(motorhome);
                    }
                }
            }
        }
        for (int i = 0; i < unAvailableMotorhomes.size(); i++) { //Removes all unAvailableMotorhome Objects from return value.
            allMotorhomesInService.remove(unAvailableMotorhomes.get(i));
        }

        return allMotorhomesInService;
    }

    public List<Motorhome> removeDuplicateBrands(List<Motorhome> withDuplicates) {
        ArrayList<Motorhome> removedDuplicates = new ArrayList<>();

        for (Motorhome motorhome : withDuplicates) {
            if (!removedDuplicates.toString().contains(motorhome.getBrandAndModel())) {
                removedDuplicates.add(motorhome);
            }
        }
        return removedDuplicates;
    }

    public List<Motorhome> fetchMotorhomesBrandAndModel(String brandAndModel, LocalDate startDate, LocalDate endDate) {
        List<Motorhome> motorhomesInService = fetchIntervalMotorhomes(startDate, endDate);
        List<Motorhome> sortedMotorhomes = new ArrayList<>();

        for (Motorhome motorhome : motorhomesInService) {
            if (motorhome.getBrandAndModel().contains(brandAndModel))
                sortedMotorhomes.add(motorhome);
        }
        return sortedMotorhomes;
    }
}
