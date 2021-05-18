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

    public Customer addCountry(Customer customer){
        return customerRepo.addCountry(customer);
    }

    public Customer addZipcode(Customer customer){
        return customerRepo.addZipcode(customer);
    }

    public Customer addAddress(Customer customer){
        return customerRepo.addAddress(customer);
    }

    public Customer addCustomer(Customer customer){
        return customerRepo.addCustomer(customer);
    }


}
