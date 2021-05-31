package com.example.demo.Service;

import com.example.demo.Model.Customer;
import com.example.demo.Repository.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    CustomerRepo customerRepo;


    public List<Customer> fetchAll(){
        return customerRepo.fetchAll();
    }


    public int addCustomerAddressZipcodeCountry(Customer customer){
        return customerRepo.addCustomerAddressZipcodeCountry(customer);
    }

    public Customer findCustomerID(int customerID) { return customerRepo.findCustomerID(customerID); }

    public Customer updateCustomer(Customer c) {
        return customerRepo.updateCustomer(c); }


}
