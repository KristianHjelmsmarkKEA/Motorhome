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


    public void addCountry(Customer customer){
        String sql = "INSERT INTO country (country) VALUES (?)";
        template.update(sql, customer.getCountry());
    }

    public void addZipcode(Customer customer) {
        String sql = "INSERT INTO zipcodes (zipcode, city, foreign_countryid) VALUES (?, ?, (select countryid from country where countryid = (select max(countryid) from country)))";
        template.update(sql, customer.getZipcode(), customer.getCity());
    }

    public void addAddress(Customer customer) {
        String sql = " INSERT INTO address (address, foreign_zipcodeid) VALUES (?, (select zipcodeid from zipcodes where zipcodeid = (select max(zipcodeid) from zipcodes)))";
        template.update(sql, customer.getAddress());
    }

    public void addCustomer(Customer customer) {
        String sql = "INSERT INTO customers (first_name, last_name, phone_number, email, driver_license, driver_since_date, foreign_addressid) VALUES (?, ?, ?, ?, ?, ?, (select addressid from address where addressid = (select max(addressid) from address)))";
        template.update(sql, customer.getFirstName(), customer.getLastName(), customer.getPhoneNumber(), customer.getEmail(), customer.getDriverLicense(), customer.getDriverSinceDate());
    }

    public int addCustomerAddressZipcodeCountry(Customer customer){
        addCountry(customer);
        addZipcode(customer);
        addAddress(customer);
        addCustomer(customer);
        return returnNewCustomerID();
    }

    public List<Customer> fetchAll() {
        String sql = "select * from customers, address, zipcodes, country WHERE zipcodes.foreign_countryid = country.countryid and address.foreign_zipcodeid = zipcodes.zipcodeid and customers.foreign_addressid = address.addressid  ";
        RowMapper<Customer> rowMapper = new BeanPropertyRowMapper<>(Customer.class);
        return template.query(sql, rowMapper);
    }
    public int returnNewCustomerID(){
        String sql = "select * from motorhome.customers where customerid = (select max(customerid) from motorhome.customers);";
        RowMapper<Customer> rowMapper = new BeanPropertyRowMapper<>(Customer.class);
        Customer c = template.queryForObject(sql, rowMapper);
        return c.getCustomerID();
    }

    public Customer findCustomerID(int customerID){
        String sql = "select * from customers, address, zipcodes, country WHERE zipcodes.foreign_countryid = country.countryid and address.foreign_zipcodeid = zipcodes.zipcodeid and customers.foreign_addressid = address.addressid AND customerid = ?";
        RowMapper<Customer> rowMapper = new BeanPropertyRowMapper<>(Customer.class);
        Customer c = template.queryForObject(sql, rowMapper, customerID);
        return c;
    }

    public Customer updateCustomerInformation(int customerID, Customer c){
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
