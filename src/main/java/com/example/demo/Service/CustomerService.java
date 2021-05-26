package com.example.demo.Service;

import com.example.demo.Model.Customer;
import com.example.demo.Repository.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    CustomerRepo customerRepo;


    public List<Customer> fetchAll(){
        return customerRepo.fetchAll();
    }

    public void addCountry(Customer customer){
        customerRepo.addCountry(customer);
    }

    public void addZipcode(Customer customer){
        customerRepo.addZipcode(customer);
    }

    public void addAddress(Customer customer){
        customerRepo.addAddress(customer);
    }

    public void addCustomer(Customer customer){
        customerRepo.addCustomer(customer);
    }

    public int addCustomerAddressZipcodeCountry(Customer customer){
        return customerRepo.addCustomerAddressZipcodeCountry(customer);
    }

    public Customer findCustomerID(int customerID) { return customerRepo.findCustomerID(customerID); }

    public Customer updateCustomerInformation(int customerID, Customer c) {
        return customerRepo.updateCustomerInformation(customerID, c); }


}
