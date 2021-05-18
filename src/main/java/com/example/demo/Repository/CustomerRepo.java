package com.example.demo.Repository;

import com.example.demo.Model.Customer;
import org.hibernate.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CustomerRepo {

    @Autowired
    JdbcTemplate template;


    public Customer addCountry(Customer customer){
        String sql = "INSERT INTO country (country) VALUES (?)";
        template.update(sql, customer.getCountry());
        return null;
    }
    //SQL STRING SKAL ÆNDRES?
    public Customer addZipcode(Customer customer) {
        String sql = "INSERT INTO zipcodes (zipcode, city) VALUES (?, ?)";
        template.update(sql, customer.getZipcode(), customer.getCity());
        return null;
    }

    //SQL STRING SKAL ÆNDRES
    public Customer addAddress(Customer customer) {
        String sql = " INSERT INTO address (address, zipcodeida) VALUES (?, (select zipcodeid from zipcodes where zipcodeid = (select max(zipcodeid) from zipcodes)))";
        template.update(sql, customer.getAddress());
        return null;
    }
    //SQL STRING SKAL ÆNDRES
    public Customer addCustomer(Customer customer) {
        String sql = "INSERT INTO customers (firstname, mobile_phone, email, driver_licence, driver_since_date, addressidc) VALUES (?, ?, ?, ?, ?, (select addressid from address where addressid = (select max(addressid) from address)))";
        template.update(sql, customer.getFirstName());
        return null;
    }


    public List<Customer> fetchAll() {
        String sql = "select * from customers;";
        RowMapper<Customer> rowMapper = new BeanPropertyRowMapper<>(Customer.class);
        return template.query(sql, rowMapper);
    }


}
