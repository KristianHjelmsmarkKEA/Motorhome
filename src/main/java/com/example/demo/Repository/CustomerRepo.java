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


    /*
    Opretter en ny customer, med alle tilhørende informationer i contry, zipcode og adresse tabellerne.
     */
    public int addCustomerAddressZipcodeCountry(Customer customer){
        String sql = "INSERT INTO country (country) VALUES (?)";
        String sql1 = "INSERT INTO zipcodes (zipcode, city, foreign_countryid) VALUES (?, ?, (select countryid from country where countryid = (select max(countryid) from country)))";
        String sql2 = " INSERT INTO address (address, foreign_zipcodeid) VALUES (?, (select zipcodeid from zipcodes where zipcodeid = (select max(zipcodeid) from zipcodes)))";
        String sql3 = "INSERT INTO customers (first_name, last_name, phone_number, email, driver_license, driver_since_date, foreign_addressid) VALUES (?, ?, ?, ?, ?, ?, (select addressid from address where addressid = (select max(addressid) from address)))";

        template.update(sql, customer.getCountry());
        template.update(sql1, customer.getZipcode(), customer.getCity());
        template.update(sql2, customer.getAddress());
        template.update(sql3, customer.getFirstName(), customer.getLastName(), customer.getPhoneNumber(), customer.getEmail(), customer.getDriverLicense(), customer.getDriverSinceDate());

        return returnNewCustomerID();
    }

    /*
    Mapper alle customers informationer fra databasen.
     */
    public List<Customer> fetchAll() {
        String sql = "select * from customers, address, zipcodes, country WHERE zipcodes.foreign_countryid = country.countryid and address.foreign_zipcodeid = zipcodes.zipcodeid and customers.foreign_addressid = address.addressid  ";
        RowMapper<Customer> rowMapper = new BeanPropertyRowMapper<>(Customer.class);
        return template.query(sql, rowMapper);
    }

    /*
    Finder det nyeste oprettet customerID fra databasen.
     */
    public int returnNewCustomerID(){
        String sql = "select * from motorhome.customers where customerid = (select max(customerid) from motorhome.customers);";
        RowMapper<Customer> rowMapper = new BeanPropertyRowMapper<>(Customer.class);
        Customer c = template.queryForObject(sql, rowMapper);
        return c.getCustomerID();
    }

    /*
    Mapper en specifik kundes informationer ud fra customerID
     */
    public Customer findCustomerID(int customerID){
        String sql = "select * from customers, address, zipcodes, country WHERE zipcodes.foreign_countryid = country.countryid and address.foreign_zipcodeid = zipcodes.zipcodeid and customers.foreign_addressid = address.addressid AND customerid = ?";
        RowMapper<Customer> rowMapper = new BeanPropertyRowMapper<>(Customer.class);
        Customer c = template.queryForObject(sql, rowMapper, customerID);
        return c;
    }

    /*
    Opdatere alle en kundes informationer i databasen, hvor instansen c indeholder infromationerne.
     */
    public Customer updateCustomer(Customer c){
        String sql = "UPDATE customers SET first_name = ?, last_name = ?, phone_number = ?, email = ? Where customerid = ?";
        String sql1 = "UPDATE address SET address = ? Where addressid = ?";
        String sql2 = "UPDATE zipcodes SET zipcode = ?, city = ? Where zipcodeid = ?";
        String sql3 = "UPDATE country SET country = ? Where countryid = ?";
        template.update(sql, c.getFirstName(), c.getLastName(), c.getPhoneNumber(), c.getEmail(),
                c.getCustomerID());
        template.update(sql1, c.getAddress(), c.getAddressID());
        template.update(sql2, c.getZipcode(), c.getCity(), c.getZipcodeID());
        template.update(sql3, c.getCountry(), c.getCountryID());
        return null;
    }



}
