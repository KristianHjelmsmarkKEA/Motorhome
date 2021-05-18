package com.example.demo.Repository;

import com.example.demo.Model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CustomerRepo {

    @Autowired
    JdbcTemplate template;

    public List<Customer> fetchAll() {
        String sql = "SELECT * FROM customers;";
        RowMapper<Customer> rowMapper = new BeanPropertyRowMapper<>(Customer.class);
        return template.query(sql, rowMapper);
    }

    public Customer addCountry(Customer customer){
        String sql = "INSERT INTO country (country) VALUES (?)";
        template.update(sql, customer.getCountry());
        return null;
    }
    //SQL STRING SKAL ÆNDRES?
    public Customer addZipcode(Customer customer) {
        String sql = "INSERT INTO zipcodes (zipcode, city, foreign_countryid) VALUES (?, ?, (select countryid from country where countryid = (select max(countryid) from country)))";
        template.update(sql, customer.getZipcode(), customer.getCity());
        return null;
    }

    //SQL STRING SKAL ÆNDRES
    public Customer addAddress(Customer customer) {
        String sql = " INSERT INTO address (address, foreign_zipcodeid) VALUES (?, (select zipcodeid from zipcodes where zipcodeid = (select max(zipcodeid) from zipcodes)))";
        template.update(sql, customer.getAddress());
        return null;
    }
    //SQL STRING SKAL ÆNDRES
    public Customer addCustomer(Customer customer) {
        String sql = "INSERT INTO customers (first_name, last_name, phone_number, email, driver_license, driver_since_date, foreign_addressid) VALUES (?, ?, ?, ?, ?, ?, (select addressid from address where addressid = (select max(addressid) from address)))";
        template.update(sql, customer.getFirstName(), customer.getLastName(), customer.getPhoneNumber(), customer.getEmail(), customer.getDiverLicense(), customer.getDriverSinceDate());
        return null;
    }



}
